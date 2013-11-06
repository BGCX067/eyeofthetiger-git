/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eyeofthetiger.model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import org.joda.time.DateTime;

/**
 *
 * @author christophe
 */
public class Participant {
    
    public Participant() {
    }
    
    protected String nom="";
    public static final String PROP_NOM = "nom";
    public String getNom() {
        return nom;
    }
    public void setNom(String nom) {
        String oldNom = this.nom;
        this.nom = nom;
        propertyChangeSupport.firePropertyChange(PROP_NOM, oldNom, nom);
    }
    
    protected String prenom = "";
    public static final String PROP_PRENOM = "prenom";
    public String getPrenom() {
        return prenom;
    }
    public void setPrenom(String prenom) {
        String oldPrenom = this.prenom;
        this.prenom = prenom;
        propertyChangeSupport.firePropertyChange(PROP_PRENOM, oldPrenom, prenom);
    }

    protected String numero = "";
    public static final String PROP_NUMERO = "numero";
    public String getNumero() {
        return numero;
    }
    public void setNumero(String numero) {
        String oldNumero = this.numero;
        this.numero = numero;
        propertyChangeSupport.firePropertyChange(PROP_NUMERO, oldNumero, numero);
    }

    protected String groupe = "";
    public static final String PROP_GROUPE = "groupe";
    public String getGroupe() {
        return groupe;
    }
    public void setGroupe(String groupe) {
        String oldGroupe = this.groupe;
        this.groupe = groupe;
        propertyChangeSupport.firePropertyChange(PROP_GROUPE, oldGroupe, groupe);
    }
    
    protected String renseignements = "";
    public static final String PROP_RENSEIGNEMENTS = "renseignements";
    public String getRenseignements() {
        return renseignements;
    }
    public void setRenseignements(String renseignements) {
        if(renseignements != null) {
            renseignements = renseignements.replace(';', ' ').replace('\n', ' ').replace("\r","");
        }
        String oldRenseignements = this.renseignements;
        this.renseignements = renseignements;
        propertyChangeSupport.firePropertyChange(PROP_RENSEIGNEMENTS, oldRenseignements, renseignements);
    }
    

    protected DateTime dateInscription = new DateTime();
    public static final String PROP_DATEINSCRIPTION = "dateInscription";
    public DateTime getDateInscription() {
        return dateInscription;
    }
    public void setDateInscription(DateTime dateInscription) {
        DateTime oldDateInscription = this.dateInscription;
        this.dateInscription = dateInscription;
        propertyChangeSupport.firePropertyChange(PROP_DATEINSCRIPTION, oldDateInscription, dateInscription);
    }

    
    public static Participant CreateInconnu(String numero) {
        Participant participant = new Participant();
        participant.setNumero(numero);
        participant.setNom("_inconnu_");
        participant.setPrenom("_inconnu_");
        participant.setGroupe("_inconnu_");
        participant.setDateInscription(new DateTime());
        return participant;
    }
    
    
    
    private PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        propertyChangeSupport.addPropertyChangeListener(listener);
    }
    public void removePropertyChangeListener(PropertyChangeListener listener) {
        propertyChangeSupport.removePropertyChangeListener(listener);
    }

    
}
