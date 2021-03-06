/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eyeofthetiger;

import com.jgoodies.looks.LookUtils;
import com.jgoodies.looks.windows.WindowsLookAndFeel;
import eyeofthetiger.gui.DialogCreateNewProject;
import eyeofthetiger.gui.DossardDesignPanel;
import eyeofthetiger.gui.ProjectView;
import eyeofthetiger.gui.TestBarcodeFrame;
import eyeofthetiger.gui.Utils;
import eyeofthetiger.model.Course;
import eyeofthetiger.model.Participant;
import eyeofthetiger.model.Project;
import eyeofthetiger.utils.PDFDossardGenerator;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.text.Caret;
import javax.swing.text.DefaultCaret;

/**
 *
 * @author christophe
 */
public class EyeOfTheTigerMainFrame extends javax.swing.JFrame {

    /**
     * Creates new form EyeOfTheTigerMainFrame
     */
    public EyeOfTheTigerMainFrame() {
        initComponents();
        
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                exitAskSave();
            }
        });

        saveTimer = new Timer(2000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                doSave(true);
            }
        });
        saveTimer.start();         
        
        jTextPaneInfos.setContentType("text/html");
        jTextPaneInfos.setEditable(false);
        Caret caret = jTextPaneInfos.getCaret();
        if(caret instanceof DefaultCaret) {
            DefaultCaret dcaret = (DefaultCaret) caret;
            dcaret.setUpdatePolicy(DefaultCaret.NEVER_UPDATE);
        }
        jTextPaneInfos.setText(InfoHTML());
    }

    
    private Timer saveTimer = null;
    
    
    public static String InfoHTML() {
        String html = new Scanner(EyeOfTheTiger.class.getResourceAsStream("/eyeofthetiger/infos.html"),"UTF-8").useDelimiter("\\A").next();
        try {
            //patch for tiger icon path
            //String tigerPath = EyeOfTheTiger.class.getResource("/eyeofthetiger/gui/resources/tiger.png" ).getPath().toString();
            //String tigerPath = new URL("classpath://eyeofthetiger/gui/resources/tiger.png").toString();
            
            String tigerPath = "" +ClassLoader.getSystemResource("eyeofthetiger/gui/resources/tiger.png");
            
            html = html.replace("tiger_path_url", tigerPath);
        }
        catch(Exception e) {
            Logger.getAnonymousLogger().log(Level.SEVERE, e.getMessage(), e);
        }
        return html;
    }    
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        mainPanel = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextPaneInfos = new javax.swing.JTextPane();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        menuBar = new javax.swing.JMenuBar();
        fileMenu = new javax.swing.JMenu();
        newProjectMenuItem = new javax.swing.JMenuItem();
        loadProjectMenuItem = new javax.swing.JMenuItem();
        saveProjectMenuItem = new javax.swing.JMenuItem();
        jSeparator3 = new javax.swing.JPopupMenu.Separator();
        exitMenuItem = new javax.swing.JMenuItem();
        jMenuCourses = new javax.swing.JMenu();
        jMenuItemNouvelleCourse = new javax.swing.JMenuItem();
        jSeparator6 = new javax.swing.JPopupMenu.Separator();
        jMenuExportCourse = new javax.swing.JMenu();
        jSeparator7 = new javax.swing.JPopupMenu.Separator();
        jMenuEditCourse = new javax.swing.JMenu();
        jSeparator5 = new javax.swing.JPopupMenu.Separator();
        jMenuDeleteCourse = new javax.swing.JMenu();
        helpMenu = new javax.swing.JMenu();
        jMenuItemOptionsDossards = new javax.swing.JMenuItem();
        jMenuItemGenerateDossard = new javax.swing.JMenuItem();
        jSeparator2 = new javax.swing.JPopupMenu.Separator();
        jMenuItemTestCodeBar = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        aboutMenuItem = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Eye of the Tiger");

        jTextPaneInfos.setContentType("text/html"); // NOI18N
        jScrollPane1.setViewportView(jTextPaneInfos);

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/eyeofthetiger/gui/resources/archive_insert.png"))); // NOI18N
        jButton1.setText("Nouveau projet");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/eyeofthetiger/gui/resources/archive_extract.png"))); // NOI18N
        jButton2.setText("Charger un projet");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/eyeofthetiger/gui/resources/keduca.png"))); // NOI18N
        jButton3.setText("Tester le lecteur de code barre");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout mainPanelLayout = new javax.swing.GroupLayout(mainPanel);
        mainPanel.setLayout(mainPanelLayout);
        mainPanelLayout.setHorizontalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(mainPanelLayout.createSequentialGroup()
                        .addComponent(jButton1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 293, Short.MAX_VALUE)
                        .addComponent(jButton3)))
                .addContainerGap())
        );
        mainPanelLayout.setVerticalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton2)
                    .addComponent(jButton3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 507, Short.MAX_VALUE)
                .addContainerGap())
        );

        fileMenu.setMnemonic('f');
        fileMenu.setText("Fichier");
        fileMenu.addMenuListener(new javax.swing.event.MenuListener() {
            public void menuCanceled(javax.swing.event.MenuEvent evt) {
            }
            public void menuDeselected(javax.swing.event.MenuEvent evt) {
            }
            public void menuSelected(javax.swing.event.MenuEvent evt) {
                fileMenuMenuSelected(evt);
            }
        });

        newProjectMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_N, java.awt.event.InputEvent.CTRL_MASK));
        newProjectMenuItem.setMnemonic('o');
        newProjectMenuItem.setText("Nouveau projet");
        newProjectMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                newProjectMenuItemActionPerformed(evt);
            }
        });
        fileMenu.add(newProjectMenuItem);

        loadProjectMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_O, java.awt.event.InputEvent.CTRL_MASK));
        loadProjectMenuItem.setMnemonic('s');
        loadProjectMenuItem.setText("Charger un projet");
        loadProjectMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loadProjectMenuItemActionPerformed(evt);
            }
        });
        fileMenu.add(loadProjectMenuItem);

        saveProjectMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.CTRL_MASK));
        saveProjectMenuItem.setMnemonic('a');
        saveProjectMenuItem.setText("Sauvegarder le projet");
        saveProjectMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveProjectMenuItemActionPerformed(evt);
            }
        });
        fileMenu.add(saveProjectMenuItem);
        fileMenu.add(jSeparator3);

        exitMenuItem.setMnemonic('x');
        exitMenuItem.setText("Quitter");
        exitMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitMenuItemActionPerformed(evt);
            }
        });
        fileMenu.add(exitMenuItem);

        menuBar.add(fileMenu);

        jMenuCourses.setText("Courses");
        jMenuCourses.addMenuListener(new javax.swing.event.MenuListener() {
            public void menuCanceled(javax.swing.event.MenuEvent evt) {
            }
            public void menuDeselected(javax.swing.event.MenuEvent evt) {
            }
            public void menuSelected(javax.swing.event.MenuEvent evt) {
                jMenuCoursesMenuSelected(evt);
            }
        });

        jMenuItemNouvelleCourse.setText("Nouvelle course");
        jMenuItemNouvelleCourse.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemNouvelleCourseActionPerformed(evt);
            }
        });
        jMenuCourses.add(jMenuItemNouvelleCourse);
        jMenuCourses.add(jSeparator6);

        jMenuExportCourse.setText("Export XLS des résultats ...");
        jMenuCourses.add(jMenuExportCourse);
        jMenuCourses.add(jSeparator7);

        jMenuEditCourse.setText("Modifier les participants ...");
        jMenuCourses.add(jMenuEditCourse);
        jMenuCourses.add(jSeparator5);

        jMenuDeleteCourse.setText("Supprimer la course ...");
        jMenuDeleteCourse.addMenuListener(new javax.swing.event.MenuListener() {
            public void menuCanceled(javax.swing.event.MenuEvent evt) {
            }
            public void menuDeselected(javax.swing.event.MenuEvent evt) {
            }
            public void menuSelected(javax.swing.event.MenuEvent evt) {
                jMenuDeleteCourseMenuSelected(evt);
            }
        });
        jMenuCourses.add(jMenuDeleteCourse);

        menuBar.add(jMenuCourses);

        helpMenu.setMnemonic('h');
        helpMenu.setText("Outils");
        helpMenu.addMenuListener(new javax.swing.event.MenuListener() {
            public void menuCanceled(javax.swing.event.MenuEvent evt) {
            }
            public void menuDeselected(javax.swing.event.MenuEvent evt) {
            }
            public void menuSelected(javax.swing.event.MenuEvent evt) {
                helpMenuMenuSelected(evt);
            }
        });

        jMenuItemOptionsDossards.setText("Options des dossards");
        jMenuItemOptionsDossards.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemOptionsDossardsActionPerformed(evt);
            }
        });
        helpMenu.add(jMenuItemOptionsDossards);

        jMenuItemGenerateDossard.setText("Générer les dossards");
        jMenuItemGenerateDossard.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemGenerateDossardActionPerformed(evt);
            }
        });
        helpMenu.add(jMenuItemGenerateDossard);
        helpMenu.add(jSeparator2);

        jMenuItemTestCodeBar.setText("Tester le lecteur de code barre");
        jMenuItemTestCodeBar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemTestCodeBarActionPerformed(evt);
            }
        });
        helpMenu.add(jMenuItemTestCodeBar);
        helpMenu.add(jSeparator1);

        aboutMenuItem.setMnemonic('a');
        aboutMenuItem.setText("A propos ...");
        aboutMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                aboutMenuItemActionPerformed(evt);
            }
        });
        helpMenu.add(aboutMenuItem);

        menuBar.add(helpMenu);

        setJMenuBar(menuBar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(mainPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(mainPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void exitMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitMenuItemActionPerformed
        exitAskSave();
    }//GEN-LAST:event_exitMenuItemActionPerformed

    
    public void exitAskSave() {
        if(currectProject != null) {
            int result = JOptionPane.showConfirmDialog(this, "Souhaitez vous enregistrer le projet avant de quitter ?", "Enregistrer avant de quitter ?", JOptionPane.YES_NO_CANCEL_OPTION);
            if(result == JOptionPane.CANCEL_OPTION) {
                return;
            }

            if(result == JOptionPane.OK_OPTION) {
                doSave(false);
            }
        }
        System.exit(0);
    }
    
    private void newProjectMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newProjectMenuItemActionPerformed
        newProject();
    }//GEN-LAST:event_newProjectMenuItemActionPerformed

    private void loadProjectMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_loadProjectMenuItemActionPerformed
        loadProject();
    }//GEN-LAST:event_loadProjectMenuItemActionPerformed

    private void saveProjectMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveProjectMenuItemActionPerformed
        doSave(false);
    }//GEN-LAST:event_saveProjectMenuItemActionPerformed

    private void jMenuItemGenerateDossardActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemGenerateDossardActionPerformed
        if(currectProjectView != null) {
            currectProjectView.exportDossardAction();
        }
    }//GEN-LAST:event_jMenuItemGenerateDossardActionPerformed

    private void jMenuItemTestCodeBarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemTestCodeBarActionPerformed
        testCodeBarre();
    }//GEN-LAST:event_jMenuItemTestCodeBarActionPerformed

    private void jMenuDeleteCourseMenuSelected(javax.swing.event.MenuEvent evt) {//GEN-FIRST:event_jMenuDeleteCourseMenuSelected
        
    }//GEN-LAST:event_jMenuDeleteCourseMenuSelected

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        testCodeBarre();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        newProject();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        loadProject();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void aboutMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_aboutMenuItemActionPerformed
        EyeOfTheTigerAboutBox dlg = new EyeOfTheTigerAboutBox(this, true);
        dlg.setLocationRelativeTo(this);
        dlg.setVisible(true);
    }//GEN-LAST:event_aboutMenuItemActionPerformed

    private void helpMenuMenuSelected(javax.swing.event.MenuEvent evt) {//GEN-FIRST:event_helpMenuMenuSelected
        if(currectProject == null) {
            jMenuItemGenerateDossard.setEnabled(false);
            jMenuItemOptionsDossards.setEnabled(false);
        }
        else {
            jMenuItemGenerateDossard.setEnabled(true);
            jMenuItemOptionsDossards.setEnabled(true);
        }
    }//GEN-LAST:event_helpMenuMenuSelected

    private void fileMenuMenuSelected(javax.swing.event.MenuEvent evt) {//GEN-FIRST:event_fileMenuMenuSelected
        if(currectProject == null) {
            saveProjectMenuItem.setEnabled(false);
        }
        else {
            saveProjectMenuItem.setEnabled(true);
        }
    }//GEN-LAST:event_fileMenuMenuSelected

    private void jMenuItemNouvelleCourseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemNouvelleCourseActionPerformed
        if(currectProjectView != null) {
           currectProjectView.createNewCourse();
        }
    }//GEN-LAST:event_jMenuItemNouvelleCourseActionPerformed

    private void jMenuItemOptionsDossardsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemOptionsDossardsActionPerformed
        if(currectProjectView != null) {
            PDFDossardGenerator pdfGenertor = new PDFDossardGenerator();
            pdfGenertor.setExportName(true);
            pdfGenertor.setExportGroup(true);
            pdfGenertor.setExportRenseignement(true);
            pdfGenertor.setExportLogos(true);
            pdfGenertor.setMarginCm(currectProject.getOptions().getMarginCm());
            pdfGenertor.setLogoLeft(new File(currectProject.getPath(),currectProject.getOptions().getLogoLeft()).getAbsolutePath());
            pdfGenertor.setLogoRight(new File(currectProject.getPath(),currectProject.getOptions().getLogoRight()).getAbsolutePath());
            pdfGenertor.setPdfBackground(new File(currectProject.getPath(),currectProject.getOptions().getPdfBackground()).getAbsolutePath());
            pdfGenertor.setLogoLeftWidth(currectProject.getOptions().getLogoLeftWidth());
            pdfGenertor.setLogoRightWidth(currectProject.getOptions().getLogoRightWidth());
            DossardDesignPanel ddp = new DossardDesignPanel();
            ddp.setPDFDossardGenerator(pdfGenertor);
            ddp.setParticipants(new LinkedList<Participant>(currectProject.getParticipants()));
            if(Utils.ShowOkCancelDialog(this, "Options des dossards", ddp,new Dimension(640, 480))) {
                currectProject.getOptions().setMarginCm(pdfGenertor.getMarginCm());
                currectProject.getOptions().setLogoLeftWidth(pdfGenertor.getLogoLeftWidth());
                currectProject.getOptions().setLogoRightWidth(pdfGenertor.getLogoRightWidht());
                try {
                    File oldFile;
                    File newFile;
                    oldFile = new File(currectProject.getPath(),currectProject.getOptions().getLogoLeft());
                    newFile = new File(pdfGenertor.getLogoLeft());
                    if(!oldFile.equals(newFile)) {
                        if(newFile.exists() && newFile.isFile()) {
                            File copyNewFile = new File(currectProject.getPath(),newFile.getName());
                            eyeofthetiger.utils.Utils.CopyFile(newFile, copyNewFile);
                            currectProject.getOptions().setLogoLeft(copyNewFile.getName());
                        }
                        else {
                            currectProject.getOptions().setLogoLeft("");
                        }
                        
                    }
                    oldFile = new File(currectProject.getPath(),currectProject.getOptions().getLogoRight());
                    newFile = new File(pdfGenertor.getLogoRight());
                    if(!oldFile.equals(newFile)) {
                        if(newFile.exists() && newFile.isFile()) {
                            File copyNewFile = new File(currectProject.getPath(),newFile.getName());
                            eyeofthetiger.utils.Utils.CopyFile(newFile, copyNewFile);
                            currectProject.getOptions().setLogoRight(copyNewFile.getName());
                        }
                        else {
                            currectProject.getOptions().setLogoRight("");
                        }
                    }
                    oldFile = new File(currectProject.getPath(),currectProject.getOptions().getPdfBackground());
                    newFile = new File(pdfGenertor.getPdfBackground());
                    if(!oldFile.equals(newFile)) {
                        if(newFile.exists() && newFile.isFile()) {
                            File copyNewFile = new File(currectProject.getPath(),newFile.getName());
                            eyeofthetiger.utils.Utils.CopyFile(newFile, copyNewFile);
                            currectProject.getOptions().setPdfBackground(copyNewFile.getName());
                        }
                        else {
                            currectProject.getOptions().setPdfBackground("");
                        }
                    }
                    
                    currectProject.saveOptions();
                }
                catch(Exception e) {
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(this, "Impossible de sauvegarder les options: " +e.getMessage());
                }
            }
        }
    }//GEN-LAST:event_jMenuItemOptionsDossardsActionPerformed

    private void jMenuCoursesMenuSelected(javax.swing.event.MenuEvent evt) {//GEN-FIRST:event_jMenuCoursesMenuSelected
        manageCourseMenuItems();
    }//GEN-LAST:event_jMenuCoursesMenuSelected

 
    private void newProject() {
        DialogCreateNewProject dlg = new DialogCreateNewProject(this, true);
        dlg.setLocationRelativeTo(this);
        dlg.setVisible(true);
        if(dlg.getReturnStatus() == DialogCreateNewProject.RET_OK) {
            dlg.getPath().mkdirs();
            Project p = new Project(dlg.getName(), dlg.getPath());
            setCurrentProject(p);
        }        
    }
    
    private void loadProject() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(Utils.GetMyDocumentsFolder());
        fileChooser.addChoosableFileFilter(new FileFilter() {
            @Override
            public boolean accept(File f) {
                if(f.isDirectory()) {
                    return true;
                    /* TODO: this hide everything !!
                    File participants = new File(f, Project.PROJECT_PARTICIPANT_CSV_FILE);
                    if(participants.exists()) {
                        return true;
                    }*/
                }
                return false;
            }
            @Override
            public String getDescription() {
                return "Charger un projet";
            }
        });
        fileChooser.setCurrentDirectory(Utils.GetMyDocumentsFolder());
        //fileChooser.setCurrentDirectory(new File("E:\\devel\\EyeOfTheTigerApp\\Projets"));
        fileChooser.setDialogTitle("Choisissez le répertoire d'un projet");
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        if(fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            File folder = fileChooser.getSelectedFile();
            try {
                Project p = Project.LoadFrom(folder);
                if(p == null) {
                    JOptionPane.showMessageDialog(this,"Erreur de chargement du projet: " + folder.getAbsolutePath() ,"Erreur de chargement d'un projet",JOptionPane.ERROR_MESSAGE);
                    return;
                }
                setCurrentProject(p);
            }
            catch(Exception e) {
                JOptionPane.showMessageDialog(this,"Erreur de chargement du projet: " + folder.getAbsolutePath() ,"Erreur de chargement d'un projet",JOptionPane.ERROR_MESSAGE);
            }
        }        
    }
    
    private void testCodeBarre() {
        TestBarcodeFrame f = new TestBarcodeFrame(currectProject);
        f.setLocationByPlatform(true);
        f.setLocationRelativeTo(this);
        f.pack();
        f.setVisible(true);        
    }
 
