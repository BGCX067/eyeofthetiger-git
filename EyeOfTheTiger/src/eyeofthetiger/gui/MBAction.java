/*
 * Copyright (c) 2012 Megabyte Limited - www.megabyte.net
 * Distributed under the LGPL license.
 */
package eyeofthetiger.gui;

import java.awt.event.ActionEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractAction;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.KeyStroke;
import org.jdesktop.application.Action;
//import org.jdesktop.application.PublicMnemonicText;

/**
 * This is a concrete Action class that reads its settings from a resource
 * bundle in the same format used by SAF.
 *
 * @author rac
 */
public class MBAction extends AbstractAction {

    private final static Logger log = Logger.getLogger(MBAction.class.getName());

    public static final String TEXT_KEY = "Text";
    public static final String CODE_KEY = "Code";
    public static final String FORM_KEY = "Form";
    public static final String RESB_KEY = "ResourceBundle";
    public static final String PROP_KEY = "Properties";

    private Method actionMethod;

    private ClassLoader classLoader;

    /**
     * Property change listener to propagate the enabled property.
     */
    private PropertyChangeListener enabledListener = new PropertyChangeListener() {
        public void propertyChange(PropertyChangeEvent evt) {
            MBAction.this.setEnabled( (Boolean)evt.getNewValue() );
        }
    };
    /**
     * Property change listener to propagate the selected property.
     */
    private PropertyChangeListener selectedListener = new PropertyChangeListener() {
        public void propertyChange(PropertyChangeEvent evt) {
            MBAction.this.setSelected( (Boolean)evt.getNewValue() );
        }
    };

    // <editor-fold defaultstate="collapsed" desc=" Property: trace ">
    private StringBuilder trace = new StringBuilder();

    /**
     * Get the trace
     */
    public String getTrace() {
        return trace.toString();
    }

    /**
     * Add a message to the trace
     */
    private void trace(String newValue, Object... args) {
        String old = getTrace();
        final String msg = String.format(newValue,args);
        System.err.println(msg);
        this.trace.append(msg).append("\n");
        firePropertyChange("errors", old, getTrace());
    }// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc=" Property: form ">
    /**
     * Get the "form" - the swing component being designed
     *
     * @return the value of form
     */
    public Object getForm() {
        return getValue(FORM_KEY);
    }

    /**
     * Set the "form" - the swing component being designed
     *
     * @param newValue new value of form
     */
    public void setForm(Object newValue) {
        putValue(FORM_KEY, newValue);
    }// </editor-fold>
    // <editor-fold defaultstate="collapsed" desc=" Property: resourceBundle ">
    private ResourceBundle resourceBundle;

    /**
     * Get the value of resourceBundle
     *
     * @return the value of resourceBundle
     */
    public ResourceBundle getResourceBundle() {
        return resourceBundle;
    }

    /**
     * Set the value of resourceBundle
     *
     * @param newValue new value of resourceBundle
     */
    public void setResourceBundle(ResourceBundle newValue) {
        internalSetResourceBundle(newValue);
        init();
    }

