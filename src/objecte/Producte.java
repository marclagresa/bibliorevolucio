package objecte;

import java.util.HashSet;
import java.util.Set;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

public class Producte {
    private SimpleIntegerProperty id;
    private SimpleStringProperty ISBN;
    private SimpleStringProperty nom;
    private SimpleIntegerProperty numPag;
    private SimpleStringProperty dimensions;
    private SimpleStringProperty anyPublicacio;
    private SimpleStringProperty resum;
    private SimpleStringProperty caracteristiques;
    private SimpleStringProperty urlPortada;
    private SimpleStringProperty adreçaWeb;
    private SimpleStringProperty lloc;
    private SimpleBooleanProperty estat;
    private SimpleObjectProperty<Idioma> idioma;
    private SimpleObjectProperty<Editorial> editorial;
    private SimpleObjectProperty<Format> format;
    private SimpleObjectProperty<Procedencia> procedencia;
    private SimpleObjectProperty<Nivell> nivell;
    private SimpleObjectProperty<Coleccio> coleccio;
    private SimpleObjectProperty<Cdu> cdu;
    private SimpleObjectProperty<Set<Exemplar>> exemplars;
    private SimpleObjectProperty<Set<Materia>> materias;
    
    //Constructor Buit

    public Producte(){
        this.lloc=new SimpleStringProperty("");
        this.id = new SimpleIntegerProperty();
        this.ISBN = new SimpleStringProperty("");
        this.nom = new SimpleStringProperty("");
        this.numPag = new SimpleIntegerProperty(-1);
        this.dimensions = new SimpleStringProperty("");
        this.anyPublicacio = new SimpleStringProperty("");
        this.resum = new SimpleStringProperty("");
        this.caracteristiques = new SimpleStringProperty("");
        this.urlPortada = new SimpleStringProperty("");
        this.adreçaWeb = new SimpleStringProperty("");
        this.estat = new SimpleBooleanProperty(false);
        this.idioma = new SimpleObjectProperty<>(new Idioma());
        this.editorial = new SimpleObjectProperty<>(new Editorial());
        this.format = new SimpleObjectProperty<>(new Format());
        this.procedencia = new SimpleObjectProperty<>(new Procedencia());
        this.nivell = new SimpleObjectProperty<>(new Nivell());
        this.coleccio = new SimpleObjectProperty<>(new Coleccio());
        this.cdu = new SimpleObjectProperty<>(new Cdu());
        this.exemplars=new SimpleObjectProperty<>(new HashSet<>(0));
        this.materias= new SimpleObjectProperty<>(new HashSet<>(0));
    }

    //Constructor Total

    public Producte(Integer id, String ISBN, String nom, Integer num_pag, String dimensions, String data, String resum,
                    String caracteristiques, String urlPortada, String adreçaWeb, boolean estat, Idioma idioma,
                    Editorial editorial, Format format, Procedencia procedencia, Nivell nivell, Coleccio coleccio,
                    Cdu cdu,Set exemplars,String lloc,Set <Materia> materies){
        this.lloc=new SimpleStringProperty(lloc);
        this.id = new SimpleIntegerProperty(id);
        this.ISBN = new SimpleStringProperty(ISBN);
        this.nom = new SimpleStringProperty(nom);
        this.numPag = new SimpleIntegerProperty(num_pag);
        this.dimensions = new SimpleStringProperty(dimensions);
        this.anyPublicacio = new SimpleStringProperty(data);
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
        this.exemplars=new SimpleObjectProperty<>(exemplars);
        this.materias=new SimpleObjectProperty<>(materies);
    }

    //Getters

    public Set getMateries(){return this.materias.get();};
    public String getLloc(){return this.lloc.get();}
    public int getId() { return id.get(); }
    public String getISBN() { return ISBN.get(); }
    public String getNom() { return nom.get(); }
    public int getNumPag() { return numPag.get(); }
    public String getDimensions() { return dimensions.get(); }
    public String getAnyPublicacio() { return anyPublicacio.get(); }
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
    public Set<Exemplar> getExemplars(){return this.exemplars.get();}

    //Setters

    public void setMateries(Set<Materia> materies){this.materias.set(materies);}
    public void setLloc(String lloc){this.lloc.set(lloc);}
    public void setId(int id) { this.id.set(id); }
    public void setISBN(String ISBN) { this.ISBN.set(ISBN); }
    public void setNom(String nom) { this.nom.set(nom); }
    public void setNumPag(int num_pag) { this.numPag.set(num_pag); }
    public void setDimensions(String dimensions) { this.dimensions.set(dimensions); }
    public void setAnyPublicacio(String data) { this.anyPublicacio.set(data); }
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
    public void setExemplars(Set <Exemplar> exemplars){this.exemplars.set(exemplars);}

    //Properties

    public SimpleObjectProperty<Set<Materia>> materiesProperty(){return this.materias;}
    public SimpleStringProperty llocProperty(){return this.lloc;}
    public SimpleIntegerProperty idProperty() { return id; }
    public SimpleStringProperty ISBNProperty() { return ISBN; }
    public SimpleStringProperty nomProperty() { return nom; }
    public SimpleIntegerProperty numPagProperty() { return numPag; }
    public SimpleStringProperty dimensionsProperty() { return dimensions; }
    public SimpleStringProperty anyPublicacioProperty() { return anyPublicacio; }
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
    public SimpleObjectProperty<Set<Exemplar>>exemplarsProperty(){return this.exemplars;}

    //To String

    @Override
    public String toString(){
        return this.getNom();
    }

}
