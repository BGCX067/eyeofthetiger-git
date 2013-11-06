/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package eyeofthetiger.utils;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.Barcode128;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfWriter;
import eyeofthetiger.model.Participant;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

/**
 *
 * @author thecat
 */
public class PDFDossardGenerator {
    
    public static float UserSpaceToCentimeter(float us) {
        //1 in. = 25.4 mm = 72 user space ~= 72 pt 
        return us * (2.54f / 72f); 
    }
    public static float CentimeterToUserSpace(float cm) {
        //1 in. = 25.4 mm = 72 user space ~= 72 pt 
        return cm * (72f / 2.54f); 
    }
    
    
    public PDFDossardGenerator() {
        
    }
    
    private boolean exportName = true;
    private boolean exportGroup = true;
    private boolean exportRenseignement = true;
    private boolean exportLogos = true;
    
    private float marginCm = 0.5f;
    private float logoLeftWidth = 0.2f;
    private float logoRightWidth = 0.2f;
    
    private String logoLeft = "";
    private String logoRight = "";

    public boolean isExportName() {
        return exportName;
    }

    public void setExportName(boolean exportName) {
        this.exportName = exportName;
    }

    public boolean isExportGroup() {
        return exportGroup;
    }

    public void setExportGroup(boolean exportGroup) {
        this.exportGroup = exportGroup;
    }

    public boolean isExportRenseignement() {
        return exportRenseignement;
    }

    public void setExportRenseignement(boolean exportRenseignement) {
        this.exportRenseignement = exportRenseignement;
    }

    public boolean isExportLogos() {
        return exportLogos;
    }

    public void setExportLogos(boolean exportLogos) {
        this.exportLogos = exportLogos;
    }

    public String getLogoLeft() {
        return logoLeft;
    }

    public void setLogoLeft(String logoLeft) {
        this.logoLeft = logoLeft;
    }

    public String getLogoRight() {
        return logoRight;
    }

    public void setLogoRight(String logoRight) {
        this.logoRight = logoRight;
    }
    
    public void setMarginCm(float cm) {
        marginCm = cm;
    }
    public float getMarginCm() {
        return marginCm;
    }
    public void setLogoLeftWidth(float w) {
        logoLeftWidth = w;
    }
    public float getLogoLeftWidth() {
        return logoLeftWidth;
    }
    public void setLogoRightWidth(float w) {
        logoRightWidth = w;
    }    
    public float getLogoRightWidht() {
        return logoRightWidth;
    }
    
    
    
    
    private Phrase createCleanPhrase(String txt1, float fontSize1, boolean bold1) {
        Phrase phrase = new Phrase(txt1);
        phrase.getFont().setSize(fontSize1);
        if(bold1) {
            phrase.getFont().setStyle(Font.BOLD);
            phrase.setLeading(fontSize1);  
        }
        
        return phrase;
    }
      
    
    private Element createCleanBarcode(PdfContentByte cb,String numero, float width,float height) {
        Barcode128 code128 = new Barcode128();
        code128.setCode(numero);
        Image barcodeImage = code128.createImageWithBarcode(cb, null, null);
        barcodeImage.setAlignment(Image.ALIGN_CENTER);
        //barcodeImage.setWidthPercentage(75f);
        barcodeImage.scaleToFit(width, height);
        return barcodeImage;        
    }
    

