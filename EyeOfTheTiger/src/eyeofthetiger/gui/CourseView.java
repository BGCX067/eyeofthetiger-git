/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * CourseView.java
 *
 * Created on Nov 25, 2011, 11:40:52 PM
 */
package eyeofthetiger.gui;

import eyeofthetiger.model.Course;
import eyeofthetiger.model.Participant;
import eyeofthetiger.model.Participation;
import eyeofthetiger.utils.Utils;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Locale;
import java.util.ResourceBundle;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.Timer;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.joda.time.Period;
import org.joda.time.ReadablePeriod;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.PeriodFormatter;
import org.joda.time.format.PeriodFormatterBuilder;

/**
 *
 * @author christophe
 */
public class CourseView extends javax.swing.JPanel {

    protected static final ResourceBundle MSGS = ResourceBundle.getBundle(MBAction.classBundleBaseName(CourseView.class));

    
    
    private static enum Mode {
        beforeRace,
        duringRace,
        afterRace
    }
    
    
    /** Creates new form CourseView */
    public CourseView(Course course) {
        this.course = course;
        
        initComponents();
        
        if(course.getStartDate() == null) {
            switchMode(Mode.beforeRace);
        }
        else if(course.getStartDate() != null && course.getEndDate() == null) {
            switchMode(Mode.duringRace);
        }
        else {
            switchMode(Mode.afterRace);
        }
        
        TableRowSorter<TableModel> sorter1 = new TableRowSorter<TableModel>(jTable1.getModel());
        jTable1.setRowSorter(sorter1);
        
        TableRowSorter<TableModel> sorter2 = new TableRowSorter<TableModel>(jTable2.getModel());
        jTable2.setRowSorter(sorter2);
        
        jTable1.setUpdateSelectionOnSort(false);
        jTable2.setUpdateSelectionOnSort(false);
    }
    

    
    private Course course;
    public Course getCourse() {
        return course;
    }
    
    
    private Mode currentMode;
    private void switchMode(Mode mode) {
        if(mode == Mode.beforeRace) {
            chronoTimer.stop();
            jButtonMode.setText("Demarrer le chrono");
            jButtonMode.setIcon(new javax.swing.ImageIcon(getClass().getResource("/eyeofthetiger/gui/resources/player_time.png")));
            jButtonNouveauTopDepart.setVisible(false);
        }
        else if(mode == Mode.duringRace) {
            chronoTimer.start();
            jButtonMode.setText("Arreter le chrono");
            jButtonMode.setIcon(new javax.swing.ImageIcon(getClass().getResource("/eyeofthetiger/gui/resources/player_pause.png")));
            jButtonNouveauTopDepart.setVisible(false);
        }
        else if(mode == Mode.afterRace) {
            chronoTimer.stop();            
            jButtonMode.setText("Continuer");
            jButtonMode.setIcon(new javax.swing.ImageIcon(getClass().getResource("/eyeofthetiger/gui/resources/player_time.png")));
            jButtonNouveauTopDepart.setVisible(true);
        }
        currentMode = mode;
    }
   

    
    private Timer chronoTimer = new Timer(100,new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            tic();
        }
    });
    

    /*
    private PeriodFormatter chronoFormat = new PeriodFormatterBuilder().printZeroAlways()
                                        .appendHours().appendSeparator("h ").printZeroAlways()
                                        .appendMinutes().appendSeparator("mn ").printZeroAlways()
                                        .appendSeconds().appendSeparator("s ").printZeroNever()
                                        .appendMillis()
                                        .toFormatter().withLocale(Locale.UK);
    */
    
    private void tic() {
        DateTime st = course.getStartDate();
        if(st != null) {
            Duration du = new Duration(course.getStartDate(),new DateTime());
            du = eyeofthetiger.utils.Utils.TrimDurationToCentiseconds(du);
            
            Period period = du.toPeriod();
            long hours = period.getHours();
            long minutes = period.getMinutes();
            long seconds = period.getSeconds();
            long centiseconds = (period.getMillis()) / 100;    

            //jLabelChrono.setText(chronoFormat.print(du.toPeriod()));
            java.util.Formatter formatter = new java.util.Formatter();
            String format = "";
            if(hours > 0) {
                format += "%1$02dh ";
            }
            
            format += "%2$02dmn %3$02d.%4$ds";
            formatter.format(format, hours, minutes, seconds, centiseconds);
            jLabelChrono.setText(formatter.toString());
        }
        else {
            chronoTimer.stop();
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

        jTextFieldNumeroArrivant = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabelChrono = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jButtonMode = new javax.swing.JButton();
        jButtonNouveauTopDepart = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabelArrivee = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabelDepart = new javax.swing.JLabel();
        jSplitPane1 = new javax.swing.JSplitPane();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jLabel4 = new javax.swing.JLabel();
        jButtonSaveXLS = new javax.swing.JButton();
        jButtonOpenXLS = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();

        setName("Form"); // NOI18N

        jTextFieldNumeroArrivant.setFont(new java.awt.Font("Dialog.plain", 0, 12)); // NOI18N
        jTextFieldNumeroArrivant.setText(MSGS.getString("jTextFieldNumeroArrivant.text")); // NOI18N
        jTextFieldNumeroArrivant.setName("jTextFieldNumeroArrivant"); // NOI18N
        jTextFieldNumeroArrivant.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldNumeroArrivantActionPerformed(evt);
            }
        });
        jTextFieldNumeroArrivant.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTextFieldNumeroArrivantFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTextFieldNumeroArrivantFocusLost(evt);
            }
        });

        jLabel2.setText(MSGS.getString("jLabel2.text")); // NOI18N
        jLabel2.setName("jLabel2"); // NOI18N

        jLabelChrono.setFont(new java.awt.Font("Dialog.plain", 0, 48)); // NOI18N
        jLabelChrono.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelChrono.setText(MSGS.getString("jLabelChrono.text")); // NOI18N
        jLabelChrono.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jLabelChrono.setName("jLabelChrono"); // NOI18N

        jPanel3.setName("jPanel3"); // NOI18N

        jButtonMode.setIcon(new javax.swing.ImageIcon(getClass().getResource("/eyeofthetiger/gui/resources/player_time.png"))); // NOI18N
        jButtonMode.setText(MSGS.getString("jButtonMode.text")); // NOI18N
        jButtonMode.setName("jButtonMode"); // NOI18N
        jButtonMode.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonModeActionPerformed(evt);
            }
        });

        jButtonNouveauTopDepart.setIcon(new javax.swing.ImageIcon(getClass().getResource("/eyeofthetiger/gui/resources/player_start.png"))); // NOI18N
        jButtonNouveauTopDepart.setText(MSGS.getString("jButtonNouveauTopDepart.text")); // NOI18N
        jButtonNouveauTopDepart.setName("jButtonNouveauTopDepart"); // NOI18N
        jButtonNouveauTopDepart.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonNouveauTopDepartActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jButtonMode)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButtonNouveauTopDepart)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonMode)
                    .addComponent(jButtonNouveauTopDepart)))
        );

        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("gui/resources/CourseView"); // NOI18N
        jPanel4.setName(bundle.getString("CourseView.jPanel4.name")); // NOI18N

        jLabel3.setText(MSGS.getString("jLabel3.text")); // NOI18N
        jLabel3.setName("jLabel3"); // NOI18N

        jLabelArrivee.setName("jLabelArrivee"); // NOI18N

        org.jdesktop.beansbinding.Binding binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, org.jdesktop.beansbinding.ELProperty.create("${course.endDate}"), jLabelArrivee, org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setConverter(new eyeofthetiger.utils.DateToStringConverter());
        bindingGroup.addBinding(binding);

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel1.setText(MSGS.getString("jLabel1.text")); // NOI18N
        jLabel1.setName("jLabel1"); // NOI18N

        jLabelDepart.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabelDepart.setName("jLabelDepart"); // NOI18N

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ, this, org.jdesktop.beansbinding.ELProperty.create("${course.startDate}"), jLabelDepart, org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setConverter(new eyeofthetiger.utils.DateToStringConverter());
        bindingGroup.addBinding(binding);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabelDepart, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabelArrivee, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGap(0, 38, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel1)
                    .addComponent(jLabelDepart, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, 24, Short.MAX_VALUE)
                    .addComponent(jLabelArrivee, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        jSplitPane1.setBorder(null);
        jSplitPane1.setDividerLocation(350);
        jSplitPane1.setName(bundle.getString("CourseView.jSplitPane1.name")); // NOI18N

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Participants"));
        jPanel1.setName("jPanel1"); // NOI18N

        jScrollPane2.setName("jScrollPane2"); // NOI18N

        jTable2.setName("jTable2"); // NOI18N

        org.jdesktop.beansbinding.ELProperty eLProperty = org.jdesktop.beansbinding.ELProperty.create("${course.participants}");
        org.jdesktop.swingbinding.JTableBinding jTableBinding = org.jdesktop.swingbinding.SwingBindings.createJTableBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, eLProperty, jTable2);
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
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${dateInscription}"));
        columnBinding.setColumnName("Inscription");
        columnBinding.setColumnClass(org.joda.time.DateTime.class);
        columnBinding.setEditable(false);
        bindingGroup.addBinding(jTableBinding);
        jTableBinding.bind();
        jScrollPane2.setViewportView(jTable2);
        jTable2.getColumnModel().getColumn(0).setHeaderValue(bundle.getString("CourseView.jTable2.columnModel.title0")); // NOI18N
        jTable2.getColumnModel().getColumn(1).setHeaderValue(bundle.getString("CourseView.jTable2.columnModel.title1")); // NOI18N
        jTable2.getColumnModel().getColumn(2).setHeaderValue(bundle.getString("CourseView.jTable2.columnModel.title2")); // NOI18N
        jTable2.getColumnModel().getColumn(3).setHeaderValue(bundle.getString("CourseView.jTable2.columnModel.title3")); // NOI18N
        jTable2.getColumnModel().getColumn(4).setCellRenderer(getDateRenderer());

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/eyeofthetiger/gui/resources/application_vnd_ms_excel_32x32.png"))); // NOI18N
        jLabel4.setText(bundle.getString("CourseView.jLabel4.text")); // NOI18N
        jLabel4.setName("jLabel4"); // NOI18N

        jButtonSaveXLS.setIcon(new javax.swing.ImageIcon(getClass().getResource("/eyeofthetiger/gui/resources/gnome_document_save_32x32.png"))); // NOI18N
        jButtonSaveXLS.setText(bundle.getString("CourseView.jButtonSaveXLS.text")); // NOI18N
        jButtonSaveXLS.setName("jButtonSaveXLS"); // NOI18N
        jButtonSaveXLS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSaveXLSActionPerformed(evt);
            }
        });

        jButtonOpenXLS.setIcon(new javax.swing.ImageIcon(getClass().getResource("/eyeofthetiger/gui/resources/application_vnd_ms_excel_32x32.png"))); // NOI18N
        jButtonOpenXLS.setText(bundle.getString("CourseView.jButtonOpenXLS.text")); // NOI18N
        jButtonOpenXLS.setName("jButtonOpenXLS"); // NOI18N
        jButtonOpenXLS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonOpenXLSActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 338, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButtonSaveXLS)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButtonOpenXLS)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 370, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jButtonSaveXLS)
                    .addComponent(jButtonOpenXLS)))
        );

        jSplitPane1.setLeftComponent(jPanel1);

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Résultats"));
        jPanel2.setName("jPanel2"); // NOI18N

        jScrollPane1.setName("jScrollPane1"); // NOI18N

        jTable1.setName("jTable1"); // NOI18N

        eLProperty = org.jdesktop.beansbinding.ELProperty.create("${course.participations}");
        jTableBinding = org.jdesktop.swingbinding.SwingBindings.createJTableBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, eLProperty, jTable1);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${participant.numero}"));
        columnBinding.setColumnName("Participant.numero");
        columnBinding.setColumnClass(String.class);
        columnBinding.setEditable(false);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${participant.nom}"));
        columnBinding.setColumnName("Participant.nom");
        columnBinding.setColumnClass(String.class);
        columnBinding.setEditable(false);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${participant.prenom}"));
        columnBinding.setColumnName("Prénom");
        columnBinding.setColumnClass(String.class);
        columnBinding.setEditable(false);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${arrivee}"));
        columnBinding.setColumnName("Arrivee");
        columnBinding.setColumnClass(org.joda.time.DateTime.class);
        columnBinding.setEditable(false);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${temps}"));
        columnBinding.setColumnName("Temps");
        columnBinding.setColumnClass(org.joda.time.Duration.class);
        columnBinding.setEditable(false);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${participant.groupe}"));
        columnBinding.setColumnName("Participant.groupe");
        columnBinding.setColumnClass(String.class);
        columnBinding.setEditable(false);
        bindingGroup.addBinding(jTableBinding);
        jTableBinding.bind();
        jScrollPane1.setViewportView(jTable1);
        jTable1.getColumnModel().getColumn(0).setHeaderValue(bundle.getString("CourseView.jTable1.columnModel.title0")); // NOI18N
        jTable1.getColumnModel().getColumn(1).setHeaderValue(bundle.getString("CourseView.jTable1.columnModel.title1")); // NOI18N
        jTable1.getColumnModel().getColumn(3).setHeaderValue(bundle.getString("CourseView.jTable1.columnModel.title3")); // NOI18N
        jTable1.getColumnModel().getColumn(3).setCellRenderer(getHourRenderer());
        jTable1.getColumnModel().getColumn(4).setHeaderValue(bundle.getString("CourseView.jTable1.columnModel.title4")); // NOI18N
        jTable1.getColumnModel().getColumn(4).setCellRenderer(getDurationRenderer());
        jTable1.getColumnModel().getColumn(5).setHeaderValue(bundle.getString("CourseView.jTable1.columnModel.title5")); // NOI18N

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 483, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 417, Short.MAX_VALUE)
        );

        jSplitPane1.setRightComponent(jPanel2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextFieldNumeroArrivant))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jLabelChrono, javax.swing.GroupLayout.PREFERRED_SIZE, 474, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
            .addComponent(jSplitPane1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jTextFieldNumeroArrivant, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel2))
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSplitPane1))
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLabelChrono, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        bindingGroup.bind();
    }// </editor-fold>//GEN-END:initComponents

