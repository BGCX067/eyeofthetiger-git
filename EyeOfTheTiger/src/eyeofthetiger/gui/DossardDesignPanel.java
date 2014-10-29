/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package eyeofthetiger.gui;

import eyeofthetiger.model.Participant;
import eyeofthetiger.utils.PDFDossardGenerator;
import eyeofthetiger.utils.PDFUtils;
import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.text.NumberFormat;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.filechooser.FileFilter;
import org.joda.time.DateTime;

/**
 *
 * @author thecat
 */
public class DossardDesignPanel extends javax.swing.JPanel {


    public DossardDesignPanel() {
        initComponents();
        
        jPanelImage.setLayout(new BorderLayout());
        jPanelImage.add(dip,BorderLayout.CENTER);
        
        ResourceBundle bundle = java.util.ResourceBundle.getBundle("eyeofthetiger/gui/resources/DossardDesignPanel");
        Utils.BalloonHelp(bundle.getString("pdf_background_help"), jTextFieldBackgroundPdf, jButtonBackgroundPdfBrowse, jLabel5);
        Utils.BalloonHelp(bundle.getString("pdf_logo_help"), jTextFieldLogoLeft, jTextFieldLogoRight, jButtonLogoLeft, jButtonLogoRight);
    }

    DossardImageDisplayPanel dip = new DossardImageDisplayPanel();
    
    private PDFDossardGenerator pdfDossardGenerator;
    public void setPDFDossardGenerator(PDFDossardGenerator pdg) {
        pdfDossardGenerator = pdg;
        refreshDisplay();
    }
    
    private List<Participant> participants = new LinkedList<Participant>();
    private int currentParticipant = 0; 
    public void setParticipants(List<Participant> p ) {
        participants = p;
        currentParticipant = 0;
        refreshDisplay();
    }
    
    private void refreshDisplay() {
        if(pdfDossardGenerator == null) {
            //TODO
            return;
        }
        
        String margin = NumberFormat.getInstance(Locale.ENGLISH).format(pdfDossardGenerator.getMarginCm());
        jTextFieldMargin.setText(margin);
        int leftWidth = (int)(pdfDossardGenerator.getLogoLeftWidth() * 100.);
        int rightWidth = (int)(pdfDossardGenerator.getLogoRightWidht() * 100.);
        jSliderLogoLeft.setValue(leftWidth);
        jSliderLogoRight.setValue(rightWidth);
        jLabelLogoLeftWidth.setText("Largeur:" + leftWidth + "%");
        jLabelLogoRightWidth.setText("Largeur:" + rightWidth + "%");
        File logoLeft = new File(pdfDossardGenerator.getLogoLeft());
        File logoRight = new File(pdfDossardGenerator.getLogoRight());
        File pdfBackground = new File(pdfDossardGenerator.getPdfBackground());
        jTextFieldLogoLeft.setText((logoLeft.exists() && logoLeft.isFile()) ? pdfDossardGenerator.getLogoLeft() : "");
        jTextFieldLogoRight.setText((logoRight.exists() && logoRight.isFile()) ? pdfDossardGenerator.getLogoRight() : "");
        jTextFieldBackgroundPdf.setText((pdfBackground.exists() && pdfBackground.isFile()) ? pdfDossardGenerator.getPdfBackground() : "");
        
        if(participants.isEmpty()) {
            Participant p = new Participant();
            p.setDateInscription(new DateTime());
            p.setGroupe("Groupe");
            p.setNom("Nom");
            p.setPrenom("Prénom");
            p.setRenseignements("Renseignements");
            p.setNumero("012345");
            participants.add(p);
            return;
        }
        
        if(currentParticipant < 0 ||currentParticipant >= participants.size()) {
           currentParticipant = 0; 
        }
        
        try {
            Participant participant = participants.get(currentParticipant);
            List<Participant> lp = new LinkedList<Participant>();
            lp.add(participant);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            pdfDossardGenerator.createPdf(lp, bos);               
            BufferedImage img = PDFUtils.PdfToImage(bos.toByteArray());
            dip.setImage(img);
            bos.close();
        }
        catch(Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Impossible de créer le dossard: " + e.getMessage());
        }
    }
    