    public void createPdf(List<Participant> participants, OutputStream out) throws IOException, DocumentException {
        Document document = new Document(PageSize.A4.rotate());
        float margin = CentimeterToUserSpace(marginCm);
        document.setMargins(margin,margin,margin,margin);
        PdfWriter writer = PdfWriter.getInstance(document, out);
        document.open();
        PdfContentByte cb = writer.getDirectContent();
        
        float documentTop = document.top();
        float documentBottom = document.bottom();
        float documentHeight = documentTop - documentBottom;
        float left = document.left();
        float right = document.right();
        float width = right - left;
        float height = documentTop - documentBottom;
        
        //cb.rectangle(left, documentBottom, width, documentHeight);
        //cb.stroke();
        
        boolean logoLeftExist = (new File(logoLeft)).exists() && (new File(logoLeft)).isFile();
        boolean logoRightExist = (new File(logoRight)).exists() && (new File(logoRight)).isFile();
        
        float imgLeftRight = left;
        float imgLeftBottom = documentTop;
        float imgRightLeft = right;
        float imgRighBottom = documentTop;
        Image imgLeft = null;
        Image imgRight = null;
        if(exportLogos) {
            if(logoLeftExist){
                imgLeft = Image.getInstance(logoLeft);
                float h = imgLeft.getHeight();
                float w = imgLeft.getWidth();
                float nw = width * logoLeftWidth;
                float nh = (h / w) * nw;
                imgLeft.scaleAbsolute(nw, nh);
                //img.scaleAbsoluteHeight(img.getScaledWidth() / xyRatio);
                imgLeft.setAbsolutePosition(left, documentTop - imgLeft.getScaledHeight());
                //cb.addImage(img);   
                
                imgLeftRight = imgLeft.getAbsoluteX() + imgLeft.getScaledWidth();
                imgLeftBottom = imgLeft.getAbsoluteY();
            }
            
            if(logoRightExist){
                imgRight = Image.getInstance(logoRight);
                float h = imgRight.getHeight();
                float w = imgRight.getWidth();
                float nw = width * logoRightWidth;
                float nh = (h / w) * nw;
                imgRight.scaleAbsolute(nw, nh);
                imgRight.setAbsolutePosition(right - imgRight.getScaledWidth(), documentTop - imgRight.getScaledHeight());
                //cb.addImage(imgRight);
                imgRightLeft = imgRight.getAbsoluteX();
                imgRighBottom = imgRight.getAbsoluteY();
            }
            
            
        }
      
        
        float nameHeightPercent = 0.35f;
        float groupHeightPercent = 0.25f; 

        
        float nameTop = documentTop ;
        float nameBottom = nameTop;
        if(exportName) {
            nameBottom = nameTop - (documentHeight * nameHeightPercent);
        }
        float groupeTop = nameBottom;
        float groupeBottom = nameBottom;
        if(exportGroup) {
            groupeBottom = groupeTop - (documentHeight *  groupHeightPercent);
        }
        float barcodeTop = groupeBottom;
        float barcodeBottom = documentBottom;
        
        
        ColumnText columnText;
        
        for(Participant participant : participants) {

            float nameFontSize = 65f;
            float groupFontSize = 45f;
            float renseignementFontSize = 35f;

            if(imgLeft != null) {
                cb.addImage(imgLeft);
            }
            if(imgRight != null) {
                cb.addImage(imgRight);
            }
            
            if(exportName) {
                columnText = new ColumnText(cb);
                columnText.setAlignment(Rectangle.ALIGN_CENTER);
                
                if(imgLeftRight != -1 && imgLeftBottom != -1) {
                    float[] leftBorder = null;
                    if(imgLeftBottom < nameBottom) {
                        leftBorder = new float[] {imgLeftRight,nameTop,imgLeftRight,nameBottom,left,nameBottom};
                    }
                    else {
                        leftBorder = new float[] {imgLeftRight,nameTop,imgLeftRight,imgLeftBottom,left,imgLeftBottom,left,nameBottom};
                    }

                    float[] rightBorder = null;
                    if(imgRighBottom < nameBottom) {
                        rightBorder = new float[] {imgRightLeft,nameTop,imgRightLeft,nameBottom,right,nameBottom};
                    }
                    else {
                        rightBorder = new float[] {imgRightLeft,nameTop,imgRightLeft,imgRighBottom,right,imgRighBottom,right,nameBottom};
                    }   
                    
                    columnText.setColumns(leftBorder, rightBorder);
                }
                else {
                    columnText.setSimpleColumn(left, nameTop, right, nameBottom);
                }
                //cb.rectangle(left, nameBottom, width, (nameTop - nameBottom));
                //cb.stroke();
                
                columnText.setExtraParagraphSpace(0f);
                columnText.setAdjustFirstLine(false);
                columnText.setIndent(0);
                
                String txt = participant.getNom().toUpperCase() + " " + participant.getPrenom();
                
                float previousPos = columnText.getYLine();
                columnText.setLeading(nameFontSize);
                columnText.setText(createCleanPhrase(txt,nameFontSize,true));
                while(nameFontSize > 1 && ColumnText.hasMoreText(columnText.go(true))) {
                    nameFontSize = nameFontSize - 0.5f;
                    columnText.setLeading(nameFontSize);
                    columnText.setText(createCleanPhrase(txt,nameFontSize,true));
                    columnText.setYLine(previousPos);
                } 
              
                columnText.setLeading(nameFontSize);
                columnText.setText(createCleanPhrase(txt,nameFontSize,true));
                columnText.setYLine(previousPos);
                columnText.go(false);

            }
            
           
            
            if(exportGroup) {
                columnText = new ColumnText(cb);
                columnText.setAlignment(Rectangle.ALIGN_CENTER);

                columnText.setSimpleColumn(document.left(), groupeTop, document.right(), groupeBottom);
                float groupeHeight = groupeTop - groupeBottom;
                //cb.rectangle(document.left(), groupeTop - groupeHeight, document.right() - document.left(), groupeHeight);
                //cb.stroke();
                
                columnText.setExtraParagraphSpace(0f);
                columnText.setAdjustFirstLine(false);
                columnText.setIndent(0);
                columnText.setFollowingIndent(0);
                
                String txt1 = participant.getGroupe();
                String txt2 = exportRenseignement ? "\n"+participant.getRenseignements() : null;
                
                float previousPos = columnText.getYLine();
                columnText.setText(null);
                columnText.setLeading(groupFontSize);
                columnText.addText(createCleanPhrase(txt1,groupFontSize,true));
                columnText.addText(createCleanPhrase(txt2,renseignementFontSize,false));   
                while(groupFontSize > 1 && ColumnText.hasMoreText(columnText.go(true))) {
                    groupFontSize = groupFontSize - 0.5f;
                    renseignementFontSize = renseignementFontSize - 0.5f;
                    columnText.setText(null);
                    columnText.setLeading(groupFontSize);
                    columnText.addText(createCleanPhrase(txt1,groupFontSize,true));
                    columnText.addText(createCleanPhrase(txt2,renseignementFontSize,false));                 
                    columnText.setYLine(previousPos);
                } 
              
                columnText.setText(null);
                columnText.setLeading(groupFontSize);
                columnText.addText(createCleanPhrase(txt1,groupFontSize,true));
                columnText.addText(createCleanPhrase(txt2,renseignementFontSize,false));               
                columnText.setYLine(previousPos);
                columnText.go(false);
            }
           
            
            {
                columnText = new ColumnText(cb);

                float topMargin = 12f;
                columnText.setSimpleColumn(left, barcodeTop-topMargin, right, barcodeBottom);
                float barcodeHeight = (barcodeTop-topMargin) - barcodeBottom;
                //cb.rectangle(left, barcodeTop - barcodeHeight, width, barcodeHeight);
                //cb.stroke();
                
                columnText.setExtraParagraphSpace(0f);
                columnText.setAdjustFirstLine(false);
                columnText.setIndent(0);
                
                float previousPos = columnText.getYLine();
                columnText.setText(null);
                columnText.addElement(createCleanBarcode(cb, participant.getNumero(),width,barcodeHeight));
                columnText.go(false);                
            }
            
            
            document.newPage();
                   
        }
       
        document.close();        
    }  
}
