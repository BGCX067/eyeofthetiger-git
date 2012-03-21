/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eyeofthetiger.model;

import java.util.Locale;
import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.joda.time.format.PeriodFormatter;
import org.joda.time.format.PeriodFormatterBuilder;


/**
 *
 * @author christophe
 */
public class Participation {
    
    public Participation(Participant participant, DateTime depart, DateTime arrivée) {
        this.depart = depart;
        this.arrivee = arrivée;
        this.participant = participant;
    }
    
    protected Participant participant;
    public Participant getParticipant() {
        return participant;
    }

    protected DateTime arrivee;
    public DateTime getArrivee() {
        return arrivee;
    }

    protected DateTime depart;
    public DateTime getDepart() {
        return depart;
    }

    
    public Duration getTemps() {
        if(getDepart() != null && getArrivee() != null) {
            return new Duration(getDepart(),getArrivee());
        }
        return null;
    }    
    

    private static PeriodFormatter formater = new PeriodFormatterBuilder().printZeroAlways()
                                        .appendHours().appendSeparator(":")
                                        .appendMinutes().appendSeparator(":")
                                        .appendSeconds().appendSeparator(":")
                                        .appendMillis()
                                        .toFormatter().withLocale(Locale.UK);
    
    
    public String getDurationAsString() {
        return formater.print((new Duration(depart,arrivee)).toPeriod());
    }
    


}
