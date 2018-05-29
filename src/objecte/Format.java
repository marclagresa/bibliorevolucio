package objecte;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Format {
    private SimpleIntegerProperty id;
    private SimpleStringProperty nom;
    private SimpleBooleanProperty activa;
    

    //Constructor Buit

    public Format(){
        this.id = new SimpleIntegerProperty(-1);
        this.nom = new SimpleStringProperty("");
        this.activa=new SimpleBooleanProperty(true);
    }

    //Constructor Total

    public Format(Integer id, String nom,boolean activa){
        this.id = new SimpleIntegerProperty(id);
        this.nom = new SimpleStringProperty(nom);
        this.activa = new SimpleBooleanProperty(activa);
    }
    
    public Format(Integer id, String nom){
        this.id = new SimpleIntegerProperty(id);
        this.nom = new SimpleStringProperty(nom);
        this.activa = new SimpleBooleanProperty(true);
    }

    //Getters
    public boolean isActiva(){return activa.get();}
    public int getId() { return id.get(); }
    public String getNom() { return nom.get(); }

    //Setters
    public void setActiva(boolean activa){this.activa.set(activa);}
    public void setId(int id) { this.id.set(id); }
    public void setNom(String nom) { this.nom.set(nom); }

    //Properties

    public SimpleIntegerProperty idProperty() { return id; }
    public SimpleStringProperty nomProperty() { return nom; }
    public SimpleBooleanProperty activaProperty() {return activa;}
    //To String

    @Override
    public String toString(){
        return this.getNom();
    }
}
