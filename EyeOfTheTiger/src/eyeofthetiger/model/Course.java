/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eyeofthetiger.model;

import com.csvreader.CsvReader;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Properties;
import org.jdesktop.observablecollections.ObservableCollections;
import org.jdesktop.observablecollections.ObservableList;
import org.jdesktop.observablecollections.ObservableListListener;
import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;
import org.joda.time.format.PeriodFormatter;
import org.joda.time.format.PeriodFormatterBuilder;

/**
 *
 * @author christophe
 */
public class Course {

    Project project;
    
    public Course(String name, Project p) {
        project = p;
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
        participations.addObservableListListener(new ObservableListListener() {
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
    
    protected String name;
    public String getName() {
        return name;
    }
    
    protected DateTime startDate;
    public static final String PROP_STARTDATE = "startDate";
    public DateTime getStartDate() {
        return startDate;
    }

    protected DateTime endDate;
    public static final String PROP_ENDDATE = "endDate";
    public DateTime getEndDate() {
        return endDate;
    }

    private boolean dirty = true;
    public boolean isDirty() {
        return dirty;
    }
    public void setDirty(boolean b) {
        dirty = b;
    }
    
    
   protected ObservableList<Participant> participants = ObservableCollections.observableList(new ArrayList<Participant>());
   public ObservableList<Participant> getParticipants() {
        return participants;
   }        

   public Participant findByNumero(String numero) {
       if(numero == null) {
           return null;
       }
       
       for(Participant p : participants) {
           if(numero.equals(p.getNumero())) {
               return p;
           }
       }
       return null;
   }
   
   protected List<Participation> synchronizedParticipations = Collections.synchronizedList(new LinkedList<Participation>());
   protected ObservableList<Participation> participations = ObservableCollections.observableList(synchronizedParticipations);
   public ObservableList<Participation> getParticipations() {
        return participations;
   }    
   

   public void depart() {
        this.startDate = eyeofthetiger.utils.Utils.NowTrimedToCentiseconds();
        this.endDate = null;
        propertyChangeSupport.firePropertyChange(PROP_STARTDATE, null, startDate);
        propertyChangeSupport.firePropertyChange(PROP_ENDDATE, null, endDate);
        setDirty(true);
   }

   public void fin() {
        this.endDate = eyeofthetiger.utils.Utils.NowTrimedToCentiseconds();
        propertyChangeSupport.firePropertyChange(PROP_ENDDATE, null, endDate);
        setDirty(true);
   }

   public void redemare() {
        this.endDate = null;
        propertyChangeSupport.firePropertyChange(PROP_ENDDATE, null, endDate);
        setDirty(true);
   }
   
   
   public void arrivee(Participant participant) {
       Participation p = new Participation(participant, getStartDate(), eyeofthetiger.utils.Utils.NowTrimedToCentiseconds());
       getParticipations().add(p);
       setDirty(true);
   }
   
   
   private PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);
   public void addPropertyChangeListener(PropertyChangeListener listener) {
       propertyChangeSupport.addPropertyChangeListener(listener);
   }
   public void removePropertyChangeListener(PropertyChangeListener listener) {
       propertyChangeSupport.removePropertyChangeListener(listener);
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

   
   private static DateTimeFormatter createPreciseHourFormat() {
       return DateTimeFormat.forPattern("HH:mm:ss:SSS").withLocale(Locale.UK);
   }
   private static DateTimeFormatter createDatePreciseHourFormat() {
       return DateTimeFormat.forPattern("yyyy/MM/dd HH:mm:ss:SSS").withLocale(Locale.UK);
   }

   
   private static PeriodFormatter createChronoFormat() {
       return new PeriodFormatterBuilder().printZeroAlways()
                                        .appendHours().appendSeparator(":")
                                        .appendMinutes().appendSeparator(":")
                                        .appendSeconds().appendSeparator(":")
                                        .appendMillis()
                                        .toFormatter().withLocale(Locale.UK);
   }

   public void save() throws Exception {
       File folder = project.getCoursePath(this);
       folder.mkdirs();
       File resultatsFileTmp = new File(folder,"resultats.csv.tmp");
       save(resultatsFileTmp);
       File resultatsFile = new File(folder,"resultats.csv");
       if(resultatsFile.exists()) {
           resultatsFile.delete();
       }
       resultatsFileTmp.renameTo(resultatsFile);
       
       File configFileTmp = new File(folder, "config.ini.tmp");
       writeConfig(configFileTmp);
       File configFile = new File(folder,"config.ini");
       if(configFile.exists()) {
           configFile.delete();
       }
       configFileTmp.renameTo(configFile);
       
       setDirty(false);
   }
   
   private void save(File resultatsFile) throws Exception {
       PrintWriter pw = new PrintWriter(resultatsFile,"UTF-8");
       
       HashSet<Participant> _participants = new HashSet<Participant>(getParticipants());
       List<Participation> _participations = new LinkedList<Participation>(getParticipations());
       
       DateTimeFormatter dateFormat = createDateFormat();
       DateTimeFormatter hourFormat = createHourFormat();
       DateTimeFormatter preciseHourFormat = createPreciseHourFormat();
       PeriodFormatter chronoFormat = createChronoFormat();
       
       pw.write("Numero;Nom;Prenom;Groupe;Renseignements;DateInscription;HeureInscription;DateDepart;HeureDepart;DateArrivee;HeureArrive;Temps;TempsEnDixiemeDeSecondes");
       pw.println();
       
       for(Participation p : _participations) {
           pw.write(p.getParticipant().getNumero());
           pw.append(';');
           pw.write(p.getParticipant().getNom());
           pw.append(';');
           pw.write(p.getParticipant().getPrenom());
           pw.append(';');
           pw.write(p.getParticipant().getGroupe());
           pw.append(';');
           pw.write(p.getParticipant().getRenseignements());
           pw.append(';');
           pw.write(dateFormat.print(p.getParticipant().getDateInscription()));
           pw.append(';');
           pw.write(hourFormat.print(p.getParticipant().getDateInscription()));
           pw.append(';');
           pw.write(dateFormat.print(p.getDepart()));
           pw.append(';');
           pw.write(preciseHourFormat.print(p.getDepart()));
           pw.append(';');
           pw.write(dateFormat.print(p.getArrivee()));
           pw.append(';');
           pw.write(preciseHourFormat.print(p.getArrivee()));
           pw.append(';');
           // duration
           Duration duration = new Duration(p.getDepart(),p.getArrivee());
           pw.write(chronoFormat.print(duration.toPeriod()));
           pw.append(';');
           // duration in decisecond
           pw.write(Long.toString(eyeofthetiger.utils.Utils.MillisecondsToDeciseconds(duration.getMillis())));
           
           pw.println();
          
           _participants.remove(p.getParticipant());
       }

       // they didn't compete
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
           pw.append(';');
           pw.append(';');
           pw.append(';');
           pw.append(';');
           pw.append(';');
           pw.append(';');
           pw.println();
       }   
       
       pw.close();
   }
   
   public static Course LoadFrom(Project project, File folder) throws Exception {
       String name = folder.getName();
       if(! name.startsWith("course_")) {
           throw new Exception("Erreur: Le repertoire de course ne commence pas par 'course_'.");
       }
       
       name = name.substring("course_".length());
       File resultats = new File(folder,"resultats.csv");
       if(!resultats.exists()) {
           throw new Exception("Erreur: pas de fichier resultat.");
       }
       
        DateTimeFormatter dateHourFormat = createDateHourFormat();
        DateTimeFormatter datePreciseHourFormat = createDatePreciseHourFormat();
        PeriodFormatter chronoFormat = createChronoFormat();

        //"Numero;Nom;Prenom;Groupe;DateInscription;HeureInscription;DateDepart;HeureDepart;DateArrivee;HeureArrive;Temps"
        CsvReader reader = new CsvReader(resultats.getAbsolutePath(),';',Charset.forName("UTF-8"));
        reader.readHeaders();
        Course course = new Course(name, project);        
        while(reader.readRecord()) {
            int i = 0;
            Participant participant = new Participant();
            participant.setNumero(reader.get(i));
            i++;
            participant.setNom(reader.get(i));
            i++;
            participant.setPrenom(reader.get(i));
            i++;
            participant.setGroupe(reader.get(i));
            i++;
            participant.setRenseignements(reader.get(i));
            i++;
            String dateHour = reader.get(i) + " " + reader.get(i+1);
            i++;i++;
            participant.setDateInscription(dateHourFormat.parseDateTime(dateHour));
            course.getParticipants().add(participant);

            //try to create participation
            String str = (reader.get(i) + " " + reader.get(i+1)).trim();
            i++;i++;
            DateTime startDate = null;
            if(! str.isEmpty()) {
                startDate = datePreciseHourFormat.parseDateTime(str);
            }
            str = (reader.get(i) + " " + reader.get(i+1)).trim();
            i++;i++;
            DateTime endDate = null;
            if(! str.isEmpty()) {
                endDate = datePreciseHourFormat.parseDateTime(str);
            }

            if(startDate != null && endDate != null) {
                Participation participation = new Participation(participant, startDate, endDate);
                course.getParticipations().add(participation);

                //Check time:
                //TODO
                /*
                str = reader.get(10);
                Period duration = chronoFormat.parsePeriod(str);
                Period testDuration = duration.minus(new Duration(startDate,endDate).toPeriod());
                if( (testDuration.getHours() + testDuration.getMinutes() + testDuration.getMillis()) != 0) {
                    throw new Exception("Erreur lors de la vérification du temps d'un participant");
                }
                 */
            }
            
        }
        reader.close();
        
        File configFile = new File(folder,"config.ini");
        course.readConfig(configFile);
            
       return course;
   }
   
   
   private void writeConfig(File configFile) throws Exception {
    DateTimeFormatter formatter = ISODateTimeFormat.basicDateTime();
    
    FileOutputStream out = new FileOutputStream(configFile);
    Properties props = new Properties();
    if(getStartDate() != null) {
        props.setProperty("startDate", formatter.print(getStartDate()));
    }
    if(getEndDate() != null) {
        props.setProperty("endDate", formatter.print(getEndDate()));
    }
    
    props.store(out, "---No Comment---");
    out.close();
   }

   private void readConfig(File configFile) throws Exception {
    DateTimeFormatter formatter = ISODateTimeFormat.basicDateTime();
    
    FileInputStream in = new FileInputStream(configFile);
    Properties props = new Properties();    
    props.load(in);
    
    if(props.containsKey("startDate")) {
        startDate = formatter.parseDateTime(props.getProperty("startDate"));
    }
    
    if(props.containsKey("endDate")) {
        endDate = formatter.parseDateTime(props.getProperty("endDate"));
    }
    
    in.close();       
   }
   
   
   

   
   
}
