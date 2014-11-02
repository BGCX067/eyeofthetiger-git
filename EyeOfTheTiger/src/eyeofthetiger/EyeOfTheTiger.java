/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eyeofthetiger;

import eyeofthetiger.utils.Utils;
import org.joda.time.Duration;

/**
 *
 * @author christophe
 */
public class EyeOfTheTiger {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        //testFormat();
        EyeOfTheTigerMainFrame.main(args);
    }
    
    private static void testFormat() {
            long hours = 3;
            long minutes = 31;
            long seconds = 5;
            java.util.Formatter formatter = new java.util.Formatter();
            formatter.format("%1$02dh %1$02dmn %1$02ds", hours, minutes, seconds);
            System.out.println(formatter.toString());
            
            System.out.println(Long.toString(454464646484l));
    }
    
    
    private static void testTrimDurationToCentiseconds() {
        Duration duration = new Duration(123456729);
        System.out.println(duration.getMillis());
        duration = Utils.TrimDurationToCentiseconds(duration);
        System.out.println(duration.getMillis());
    }
}
