package objecte;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;

public class Exemplar {
    private SimpleIntegerProperty id;
    private SimpleBooleanProperty estat;
    private SimpleIntegerProperty numprestecs;
    private SimpleObjectProperty<Producte> producte;
    private SimpleObjectProperty<Biblioteca> biblioteca;

    //Constructor Buit

    public Exemplar(){
        this.id = new SimpleIntegerProperty();
        this.estat = new SimpleBooleanProperty(false);
        this.numprestecs = new SimpleIntegerProperty(0);
        this.producte = new SimpleObjectProperty<>();
        this.biblioteca = new SimpleObjectProperty<>();
    }

    //Constructor Total

    public Exemplar(Integer id, boolean estat, Integer numprestecs, Producte producte, Biblioteca biblioteca){
        this.id = new SimpleIntegerProperty(id);
        this.estat = new SimpleBooleanProperty(estat);
        this.numprestecs = new SimpleIntegerProperty(numprestecs);
        this.producte = new SimpleObjectProperty<>(producte);
        this.biblioteca = new SimpleObjectProperty<>(biblioteca);
    }

    //Getters

    public int getId() { return id.get(); }
    public boolean getEstat() { return estat.get(); }
    public int getNumprestecs() { return numprestecs.get(); }
    public Producte getProducte() { return producte.get(); }
    public Biblioteca getBiblioteca() { return biblioteca.get(); }

    //Setters

    public void setId(int id) { this.id.set(id); }
    public void setEstat(boolean estat) { this.estat.set(estat); }
    public void setNumprestecs(int numprestecs) { this.numprestecs.set(numprestecs); }
    public void setIdProducte(Producte producte) { this.producte.set(producte); }
    public void setIdBiblioteca(Biblioteca biblioteca) { this.biblioteca.set(biblioteca); }

    //Properties

    public SimpleIntegerProperty idProperty() { return id; }
    public SimpleBooleanProperty estatProperty() { return estat; }
    public SimpleIntegerProperty numprestecsProperty() { return numprestecs; }
    public SimpleObjectProperty<Producte> producteProperty() { return producte; }
    public SimpleObjectProperty<Biblioteca> bibliotecaProperty() { return biblioteca; }
}
