    /*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eyeofthetiger.model;

import com.csvreader.CsvReader;
import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import javax.swing.JOptionPane;
import org.jdesktop.observablecollections.ObservableCollections;
import org.jdesktop.observablecollections.ObservableList;
import org.jdesktop.observablecollections.ObservableListListener;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;


/**
 *
 * @author christophe
 */
public class Project {
    
    public static String PROJECT_OPTIONS_FILE = "options.xml";
    public static String PROJECT_PARTICIPANT_CSV_FILE = "participants.csv";
    public static String PROJECT_PARTICIPANT_CSV_FILE_TMP = "participants.csv.tmp";
    
    public Project(String name, File path) {
        this.path = path;
        this.name = name;
        
        participants.addObservableListListener(new ObservableListListener() {
            public void listElementsAdded(ObservableList list, int index, int length) {
                setDirty(true);
            }
            public void listElementsRemoved(ObservableList list, int index, List oldElements) {
                setDirty(true);
            }
            public void listElementReplaced(ObservableList list, int index, Object oldElement) {
                setDirty(true);
            }
            public void listElementPropertyChanged(ObservableList list, int index) {
                setDirty(true);
            }
        });
    }
    
    protected File path;
    public File getPath() {
        return path;
    }
    
    protected String name;
    public String getName() {
        return name;
    }

    private boolean dirty = true;
    public boolean isDirty() {
        return dirty;
    }
    public void setDirty(boolean b) {
        dirty = b;
    }    
    
    private Options options = new Options();
    public Options getOptions() {
        return options;
    }
    
    protected ObservableList<Participant> participants = ObservableCollections.observableList(new ArrayList<Participant>());
    public ObservableList<Participant> getParticipants() {
        return participants;
    }
    

    private LinkedList<Course> courses = new LinkedList<Course>();
    public List<Course> getCourse() {
        return courses;
    }
    public void addCourse(Course c) {
        courses.add(c);
        setDirty(true);
    }
    public void deleteCourse(Course c) throws Exception {
        // we don't delete the folder: instead we just rename it
        File coursePath = getCoursePath(c);
        File courseParentFolder = coursePath.getParentFile();
        String courseFolderName = coursePath.getName();
        File renamedCoursePath = null;
        int i = 0;
        do {
            renamedCoursePath = new File(courseParentFolder, "deleted" + i + "_" + courseFolderName);
            i++;
        }
        while(renamedCoursePath.exists());

        if(!coursePath.renameTo(renamedCoursePath)) {
            throw new Exception("Impossible de renomer le répertoire: " + coursePath.getAbsolutePath());
        } 

        courses.remove(c);
    }

    
    public File getCoursePath(Course c) {
        return new File(getPath(),"course_" + c.getName());
    }
    
    

   private static DateTimeFormatter createDateFormat() {
       return DateTimeFormat.forPattern("yyyy/MM/dd").withLocale(Locale.UK);
   }
   
   private static DateTimeFormatter createHourFormat() {
       return DateTimeFormat.forPattern("HH:mm").withLocale(Locale.UK);
   }    

   private static DateTimeFormatter createDateHourFormat() {
       return DateTimeFormat.forPattern("yyyy/MM/dd HH:mm").withLocale(Locale.UK);
   }
   
   
   
   public void saveOptions() throws Exception {
        File folder = this.getPath();
        File optionsFile = new File(folder,PROJECT_OPTIONS_FILE);
        XMLEncoder encoder = new XMLEncoder(new FileOutputStream(optionsFile));
        try {
            encoder.writeObject(options);
            encoder.flush();
        }
        catch(Exception e) {
            throw e;
        }
   }
   
   
   public void loadOptions() throws Exception {
        File folder = this.getPath();
        File optionsFile = new File(folder,PROJECT_OPTIONS_FILE);
        if(optionsFile.exists()) {
            XMLDecoder decoder = new XMLDecoder(new FileInputStream(optionsFile));
            try {
                options = (Options)decoder.readObject();
            }
            catch(Exception e) {
                options = new Options();
                throw new Exception("Impossible de charger les options du projets.",e);
            }
        }
        else {
            options = new Options();
        }
   }
   
   public void save()throws Exception {
       File folder = this.getPath();
       File fileTmp = new File(folder,Project.PROJECT_PARTICIPANT_CSV_FILE_TMP);
       save(fileTmp);
       File file = new File(folder,Project.PROJECT_PARTICIPANT_CSV_FILE);
       if(file.exists()) {
           file.delete();
       }
       fileTmp.renameTo(file);
       
       setDirty(false);
   }
   
   private void save(File file) throws Exception {
       PrintWriter pw = new PrintWriter(file, "UTF-8");
       
       HashSet<Participant> _participants = new HashSet<Participant>(getParticipants());
       
       DateTimeFormatter dateFormat = createDateFormat();
       DateTimeFormatter hourFormat = createHourFormat();
       
       pw.write("Numero;Nom;Prenom;Groupe;Renseignements;DateInscription;HeureInscription");
       pw.println();
       
       for(Participant p : _participants) {
           pw.write(p.getNumero());
           pw.append(';');
           pw.write(p.getNom());
           pw.append(';');
           pw.write(p.getPrenom());
           pw.append(';');
           pw.write(p.getGroupe());
           pw.append(';');
           pw.write(p.getRenseignements());
           pw.append(';');
           pw.write(dateFormat.print(p.getDateInscription()));
           pw.append(';');
           pw.write(hourFormat.print(p.getDateInscription()));
           pw.println();
       }

       pw.close();
   }    
    
    
    public static Project LoadFrom(File folder) throws Exception {
        if(!folder.isDirectory()) {
            return null;
        }
        
        //project name is folder name !
        String name = folder.getName();

        DateTimeFormatter dateHourFormat = createDateHourFormat();
        
        //try to load all participants:
        File file = new File(folder , Project.PROJECT_PARTICIPANT_CSV_FILE);
        CsvReader reader = new CsvReader(file.getAbsolutePath(),';',Charset.forName("UTF-8"));
        reader.readHeaders();        
        Project project = new Project(name, folder);        
        while(reader.readRecord()) {
            int i = 0;
            Participant p = new Participant();
            p.setNumero(reader.get(i));
            i++;
            p.setNom(reader.get(i));
            i++;
            p.setPrenom(reader.get(i));
            i++;
            p.setGroupe(reader.get(i));
            i++;
            p.setRenseignements(reader.get(i));
            i++;
            String dateHourString = reader.get(i) + " " + reader.get(i+1);
            i++;i++;
            p.setDateInscription(dateHourFormat.parseDateTime(dateHourString));
            project.getParticipants().add(p);
        }

        // read all course !
        // meaning: folder starting with "course_"
        File[] subfolders = folder.listFiles();
        for(File f : subfolders) {
            if(f.isDirectory() && f.getName().startsWith("course_")) {
                try {
                    Course c = Course.LoadFrom(project,f);
                    project.addCourse(c);
                }
                catch(Exception e) {
                    JOptionPane.showMessageDialog(null,"Erreur de chargement de la course: " + f.getAbsolutePath() ,"Erreur de chargement d'une course",JOptionPane.ERROR_MESSAGE);
                    e.printStackTrace();
                }
            }
        }
        
        
        project.loadOptions();
        return project;
    }
    
}
