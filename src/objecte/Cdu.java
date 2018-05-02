package objecte;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Cdu {
    private SimpleIntegerProperty id;
    private SimpleIntegerProperty idPare;
    private SimpleStringProperty nom;

    //Constructor Buit

    public Cdu(){
        this.id = new SimpleIntegerProperty();
        this.idPare = new SimpleIntegerProperty();
        this.nom = new SimpleStringProperty("");
    }

    //Constructor Total

    public Cdu(Integer id, Integer idPare, String nom){
        this.id = new SimpleIntegerProperty(id);
        this.idPare = new SimpleIntegerProperty(idPare);
        this.nom = new SimpleStringProperty(nom);
    }

    //Getters

    public int getId() { return id.get(); }
    public int getIdPare() { return idPare.get(); }
    public String getNom() { return nom.get(); }

    //Setters

    public void setId(int id) { this.id.set(id); }
    public void setIdPare(int idPare) { this.idPare.set(idPare); }
    public void setNom(String nom) { this.nom.set(nom); }

    //Properties

    public SimpleIntegerProperty idProperty() { return id; }
    public SimpleIntegerProperty idPareProperty() { return idPare; }
    public SimpleStringProperty nomProperty() { return nom; }
}
