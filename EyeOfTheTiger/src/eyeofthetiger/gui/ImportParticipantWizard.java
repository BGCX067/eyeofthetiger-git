/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * ImportParticipantWizard.java
 *
 * Created on Nov 25, 2011, 4:51:38 PM
 */
package eyeofthetiger.gui;

import eyeofthetiger.model.Participant;
import fileimport.CellValue;
import fileimport.MappingSupport;
import fileimport.ui.FileImportWizardPanel;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;
import javax.swing.Timer;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import org.jdesktop.observablecollections.ObservableCollections;
import org.jdesktop.observablecollections.ObservableList;



public class ImportParticipantWizard extends javax.swing.JPanel {

    protected static final ResourceBundle MSGS = ResourceBundle.getBundle(MBAction.classBundleBaseName(ImportParticipantWizard.class));
    
    private FileImportWizardPanel fileImport;
    private MappingSupport mappingSupport;
    
    public ImportParticipantWizard() {
        initComponents();
        jLabelError.setVisible(false);
        jTableParticipant.setAutoCreateRowSorter(true);

        mappingSupport = new MappingSupport();
        mappingSupport.addItem("numero","Numéro");
        mappingSupport.addItem("nom","Nom");
        mappingSupport.addItem("prenom","Prénom");
        mappingSupport.addItem("groupe","Groupe");
        mappingSupport.addItem("renseignement","Renseignement");
        
        mappingSupport.addObserver(new Observer() {
            public void update(Observable o, Object arg) {
                doImport();
            }
        });
        
        fileImport = new FileImportWizardPanel();
        fileImport.setMappingSupport(mappingSupport);
        jPanelFileImport.setLayout(new BorderLayout());
        jPanelFileImport.add(fileImport,BorderLayout.CENTER);
        
        TableRowSorter<TableModel> sorterCourseParticipants = new TableRowSorter<TableModel>(jTableParticipant.getModel());
        jTableParticipant.setRowSorter(sorterCourseParticipants);
    
        jLabelNbImport.setText("");
    }


           
    
    protected ObservableList<Participant> participants = ObservableCollections.observableList(new ArrayList<Participant>());
    public ObservableList<Participant> getParticipants() {
        return participants;
    }
    
    
    
    
    
    private void doImport() {
        participants.clear();
        jToggleButtonGenerateNumber.setSelected(false);
        
        try {
            int iNumero = mappingSupport.getFirstColumnsIdMappedToKey("numero");
            int iNom = mappingSupport.getFirstColumnsIdMappedToKey("nom");
            int iPrenom = mappingSupport.getFirstColumnsIdMappedToKey("prenom");
            int iGroupe = mappingSupport.getFirstColumnsIdMappedToKey("groupe");
            int iRenseignement = mappingSupport.getFirstColumnsIdMappedToKey("renseignement");

            List<CellValue[]> listCells = fileImport.getAllDataRows();
            Iterator<CellValue[]> iter = listCells.iterator();
            while (iter.hasNext()) {
                CellValue[] cells = iter.next();
                
                if(cells == null) {
                    continue;
                }
                
                Participant p = new Participant();
                if(iNumero > -1 && cells.length > iNumero) {
                    p.setNumero(cells[iNumero].getValue());
                }
                if(iNom > -1 && cells.length > iNom) {
                    p.setNom(cells[iNom].getValue());
                }
                if(iPrenom > -1 && cells.length > iPrenom) {
                    p.setPrenom(cells[iPrenom].getValue());
                }    
                if(iGroupe > -1 && cells.length > iGroupe) {
                    p.setGroupe(cells[iGroupe].getValue());
                }    
                if(iRenseignement > -1 && cells.length > iRenseignement) {
                    p.setRenseignements(cells[iRenseignement].getValue());
                }
                participants.add(p);
            }
        }
        catch(Exception e) {
            error("Erreur lors de la lecture du fichier: " + e.getLocalizedMessage());
        }
        
        jLabelNbImport.setText(""+participants.size() + " participants");
    }

    
    
