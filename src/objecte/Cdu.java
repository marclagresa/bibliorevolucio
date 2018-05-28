package objecte;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;

public class Cdu {
    private SimpleStringProperty id;
    private SimpleStringProperty idPare;
    private SimpleStringProperty nom;
    private SimpleBooleanProperty activa;
    //Constructor Buit

    public Cdu(){
        this.id = new SimpleStringProperty("");
        this.idPare = new SimpleStringProperty("");
        this.nom = new SimpleStringProperty("");
        this.activa = new SimpleBooleanProperty(true);
    }

    //Constructor Total

    public Cdu(String id, String idPare, String nom,boolean activa){
        this.id = new SimpleStringProperty(id);
        this.idPare = new SimpleStringProperty(idPare);
        this.nom = new SimpleStringProperty(nom);
        this.activa=new SimpleBooleanProperty(activa);
    }

    //Getters
    public Boolean isActiva (){return activa.get();}
    public String getId() { return id.get(); }
    public String getIdPare() { return idPare.get(); }
    public String getNom() { return nom.get(); }

    //Setters
    public void setActiva(boolean activa){this.activa.set(activa);}
    public void setId(String id) { this.id.set(id); }
    public void setIdPare(String idPare) { this.idPare.set(idPare); }
    public void setNom(String nom) { this.nom.set(nom); }

    //Properties
    public SimpleBooleanProperty activaProperty(){return activa;}
    public SimpleStringProperty idProperty() { return id; }
    public SimpleStringProperty idPareProperty() { return idPare; }
    public SimpleStringProperty nomProperty() { return nom; }

    //To String

    @Override
    public String toString(){
        return this.getNom();
    }
}
