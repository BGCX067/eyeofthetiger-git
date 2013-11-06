/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * DialogCreateCourse.java
 *
 * Created on Nov 26, 2011, 2:42:02 PM
 */
package eyeofthetiger.gui;

import eyeofthetiger.model.Course;
import eyeofthetiger.model.Participant;
import eyeofthetiger.model.Project;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;
import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.KeyStroke;
import javax.swing.Timer;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import org.jdesktop.observablecollections.ObservableCollections;
import org.jdesktop.observablecollections.ObservableList;

/**
 *
 * @author christophe
 */
public class DialogCreateOrEditCourse extends javax.swing.JDialog {

    protected static final ResourceBundle MSGS = ResourceBundle.getBundle(MBAction.classBundleBaseName(DialogCreateOrEditCourse.class));
    
    public static final int RET_CANCEL = 0;
    public static final int RET_OK = 1;


    public DialogCreateOrEditCourse(Project p, Course c, java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        project = p;
        course = c;
        leftParticipants.addAll(project.getParticipants());
        
        initComponents();
        
        if(course == null) {
            jTextFieldNom.getDocument().addDocumentListener(new DocumentListener() {
                public void insertUpdate(DocumentEvent e) {
                    check();
                }
                public void removeUpdate(DocumentEvent e) {
                    check();
                }
                public void changedUpdate(DocumentEvent e) {
                    check();
                }
            });
        }
        else {
            jLabel2.setVisible(false);
            jTextFieldNom.setVisible(false);
            
            leftParticipants.removeAll(course.getParticipants());
            rightParticipants.addAll(course.getParticipants());
        }
        
        // Close the dialog when Esc is pressed
        String cancelName = "cancel";
        InputMap inputMap = getRootPane().getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), cancelName);
        ActionMap actionMap = getRootPane().getActionMap();
        actionMap.put(cancelName, new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                doClose(RET_CANCEL);
            }
        });
        
        TableRowSorter<TableModel> sorterAllParticipants = new TableRowSorter<TableModel>(jTableAllParticipants.getModel());
        jTableAllParticipants.setRowSorter(sorterAllParticipants);
        
        TableRowSorter<TableModel> sorterCourseParticipants = new TableRowSorter<TableModel>(jTableCourseParticipants.getModel());
        jTableCourseParticipants.setRowSorter(sorterCourseParticipants);
    }
    
    
    /** @return the return status of this dialog - one of RET_OK or RET_CANCEL */
    public int getReturnStatus() {
        return returnStatus;
    }

    
    public String getNomCourse() {
        return jTextFieldNom.getText().trim();
    }

    private boolean check() {
        if(course == null) {
            File f = new File(project.getPath(),getNomCourse());
            if(f.exists()) {
                error("Course déja existante !");
                return false;
            }

            if(getRightParticipants().size() == 0) {
                error("Vous devez selectionez des participants");
                return false;
            }
        }
        return true;
    }
    
    private Project project;
    private Course course;
    private Timer errorTimer = null;
    
    
    private void error(String message) {
        if(errorTimer == null) {
            errorTimer = new Timer(10000, new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    errorTimer.stop();
                    jLabelError.setVisible(false);
                }
            });            
        }
        errorTimer.setRepeats(false);
        errorTimer.restart();
        
        jLabelError.setText(message);
        jLabelError.setVisible(true);
    }    
            
    
   
    protected ObservableList<Participant> leftParticipants = ObservableCollections.observableList(new ArrayList<Participant>());
    public ObservableList<Participant> getLeftParticipants() {
        return leftParticipants;
    }    

    protected ObservableList<Participant> rightParticipants = ObservableCollections.observableList(new ArrayList<Participant>());
    public ObservableList<Participant> getRightParticipants() {
        return rightParticipants;
    }    
    
    protected List<Participant> leftSelection = new LinkedList<Participant>();
    public List<Participant> getLeftSelection() {
        return leftSelection;
    }    
    public void setLeftSelection(List<Participant> s) {
        leftSelection = s;
    }    

    
    protected List<Participant> rightSelection = new LinkedList<Participant>();
    public List<Participant> getRightSelection() {
        return rightSelection;
    }    
    public void setRightSelection(List<Participant> s) {
        rightSelection = s;
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

        okButton = new javax.swing.JButton();
        cancelButton = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabelError = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jTextFieldNom = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTableAllParticipants = new javax.swing.JTable();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableCourseParticipants = new javax.swing.JTable();
        jButtonAdd = new javax.swing.JButton();
        jButtonRemove = new javax.swing.JButton();

        setName("Form"); // NOI18N
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                closeDialog(evt);
            }
        });

        okButton.setText(MSGS.getString("okButton.text")); // NOI18N
        okButton.setName("okButton"); // NOI18N
        okButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                okButtonActionPerformed(evt);
            }
        });

        cancelButton.setText(MSGS.getString("cancelButton.text")); // NOI18N
        cancelButton.setName("cancelButton"); // NOI18N
        cancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelButtonActionPerformed(evt);
            }
        });

        jPanel1.setName("jPanel1"); // NOI18N
        jPanel1.setLayout(new java.awt.BorderLayout());

        jLabelError.setBackground(new java.awt.Color(255, 204, 204));
        jLabelError.setText(MSGS.getString("jLabelError.text")); // NOI18N
        jLabelError.setName("jLabelError"); // NOI18N
        jLabelError.setOpaque(true);
        jPanel1.add(jLabelError, java.awt.BorderLayout.PAGE_START);

        jPanel2.setName("jPanel2"); // NOI18N

        jLabel2.setText(MSGS.getString("jLabel2.text")); // NOI18N
        jLabel2.setName("jLabel2"); // NOI18N

        jTextFieldNom.setText(MSGS.getString("jTextFieldNom.text")); // NOI18N
        jTextFieldNom.setName("jTextFieldNom"); // NOI18N

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Participants possibles:"));
        jPanel3.setName("jPanel3"); // NOI18N

        jScrollPane2.setName("jScrollPane2"); // NOI18N

        jTableAllParticipants.setName("jTableAllParticipants"); // NOI18N

        org.jdesktop.beansbinding.ELProperty eLProperty = org.jdesktop.beansbinding.ELProperty.create("${leftParticipants}");
        org.jdesktop.swingbinding.JTableBinding jTableBinding = org.jdesktop.swingbinding.SwingBindings.createJTableBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, eLProperty, jTableAllParticipants);
        org.jdesktop.swingbinding.JTableBinding.ColumnBinding columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${numero}"));
        columnBinding.setColumnName("Numero");
        columnBinding.setColumnClass(String.class);
        columnBinding.setEditable(false);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${nom}"));
        columnBinding.setColumnName("Nom");
        columnBinding.setColumnClass(String.class);
        columnBinding.setEditable(false);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${prenom}"));
        columnBinding.setColumnName("Prenom");
        columnBinding.setColumnClass(String.class);
        columnBinding.setEditable(false);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${groupe}"));
        columnBinding.setColumnName("Groupe");
        columnBinding.setColumnClass(String.class);
        columnBinding.setEditable(false);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${renseignements}"));
        columnBinding.setColumnName("Renseignements");
        columnBinding.setColumnClass(String.class);
        columnBinding.setEditable(false);
        bindingGroup.addBinding(jTableBinding);
        jTableBinding.bind();org.jdesktop.beansbinding.Binding binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, org.jdesktop.beansbinding.ELProperty.create("${leftSelection}"), jTableAllParticipants, org.jdesktop.beansbinding.BeanProperty.create("selectedElements"));
        bindingGroup.addBinding(binding);

        jScrollPane2.setViewportView(jTableAllParticipants);
        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("gui/resources/DialogCreateCourse"); // NOI18N
        jTableAllParticipants.getColumnModel().getColumn(0).setHeaderValue(bundle.getString("DialogCreateOrEditCourse.jTableAllParticipants.columnModel.title0")); // NOI18N
        jTableAllParticipants.getColumnModel().getColumn(1).setHeaderValue(bundle.getString("DialogCreateOrEditCourse.jTableAllParticipants.columnModel.title1")); // NOI18N
        jTableAllParticipants.getColumnModel().getColumn(2).setHeaderValue(bundle.getString("DialogCreateOrEditCourse.jTableAllParticipants.columnModel.title2")); // NOI18N
        jTableAllParticipants.getColumnModel().getColumn(3).setHeaderValue(bundle.getString("DialogCreateOrEditCourse.jTableAllParticipants.columnModel.title3")); // NOI18N
        jTableAllParticipants.getColumnModel().getColumn(4).setHeaderValue(bundle.getString("DialogCreateOrEditCourse.jTableAllParticipants.columnModel.title4")); // NOI18N

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 406, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 478, Short.MAX_VALUE)
        );

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder("Participants de la course:"));
        jPanel4.setName("jPanel4"); // NOI18N

        jScrollPane1.setName("jScrollPane1"); // NOI18N

        jTableCourseParticipants.setName("jTableCourseParticipants"); // NOI18N

        eLProperty = org.jdesktop.beansbinding.ELProperty.create("${rightParticipants}");
        jTableBinding = org.jdesktop.swingbinding.SwingBindings.createJTableBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, eLProperty, jTableCourseParticipants);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${numero}"));
        columnBinding.setColumnName("Numero");
        columnBinding.setColumnClass(String.class);
        columnBinding.setEditable(false);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${nom}"));
        columnBinding.setColumnName("Nom");
        columnBinding.setColumnClass(String.class);
        columnBinding.setEditable(false);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${prenom}"));
        columnBinding.setColumnName("Prenom");
        columnBinding.setColumnClass(String.class);
        columnBinding.setEditable(false);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${groupe}"));
        columnBinding.setColumnName("Groupe");
        columnBinding.setColumnClass(String.class);
        columnBinding.setEditable(false);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${renseignements}"));
        columnBinding.setColumnName("Renseignements");
        columnBinding.setColumnClass(String.class);
        bindingGroup.addBinding(jTableBinding);
        jTableBinding.bind();binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, org.jdesktop.beansbinding.ELProperty.create("${rightSelection}"), jTableCourseParticipants, org.jdesktop.beansbinding.BeanProperty.create("selectedElements"));
        bindingGroup.addBinding(binding);

        jScrollPane1.setViewportView(jTableCourseParticipants);
        jTableCourseParticipants.getColumnModel().getColumn(0).setHeaderValue(bundle.getString("DialogCreateOrEditCourse.jTableCourseParticipants.columnModel.title0")); // NOI18N
        jTableCourseParticipants.getColumnModel().getColumn(1).setHeaderValue(bundle.getString("DialogCreateOrEditCourse.jTableCourseParticipants.columnModel.title1")); // NOI18N
        jTableCourseParticipants.getColumnModel().getColumn(2).setHeaderValue(bundle.getString("DialogCreateOrEditCourse.jTableCourseParticipants.columnModel.title2")); // NOI18N
        jTableCourseParticipants.getColumnModel().getColumn(3).setHeaderValue(bundle.getString("DialogCreateOrEditCourse.jTableCourseParticipants.columnModel.title3")); // NOI18N
        jTableCourseParticipants.getColumnModel().getColumn(4).setHeaderValue(bundle.getString("DialogCreateOrEditCourse.jTableCourseParticipants.columnModel.title4")); // NOI18N

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 403, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 478, Short.MAX_VALUE)
        );

        jButtonAdd.setText(MSGS.getString("jButtonAdd.text")); // NOI18N
        jButtonAdd.setName("jButtonAdd"); // NOI18N
        jButtonAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAddActionPerformed(evt);
            }
        });

        jButtonRemove.setText(MSGS.getString("jButtonRemove.text")); // NOI18N
        jButtonRemove.setName("jButtonRemove"); // NOI18N
        jButtonRemove.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonRemoveActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(10, 10, 10)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jButtonRemove, 0, 0, Short.MAX_VALUE)
                            .addComponent(jButtonAdd, javax.swing.GroupLayout.DEFAULT_SIZE, 59, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextFieldNom, javax.swing.GroupLayout.PREFERRED_SIZE, 219, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jTextFieldNom, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(130, 130, 130)
                        .addComponent(jButtonAdd)
                        .addGap(56, 56, 56)
                        .addComponent(jButtonRemove)
                        .addContainerGap())
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        jPanel1.add(jPanel2, java.awt.BorderLayout.CENTER);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(okButton, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cancelButton)
                .addContainerGap())
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {cancelButton, okButton});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cancelButton)
                    .addComponent(okButton))
                .addContainerGap())
        );

        getRootPane().setDefaultButton(okButton);

        bindingGroup.bind();

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void okButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_okButtonActionPerformed
        doClose(RET_OK);
    }//GEN-LAST:event_okButtonActionPerformed
    
    private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelButtonActionPerformed
        doClose(RET_CANCEL);
    }//GEN-LAST:event_cancelButtonActionPerformed

    /** Closes the dialog */
    private void closeDialog(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_closeDialog
        doClose(RET_CANCEL);
    }//GEN-LAST:event_closeDialog