    private void generateNumber() {
        if(jToggleButtonGenerateNumber.isSelected()) {
            int number = 0;
            try {
                number = Integer.parseInt(jTextFieldStartNumber.getText());
            }
            catch(NumberFormatException e) {
                error("Numéro de départ invalide !");
                return;
            }

            //TODO use the tablemodel to take care about the sorting issue.
            String format = jTextFieldNumberFormat.getText();
            for(Participant p : participants) {
                p.setNumero(format.replace("#", ""+number));
                number++;
            }
        }
        else {
            //clean
            for(Participant p : participants) {
                p.setNumero("");
            }
        }
    }
    

    
    Timer errorTimer = null;
    
    private void error(String message) {
        if(errorTimer == null) {
            errorTimer = new Timer(10000, new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    jLabelError.setVisible(false);
                }
            });            
        }
        errorTimer.setRepeats(false);
        errorTimer.restart();
        
        jLabelError.setText(message);
        jLabelError.setVisible(true);
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

        jPanelInner = new javax.swing.JPanel();
        jToggleButtonGenerateNumber = new javax.swing.JToggleButton();
        jLabel3 = new javax.swing.JLabel();
        jTextFieldNumberFormat = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jTextFieldStartNumber = new javax.swing.JTextField();
        jSplitPane1 = new javax.swing.JSplitPane();
        jPanelFileImport = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTableParticipant = new javax.swing.JTable();
        jLabelNbImport = new javax.swing.JLabel();
        jLabelError = new javax.swing.JLabel();

        setName("Form"); // NOI18N
        setLayout(new java.awt.BorderLayout());

        jPanelInner.setName("jPanelInner"); // NOI18N

        jToggleButtonGenerateNumber.setText(MSGS.getString("jToggleButtonGenerateNumber.text")); // NOI18N
        jToggleButtonGenerateNumber.setName("jToggleButtonGenerateNumber"); // NOI18N
        jToggleButtonGenerateNumber.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jToggleButtonGenerateNumberItemStateChanged(evt);
            }
        });

        jLabel3.setText(MSGS.getString("jLabel3.text")); // NOI18N
        jLabel3.setName("jLabel3"); // NOI18N

        jTextFieldNumberFormat.setText(MSGS.getString("jTextFieldNumberFormat.text")); // NOI18N
        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("gui/resources/ImportParticipantWizard"); // NOI18N
        jTextFieldNumberFormat.setToolTipText(bundle.getString("ImportParticipantWizard.jTextFieldNumberFormat.toolTipText")); // NOI18N
        jTextFieldNumberFormat.setName("jTextFieldNumberFormat"); // NOI18N

        jLabel2.setText(MSGS.getString("jLabel2.text")); // NOI18N
        jLabel2.setName("jLabel2"); // NOI18N

        jTextFieldStartNumber.setText(MSGS.getString("jTextFieldStartNumber.text")); // NOI18N
        jTextFieldStartNumber.setName("jTextFieldStartNumber"); // NOI18N

        jSplitPane1.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
        jSplitPane1.setResizeWeight(0.4);
        jSplitPane1.setName("jSplitPane1"); // NOI18N

        jPanelFileImport.setName("jPanelFileImport"); // NOI18N

        javax.swing.GroupLayout jPanelFileImportLayout = new javax.swing.GroupLayout(jPanelFileImport);
        jPanelFileImport.setLayout(jPanelFileImportLayout);
        jPanelFileImportLayout.setHorizontalGroup(
            jPanelFileImportLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 612, Short.MAX_VALUE)
        );
        jPanelFileImportLayout.setVerticalGroup(
            jPanelFileImportLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 154, Short.MAX_VALUE)
        );

        jSplitPane1.setTopComponent(jPanelFileImport);

        jScrollPane2.setName("jScrollPane2"); // NOI18N

        jTableParticipant.setName("jTableParticipant"); // NOI18N

        org.jdesktop.beansbinding.ELProperty eLProperty = org.jdesktop.beansbinding.ELProperty.create("${participants}");
        org.jdesktop.swingbinding.JTableBinding jTableBinding = org.jdesktop.swingbinding.SwingBindings.createJTableBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, eLProperty, jTableParticipant);
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
        jTableBinding.bind();
        jScrollPane2.setViewportView(jTableParticipant);
        if (jTableParticipant.getColumnModel().getColumnCount() > 0) {
            jTableParticipant.getColumnModel().getColumn(0).setHeaderValue(bundle.getString("ImportParticipantWizard.jTableParticipant.columnModel.title0")); // NOI18N
            jTableParticipant.getColumnModel().getColumn(1).setHeaderValue(bundle.getString("ImportParticipantWizard.jTableParticipant.columnModel.title1")); // NOI18N
            jTableParticipant.getColumnModel().getColumn(2).setHeaderValue(bundle.getString("ImportParticipantWizard.jTableParticipant.columnModel.title2")); // NOI18N
            jTableParticipant.getColumnModel().getColumn(3).setHeaderValue(bundle.getString("ImportParticipantWizard.jTableParticipant.columnModel.title3")); // NOI18N
            jTableParticipant.getColumnModel().getColumn(4).setHeaderValue(bundle.getString("ImportParticipantWizard.jTableParticipant.columnModel.title4")); // NOI18N
        }

        jSplitPane1.setRightComponent(jScrollPane2);

        jLabelNbImport.setText(bundle.getString("ImportParticipantWizard.jLabelNbImport.text")); // NOI18N
        jLabelNbImport.setName("jLabelNbImport"); // NOI18N

        javax.swing.GroupLayout jPanelInnerLayout = new javax.swing.GroupLayout(jPanelInner);
        jPanelInner.setLayout(jPanelInnerLayout);
        jPanelInnerLayout.setHorizontalGroup(
            jPanelInnerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelInnerLayout.createSequentialGroup()
                .addComponent(jToggleButtonGenerateNumber)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextFieldNumberFormat, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextFieldStartNumber, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabelNbImport))
            .addComponent(jSplitPane1)
        );
        jPanelInnerLayout.setVerticalGroup(
            jPanelInnerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelInnerLayout.createSequentialGroup()
                .addComponent(jSplitPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 419, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelInnerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jToggleButtonGenerateNumber)
                    .addComponent(jLabel3)
                    .addComponent(jTextFieldNumberFormat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(jTextFieldStartNumber, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelNbImport))
                .addGap(0, 0, 0))
        );

        add(jPanelInner, java.awt.BorderLayout.CENTER);

        jLabelError.setBackground(new java.awt.Color(255, 204, 204));
        jLabelError.setText(MSGS.getString("jLabelError.text")); // NOI18N
        jLabelError.setName("jLabelError"); // NOI18N
        jLabelError.setOpaque(true);
        add(jLabelError, java.awt.BorderLayout.PAGE_START);

        bindingGroup.bind();
    }// </editor-fold>//GEN-END:initComponents

    private void jToggleButtonGenerateNumberItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jToggleButtonGenerateNumberItemStateChanged
        generateNumber();
    }//GEN-LAST:event_jToggleButtonGenerateNumberItemStateChanged

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabelError;
    private javax.swing.JLabel jLabelNbImport;
    private javax.swing.JPanel jPanelFileImport;
    private javax.swing.JPanel jPanelInner;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JTable jTableParticipant;
    private javax.swing.JTextField jTextFieldNumberFormat;
    private javax.swing.JTextField jTextFieldStartNumber;
    private javax.swing.JToggleButton jToggleButtonGenerateNumber;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    // End of variables declaration//GEN-END:variables
}
