/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eyeofthetiger.utils;


import eyeofthetiger.EyeOfTheTiger;
import java.awt.Color;
import java.awt.Font;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.JComponent;
import net.sourceforge.barbecue.BarcodeFactory;
import org.joda.time.DateTime;
import org.joda.time.Duration;

/**
 *
 * @author christophe
 */
public class Utils {
    
    public static void PlaySound(String file) {
        try {
          Clip clip = AudioSystem.getClip();
          AudioInputStream inputStream = AudioSystem.getAudioInputStream(EyeOfTheTiger.class.getResourceAsStream("/eyeofthetiger/"+file));
          clip.open(inputStream);
          clip.start(); 
        } catch (Exception e) {
          e.printStackTrace();
        }
    }    
    

    
    public static void OkSound() {
        PlaySound("ok.wav");
    }

    public static void BadSound() {
        PlaySound("bad.wav");
    }

    
    public static JComponent BarcodeFor(String code) {
        try {
            return BarcodeFactory.createCode128(code);
 	} catch (Exception e) {
            e.printStackTrace();
 	}        
        return null;
    }
    
    
    public static Color LightBlue = new Color(123, 126, 253);
    public static Font BigFont = new Font("Tahoma", Font.BOLD, 24);
    
    
    
    public static Duration TrimDurationToCentiseconds(Duration du) {
        double millis = du.getMillis();
        long trimmed = ((long) Math.rint(millis / 100.0)) * ((long)100);
        return new Duration(trimmed);
    }    
    
    public static DateTime NowTrimedToCentiseconds() {
        DateTime dateTime = new DateTime();
        double millis = dateTime.getMillis();
        long trimmed = ((long) Math.rint(millis / 100.0)) * ((long)100);
        return new DateTime(trimmed);       
    }    

    public static long MillisecondsToDeciseconds(long millis) {
        return (long) Math.rint(millis / 100.0);
    }    
    
    
    public static void CopyFile(File source, File dest) throws IOException {
        InputStream is = null;
        OutputStream os = null;
        try {
            is = new FileInputStream(source);
            os = new FileOutputStream(dest);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = is.read(buffer)) > 0) {
                os.write(buffer, 0, length);
            }
        } finally {
            is.close();
            os.close();
        }
    }
}
 