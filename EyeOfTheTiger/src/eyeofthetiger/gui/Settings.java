/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eyeofthetiger.gui;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Properties;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;

/**
 *
 * @author christophe
 */
public class Settings {

    
    protected boolean autoSave;
    public static final String PROP_AUTOSAVE = "autoSave";

    public boolean isAutoSave() {
        return autoSave;
    }

    public void setAutoSave(boolean autoSave) {
        boolean oldAutoSave = this.autoSave;
        this.autoSave = autoSave;
        propertyChangeSupport.firePropertyChange(PROP_AUTOSAVE, oldAutoSave, autoSave);
    }
    private PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        propertyChangeSupport.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        propertyChangeSupport.removePropertyChangeListener(listener);
    }

    
    private static File GetSettingsFilePath() throws Exception {
        String appFolder = "Application";
        String settingFileName = "eyeofthetiger.settings";
        File folder = new File(System.getProperty("user.dir"), appFolder);
        folder.mkdirs();
        return new File(folder,settingFileName);
    }
    
    
   private void read() throws Exception {
       
    FileOutputStream out = new FileOutputStream(GetSettingsFilePath());
    Properties props = new Properties();
    props.setProperty("autosave", String.valueOf(isAutoSave()));

    props.store(out, "---No Comment---");
    out.close();
   }

   private void write(File configFile) throws Exception {
   
    FileInputStream in = new FileInputStream(GetSettingsFilePath());
    Properties props = new Properties();    
    props.load(in);
    
    setAutoSave(Boolean.parseBoolean(props.getProperty("autosave")));
    
    in.close();       
   }    
    
}
