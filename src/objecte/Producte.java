package com.company.DAM2.Bibliorevolució.objecte;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
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
    private SimpleIntegerProperty idTipusProducte;
    private SimpleIntegerProperty idIdioma;
    private SimpleIntegerProperty idEditorial;
    private SimpleIntegerProperty idFormat;
    private SimpleIntegerProperty idProcedencia;
    private SimpleIntegerProperty idNivell;
    private SimpleIntegerProperty idColeccio;
    private SimpleIntegerProperty idCDU;

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
        this.idTipusProducte = new SimpleIntegerProperty(-1);
        this.idIdioma = new SimpleIntegerProperty(-1);
        this.idEditorial = new SimpleIntegerProperty(-1);
        this.idFormat = new SimpleIntegerProperty(-1);
        this.idProcedencia = new SimpleIntegerProperty(-1);
        this.idNivell = new SimpleIntegerProperty(-1);
        this.idColeccio = new SimpleIntegerProperty(-1);
        this.idCDU = new SimpleIntegerProperty(-1);
    }

    //Constructor Total

    public Producte(Integer id, String ISBN, String nom, Integer num_pag, String dimensions, String data, String resum,
                    String caracteristiques, String urlPortada, String adreçaWeb, boolean estat, Integer idTipusProducte,
                    Integer idIdioma, Integer idEditorial, Integer idFormat, Integer idProcedencia, Integer idNivell,
                    Integer idColeccio, Integer idCDU){
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
        this.idTipusProducte = new SimpleIntegerProperty(idTipusProducte);
        this.idIdioma = new SimpleIntegerProperty(idIdioma);
        this.idEditorial = new SimpleIntegerProperty(idEditorial);
        this.idFormat = new SimpleIntegerProperty(idFormat);
        this.idProcedencia = new SimpleIntegerProperty(idProcedencia);
        this.idNivell = new SimpleIntegerProperty(idNivell);
        this.idColeccio = new SimpleIntegerProperty(idColeccio);
        this.idCDU = new SimpleIntegerProperty(idCDU);
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
    public int getIdTipusProducte() { return idTipusProducte.get(); }
    public int getIdIdioma() { return idIdioma.get(); }
    public int getIdEditorial() { return idEditorial.get(); }
    public int getIdFormat() { return idFormat.get(); }
    public int getIdProcedencia() { return idProcedencia.get(); }
    public int getIdNivell() { return idNivell.get(); }
    public int getIdColeccio() { return idColeccio.get(); }
    public int getIdCDU() { return idCDU.get(); }

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
    public void setIdTipusProducte(int idTipusProducte) { this.idTipusProducte.set(idTipusProducte); }
    public void setIdIdioma(int idIdioma) { this.idIdioma.set(idIdioma); }
    public void setIdEditorial(int idEditorial) { this.idEditorial.set(idEditorial); }
    public void setIdFormat(int idFormat) { this.idFormat.set(idFormat); }
    public void setIdProcedencia(int idProcedencia) { this.idProcedencia.set(idProcedencia); }
    public void setIdNivell(int idNivell) { this.idNivell.set(idNivell); }
    public void setIdColeccio(int idColeccio) { this.idColeccio.set(idColeccio); }
    public void setIdCDU(int idCDU) { this.idCDU.set(idCDU); }

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
    public SimpleIntegerProperty idTipusProducteProperty() { return idTipusProducte; }
    public SimpleIntegerProperty idIdiomaProperty() { return idIdioma; }
    public SimpleIntegerProperty idEditorialProperty() { return idEditorial; }
    public SimpleIntegerProperty idFormatProperty() { return idFormat; }
    public SimpleIntegerProperty idProcedenciaProperty() { return idProcedencia; }
    public SimpleIntegerProperty idNivellProperty() { return idNivell; }
    public SimpleIntegerProperty idColeccioProperty() { return idColeccio; }
    public SimpleIntegerProperty idCDUProperty() { return idCDU; }


}
