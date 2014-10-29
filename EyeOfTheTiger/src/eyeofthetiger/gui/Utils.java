/*
 * Copyright (c) 2012 Megabyte Limited - www.megabyte.net
 * Distributed under the LGPL license.
 */
package eyeofthetiger.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.lang.reflect.Method;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileSystemView;
import net.java.balloontip.BalloonTip;
import net.java.balloontip.utils.FadingUtils;
import net.java.balloontip.utils.ToolTipUtils;

/**
 * Some utility functions.
 * @author rac
 */
public class Utils {


    /**
     * Adds a property change listener to the given bean by dynamically searching
     * for the method, since there is no interface for classes with property change
     * listeners.
     *
     * @param bean Object to add listener to.
     * @param prop Property name to listen on.
     * @param listener Listener class
     * @return True if the listener was added. False otherwise (eg, could not find method)
     */
    public static boolean addPropertyChangeListener( Object bean, String prop, PropertyChangeListener listener)  {
        if(bean==null) throw new IllegalArgumentException("bean cannot be null");
        try {
            Class cls = bean.getClass();
            try {
                Method m = cls.getMethod("addPropertyChangeListener", String.class, PropertyChangeListener.class);
                m.invoke(bean, prop, listener);
                return true;
            } catch (NoSuchMethodException ignored) {
            }
            try {
                Method m = cls.getMethod("addPropertyChangeListener", PropertyChangeListener.class);
                m.invoke(bean, prop, new WrappedListener(prop, listener));
                return true;
            } catch (NoSuchMethodException ignored) {
            }

            return false;
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
    /**
     * Remove a property change listener.
     * @see #addPropertyChangeListener(java.lang.Object, java.lang.String, java.beans.PropertyChangeListener)
     */
    public static boolean removePropertyChangeListener( Object bean, String prop, PropertyChangeListener listener) {
        if(bean==null) throw new IllegalArgumentException("bean cannot be null");
        try {
            Class cls = bean.getClass();
            try {
                Method m = cls.getMethod("removePropertyChangeListener", String.class, PropertyChangeListener.class);
                m.invoke(bean, prop, listener);
                return true;
            } catch (NoSuchMethodException ignored) {
            }
            try {
                Method m = cls.getMethod("removePropertyChangeListener", PropertyChangeListener.class);
                m.invoke(bean, prop, new WrappedListener(prop,listener));
                m.invoke(bean, prop, listener);
                return true;
            } catch (NoSuchMethodException ignored) {
            }

            return false;
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    /**
     * Adds a property change listener to the given bean by dynamically searching
     * for the method, since there is no interface for classes with property change
     * listeners.
     *
     * @param bean Object to add listener to.
     * @param listener Listener class
     * @return True if the listener was added. False otherwise (eg, could not find method)
     */
    public static boolean addPropertyChangeListener( Object bean, PropertyChangeListener listener)  {
        if(bean==null) throw new IllegalArgumentException("bean cannot be null");
        try {
            Class cls = bean.getClass();
            try {
                Method m = cls.getMethod("addPropertyChangeListener", PropertyChangeListener.class);
                m.invoke(bean, listener);
                return true;
            } catch (NoSuchMethodException ignored) {
            }
            return false;
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
    /**
     * Remove a property change listener.
     * @see #addPropertyChangeListener(java.lang.Object, java.lang.String, java.beans.PropertyChangeListener)
     */
    public static boolean removePropertyChangeListener( Object bean, PropertyChangeListener listener) {
        if(bean==null) throw new IllegalArgumentException("bean cannot be null");
        try {
            Class cls = bean.getClass();
            try {
                Method m = cls.getMethod("removePropertyChangeListener", PropertyChangeListener.class);
                m.invoke(bean, listener);
                return true;
            } catch (NoSuchMethodException ignored) {
            }

            return false;
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    /**
     * In case a class only contains an addPropertyChangeListener method without
     * a property name, this wraps a listener and filters out other properties.
     */
    private static class WrappedListener implements PropertyChangeListener {
        private final String prop;
        private final PropertyChangeListener listener;

        public WrappedListener(String prop, PropertyChangeListener listener) {
            this.prop = prop;
            this.listener = listener;
        }

        @Override
        public void propertyChange(PropertyChangeEvent evt) {
            if(evt.getPropertyName().equals(prop)) {
                listener.propertyChange(evt);
            }
        }

        //<editor-fold defaultstate="collapsed" desc=" internals ">
        @Override
        public boolean equals(Object obj) {
            if (obj == null) {
                return false;
            }
            if (getClass() != obj.getClass()) {
                return false;
            }
            final WrappedListener other = (WrappedListener) obj;
            if ((this.prop == null) ? (other.prop != null) : !this.prop.equals(other.prop)) {
                return false;
            }
            if (this.listener != other.listener && (this.listener == null || !this.listener.equals(other.listener))) {
                return false;
            }
            return true;
        }

        @Override
        public int hashCode() {
            int hash = 7;
            hash = 43 * hash + (this.prop != null ? this.prop.hashCode() : 0);
            hash = 43 * hash + (this.listener != null ? this.listener.hashCode() : 0);
            return hash;
        }
        //</editor-fold>

    }

    
    
    final static ResourceBundle rb = ResourceBundle.getBundle("eyeofthetiger.version");
    public static String getVersionProperty(String propToken) {
        String msg = "";
        try {
            
            msg = rb.getString(propToken);
        } 
        catch (MissingResourceException e) {
            Logger.getAnonymousLogger().log(Level.SEVERE,e.getMessage(),e);
        }
        return msg;
    }     
    
    
    public static boolean ShowOkCancelDialog(Frame parent,String title, JPanel panel,Dimension size) {
        final boolean b[] = new boolean[] {false};
        final JDialog dlg = new JDialog(parent, title);
        if(size != null) {
            dlg.setSize(size);
        }
        dlg.setLocationByPlatform(true);
        dlg.setLocationRelativeTo(parent);
        dlg.getContentPane().setLayout(new BorderLayout());
        JPanel okCancelPanel = new JPanel();
        okCancelPanel.setLayout(new FlowLayout());
        JButton ok = new JButton("Ok");
        ok.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                b[0] = true;
                dlg.setVisible(false);
            }
        });
        JButton cancel = new JButton("Annuler");
        cancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                b[0] = false;
                dlg.setVisible(false);
            }
        });
        okCancelPanel.add(ok);
        okCancelPanel.add(cancel);
        dlg.add(okCancelPanel,BorderLayout.SOUTH);
        dlg.add(panel,BorderLayout.CENTER);
        dlg.setModal(true);
        dlg.pack();
        if(size != null) {
            dlg.setSize(size);
        }
        dlg.setVisible(true);
        return b[0];
    }
    
    
    public static void BalloonHelp(String msg, JComponent ... components) {
        for(JComponent c : components) {
            BalloonTip myBalloonTip = new BalloonTip(c, msg);
            FadingUtils.fadeInBalloon(myBalloonTip, null, 500, 24);
            myBalloonTip.setCloseButton(null);
            ToolTipUtils.balloonToToolTip(myBalloonTip, 500, 15000); 
        }
    }
    
    
    
    public static File GetMyDocumentsFolder() {
         JFileChooser fr = new JFileChooser();
         FileSystemView fw = fr.getFileSystemView();
         return fw.getDefaultDirectory();
    }

    
    public static FileFilter PDF_FILE_FILTER = new FileFilter() {
            @Override
            public boolean accept(File f) {
                if(f!=null) {
                    return f.getName().toLowerCase().endsWith(".pdf");
                }
                return false;
            }

            @Override
            public String getDescription() {
               return "Fichier PDF";
            }
        };
    
    public static FileFilter IMAGE_FILE_FILTER = new FileFilter() {
            @Override
            public boolean accept(File f) {
                if(f!=null) {
                    String fileName = f.getName().toLowerCase();
                    if(fileName.endsWith(".jpg") 
                       || fileName.endsWith(".jpeg")
                       || fileName.endsWith(".png")
                       || fileName.endsWith(".gif")
                       || fileName.endsWith(".bmp")) {
                        return true;
                    }
                }
                return false;
            }

            @Override
            public String getDescription() {
               return "Fichier image (png,jpg,gif,bmp)";
            }
        };    

}
