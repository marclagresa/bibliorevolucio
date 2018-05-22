package objecte;

import javafx.beans.property.SimpleStringProperty;

public class Cdu {
    private SimpleStringProperty id;
    private SimpleStringProperty idPare;
    private SimpleStringProperty nom;

    //Constructor Buit

    public Cdu(){
        this.id = new SimpleStringProperty("");
        this.idPare = new SimpleStringProperty("");
        this.nom = new SimpleStringProperty("");
    }

    //Constructor Total

    public Cdu(String id, String idPare, String nom){
        this.id = new SimpleStringProperty(id);
        this.idPare = new SimpleStringProperty(idPare);
        this.nom = new SimpleStringProperty(nom);
    }

    //Getters

    public String getId() { return id.get(); }
    public String getIdPare() { return idPare.get(); }
    public String getNom() { return nom.get(); }

    //Setters

    public void setId(String id) { this.id.set(id); }
    public void setIdPare(String idPare) { this.idPare.set(idPare); }
    public void setNom(String nom) { this.nom.set(nom); }

    //Properties

    public SimpleStringProperty idProperty() { return id; }
    public SimpleStringProperty idPareProperty() { return idPare; }
    public SimpleStringProperty nomProperty() { return nom; }
}
