/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classes;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;


/**
 *
 * @author sergiclotas
 */
public class Editorial {
    
    private final IntegerProperty id; //Id de la editorial
    private final StringProperty nom;  //Nom de la editorial
    private final StringProperty pais; //Pais de la editorial
    private final StringProperty adreca; //Adreça de la editorial
    
    /**
     * Constructor amb valors per defecte:
     * id = -1
     * nom = ""
     * pais = ""
     * adreca = ""
     * 
     */
    public Editorial() {
        this.id = new SimpleIntegerProperty(-1);
        this.nom = new SimpleStringProperty("");
        this.pais = new SimpleStringProperty("");
        this.adreca = new SimpleStringProperty("");
    }
    /**
     * Constructor amb parametres
     * @param id Editorial
     * @param nom Editorial
     * @param pais Editorial 
     * @param adreca Editorial
     */
    public Editorial(int id,String nom,String pais,String adreca){
        this.id=new SimpleIntegerProperty(id);
        this.nom = new SimpleStringProperty(nom);
        this.pais = new SimpleStringProperty(pais);
        this.adreca=new SimpleStringProperty(adreca);
    
    }
    //<editor-fold desc="Getters">
    /**
     * getter id
     * @return this.id.get()
     */
    public int getId(){
        return this.id.get();
    }
    
    /**
     * getter nom
     * @return this.nom.get()
     */
    public String getNom(){
        return this.nom.get();
    }
    
    /**
     * getter pais
     * @return this.pais.get()
     */
    public String getPais(){
        return this.pais.get();
    }
    
    /**
     * getter adreça
     * @return this.adreca.get()
     */
    public String getAdreca(){
        return this.adreca.get();
    }
    //</editor-fold>
    //<editor-fold desc="Setters">
    
    /**
     * Setter id
     * @param id nova 
     */
    public void setId(int id){
        this.id.set(id);
    }
    
    /**
     * Setter nom
     * @param nom nou 
     */
    public void setNom(String nom){
        this.nom.set(nom);
    }
    /**
     * Setter pais
     * @param pais nou
     */
    public void setPais(String pais){
        this.pais.set(pais);
    }
    /**
     * Setter adreça
     * @param adreca nova 
     */
    public void setAdreca(String adreca){
        this.adreca.set(adreca);
    }
    //</editor-fold>
    //<editor-fold desc="Property Getters">
    /**
     * Getter propietat id
     * @return this.id
     */
    public IntegerProperty getIdProperty(){
        return this.id;
    }
    
    /**
     * Getter propietat nom
     * @return this.nom
     */
    public StringProperty getNomProperty(){
        return this.nom;
    }
    
    /**
     * Getter propietat pais
     * @return this.pais
     */
    public StringProperty getPaisProperty(){
        return this.pais;
    }
    
    /**
     * Getter propietat adreça
     * @return this.adreca
     */
    public StringProperty getAdrecaProperty(){
        return this.adreca;
    }
    //</editor-fold>
    
    /**
     *  
     * @return el nom de la editorial 
     */
    @Override
    public String toString(){
        return this.getNom();
    }
}
