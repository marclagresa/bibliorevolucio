package objecte;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

public class Producte {
    private SimpleIntegerProperty id;
    private SimpleStringProperty ISBN;
    private SimpleStringProperty nom;
    private SimpleIntegerProperty num_pag;
    private SimpleStringProperty dimensions;
    private SimpleStringProperty data;
    private SimpleStringProperty resum;
    private SimpleStringProperty caracteristiques;
    private SimpleStringProperty urlPortada;
    private SimpleStringProperty adreçaWeb;
    private SimpleBooleanProperty estat;
    private SimpleObjectProperty<Idioma> idioma;
    private SimpleObjectProperty<Editorial> editorial;
    private SimpleObjectProperty<Format> format;
    private SimpleObjectProperty<Procedencia> procedencia;
    private SimpleObjectProperty<Nivell> nivell;
    private SimpleObjectProperty<Coleccio> coleccio;
    private SimpleObjectProperty<Cdu> cdu;

    //Constructor Buit

    public Producte(){
        this.id = new SimpleIntegerProperty();
        this.ISBN = new SimpleStringProperty("");
        this.nom = new SimpleStringProperty("");
        this.num_pag = new SimpleIntegerProperty(-1);
        this.dimensions = new SimpleStringProperty("");
        this.data = new SimpleStringProperty("");
        this.resum = new SimpleStringProperty("");
        this.caracteristiques = new SimpleStringProperty("");
        this.urlPortada = new SimpleStringProperty("");
        this.adreçaWeb = new SimpleStringProperty("");
        this.estat = new SimpleBooleanProperty(false);
        this.idioma = new SimpleObjectProperty<>();
        this.editorial = new SimpleObjectProperty<>();
        this.format = new SimpleObjectProperty<>();
        this.procedencia = new SimpleObjectProperty<>();
        this.nivell = new SimpleObjectProperty<>();
        this.coleccio = new SimpleObjectProperty<>();
        this.cdu = new SimpleObjectProperty<>();
    }

    //Constructor Total

    public Producte(Integer id, String ISBN, String nom, Integer num_pag, String dimensions, String data, String resum,
                    String caracteristiques, String urlPortada, String adreçaWeb, boolean estat, Idioma idioma,
                    Editorial editorial, Format format, Procedencia procedencia, Nivell nivell, Coleccio coleccio,
                    Cdu cdu){
        this.id = new SimpleIntegerProperty(id);
        this.ISBN = new SimpleStringProperty(ISBN);
        this.nom = new SimpleStringProperty(nom);
        this.num_pag = new SimpleIntegerProperty(num_pag);
        this.dimensions = new SimpleStringProperty(dimensions);
        this.data = new SimpleStringProperty(data);
        this.resum = new SimpleStringProperty(resum);
        this.caracteristiques = new SimpleStringProperty(caracteristiques);
        this.urlPortada = new SimpleStringProperty(urlPortada);
        this.adreçaWeb = new SimpleStringProperty(adreçaWeb);
        this.estat = new SimpleBooleanProperty(estat);
        this.idioma = new SimpleObjectProperty<>(idioma);
        this.editorial = new SimpleObjectProperty<>(editorial);
        this.format = new SimpleObjectProperty<>(format);
        this.procedencia = new SimpleObjectProperty<>(procedencia);
        this.nivell = new SimpleObjectProperty<>(nivell);
        this.coleccio = new SimpleObjectProperty<>(coleccio);
        this.cdu = new SimpleObjectProperty<>(cdu);
    }

    //Getters

    public int getId() { return id.get(); }
    public String getISBN() { return ISBN.get(); }
    public String getNom() { return nom.get(); }
    public int getNum_pag() { return num_pag.get(); }
    public String getDimensions() { return dimensions.get(); }
    public String getData() { return data.get(); }
    public String getResum() { return resum.get(); }
    public String getCaracteristiques() { return caracteristiques.get(); }
    public String getUrlPortada() { return urlPortada.get(); }
    public String getAdreçaWeb() { return adreçaWeb.get(); }
    public boolean getEstat() { return estat.get(); }
    public Idioma getIdioma() { return idioma.get(); }
    public Editorial getEditorial() { return editorial.get(); }
    public Format getFormat() { return format.get(); }
    public Procedencia getProcedencia() { return procedencia.get(); }
    public Nivell getNivell() { return nivell.get(); }
    public Coleccio getColeccio() { return coleccio.get(); }
    public Cdu getCDU() { return cdu.get(); }

    //Setters

    public void setId(int id) { this.id.set(id); }
    public void setISBN(String ISBN) { this.ISBN.set(ISBN); }
    public void setNom(String nom) { this.nom.set(nom); }
    public void setNum_pag(int num_pag) { this.num_pag.set(num_pag); }
    public void setDimensions(String dimensions) { this.dimensions.set(dimensions); }
    public void setData(String data) { this.data.set(data); }
    public void setResum(String resum) { this.resum.set(resum); }
    public void setCaracteristiques(String caracteristiques) { this.caracteristiques.set(caracteristiques); }
    public void setUrlPortada(String urlPortada) { this.urlPortada.set(urlPortada); }
    public void setAdreçaWeb(String adreçaWeb) { this.adreçaWeb.set(adreçaWeb); }
    public void setEstat(boolean estat) { this.estat.set(estat); }
    public void setIdioma(Idioma idioma) { this.idioma.set(idioma); }
    public void setEditorial(Editorial editorial) { this.editorial.set(editorial); }
    public void setFormat(Format format) { this.format.set(format); }
    public void setProcedencia(Procedencia procedencia) { this.procedencia.set(procedencia); }
    public void setNivell(Nivell nivell) { this.nivell.set(nivell); }
    public void setColeccio(Coleccio coleccio) { this.coleccio.set(coleccio); }
    public void setCDU(Cdu cdu) { this.cdu.set(cdu); }

    //Properties

    public SimpleIntegerProperty idProperty() { return id; }
    public SimpleStringProperty ISBNProperty() { return ISBN; }
    public SimpleStringProperty nomProperty() { return nom; }
    public SimpleIntegerProperty num_pagProperty() { return num_pag; }
    public SimpleStringProperty dimensionsProperty() { return dimensions; }
    public SimpleStringProperty dataProperty() { return data; }
    public SimpleStringProperty resumProperty() { return resum; }
    public SimpleStringProperty caracteristiquesProperty() { return caracteristiques; }
    public SimpleStringProperty urlPortadaProperty() { return urlPortada; }
    public SimpleStringProperty adreçaWebProperty() { return adreçaWeb; }
    public SimpleBooleanProperty estatProperty() { return estat; }
    public SimpleObjectProperty<Idioma> idiomaProperty() { return idioma; }
    public SimpleObjectProperty<Editorial> editorialProperty() { return editorial; }
    public SimpleObjectProperty<Format> formatProperty() { return format; }
    public SimpleObjectProperty<Procedencia> procedenciaProperty() { return procedencia; }
    public SimpleObjectProperty<Nivell> nivellProperty() { return nivell; }
    public SimpleObjectProperty<Coleccio> coleccioProperty() { return coleccio; }
    public SimpleObjectProperty<Cdu> cduProperty() { return cdu; }


}
