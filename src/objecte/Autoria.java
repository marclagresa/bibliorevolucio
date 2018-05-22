package objecte;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

public class Autoria {
    private SimpleIntegerProperty id;
    private SimpleObjectProperty<Producte> producte;
    private SimpleObjectProperty<Persona> persona;
    private SimpleStringProperty descripcio;

    //Constructor Simple

    public Autoria() {
        this.id = new SimpleIntegerProperty();
        this.producte = new SimpleObjectProperty<>();
        this.persona = new SimpleObjectProperty<>();
        this.descripcio = new SimpleStringProperty();
    }

    //Constructor Complet

    public Autoria(Integer id, Producte producte, Persona persona, String descripcio) {
        this.id = new SimpleIntegerProperty(id);
        this.producte = new SimpleObjectProperty<>(producte);
        this.persona = new SimpleObjectProperty<>(persona);
        this.descripcio = new SimpleStringProperty(descripcio);
    }

    //Getters

    public int getId() { return id.get(); }
    public Producte getProducte() { return producte.get(); }
    public Persona getPersona() { return persona.get(); }
    public String getDescripcio() { return descripcio.get(); }

    //Setters

    public void setId(int id) { this.id.set(id); }
    public void setProducte(Producte producte) { this.producte.set(producte); }
    public void setPersona(Persona persona) { this.persona.set(persona); }
    public void setDescripcio(String descripcio) { this.descripcio.set(descripcio); }

    //Property

    public SimpleIntegerProperty idProperty() { return id; }
    public SimpleObjectProperty<Producte> producteProperty() { return producte; }
    public SimpleObjectProperty<Persona> personaProperty() { return persona; }
    public SimpleStringProperty descripcioProperty() { return descripcio; }

    //To String

    @Override
    public String toString(){
        return this.getDescripcio();
    }
}

