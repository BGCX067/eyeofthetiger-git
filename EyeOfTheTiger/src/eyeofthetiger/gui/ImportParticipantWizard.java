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

import com.csvreader.CsvReader;
import eyeofthetiger.model.Participant;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.ResourceBundle;
import javax.swing.JFileChooser;
import javax.swing.ScrollPaneConstants;
import javax.swing.Timer;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.jdesktop.observablecollections.ObservableCollections;
import org.jdesktop.observablecollections.ObservableList;

/**
 *
 * @author christophe
 */
public class ImportParticipantWizard extends javax.swing.JPanel {

    protected static final ResourceBundle MSGS = ResourceBundle.getBundle(MBAction.classBundleBaseName(ImportParticipantWizard.class));
    
    
    public ImportParticipantWizard() {
        initComponents();
        jLabelError.setVisible(false);
        jTableParticipant.setAutoCreateRowSorter(true);
        jScrollPaneTextMessage.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        jTextPane1.setCaretPosition(0);
        jTextFieldDelimiter.getDocument().addDocumentListener(new DocumentListener() {

            public void insertUpdate(DocumentEvent e) {
                doImport();
            }

            public void removeUpdate(DocumentEvent e) {
                doImport();
            }

            public void changedUpdate(DocumentEvent e) {
                doImport();
            }
        });
        
        TableRowSorter<TableModel> sorterCourseParticipants = new TableRowSorter<TableModel>(jTableParticipant.getModel());
        jTableParticipant.setRowSorter(sorterCourseParticipants);
        
        Map<String,Charset> charsets = Charset.availableCharsets();
        jComboBoxCharset.removeAllItems();
        jComboBoxCharset.addItem(DEFAULT_CHARSET);
        for(String s: charsets.keySet()) {
            jComboBoxCharset.addItem(s);
        }
        jComboBoxCharset.setSelectedItem(DEFAULT_CHARSET);
        
        jComboBoxCharset.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                doImport();
            }
        });
    }

    private static String DEFAULT_CHARSET = "_default_";
           
    
    protected ObservableList<Participant> participants = ObservableCollections.observableList(new ArrayList<Participant>());
    public ObservableList<Participant> getParticipants() {
        return participants;
    }
    
    
    private static abstract class DataFileReader {
        public abstract boolean readNext() throws Exception;
        public abstract String getField(int i) throws Exception;
    }
    
    
    private static class CSVDataFileReader extends DataFileReader {
        private CsvReader csvReader = null;
        private CSVDataFileReader(File file, String charset, char delimiter, boolean skipFirstLine) throws Exception {
            
            if(DEFAULT_CHARSET.equals(charset)) {
               csvReader = new CsvReader(file.getAbsolutePath());
            }
            else {
                csvReader = new CsvReader(new FileInputStream(file),Charset.forName(charset));
            }
            csvReader.setDelimiter(delimiter);
            if(skipFirstLine) {
                csvReader.readHeaders();
            }
        }
        
        @Override
        public boolean readNext() throws Exception {
            return csvReader.readRecord();
        }

        @Override
        public String getField(int i) throws Exception {
            if(i<csvReader.getColumnCount()) {
                return csvReader.get(i);
             }
            return null;
        }
        
    }
    
    
    
    private static class XLSDataFileReader extends DataFileReader {
        private Iterator<Row> rowIterator = null;
        private Row currentRow = null;
        private XLSDataFileReader(File xlsFile, boolean skipFirstLine) throws Exception {
            FileInputStream is = new FileInputStream(xlsFile);
            HSSFWorkbook workbook = new HSSFWorkbook(is);
            HSSFSheet sheet = workbook.getSheetAt(0);
            rowIterator = sheet.iterator();
            if(skipFirstLine && rowIterator.hasNext()) {
                rowIterator.next();
            }
        }
       
        @Override
        public boolean readNext() throws Exception {
            if(rowIterator.hasNext()) {
                currentRow = rowIterator.next();
                return true;
            }
            return false;
        }

        @Override
        public String getField(int i) throws Exception {
            if(currentRow != null) {
                Cell cell = currentRow.getCell(i);
                if(cell != null) {
                    return cell.getStringCellValue();
                }
            }
            return null;
        }
    }
    
    
    private void doImport() {
        participants.clear();
        jToggleButtonGenerateNumber.setSelected(false);
        
        String fileName = jTextFieldFile.getText();
        boolean firstColumnIsNumber = jCheckBoxFirstColumnIsNumber.isSelected();
        boolean skipFirstLine = jCheckBoxSkipFirstLine.isSelected();
        
        try {
            DataFileReader dataFileReader = null;
            if(fileName.toLowerCase().endsWith(".xls")) {
                dataFileReader = new XLSDataFileReader(new File(fileName), skipFirstLine);
            }
            else {
                CSVDataFileReader csvDataFileReader = null;
                String selectedCharset = ""+jComboBoxCharset.getSelectedItem();
                char delimiter = ';';
                String strDelim = jTextFieldDelimiter.getText();
                if(strDelim != null && strDelim.length() == 1) {
                    delimiter = strDelim.charAt(0);
                }
                csvDataFileReader = new CSVDataFileReader(new File(fileName), selectedCharset, delimiter, skipFirstLine);
                dataFileReader = csvDataFileReader;
            }
        
            while (dataFileReader.readNext()) {
                // I must try to read up to 5 fields: id;firstname;name;group;renseignements
                Participant p = new Participant();
                int i = 0;
                String value = null;
                if(firstColumnIsNumber) {
                    value = dataFileReader.getField(i);
                    if(value != null) {
                        p.setNumero(value);
                    }
                    i++;
                }

                value = dataFileReader.getField(i);
                if(value != null) {
                    p.setNom(value);
                }
                i++;

                value = dataFileReader.getField(i);
                if(value != null) {
                    p.setPrenom(value);
                }
                i++;

                value = dataFileReader.getField(i);
                if(value != null) {
                    p.setGroupe(value);
                }
                i++;

                value = dataFileReader.getField(i);
                if(value != null) {
                    p.setRenseignements(value);
                }
                i++;
                
                participants.add(p);
            }
        }
        catch(FileNotFoundException e) {
            error("Le fichier est introuvable !");
        }
        catch(Exception e) {
            error("Erreur lors de la lecture du fichier: " + e.getLocalizedMessage());
        }
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
        jLabel1 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTableParticipant = new javax.swing.JTable();
        jToggleButtonGenerateNumber = new javax.swing.JToggleButton();
        jLabel3 = new javax.swing.JLabel();
        jTextFieldNumberFormat = new javax.swing.JTextField();
        jButtonChooseFile = new javax.swing.JButton();
        jTextFieldFile = new javax.swing.JTextField();
        jCheckBoxSkipFirstLine = new javax.swing.JCheckBox();
        jCheckBoxFirstColumnIsNumber = new javax.swing.JCheckBox();
        jLabel2 = new javax.swing.JLabel();
        jTextFieldStartNumber = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jTextFieldDelimiter = new javax.swing.JTextField();
        jScrollPaneTextMessage = new javax.swing.JScrollPane();
        jTextPane1 = new javax.swing.JTextPane();
        jComboBoxCharset = new javax.swing.JComboBox();
        jLabelError = new javax.swing.JLabel();

        setName("Form"); // NOI18N
        setLayout(new java.awt.BorderLayout());

        jPanelInner.setName("jPanelInner"); // NOI18N

        jLabel1.setText(MSGS.getString("jLabel1.text")); // NOI18N
        jLabel1.setName("jLabel1"); // NOI18N

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
        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("gui/resources/ImportParticipantWizard"); // NOI18N
        jTableParticipant.getColumnModel().getColumn(0).setHeaderValue(bundle.getString("ImportParticipantWizard.jTableParticipant.columnModel.title0")); // NOI18N
        jTableParticipant.getColumnModel().getColumn(1).setHeaderValue(bundle.getString("ImportParticipantWizard.jTableParticipant.columnModel.title1")); // NOI18N
        jTableParticipant.getColumnModel().getColumn(2).setHeaderValue(bundle.getString("ImportParticipantWizard.jTableParticipant.columnModel.title2")); // NOI18N
        jTableParticipant.getColumnModel().getColumn(3).setHeaderValue(bundle.getString("ImportParticipantWizard.jTableParticipant.columnModel.title3")); // NOI18N
        jTableParticipant.getColumnModel().getColumn(4).setHeaderValue(bundle.getString("ImportParticipantWizard.jTableParticipant.columnModel.title4")); // NOI18N

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
        jTextFieldNumberFormat.setToolTipText(bundle.getString("ImportParticipantWizard.jTextFieldNumberFormat.toolTipText")); // NOI18N
        jTextFieldNumberFormat.setName("jTextFieldNumberFormat"); // NOI18N

        jButtonChooseFile.setText(MSGS.getString("jButtonChooseFile.text")); // NOI18N
        jButtonChooseFile.setName("jButtonChooseFile"); // NOI18N
        jButtonChooseFile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonChooseFileActionPerformed(evt);
            }
        });

        jTextFieldFile.setText(MSGS.getString("jTextFieldFile.text")); // NOI18N
        jTextFieldFile.setName("jTextFieldFile"); // NOI18N
        jTextFieldFile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldFileActionPerformed(evt);
            }
        });

        jCheckBoxSkipFirstLine.setText(MSGS.getString("jCheckBoxSkipFirstLine.text")); // NOI18N
        jCheckBoxSkipFirstLine.setName("jCheckBoxSkipFirstLine"); // NOI18N
        jCheckBoxSkipFirstLine.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jCheckBoxSkipFirstLineItemStateChanged(evt);
            }
        });

        jCheckBoxFirstColumnIsNumber.setText(MSGS.getString("jCheckBoxFirstColumnIsNumber.text")); // NOI18N
        jCheckBoxFirstColumnIsNumber.setName("jCheckBoxFirstColumnIsNumber"); // NOI18N
        jCheckBoxFirstColumnIsNumber.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jCheckBoxFirstColumnIsNumberItemStateChanged(evt);
            }
        });

        jLabel2.setText(MSGS.getString("jLabel2.text")); // NOI18N
        jLabel2.setName("jLabel2"); // NOI18N

        jTextFieldStartNumber.setText(MSGS.getString("jTextFieldStartNumber.text")); // NOI18N
        jTextFieldStartNumber.setName("jTextFieldStartNumber"); // NOI18N

        jLabel4.setText(MSGS.getString("jLabel4.text")); // NOI18N
        jLabel4.setName("jLabel4"); // NOI18N

        jTextFieldDelimiter.setText(MSGS.getString("jTextFieldDelimiter.text")); // NOI18N
        jTextFieldDelimiter.setName("jTextFieldDelimiter"); // NOI18N

        jScrollPaneTextMessage.setName("jScrollPaneTextMessage"); // NOI18N

        jTextPane1.setEditable(false);
        jTextPane1.setBorder(null);
        jTextPane1.setText(MSGS.getString("jTextPane1.text")); // NOI18N
        jTextPane1.setFocusCycleRoot(false);
        jTextPane1.setFocusable(false);
        jTextPane1.setName("jTextPane1"); // NOI18N
        jTextPane1.setRequestFocusEnabled(false);
        jScrollPaneTextMessage.setViewportView(jTextPane1);

        jComboBoxCharset.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jComboBoxCharset.setToolTipText(bundle.getString("ImportParticipantWizard.jComboBoxCharset.toolTipText")); // NOI18N
        jComboBoxCharset.setName("jComboBoxCharset"); // NOI18N

        javax.swing.GroupLayout jPanelInnerLayout = new javax.swing.GroupLayout(jPanelInner);
        jPanelInner.setLayout(jPanelInnerLayout);
        jPanelInnerLayout.setHorizontalGroup(
            jPanelInnerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelInnerLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelInnerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 620, Short.MAX_VALUE)
                    .addGroup(jPanelInnerLayout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(9, 9, 9)
                        .addComponent(jTextFieldFile)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButtonChooseFile)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jComboBoxCharset, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanelInnerLayout.createSequentialGroup()
                        .addComponent(jToggleButtonGenerateNumber)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel3)
                        .addGap(10, 10, 10)
                        .addComponent(jTextFieldNumberFormat, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextFieldStartNumber, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanelInnerLayout.createSequentialGroup()
                        .addGroup(jPanelInnerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jCheckBoxFirstColumnIsNumber)
                            .addComponent(jCheckBoxSkipFirstLine)
                            .addGroup(jPanelInnerLayout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextFieldDelimiter, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPaneTextMessage, javax.swing.GroupLayout.DEFAULT_SIZE, 401, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanelInnerLayout.setVerticalGroup(
            jPanelInnerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelInnerLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelInnerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldFile, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1)
                    .addComponent(jButtonChooseFile)
                    .addComponent(jComboBoxCharset, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelInnerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelInnerLayout.createSequentialGroup()
                        .addComponent(jCheckBoxFirstColumnIsNumber)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jCheckBoxSkipFirstLine)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanelInnerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(jTextFieldDelimiter, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jScrollPaneTextMessage, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 258, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelInnerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jToggleButtonGenerateNumber)
                    .addComponent(jLabel3)
                    .addComponent(jTextFieldNumberFormat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(jTextFieldStartNumber, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        add(jPanelInner, java.awt.BorderLayout.CENTER);

        jLabelError.setBackground(new java.awt.Color(255, 204, 204));
        jLabelError.setText(MSGS.getString("jLabelError.text")); // NOI18N
        jLabelError.setName("jLabelError"); // NOI18N
        jLabelError.setOpaque(true);
        add(jLabelError, java.awt.BorderLayout.PAGE_START);

        bindingGroup.bind();
    }// </editor-fold>//GEN-END:initComponents

private void jButtonChooseFileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonChooseFileActionPerformed
    JFileChooser fileChooser = new JFileChooser();
    if(fileChooser.showOpenDialog(this)==JFileChooser.APPROVE_OPTION){
       jTextFieldFile.setText(fileChooser.getSelectedFile().getAbsolutePath());
       doImport();
    }
}//GEN-LAST:event_jButtonChooseFileActionPerformed

private void jToggleButtonGenerateNumberItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jToggleButtonGenerateNumberItemStateChanged
    generateNumber();
}//GEN-LAST:event_jToggleButtonGenerateNumberItemStateChanged

private void jCheckBoxFirstColumnIsNumberItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jCheckBoxFirstColumnIsNumberItemStateChanged
    doImport();
}//GEN-LAST:event_jCheckBoxFirstColumnIsNumberItemStateChanged

private void jCheckBoxSkipFirstLineItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jCheckBoxSkipFirstLineItemStateChanged
    doImport();
}//GEN-LAST:event_jCheckBoxSkipFirstLineItemStateChanged

private void jTextFieldFileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldFileActionPerformed
    doImport();
}//GEN-LAST:event_jTextFieldFileActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonChooseFile;
    private javax.swing.JCheckBox jCheckBoxFirstColumnIsNumber;
    private javax.swing.JCheckBox jCheckBoxSkipFirstLine;
    private javax.swing.JComboBox jComboBoxCharset;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabelError;
    private javax.swing.JPanel jPanelInner;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPaneTextMessage;
    private javax.swing.JTable jTableParticipant;
    private javax.swing.JTextField jTextFieldDelimiter;
    private javax.swing.JTextField jTextFieldFile;
    private javax.swing.JTextField jTextFieldNumberFormat;
    private javax.swing.JTextField jTextFieldStartNumber;
    private javax.swing.JTextPane jTextPane1;
    private javax.swing.JToggleButton jToggleButtonGenerateNumber;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    // End of variables declaration//GEN-END:variables
}
