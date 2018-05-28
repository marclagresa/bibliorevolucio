/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package objecte;


import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;


/**
 *
 * @author sergiclotas
 */
public class Editorial {
    
    private final SimpleIntegerProperty id; //Id de la editorial
    private final SimpleStringProperty nom;  //Nom de la editorial
    private final SimpleBooleanProperty activa;
    
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
        this.activa=new SimpleBooleanProperty(true);
    }
    /**
     * Constructor amb parametres
     * @param id Editorial
     * @param nom Editorial
     * @param activa Editorial
     */
    public Editorial(int id,String nom,boolean activa){
        this.id=new SimpleIntegerProperty(id);
        this.nom = new SimpleStringProperty(nom);
        this.activa = new SimpleBooleanProperty(activa);
    }   
    /**
     * Constructor amb parametres
     * @param id editorial
     * @param nom editorial
     */
    public Editorial(int id,String nom){
        this.id=new SimpleIntegerProperty(id);
        this.nom = new SimpleStringProperty(nom);
        this.activa = new SimpleBooleanProperty(true);
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
     * Getter activa
     * @return this.activa.get()
     */
    public boolean isActiva(){
        return this.activa.get();
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
     * Setter activa
     * @param activa nou
     */
    public void setActiva(boolean activa){
        this.activa.set(activa);
    }
    //</editor-fold>
    //<editor-fold desc="Property Getters">
    /**
     * Getter propietat id
     * @return this.id
     */
    public SimpleIntegerProperty idProperty(){
        return this.id;
    }
    /**
     * Getter propietat activa
     * @return this.activa
     */
    public SimpleBooleanProperty activaProperty(){
        return this.activa;
    }
    /**
     * Getter propietat nom
     * @return this.nom
     */
    public SimpleStringProperty nomProperty(){
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
