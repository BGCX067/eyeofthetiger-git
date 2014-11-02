/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * ProjectView.java
 *
 * Created on Nov 25, 2011, 6:56:33 PM
 */
package eyeofthetiger.gui;

import eyeofthetiger.model.Course;
import eyeofthetiger.model.Participant;
import eyeofthetiger.model.Project;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;
import javax.swing.AbstractAction;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author christophe
 */
public class ProjectView extends javax.swing.JPanel {

    protected static final ResourceBundle MSGS = ResourceBundle.getBundle(MBAction.classBundleBaseName(ProjectView.class));

    
    /** Creates new form ProjectView */
    public ProjectView(Project project) {
        this.project = project;
        initComponents();
        
        //add all course
        for(Course c: project.getCourse()) {
            addCourseView(c);
        }
        
        TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(jTableParticipants.getModel());
        jTableParticipants.setRowSorter(sorter);
        
        jTableParticipants.getModel().addTableModelListener(new TableModelListener() {
            public void tableChanged(TableModelEvent e) {
                projectDatasChanged();
            }
        });
        
        JPopupMenu menu = new JPopupMenu();
        JMenuItem item = new JMenuItem(new AbstractAction("Supprimer les participants sélectionné") {
            public void actionPerformed(ActionEvent e) {
                deleteParticipantsAction();
            }           
        });
        menu.add(item);
        item = new JMenuItem(new AbstractAction("Générer les dossards ...") {
            public void actionPerformed(ActionEvent e) {
                exportDossardAction();
            } 
        });
        menu.addSeparator();
        menu.add(item);
        jTableParticipants.setComponentPopupMenu(menu);
        
        
        jTabbedPane1.setTitleAt(0, "Projet: " + project.getName());
    }

    
    private Project project;
    public Project getProject() {
        return project;
    }    
    
    private void projectDatasChanged() {
        if(project != null) {
            project.setDirty(true);
        }        
    }
    
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        bindingGroup = new org.jdesktop.beansbinding.BindingGroup();

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableParticipants = new javax.swing.JTable();
        jButtonImport = new javax.swing.JButton();
        jButtonAdd = new javax.swing.JButton();
        jButtonNewCourse = new javax.swing.JButton();

        setName("Form"); // NOI18N

        jTabbedPane1.setName("jTabbedPane1"); // NOI18N

        jPanel1.setName("jPanel1"); // NOI18N

        jScrollPane1.setName("jScrollPane1"); // NOI18N

        jTableParticipants.setName("jTableParticipants"); // NOI18N

