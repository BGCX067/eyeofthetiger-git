/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eyeofthetiger.utils;

import java.util.Locale;
import org.jdesktop.beansbinding.Converter;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

/**
 *
 * @author christophe
 */
public class DateToStringConverter extends Converter<DateTime,String> {

    static DateTimeFormatter formater = DateTimeFormat.forPattern("yyyy/MM/dd HH:mm:ss").withLocale(Locale.UK);
    
    @Override
    public String convertForward(DateTime value) {
        if(value == null) {
            return null;
        }
        return formater.print(value);
    }

    @Override
    public DateTime convertReverse(String value) {
        if(value == null) {
            return null;
        }
        return formater.parseDateTime(value);
    }
}   