private void jButtonModeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonModeActionPerformed
    if(currentMode == Mode.beforeRace) {
        course.depart();
        switchMode(Mode.duringRace);
    }
    else if(currentMode == Mode.duringRace) {
        course.fin();
        switchMode(Mode.afterRace);
    }
    else if(currentMode == Mode.afterRace) {
        course.redemare();
        switchMode(Mode.duringRace);
    }
}//GEN-LAST:event_jButtonModeActionPerformed

private void jTextFieldNumeroArrivantActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldNumeroArrivantActionPerformed
    String numero = jTextFieldNumeroArrivant.getText().trim();
    Participant participant = course.findByNumero(numero);
    String soundToPlay = "ok.wav";
    if(participant == null) {
        participant = Participant.CreateInconnu(numero);
        soundToPlay = "bad.wav";
    }
    course.arrivee(participant);
    jTextFieldNumeroArrivant.setText("");
    Utils.PlaySound(soundToPlay);
}//GEN-LAST:event_jTextFieldNumeroArrivantActionPerformed

private void jTextFieldNumeroArrivantFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextFieldNumeroArrivantFocusGained
    jTextFieldNumeroArrivant.setBackground(Utils.LightBlue);
}//GEN-LAST:event_jTextFieldNumeroArrivantFocusGained

private void jTextFieldNumeroArrivantFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextFieldNumeroArrivantFocusLost
    jTextFieldNumeroArrivant.setBackground(Color.white);
}//GEN-LAST:event_jTextFieldNumeroArrivantFocusLost

private void jButtonNouveauTopDepartActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonNouveauTopDepartActionPerformed
    course.depart();
    switchMode(Mode.duringRace);
}//GEN-LAST:event_jButtonNouveauTopDepartActionPerformed

    private void jButtonSaveXLSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSaveXLSActionPerformed
        try {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            fileChooser.setMultiSelectionEnabled(false);
            if(fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                if(!file.getName().toLowerCase().endsWith(".xls")) {
                    file = new File(file.getParentFile(),file.getName()+".xls");
                }
                exportAsXLS(file);        
            }
        }
        catch(Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erreur ! " + e.getMessage());
        }
    }//GEN-LAST:event_jButtonSaveXLSActionPerformed

    private void jButtonOpenXLSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonOpenXLSActionPerformed
        try {
            File file = File.createTempFile("eyeofthetiger", ".xls");
            exportAsXLS(file);
            Desktop.getDesktop().open(file);
        }
        catch(Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erreur ! " + e.getMessage());
        }
    }//GEN-LAST:event_jButtonOpenXLSActionPerformed




    private void exportAsXLS(File outputFile) throws Exception {
        Workbook wb = new HSSFWorkbook();
        Sheet sheet  = wb.createSheet("Course " + course.getName());

        CellStyle cellDateHourStyle = wb.createCellStyle();
        DataFormat df = wb.createDataFormat();
        cellDateHourStyle.setDataFormat(df.getFormat("yyyy/MM/dd hh:mm:ss;@"));

        PeriodFormatter durationFormat = new PeriodFormatterBuilder().printZeroAlways()
                                        .appendHours().minimumPrintedDigits(2).appendSeparator("h")
                                        .appendMinutes().minimumPrintedDigits(2).appendSeparator("mn")
                                        .appendSeconds().minimumPrintedDigits(2).appendLiteral("s")
                                        .toFormatter().withLocale(Locale.UK);
        
        
        int rowIndex = 0;
        int cellIndex = 0;
        Row row = sheet.createRow(rowIndex++);
        Cell cell = null;

        row.createCell(cellIndex++).setCellValue("GROUPE");
        row.createCell(cellIndex++).setCellValue("NUMERO");
        row.createCell(cellIndex++).setCellValue("NOM");
        row.createCell(cellIndex++).setCellValue("PRENOM");
        row.createCell(cellIndex++).setCellValue("TEMPS");
        row.createCell(cellIndex++).setCellValue("DEPART");
        row.createCell(cellIndex++).setCellValue("ARRIVEE");
        row.createCell(cellIndex++).setCellValue("RENSEIGNEMENTS");
        row.createCell(cellIndex++).setCellValue("INSCRIPTION");

        HashSet<Participant> _participants = new HashSet<Participant>(course.getParticipants());
        
        for(Participation p : course.getParticipations()) {
            cellIndex = 0;
            row = sheet.createRow(rowIndex++);
            row.createCell(cellIndex++).setCellValue("" + p.getParticipant().getGroupe());
            row.createCell(cellIndex++).setCellValue("" + p.getParticipant().getNumero());
            row.createCell(cellIndex++).setCellValue("" + p.getParticipant().getNom());
            row.createCell(cellIndex++).setCellValue("" + p.getParticipant().getPrenom());

            cell = row.createCell(cellIndex++);
            cell.setCellType(Cell.CELL_TYPE_STRING);
            Duration duration = new Duration(p.getDepart(),p.getArrivee());
            cell.setCellValue(durationFormat.print(duration.toPeriod()));

            cell = row.createCell(cellIndex++);
            cell.setCellValue(p.getDepart().toDate());
            cell.setCellStyle(cellDateHourStyle);

            cell = row.createCell(cellIndex++);
            cell.setCellValue(p.getArrivee().toDate());
            cell.setCellValue(p.getArrivee().toDate());
            cell.setCellStyle(cellDateHourStyle);
            
            row.createCell(cellIndex++).setCellValue("" + p.getParticipant().getRenseignements());
            
            cell = row.createCell(cellIndex++);
            cell.setCellValue(p.getParticipant().getDateInscription().toDate());
            cell.setCellStyle(cellDateHourStyle);
            
            _participants.remove(p.getParticipant());
        }
        
        for(Participant p : _participants) {
            cellIndex = 0;
            row = sheet.createRow(rowIndex++);
            row.createCell(cellIndex++).setCellValue("" + p.getGroupe());
            row.createCell(cellIndex++).setCellValue("" + p.getNumero());
            row.createCell(cellIndex++).setCellValue("" + p.getNom());
            row.createCell(cellIndex++).setCellValue("" + p.getPrenom());

            cell = row.createCell(cellIndex++);
            //cell.setCellType(Cell.CELL_TYPE_STRING);
            //Duration duration = new Duration(p.getDepart(),p.getArrivee());
            //cell.setCellValue(durationFormat.print(duration.toPeriod()));

            cell = row.createCell(cellIndex++);
            //cell.setCellValue(p.getDepart().toDate());
            //cell.setCellStyle(cellDateHourStyle);

            cell= row.createCell(cellIndex++);
            //cell.setCellValue(p.getArrivee().toDate());
            //cell.setCellValue(p.getArrivee().toDate());
            //cell.setCellStyle(cellDateHourStyle);
            
            row.createCell(cellIndex++).setCellValue("" + p.getRenseignements());
            
            cell = row.createCell(cellIndex++);
            cell.setCellValue(p.getDateInscription().toDate());
            cell.setCellStyle(cellDateHourStyle);           
        }

        FileOutputStream fileOut = new FileOutputStream(outputFile);
        wb.write(fileOut);
        fileOut.close();

    }


    private TableCellRenderer hourRenderer;
    public TableCellRenderer getHourRenderer() {
        if(hourRenderer == null) {
            hourRenderer = new HourRenderer();    
        }
        return hourRenderer;
    }
    
    private TableCellRenderer dateRenderer;
    public TableCellRenderer getDateRenderer() {
        if(dateRenderer == null) {
            dateRenderer = new DateRenderer();
        }
        return dateRenderer;
    }
    
    private TableCellRenderer durationRenderer;
    public TableCellRenderer getDurationRenderer() {
        if(durationRenderer == null) {
            durationRenderer = new DurationRenderer();
        }
        return durationRenderer;
    }

    /*
    private Converter<DateTime,String> dateToStringConverter;
    public Converter<DateTime,String> getDateToStringConverter() {
        if(dateToStringConverter == null) {
            dateToStringConverter = new DateToStringConverter();
        }
        new eyeofthetiger.utils.DateToStringConverter();
        return dateToStringConverter;
    }
    */
    
   
    