        org.jdesktop.beansbinding.ELProperty eLProperty = org.jdesktop.beansbinding.ELProperty.create("${project.participants}");
        org.jdesktop.swingbinding.JTableBinding jTableBinding = org.jdesktop.swingbinding.SwingBindings.createJTableBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, eLProperty, jTableParticipants);
        org.jdesktop.swingbinding.JTableBinding.ColumnBinding columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${numero}"));
        columnBinding.setColumnName("Numero");
        columnBinding.setColumnClass(String.class);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${nom}"));
        columnBinding.setColumnName("Nom");
        columnBinding.setColumnClass(String.class);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${prenom}"));
        columnBinding.setColumnName("Prenom");
        columnBinding.setColumnClass(String.class);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${groupe}"));
        columnBinding.setColumnName("Groupe");
        columnBinding.setColumnClass(String.class);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${renseignements}"));
        columnBinding.setColumnName("Renseignements");
        columnBinding.setColumnClass(String.class);
        jTableBinding.setSourceUnreadableValue(java.util.Collections.emptyList());
        bindingGroup.addBinding(jTableBinding);
        jTableBinding.bind();org.jdesktop.beansbinding.Binding binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, org.jdesktop.beansbinding.ELProperty.create("${participantSelection}"), jTableParticipants, org.jdesktop.beansbinding.BeanProperty.create("selectedElements"));
        bindingGroup.addBinding(binding);

        jTableParticipants.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTableParticipantsKeyPressed(evt);
            }
        });
        jScrollPane1.setViewportView(jTableParticipants);
        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("gui/resources/ProjectView"); // NOI18N
        jTableParticipants.getColumnModel().getColumn(0).setHeaderValue(bundle.getString("ProjectView.jTableParticipants.columnModel.title0")); // NOI18N
        jTableParticipants.getColumnModel().getColumn(1).setHeaderValue(bundle.getString("ProjectView.jTableParticipants.columnModel.title1")); // NOI18N
        jTableParticipants.getColumnModel().getColumn(2).setHeaderValue(bundle.getString("ProjectView.jTableParticipants.columnModel.title2")); // NOI18N
        jTableParticipants.getColumnModel().getColumn(3).setHeaderValue(bundle.getString("ProjectView.jTableParticipants.columnModel.title3")); // NOI18N
        jTableParticipants.getColumnModel().getColumn(4).setHeaderValue(bundle.getString("ProjectView.jTableParticipants.columnModel.title4")); // NOI18N

        jButtonImport.setIcon(new javax.swing.ImageIcon(getClass().getResource("/eyeofthetiger/gui/resources/contact_new.png"))); // NOI18N
        jButtonImport.setText(MSGS.getString("jButtonImport.text")); // NOI18N
        jButtonImport.setToolTipText(bundle.getString("ProjectView.jButtonImport.toolTipText")); // NOI18N
        jButtonImport.setName("jButtonImport"); // NOI18N
        jButtonImport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonImportActionPerformed(evt);
            }
        });

        jButtonAdd.setIcon(new javax.swing.ImageIcon(getClass().getResource("/eyeofthetiger/gui/resources/add_user.png"))); // NOI18N
        jButtonAdd.setText(MSGS.getString("jButtonAdd.text")); // NOI18N
        jButtonAdd.setToolTipText(bundle.getString("ProjectView.jButtonAdd.toolTipText")); // NOI18N
        jButtonAdd.setName("jButtonAdd"); // NOI18N
        jButtonAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAddActionPerformed(evt);
            }
        });

        jButtonNewCourse.setIcon(new javax.swing.ImageIcon(getClass().getResource("/eyeofthetiger/gui/resources/course_new.png"))); // NOI18N
        jButtonNewCourse.setText(MSGS.getString("jButtonNewCourse.text")); // NOI18N
        jButtonNewCourse.setToolTipText(bundle.getString("ProjectView.jButtonNewCourse.toolTipText")); // NOI18N
        jButtonNewCourse.setName("jButtonNewCourse"); // NOI18N
        jButtonNewCourse.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonNewCourseActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 657, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButtonAdd)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButtonImport)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 72, Short.MAX_VALUE)
                .addComponent(jButtonNewCourse)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonAdd)
                    .addComponent(jButtonImport)
                    .addComponent(jButtonNewCourse))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 375, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab(MSGS.getString("jPanel1.TabConstraints.tabTitle"), jPanel1); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
        );

        jTabbedPane1.getAccessibleContext().setAccessibleName(bundle.getString("ProjectView.jTabbedPane1.AccessibleContext.accessibleName")); // NOI18N

        bindingGroup.bind();
    }// </editor-fold>//GEN-END:initComponents

    
    protected List<Participant> participantSelection = new LinkedList<Participant>();
    public List<Participant> getParticipantSelection() {
        return participantSelection;
    }    
    public void setParticipantSelection(List<Participant> s) {
        participantSelection = s;
    }    
    
    
private void jTableParticipantsKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTableParticipantsKeyPressed
    if(evt.getKeyCode() == KeyEvent.VK_DELETE && getParticipantSelection() != null && getParticipantSelection().size()>0) {
        deleteParticipantsAction();
        evt.consume();
    }
}//GEN-LAST:event_jTableParticipantsKeyPressed