private void jButtonAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAddActionPerformed
    if(leftSelection.isEmpty()) {
        return;
    }

    jTableAllParticipants.setUpdateSelectionOnSort(false);
    jTableCourseParticipants.setUpdateSelectionOnSort(false);
    
    LinkedList<Participant> ps = new LinkedList<Participant>(leftSelection);
    rightParticipants.addAll(ps);
    leftParticipants.removeAll(ps);
    
    jTableAllParticipants.setUpdateSelectionOnSort(true);
    jTableCourseParticipants.setUpdateSelectionOnSort(true);    
    jTableAllParticipants.getSelectionModel().clearSelection();
    jTableCourseParticipants.getSelectionModel().clearSelection();
}//GEN-LAST:event_jButtonAddActionPerformed

private void jButtonRemoveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonRemoveActionPerformed
    if(rightSelection.isEmpty()) {
        return;
    }
    
    jTableAllParticipants.setUpdateSelectionOnSort(false);
    jTableCourseParticipants.setUpdateSelectionOnSort(false);    
    
    LinkedList<Participant> ps = new LinkedList<Participant>(rightSelection);
    rightParticipants.removeAll(ps);
    leftParticipants.addAll(ps);
    
    jTableAllParticipants.setUpdateSelectionOnSort(true);
    jTableCourseParticipants.setUpdateSelectionOnSort(true);    
    jTableAllParticipants.getSelectionModel().clearSelection();
    jTableCourseParticipants.getSelectionModel().clearSelection();    
}//GEN-LAST:event_jButtonRemoveActionPerformed
    
    private void doClose(int retStatus) {
        if(retStatus == RET_OK && !check()) {
            return;
        }
        returnStatus = retStatus;
        setVisible(false);
        dispose();
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton cancelButton;
    private javax.swing.JButton jButtonAdd;
    private javax.swing.JButton jButtonRemove;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabelError;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTableAllParticipants;
    private javax.swing.JTable jTableCourseParticipants;
    private javax.swing.JTextField jTextFieldNom;
    private javax.swing.JButton okButton;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    // End of variables declaration//GEN-END:variables
    private int returnStatus = RET_CANCEL;
}
