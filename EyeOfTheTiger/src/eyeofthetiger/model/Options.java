/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package eyeofthetiger.model;

import java.io.Serializable;


public class Options implements Serializable {

    private float marginCm = 0.5f;
    private String logoLeft = "";
    private String logoRight = "";
    private float logoLeftWidth = 0.2f;
    private float logoRightWidth = 0.2f;

    public float getMarginCm() {
        return marginCm;
    }

    public void setMarginCm(float marginCm) {
        this.marginCm = marginCm;
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

    public float getLogoLeftWidth() {
        return logoLeftWidth;
    }

    public void setLogoLeftWidth(float logoLeftWidth) {
        this.logoLeftWidth = logoLeftWidth;
    }

    public float getLogoRightWidth() {
        return logoRightWidth;
    }

    public void setLogoRightWidth(float logoRightWidth) {
        this.logoRightWidth = logoRightWidth;
    }
    
    
    
}