public void exportDossardAction() {
    if(project != null) {
        ExportDossard frame = new ExportDossard(project,getParticipantSelection());
        frame.setLocationByPlatform(true);
        frame.setLocationRelativeTo(this);
        frame.setVisible(true);
    }
}

private void deleteParticipantsAction() {
    if(getParticipantSelection() != null && getParticipantSelection().size()>0) {
        if(JOptionPane.showConfirmDialog(this, "Souhaitez vous réélement supprimer ces participants ?", "Supprimer des participants", JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
            jTableParticipants.setUpdateSelectionOnSort(false);

            List<Participant> list = new LinkedList<Participant>(getParticipantSelection());
            project.getParticipants().removeAll(list);

            jTableParticipants.setUpdateSelectionOnSort(true);
            jTableParticipants.getSelectionModel().clearSelection();
        }    
    }
}

private void jButtonImportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonImportActionPerformed
    ImportParticipantWizard wizard = new ImportParticipantWizard();
    OkCancelDialog dlg = new OkCancelDialog(null, true);
    dlg.setMainPanel(wizard);
    dlg.setLocationRelativeTo(this);
    dlg.setVisible(true);
    if(dlg.getReturnStatus() == OkCancelDialog.RET_OK) {
        project.getParticipants().addAll(wizard.getParticipants());
    }
}//GEN-LAST:event_jButtonImportActionPerformed

private void jButtonAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAddActionPerformed
    project.getParticipants().add(new Participant());
}//GEN-LAST:event_jButtonAddActionPerformed

private void jButtonNewCourseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonNewCourseActionPerformed
    createNewCourse();
}//GEN-LAST:event_jButtonNewCourseActionPerformed



public void createNewCourse() {
    DialogCreateOrEditCourse dlg = new DialogCreateOrEditCourse(project,null,null, true);
    dlg.setTitle("Creation d'une nouvelle course");
    dlg.setLocationRelativeTo(this);
    dlg.setVisible(true);
    if(dlg.getReturnStatus() == DialogCreateOrEditCourse.RET_OK) {
        Course c = new Course(dlg.getNomCourse(), project);
        c.getParticipants().addAll(dlg.getRightParticipants());
        project.addCourse(c);
        CourseView view = addCourseView(c);
        jTabbedPane1.setSelectedComponent(view);
    }    
}

public void editCourse(Course c) {
    DialogCreateOrEditCourse dlg = new DialogCreateOrEditCourse(project,c,null, true);
    dlg.setTitle("Modification des participants à la course " + c.getName());
    dlg.setLocationRelativeTo(this);
    dlg.setVisible(true);
    if(dlg.getReturnStatus() == DialogCreateOrEditCourse.RET_OK) {
        c.getParticipants().clear();
        c.getParticipants().addAll(dlg.getRightParticipants());
    }   
}

public void exportCourse(Course c) throws Exception {
    for(int i = 0; i < jTabbedPane1.getTabCount(); i++) {
        Component tabC = jTabbedPane1.getComponentAt(i);
        if(tabC instanceof CourseView) {
            CourseView cv = (CourseView)tabC;
            if(cv.getCourse() == c) {
                cv.saveAsXLS();
                break;
            }
        }
    }    
}

private CourseView addCourseView(Course c) {
    CourseView view = new CourseView(c);
    jTabbedPane1.addTab("Course: " + c.getName(), view);    
    return view;
}

public void removeCourseView(Course c) {
    for(int i = 0; i < jTabbedPane1.getTabCount(); i++) {
        Component tabC = jTabbedPane1.getComponentAt(i);
        if(tabC instanceof CourseView) {
            CourseView cv = (CourseView)tabC;
            if(cv.getCourse() == c) {
                jTabbedPane1.removeTabAt(i);
                break;
            }
        }
    }
}

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonAdd;
    private javax.swing.JButton jButtonImport;
    private javax.swing.JButton jButtonNewCourse;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable jTableParticipants;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    // End of variables declaration//GEN-END:variables
}
