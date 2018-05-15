/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package objecte;


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

    }
    /**
     * Constructor amb parametres
     * @param id Editorial
     * @param nom Editorial
     * @param pais Editorial 
     * @param adreca Editorial
     */
    public Editorial(int id,String nom){
        this.id=new SimpleIntegerProperty(id);
        this.nom = new SimpleStringProperty(nom);

    
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
    //</editor-fold>
    //<editor-fold desc="Property Getters">
    /**
     * Getter propietat id
     * @return this.id
     */
    public IntegerProperty idProperty(){
        return this.id;
    }
    
    /**
     * Getter propietat nom
     * @return this.nom
     */
    public StringProperty nomProperty(){
        return this.nom;
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