    private void readAndUpdateMargin() {
        try {
            String margin = jTextFieldMargin.getText();
            margin = margin.replace(',', '.');
            NumberFormat format = NumberFormat.getInstance(Locale.ENGLISH);
            Number value = format.parse(margin);
            double dvalue = value.doubleValue();
            pdfDossardGenerator.setMarginCm((float)dvalue);
        }
        catch(Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erreur de marge: " +e.getMessage());
        }
        refreshDisplay();
    }
    
    
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButtonPrevious = new javax.swing.JButton();
        jButtonNext = new javax.swing.JButton();
        jPanelImage = new javax.swing.JPanel();
        jPanelOptions = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jButtonLogoLeft = new javax.swing.JButton();
        jButtonLogoRight = new javax.swing.JButton();
        jTextFieldLogoLeft = new javax.swing.JTextField();
        jTextFieldLogoRight = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jTextFieldMargin = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jSliderLogoLeft = new javax.swing.JSlider();
        jSliderLogoRight = new javax.swing.JSlider();
        jLabelLogoLeftWidth = new javax.swing.JLabel();
        jLabelLogoRightWidth = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jTextFieldBackgroundPdf = new javax.swing.JTextField();
        jButtonBackgroundPdfBrowse = new javax.swing.JButton();

        jButtonPrevious.setIcon(new javax.swing.ImageIcon(getClass().getResource("/eyeofthetiger/gui/resources/gnome-go-previous_32x32.png"))); // NOI18N
        jButtonPrevious.setText("Participant précédent");
        jButtonPrevious.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonPreviousActionPerformed(evt);
            }
        });

        jButtonNext.setIcon(new javax.swing.ImageIcon(getClass().getResource("/eyeofthetiger/gui/resources/gnome-go-next_32x32.png"))); // NOI18N
        jButtonNext.setText("Participant suivant");
        jButtonNext.setHorizontalTextPosition(javax.swing.SwingConstants.LEADING);
        jButtonNext.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonNextActionPerformed(evt);
            }
        });

        jPanelImage.setBorder(javax.swing.BorderFactory.createTitledBorder("Résultat"));

        javax.swing.GroupLayout jPanelImageLayout = new javax.swing.GroupLayout(jPanelImage);
        jPanelImage.setLayout(jPanelImageLayout);
        jPanelImageLayout.setHorizontalGroup(
            jPanelImageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanelImageLayout.setVerticalGroup(
            jPanelImageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 338, Short.MAX_VALUE)
        );

        jPanelOptions.setBorder(javax.swing.BorderFactory.createTitledBorder("Options"));

        jLabel2.setText("Logo gauche:");

        jLabel3.setText("Logo droite:");

        jButtonLogoLeft.setText("...");
        jButtonLogoLeft.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonLogoLeftActionPerformed(evt);
            }
        });

        jButtonLogoRight.setText("...");
        jButtonLogoRight.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonLogoRightActionPerformed(evt);
            }
        });

        jTextFieldLogoLeft.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldLogoLeftActionPerformed(evt);
            }
        });

        jTextFieldLogoRight.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldLogoRightActionPerformed(evt);
            }
        });

        jLabel1.setText("Marges:");

        jTextFieldMargin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldMarginActionPerformed(evt);
            }
        });
        jTextFieldMargin.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTextFieldMarginFocusLost(evt);
            }
        });

        jLabel4.setText("cm");

        jSliderLogoLeft.setMinorTickSpacing(1);
        jSliderLogoLeft.setPaintLabels(true);
        jSliderLogoLeft.setSnapToTicks(true);
        jSliderLogoLeft.setToolTipText("Largeur du logo en % de la largeur du dossard (defaut: 20%)");
        jSliderLogoLeft.setValue(20);
        jSliderLogoLeft.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jSliderLogoLeftStateChanged(evt);
            }
        });

        jSliderLogoRight.setMinorTickSpacing(1);
        jSliderLogoRight.setPaintLabels(true);
        jSliderLogoRight.setSnapToTicks(true);
        jSliderLogoRight.setToolTipText("Largeur du logo en % de la largeur du dossard (defaut: 20%)");
        jSliderLogoRight.setValue(20);
        jSliderLogoRight.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jSliderLogoRightStateChanged(evt);
            }
        });

        jLabelLogoLeftWidth.setText("Largeur:20%");

        jLabelLogoRightWidth.setText("Largeur:20%");

        jLabel5.setText("PDF de fond:");

        jTextFieldBackgroundPdf.setToolTipText("");
        jTextFieldBackgroundPdf.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldBackgroundPdfActionPerformed(evt);
            }
        });

        jButtonBackgroundPdfBrowse.setText("...");
        jButtonBackgroundPdfBrowse.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonBackgroundPdfBrowseActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanelOptionsLayout = new javax.swing.GroupLayout(jPanelOptions);
        jPanelOptions.setLayout(jPanelOptionsLayout);
        jPanelOptionsLayout.setHorizontalGroup(
            jPanelOptionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelOptionsLayout.createSequentialGroup()
                .addGroup(jPanelOptionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelOptionsLayout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextFieldBackgroundPdf, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanelOptionsLayout.createSequentialGroup()
                        .addGroup(jPanelOptionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanelOptionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextFieldLogoLeft)
                            .addComponent(jTextFieldLogoRight))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelOptionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButtonLogoLeft, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButtonBackgroundPdfBrowse, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButtonLogoRight, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelOptionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelOptionsLayout.createSequentialGroup()
                        .addGroup(jPanelOptionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabelLogoLeftWidth)
                            .addComponent(jLabelLogoRightWidth))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanelOptionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jSliderLogoRight, javax.swing.GroupLayout.DEFAULT_SIZE, 334, Short.MAX_VALUE)
                            .addComponent(jSliderLogoLeft, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(jPanelOptionsLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextFieldMargin, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel4))))
        );
        jPanelOptionsLayout.setVerticalGroup(
            jPanelOptionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelOptionsLayout.createSequentialGroup()
                .addGroup(jPanelOptionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jTextFieldBackgroundPdf, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonBackgroundPdfBrowse)
                    .addComponent(jLabel1)
                    .addComponent(jTextFieldMargin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelOptionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanelOptionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel2)
                        .addComponent(jButtonLogoLeft)
                        .addComponent(jTextFieldLogoLeft, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabelLogoLeftWidth))
                    .addComponent(jSliderLogoLeft, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelOptionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelOptionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel3)
                        .addComponent(jTextFieldLogoRight, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jButtonLogoRight)
                        .addComponent(jLabelLogoRightWidth))
                    .addComponent(jSliderLogoRight, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jButtonPrevious)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButtonNext))
            .addComponent(jPanelOptions, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanelImage, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanelOptions, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanelImage, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonPrevious)
                    .addComponent(jButtonNext)))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonNextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonNextActionPerformed
        currentParticipant++;
        refreshDisplay();
    }//GEN-LAST:event_jButtonNextActionPerformed

    private void jButtonPreviousActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonPreviousActionPerformed
       currentParticipant--;
       refreshDisplay();
    }//GEN-LAST:event_jButtonPreviousActionPerformed

    private void jButtonLogoLeftActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonLogoLeftActionPerformed
        JFileChooser chooser = new JFileChooser();
        chooser.addChoosableFileFilter(Utils.IMAGE_FILE_FILTER);
        chooser.setFileFilter(Utils.IMAGE_FILE_FILTER);  
        chooser.setCurrentDirectory(Utils.GetMyDocumentsFolder());
        chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        if(chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
           String filePath = chooser.getSelectedFile().getAbsolutePath();
           if(!chooser.getSelectedFile().exists()  || !chooser.getSelectedFile().isFile()){
               filePath = "";
           }
           pdfDossardGenerator.setLogoLeft(filePath);
           refreshDisplay();
        }
    }//GEN-LAST:event_jButtonLogoLeftActionPerformed

    private void jButtonLogoRightActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonLogoRightActionPerformed
        JFileChooser chooser = new JFileChooser();
        chooser.addChoosableFileFilter(Utils.IMAGE_FILE_FILTER);
        chooser.setFileFilter(Utils.IMAGE_FILE_FILTER);        
        chooser.setCurrentDirectory(Utils.GetMyDocumentsFolder());
        chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        if(chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
           String filePath = chooser.getSelectedFile().getAbsolutePath();
           if(!chooser.getSelectedFile().exists()  || !chooser.getSelectedFile().isFile()){
               filePath = "";
           }
           pdfDossardGenerator.setLogoRight(filePath);
           refreshDisplay();
        }
    }//GEN-LAST:event_jButtonLogoRightActionPerformed

    private void jTextFieldMarginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldMarginActionPerformed
        readAndUpdateMargin();
    }//GEN-LAST:event_jTextFieldMarginActionPerformed

    private void jTextFieldMarginFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextFieldMarginFocusLost
        readAndUpdateMargin();
    }//GEN-LAST:event_jTextFieldMarginFocusLost

    private void jSliderLogoLeftStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jSliderLogoLeftStateChanged
        if(!jSliderLogoLeft.getValueIsAdjusting()) {
            int value = jSliderLogoLeft.getValue();
            if(value < 0) {
                value = 0;
            }
            if(value > 100) {
                value = 100;
            }
            float w = ((float)value) / 100f;
            pdfDossardGenerator.setLogoLeftWidth(w);
            refreshDisplay();
        }
    }//GEN-LAST:event_jSliderLogoLeftStateChanged

    private void jSliderLogoRightStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jSliderLogoRightStateChanged
        if(!jSliderLogoRight.getValueIsAdjusting()) {
            int value = jSliderLogoRight.getValue();
            if(value < 0) {
                value = 0;
            }
            if(value > 100) {
                value = 100;
            }
            float w = ((float)value) / 100f;
            pdfDossardGenerator.setLogoRightWidth(w);
            refreshDisplay();
        }
    }//GEN-LAST:event_jSliderLogoRightStateChanged

    private void jTextFieldLogoLeftActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldLogoLeftActionPerformed
        File file = new File(jTextFieldLogoLeft.getText().trim());
        String filePath = file.getAbsolutePath();
        if(!file.exists()  || !file.isFile()){
            filePath = "";
        }
        pdfDossardGenerator.setLogoLeft(filePath);
        refreshDisplay();
    }//GEN-LAST:event_jTextFieldLogoLeftActionPerformed

    private void jTextFieldLogoRightActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldLogoRightActionPerformed
        File file = new File(jTextFieldLogoRight.getText().trim());
        String filePath = file.getAbsolutePath();
        if(!file.exists()  || !file.isFile()){
            filePath = "";
        }
        pdfDossardGenerator.setLogoRight(filePath);
        refreshDisplay();
    }//GEN-LAST:event_jTextFieldLogoRightActionPerformed

    private void jTextFieldBackgroundPdfActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldBackgroundPdfActionPerformed
        File file = new File(jTextFieldBackgroundPdf.getText().trim());
        String filePath = file.getAbsolutePath();
        if(!file.exists()  || !file.isFile()){
            filePath = "";
        }
        pdfDossardGenerator.setPdfBackground(filePath);
        refreshDisplay();
    }//GEN-LAST:event_jTextFieldBackgroundPdfActionPerformed

    private void jButtonBackgroundPdfBrowseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonBackgroundPdfBrowseActionPerformed
        JFileChooser chooser = new JFileChooser();
        chooser.addChoosableFileFilter(Utils.PDF_FILE_FILTER);
        chooser.setFileFilter(Utils.PDF_FILE_FILTER);
        
        chooser.setCurrentDirectory(Utils.GetMyDocumentsFolder());
        chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        if(chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
           String filePath = chooser.getSelectedFile().getAbsolutePath();
           if(!chooser.getSelectedFile().exists()  || !chooser.getSelectedFile().isFile()){
               filePath = "";
           }
           pdfDossardGenerator.setPdfBackground(filePath);
           refreshDisplay();
        }
    }//GEN-LAST:event_jButtonBackgroundPdfBrowseActionPerformed
   
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonBackgroundPdfBrowse;
    private javax.swing.JButton jButtonLogoLeft;
    private javax.swing.JButton jButtonLogoRight;
    private javax.swing.JButton jButtonNext;
    private javax.swing.JButton jButtonPrevious;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabelLogoLeftWidth;
    private javax.swing.JLabel jLabelLogoRightWidth;
    private javax.swing.JPanel jPanelImage;
    private javax.swing.JPanel jPanelOptions;
    private javax.swing.JSlider jSliderLogoLeft;
    private javax.swing.JSlider jSliderLogoRight;
    private javax.swing.JTextField jTextFieldBackgroundPdf;
    private javax.swing.JTextField jTextFieldLogoLeft;
    private javax.swing.JTextField jTextFieldLogoRight;
    private javax.swing.JTextField jTextFieldMargin;
    // End of variables declaration//GEN-END:variables


    
        
public static class DossardImageDisplayPanel extends JPanel
{
    private BufferedImage image;
    public void setImage(BufferedImage img) {
        image = img;
        repaint();
    }
    
    @Override
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g); 
        if(image != null) {
            //respect ratio!
            double w = image.getWidth();
            double h = image.getHeight();
            double newW = this.getWidth();
            double newH = (h / w) * newW;
            if(newH > this.getHeight()) {
                newH = this.getHeight();
                newW = (w/h) * newH;
            }
            int xOffset = (int)((this.getWidth() - newW) / 2);
            int yOffset = (int)((this.getHeight() - newH) / 2);
            g.drawImage(image, xOffset, yOffset,(int)newW,(int)newH, this);
        }
    }
}


}
