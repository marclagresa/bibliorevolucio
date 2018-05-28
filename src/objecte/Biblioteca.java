package objecte;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Biblioteca {
    private SimpleIntegerProperty id;
    private SimpleStringProperty nom;
    private SimpleBooleanProperty activa;

    //Constructor Buit

    public Biblioteca(){
        this.id = new SimpleIntegerProperty();
        this.nom = new SimpleStringProperty("");
        this.activa=new SimpleBooleanProperty(true);
    }

    //Constructor Total

    public Biblioteca(Integer id, String nom,Boolean activa){
        this.id = new SimpleIntegerProperty(id);
        this.nom = new SimpleStringProperty(nom);
        this.activa=new SimpleBooleanProperty(activa);
    }
    public Biblioteca(Integer id,String nom){
        this.id = new SimpleIntegerProperty(id);
        this.nom = new SimpleStringProperty(nom);
        this.activa=new SimpleBooleanProperty(true);
    }
    //Getters

    public int getId() { return id.get(); }
    public String getNom() { return nom.get(); }
    public boolean isActiva () {return activa.get();}

    //Setters
    public void setActiva(boolean activa){this.activa.set(activa);}
    public void setId(int id) { this.id.set(id); }
    public void setNom(String nom) { this.nom.set(nom); }

    //Properties
    public SimpleBooleanProperty activaProperty(){return activa;}
    public SimpleIntegerProperty idProperty() { return id; }
    public SimpleStringProperty nomProperty() { return nom; }

    //To String

    @Override
    public String toString(){
        return this.getNom();
    }
}