static class HourRenderer extends DefaultTableCellRenderer {
    static DateTimeFormatter formater = DateTimeFormat.forPattern("HH:mm").withLocale(Locale.UK);

    public HourRenderer() { super(); }

    @Override
    public void setValue(Object value) {
        String str= "";
        if(value == null) {
        }
        else if(value instanceof DateTime) {
            str = formater.print((DateTime)value);
        }
        else {
            str = "#incompatible type#";
        }
        setText(str);
    }
}


static class DateRenderer extends DefaultTableCellRenderer {
    static DateTimeFormatter formater = DateTimeFormat.forPattern("yyyy/MM/dd").withLocale(Locale.UK);

    public DateRenderer() { super(); }

    @Override
    public void setValue(Object value) {
        String str= "";
        if(value == null) {
        }
        else if(value instanceof DateTime) {
            str = formater.print((DateTime)value);
        }
        else {
            str = "#incompatible type#";
        }
        setText(str);
    }
}

            
            
static class DurationRenderer extends DefaultTableCellRenderer {
    public DurationRenderer() { super(); }

    @Override
    public void setValue(Object value) {
        String str= "";
        if(value == null) {
        }
        else if(value instanceof Duration) {
            Period period = ((Duration)value).toPeriod();
            long hours = period.getHours();
            long minutes = period.getMinutes();
            long seconds = period.getSeconds();
            long centiseconds = (period.getMillis()) / 100;    

            String format = "";
            if(hours > 0) {
                format += "%1$02dh ";
            }
            format += "%2$02dmn %3$02d.%4$ds";
            java.util.Formatter formatter = new java.util.Formatter();
            formatter.format(format, hours, minutes, seconds, centiseconds);
            str = formatter.toString();
        }
        else if(value instanceof ReadablePeriod) {
            Period period = ((ReadablePeriod)value).toPeriod();
            long hours = period.getHours();
            long minutes = period.getMinutes();
            long seconds = period.getSeconds();
            long centiseconds = (period.getMillis()) / 100;    

            String format = "";
            if(hours > 0) {
                format += "%1$02dh ";
            }
            format += "%2$02dmn %3$02d.%4$ds";
            java.util.Formatter formatter = new java.util.Formatter();
            formatter.format(format, hours, minutes, seconds, centiseconds);
            str = formatter.toString();
        }
        else {
            str = "#incompatible type#";
        }
        setText(str);
    }
}            
            
/*
static class DurationRenderer extends DefaultTableCellRenderer {
   private static PeriodFormatter formater = new PeriodFormatterBuilder().printZeroAlways()
                                        .appendHours().appendSeparator("h")
                                        .appendMinutes().appendSeparator("m")
                                        .appendSeconds().appendSeparator("s")
                                        .appendMillis()
                                        .toFormatter().withLocale(Locale.UK);

    public DurationRenderer() { super(); }

    @Override
    public void setValue(Object value) {
        String str= "";
        if(value == null) {
        }
        else if(value instanceof Duration) {
            str = formater.print(((Duration)value).toPeriod());
        }
        else if(value instanceof ReadablePeriod) {
            str = formater.print((ReadablePeriod)value);
        }
        else {
            str = "#incompatible type#";
        }
        setText(str);
    }
}
*/





    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonMode;
    private javax.swing.JButton jButtonNouveauTopDepart;
    private javax.swing.JButton jButtonOpenXLS;
    private javax.swing.JButton jButtonSaveXLS;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabelArrivee;
    private javax.swing.JLabel jLabelChrono;
    private javax.swing.JLabel jLabelDepart;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JTextField jTextFieldNumeroArrivant;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    // End of variables declaration//GEN-END:variables
}