private void manageCourseMenuItems() {
    jMenuExportCourse.removeAll();
    jMenuDeleteCourse.removeAll();
    jMenuEditCourse.removeAll();
    
    
    if(currectProject == null) {
        jMenuItemNouvelleCourse.setEnabled(false);
    }
    else {
        jMenuItemNouvelleCourse.setEnabled(true);
    }
    
    if(currectProject != null && currectProject.getCourse().size() >0) {
        jMenuDeleteCourse.setEnabled(true);
        jMenuEditCourse.setEnabled(true);
        jMenuExportCourse.setEnabled(true);
        for(final Course c : currectProject.getCourse()) {
            JMenuItem itemDelete = new JMenuItem(new AbstractAction(c.getName()) {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        try {
                            if(JOptionPane.showConfirmDialog(null 
                                                            , "Etes-vous certains de vouloir\nsupprimer la course\n'" + c.getName() + "' ?"
                                                            , "Suppression de la course '" + c.getName() + "'"
                                                            , JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                                currectProject.deleteCourse(c);
                                if(currectProjectView != null) {
                                    currectProjectView.removeCourseView(c);
                                }
                            }
                        }
                        catch(Exception ex) {
                            JOptionPane.showMessageDialog(EyeOfTheTigerMainFrame.this,"Erreur lors de la suppression de la course '" + c.getName() + "': " +ex.getMessage() ,"Erreur",JOptionPane.ERROR_MESSAGE);
                        }
                    }
                });
            jMenuDeleteCourse.insert(itemDelete, 0);
            
            JMenuItem itemEdit = new JMenuItem(new AbstractAction(c.getName()) {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        try {
                            if(currectProjectView != null) {
                                currectProjectView.editCourse(c);
                            }
                        }
                        catch(Exception ex) {
                            JOptionPane.showMessageDialog(EyeOfTheTigerMainFrame.this,"Erreur lors de la modification de la course '" + c.getName() + "': " +ex.getMessage() ,"Erreur",JOptionPane.ERROR_MESSAGE);
                        }
                    }
                });
            jMenuEditCourse.insert(itemEdit, 0);    
            
            JMenuItem itemExport = new JMenuItem(new AbstractAction(c.getName()) {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        try {
                            if(currectProjectView != null) {
                                currectProjectView.exportCourse(c);
                            }
                        }
                        catch(Exception ex) {
                            JOptionPane.showMessageDialog(EyeOfTheTigerMainFrame.this,"Erreur lors de l'export de la course '" + c.getName() + "': " +ex.getMessage() ,"Erreur",JOptionPane.ERROR_MESSAGE);
                        }
                    }
                });
            jMenuExportCourse.insert(itemExport, 0);            
        }
    }
    else {
        jMenuDeleteCourse.setEnabled(false);
        jMenuEditCourse.setEnabled(false);
        jMenuExportCourse.setEnabled(false);
    }
}    
   
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /*
         * Set the Nimbus look and feel
         */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /*
         * If Nimbus (introduced in Java SE 6) is not available, stay with the
         * default look and feel. For details see
         * http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
        /*
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(EyeOfTheTigerMainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(EyeOfTheTigerMainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(EyeOfTheTigerMainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(EyeOfTheTigerMainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        */
        //</editor-fold>

        
        try {
            UIManager.put("ClassLoader", LookUtils.class.getClassLoader());
            UIManager.setLookAndFeel(new WindowsLookAndFeel());
        } catch (Exception e) {
            e.printStackTrace();
        }        
        
        /*
         * Create and display the form
         */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                EyeOfTheTigerMainFrame eottmf = new EyeOfTheTigerMainFrame();
                boolean debug = false;
                if(debug) {
                    try {
                        Project p = Project.LoadFrom(new File("e:\\tmp\\cross01"));
                        eottmf.setCurrentProject(p);
                    }
                    catch(Exception e) {
                        e.printStackTrace();
                    }
                }
                eottmf.setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem aboutMenuItem;
    private javax.swing.JMenuItem exitMenuItem;
    private javax.swing.JMenu fileMenu;
    private javax.swing.JMenu helpMenu;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JMenu jMenuCourses;
    private javax.swing.JMenu jMenuDeleteCourse;
    private javax.swing.JMenu jMenuEditCourse;
    private javax.swing.JMenu jMenuExportCourse;
    private javax.swing.JMenuItem jMenuItemGenerateDossard;
    private javax.swing.JMenuItem jMenuItemNouvelleCourse;
    private javax.swing.JMenuItem jMenuItemOptionsDossards;
    private javax.swing.JMenuItem jMenuItemTestCodeBar;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JPopupMenu.Separator jSeparator2;
    private javax.swing.JPopupMenu.Separator jSeparator3;
    private javax.swing.JPopupMenu.Separator jSeparator5;
    private javax.swing.JPopupMenu.Separator jSeparator6;
    private javax.swing.JPopupMenu.Separator jSeparator7;
    private javax.swing.JTextPane jTextPaneInfos;
    private javax.swing.JMenuItem loadProjectMenuItem;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JMenuItem newProjectMenuItem;
    private javax.swing.JMenuItem saveProjectMenuItem;
    // End of variables declaration//GEN-END:variables






    private JLabel quietErrorLabel;
    
    private void setQuietErrorMessage(String msg) {
        if(msg == null || msg.isEmpty()) {
            quietErrorLabel.setVisible(false);
        }
        else {
            quietErrorLabel.setText(msg);
            quietErrorLabel.setVisible(true);
        }
    }
    


    private ProjectView currectProjectView = null;
    private Project currectProject = null;
    
    private void setCurrentProject(Project p) {
        currectProject = p;
        this.mainPanel.removeAll();
        this.mainPanel.setLayout(new BorderLayout());        
        currectProjectView = new ProjectView(p);
        this.mainPanel.add(currectProjectView,BorderLayout.CENTER);
        
        
        quietErrorLabel = new JLabel();
        quietErrorLabel.setOpaque(true);
        quietErrorLabel.setBackground(Color.red.brighter());
        quietErrorLabel.setVisible(false);
        this.mainPanel.add(quietErrorLabel,BorderLayout.NORTH);
        
        this.mainPanel.updateUI();
        
        if(currectProject != null) {
            this.setTitle("Eye of the Tiger - " + currectProject.getName() + "  ( " + currectProject.getPath() + " )");
        }
    }

    
             
    private void doSave(boolean quiet) {
        if(currectProject == null) {
            return;
        }

        saveTimer.stop();

        if(currectProject.isDirty()) {
            try {
                currectProject.save();
                setQuietErrorMessage(null);
            }
            catch(Exception e) {
                if(quiet) {
                    setQuietErrorMessage("Impossible d'enregistrer le projet: " + e.getMessage());
                }
                else {
                    JOptionPane.showMessageDialog(null,"Impossible d'enregistrer le projet: " + e.getMessage());
                }
            }
        }
        for(Course c : currectProject.getCourse()) {
            if(c.isDirty()) {
                try {
                    c.save(); 
                    setQuietErrorMessage(null);
                }
                catch(Exception e) {
                    if(quiet) {
                        setQuietErrorMessage("Impossible d'enregistrer la course '" + c.getName() + "' :" + e.getMessage());   
                    }
                    else {                    
                        JOptionPane.showMessageDialog(null,"Impossible d'enregistrer la course '" + c.getName() + "' :" + e.getMessage());
                    }
                }
            }
        }
        
        saveTimer.restart();
    }



}