    /**
     * Set the value of resourceBundle (does not call init)
     *
     * @param newValue new value of resourceBundle
     */
    public void internalSetResourceBundle(ResourceBundle newValue) {
        if(newValue==null) return;
        ResourceBundle oldValue = this.resourceBundle;
        this.resourceBundle = newValue;
        firePropertyChange("resourceBundle", oldValue, newValue); //NOI18N
    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc=" Property: resourceBundleName ">
    /**
     * Loads and sets the specified resource bundle, UNLESS the resource bundle has already been set.
     *
     * @param newValue new value of resourceBundleName
     */
    public void setResourceBundleName(String newValue) {
        if(getResourceBundle()!=null) {
            trace("resourceBundle already set; ignoring %s", newValue);
            return;
        }
        try {
            if(classLoader==null) classLoader= ClassLoader.getSystemClassLoader();
            ResourceBundle rb = ResourceBundle.getBundle(newValue, Locale.getDefault(), classLoader);
            setResourceBundle(rb);
        } catch (MissingResourceException e) {
            trace("MissingResourceException: %s", e);
        }
        if(getResourceBundle()==null) try {
            classLoader= ClassLoader.getSystemClassLoader();
            ResourceBundle rb = ResourceBundle.getBundle(newValue, Locale.getDefault(), classLoader);
            setResourceBundle(rb);
        } catch (MissingResourceException e) {
            trace("MissingResourceException: %s", e);
        }
    }// </editor-fold>
    // <editor-fold defaultstate="collapsed" desc=" Property: name ">
    /**
     * Get the value of name
     *
     * @return the value of name
     */
    public String getName() {
        return (String)getValue(NAME);
    }

    /**
     * Set the value of name
     *
     * @param newValue new value of name
     */
    public void setName(String newValue) {
        putValue(NAME, newValue);
    }// </editor-fold>
    // <editor-fold defaultstate="collapsed" desc=" Property: code ">
    /**
     * Get the value of code
     *
     * @return the value of code
     */
    public String getCode() {
        return (String)getValue(CODE_KEY);
    }

    /**
     * Set the value of code
     *
     * @param newValue new value of code
     */
    public void setCode(String newValue) {
        putValue(CODE_KEY, newValue);
    }// </editor-fold>
    // <editor-fold defaultstate="collapsed" desc=" Property: selected ">
    /**
     * Get the value of selected
     *
     * @return the value of selected
     */
    public Boolean getSelected() {
        return (Boolean)getValue(SELECTED_KEY);
    }

    /**
     * Set the value of selected
     *
     * @param newValue new value of selected
     */
    public void setSelected(Boolean newValue) {
        putValue(SELECTED_KEY, newValue);
    }// </editor-fold>
    // <editor-fold defaultstate="collapsed" desc=" Property: propertiesFileName ">
    /**
     * Get the value of propertiesFileName
     *
     * @return the value of propertiesFileName
     */
    public String getPropertiesFileName() {
        return (String)getValue(PROP_KEY);
    }

    /**
     * Set the value of propertiesFileName
     *
     * @param stage new value of propertiesFileName
     */
    public void setPropertiesFileName(String newValue) {
        putValue(PROP_KEY, newValue);
    }// </editor-fold>



    @Override
    public Object getValue(String key) {
        final Object ret = super.getValue(key);
        return ret;
    }

    @Override
    public void putValue(String key, Object newValue) {
        trace("%s=%s", key, newValue);
        try {
            super.putValue(key, newValue);
            if (CODE_KEY.equals(key)) {
                init();
            } else if (PROP_KEY.equals(key)) {
                try {
                    setResourceBundle(new PropertyResourceBundle(new FileReader((String)newValue)));
                } catch (IOException ex) {
                    trace("I/O error: %s", getStackTrace(ex));
                }
            } else if (TEXT_KEY.equals(key)) {
                if (newValue != null) {
                    //PublicMnemonicText.configure(this, (String) newValue);
                    throw new UnsupportedOperationException();
                }
                init();
            } else if (FORM_KEY.equals(key)) {
                if (getResourceBundle() == null && newValue != null) {
                    Class cls = newValue.getClass();
                    classLoader = cls.getClassLoader();
                    if(classLoader==null) classLoader= ClassLoader.getSystemClassLoader();
                    try {
                        final String clbn = classBundleBaseName(cls);
                        internalSetResourceBundle(ResourceBundle.getBundle(clbn, Locale.getDefault(), classLoader));
                    } catch (MissingResourceException e) {
                        try {
                        internalSetResourceBundle(ResourceBundle.getBundle(cls.getName(), Locale.getDefault(), classLoader));
                        } catch (MissingResourceException e2) {
                            trace("Error: %s", getStackTrace(e2));
                        }
                    }
                }
                init();
            }
        } catch (Throwable e) {
            trace("Error: %s", getStackTrace(e));
        }
    }

    /**
     * Get a string from a resourcebundle, returning null if it does not exist.
     * @param key
     * @return
     */
    private String getStringNL(String key) {
        try {
            return getResourceBundle().getString(key);
        } catch (MissingResourceException e) {
            return null;
        }
    }
    /**
     * Loads the specified resource value as an icon.
     * @param key Property key value
     * @return Icon, or null if not specified or not found.
     */
    private Icon getIcon(String key) {
        String res = getStringNL(key);
        if(res == null) return null;
        if(res.startsWith("/")) res = res.substring(1);
        if(classLoader==null) classLoader = ClassLoader.getSystemClassLoader();
        URL url = classLoader.getResource(res);
        if(url == null) {
            trace("classloader could not find %s (key=%s)", res, key);
            return null;
        }
        return new ImageIcon(url);
    }
    /**
     * Loads the specified resource value as an integer.
     * @param key Property key value
     * @return Integer, or null if not specified or not found.
     */
    private Integer getInteger(String key) {
        String res = getStringNL(key);
        if(res == null) return null;
        return Integer.valueOf(res);
    }
    /**
     * Loads the specified resource value as a keystroke.
     * @param key Property key value
     * @return Keystroke, or null if not specified or not found.
     */
    private KeyStroke getKeyStroke(String key) {
        String res = getStringNL(key);
        if(res == null) return null;
        return KeyStroke.getKeyStroke(res);
    }
    /**
     * Loads the specified resource value as a key code.
     * @param key Property key value
     * @return Key code, or null if not specified or not found.
     */
    private Integer getKeyCode(String key) {
        KeyStroke ks = getKeyStroke(key);
        if(ks == null) return null;
        return ks.getKeyCode();
    }

    // endless loops are bad.
    private boolean inInit = false;
    /*
     * Init all of the javax.swing.Action properties for the @Action named
     * actionName.
     */
    private void init() {
        if(!inInit)
        try {
            inInit = true;

            String code = getCode();
            if (code == null) {
                trace("init: no code");
                return;
            }


            if (getForm() != null) {
                try {
                    // find the action method
                    actionMethod = getForm().getClass().getMethod(code);
                    // find the @Action annotation.
                    Action ac = actionMethod.getAnnotation(Action.class);
                    String eprop = ac.enabledProperty();
                    if(eprop!=null && !eprop.isEmpty()) {
                        Utils.addPropertyChangeListener(getForm(), eprop, enabledListener);
                    }
                    String sprop = ac.selectedProperty();
                    if(sprop!=null && !sprop.isEmpty()) {
                        Utils.addPropertyChangeListener(getForm(), sprop, selectedListener);
                    }
                } catch (NoSuchMethodException ex) {
                    log.severe(String.format("No method named %s in %s", code, getForm().getClass().getName()));
                } catch (SecurityException ex) {
                    throw new RuntimeException(ex);
                }
            }

            if (getResourceBundle() == null) {
                trace("init: no rb");
                return;
            }

            putValue(TEXT_KEY,getStringNL(code + ".Action.text"));
            putValue(MNEMONIC_KEY,getKeyCode(code + ".Action.mnemonic"));
            putValue(DISPLAYED_MNEMONIC_INDEX_KEY,getInteger(code + ".Action.displayedMnemonicIndex"));
            putValue(ACCELERATOR_KEY, getKeyStroke(code + ".Action.accelerator"));
            Icon icon = getIcon(code + ".Action.icon");
            if (icon != null) {
                putValue(SMALL_ICON,icon);
                putValue(LARGE_ICON_KEY,icon);
            }
            Icon smallIcon = getIcon(code + ".Action.smallIcon");
            if (smallIcon != null) {
                putValue(SMALL_ICON, smallIcon);
            }
            Icon largeIcon = getIcon(code + ".Action.largeIcon");
            if (largeIcon != null) {
                putValue(LARGE_ICON_KEY, largeIcon);
            }

            putValue(SHORT_DESCRIPTION,getStringNL(code + ".Action.shortDescription"));
            putValue(LONG_DESCRIPTION,getStringNL(code + ".Action.longDescription"));
            putValue(ACTION_COMMAND_KEY,getStringNL(code + ".Action.command"));

        } catch (RuntimeException ex) {
            trace("%s", getStackTrace(ex));
        } finally {
            inInit = false;
        }
    }


    /**
     * Invoke the action method.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if(actionMethod == null) {
            log.severe("Action object has no method set.");
            return;
        }
        try {
            actionMethod.invoke(getForm());
        } catch (InvocationTargetException ex) {
            log.log(Level.SEVERE, "Error calling action", ex.getTargetException());
            throw new RuntimeException(ex.getTargetException());
        } catch (Exception ex) {
            log.log(Level.SEVERE, "Error calling action", ex);
            throw new RuntimeException(ex);
        }
    }

    /**
     * Convert the stack trace to a string.
     * @param ex Throwable
     * @return String representation
     */
    private String getStackTrace(Throwable ex) {
        StringWriter sw = new StringWriter();
        ex.printStackTrace(new PrintWriter(sw));
        return sw.toString();
    }


    /**
     * Convert a class name to an eponymous resource bundle in the
     * resources subpackage.  For example, given a class named
     * com.foo.bar.MyClass, the ResourceBundle name would be
     * "com.foo.bar.resources.MyClass"  If MyClass is an inner class,
     * only its "simple name" is used.  For example, given an
     * inner class named com.foo.bar.OuterClass$InnerClass, the
     * ResourceBundle name would be "com.foo.bar.resources.InnerClass".
     * Although this could result in a collision, creating more
     * complex rules for inner classes would be a burden for
     * developers.
     */
    public static String classBundleBaseName(Class cls) {
        String className = cls.getName();
        StringBuilder sb = new StringBuilder();
        int i = className.lastIndexOf('.');
        if (i > 0) {
            sb.append(className.substring(0, i));
            sb.append(".resources."); //NOI18N
            sb.append(cls.getSimpleName());
        } else {
            sb.append("resources."); //NOI18N
            sb.append(cls.getSimpleName());
        }
        return sb.toString();
    }
}
