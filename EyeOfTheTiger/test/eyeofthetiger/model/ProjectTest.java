/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eyeofthetiger.model;

import java.util.Scanner;
import java.io.File;
import org.joda.time.DateTime;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author christophe
 */
public class ProjectTest {
    
    public ProjectTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }



    /**
     * Test of save method, of class Project.
     */
    @Test
    public void testLoadSave() throws Exception {
        File tempFolder = new File(File.createTempFile("foo", "bar").getParentFile(),"testEyeOfTheTigerTest1");
        tempFolder.mkdirs();
        Project project1 = new Project("testEyeOfTheTigerTest1", tempFolder);
        
        Participant participant;
        participant = new Participant();
        for(int i = 0; i <10; i ++) {
            participant.setNom("Jhon" + i);
            participant.setPrenom("Doe" + i);
            participant.setDateInscription(new DateTime());
            participant.setNumero("numero" + i);
            participant.setGroupe("G1");
            project1.getParticipants().add(participant);
        }
        
        project1.save();
        
        //read the project file
        String projectContent1 = ReadFile(new File(project1.getPath(),"participants.csv"));
        
        Course course1 = new Course("courseTMP",project1);
        project1.addCourse(course1);
        course1.getParticipants().addAll(project1.getParticipants());

        course1.depart();
        Thread.sleep(1120);
        course1.arrivee(course1.getParticipants().get(2));
        course1.fin();
        
        project1.setDirty(true);
        course1.setDirty(true);
        project1.save();
        course1.save();
        String courseContent1 = ReadFile(new File(project1.getPath(), "course_courseTMP\\resultats.csv"));

        Project project2 = Project.LoadFrom(project1.getPath());
        project2.setDirty(true);
        project2.getCourse().get(0).setDirty(true);
        project2.save();
        String projectContent2 = ReadFile(new File(project2.getPath(),"participants.csv"));
        String courseContent2 = ReadFile(new File(project1.getPath(), "course_courseTMP\\resultats.csv"));

        boolean result = projectContent1.equals(projectContent2) && courseContent1.equals(courseContent2);
        
        DeleteDir(project1.getPath());
        assertTrue(result);
    }

    private String ReadFile(File f) throws Exception {
        return new Scanner(f).useDelimiter("\\Z").next();          
    }
    
    
    private static boolean DeleteDir(File dir) {
        if (dir.isDirectory()) {
            File[] children = dir.listFiles();
            for(File child : children) {
                boolean success = DeleteDir(child);
                if (!success) {
                    return false;
                }
            }
        }

        // The directory is now empty so delete it
        return dir.delete();
    }    
    
}
