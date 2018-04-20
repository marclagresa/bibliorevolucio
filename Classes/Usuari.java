package com.company.DAM2.Bibliorevolució.Classes;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Usuari {
    private SimpleIntegerProperty id;
    private SimpleStringProperty nom;
    private SimpleStringProperty pcognom;
    private SimpleStringProperty scognom;
    private SimpleIntegerProperty telefon_mobil;
    private SimpleIntegerProperty telefon_fixe;
    private SimpleStringProperty email;
    private SimpleStringProperty contrasenya;
    private SimpleIntegerProperty estat;
    private SimpleIntegerProperty salt;
    private SimpleIntegerProperty esAdmin;
    private SimpleStringProperty nivell;

    //Constructor Buit

    public Usuari(){
        this.id = new SimpleIntegerProperty();
        this.nom = new SimpleStringProperty("");
        this.pcognom = new SimpleStringProperty("");
        this.scognom = new SimpleStringProperty("");
        this.telefon_mobil = new SimpleIntegerProperty();
        this.telefon_fixe = new SimpleIntegerProperty();
        this.email = new SimpleStringProperty("");
        this.contrasenya = new SimpleStringProperty("");
        this.estat = new SimpleIntegerProperty();
        this.salt = new SimpleIntegerProperty();
        this.esAdmin = new SimpleIntegerProperty();
        this.nivell = new SimpleStringProperty("");
    }

    //Constructor Total

    public Usuari(Integer id, String nom, String pcognom, String scognom,Integer telefon_mobil, Integer telefon_fixe,
                  String email,  String contrasenya, Integer estat, Integer salt, Integer esAdmin,
                  String nivell){
        this.id = new SimpleIntegerProperty(id);
        this.nom = new SimpleStringProperty(nom);
        this.pcognom = new SimpleStringProperty(pcognom);
        this.scognom = new SimpleStringProperty(scognom);
        this.telefon_mobil = new SimpleIntegerProperty(telefon_mobil);
        this.telefon_fixe = new SimpleIntegerProperty(telefon_fixe);
        this.email = new SimpleStringProperty(email);
        this.contrasenya = new SimpleStringProperty(contrasenya);
        this.estat = new SimpleIntegerProperty(estat);
        this.salt = new SimpleIntegerProperty(salt);
        this.esAdmin = new SimpleIntegerProperty(esAdmin);
        this.nivell = new SimpleStringProperty(nivell);
    }

    //Getters

    public int getId() { return id.get(); }
    public String getNom() { return nom.get(); }
    public String getPcognom() { return pcognom.get(); }
    public String getScognom() { return scognom.get(); }
    public int getTelefon_mobil() { return telefon_mobil.get(); }
    public int getTelefon_fixe() { return telefon_fixe.get(); }
    public String getEmail() { return email.get(); }
    public String getContrasenya() { return contrasenya.get(); }
    public int getEstat() { return estat.get(); }
    public int getSalt() { return salt.get(); }
    public int getEsAdmin() { return esAdmin.get(); }
    public String getNivell() { return nivell.get(); }

    //Setters

    public void setId(int id) { this.id.set(id); }
    public void setNom(String nom) { this.nom.set(nom); }
    public void setPcognom(String pcognom) { this.pcognom.set(pcognom); }
    public void setScognom(String scognom) { this.scognom.set(scognom); }
    public void setTelefon_mobil(int telefon_mobil) { this.telefon_mobil.set(telefon_mobil); }
    public void setTelefon_fixe(int telefon_fixe) { this.telefon_fixe.set(telefon_fixe); }
    public void setEmail(String email) { this.email.set(email); }
    public void setContrasenya(String contrasenya) { this.contrasenya.set(contrasenya); }
    public void setEstat(int estat) { this.estat.set(estat); }
    public void setSalt(int salt) { this.salt.set(salt); }
    public void setEsAdmin(int esAdmin) { this.esAdmin.set(esAdmin); }
    public void setNivell(String nivell) { this.nivell.set(nivell); }

    //Properties

    public SimpleIntegerProperty idProperty() { return id; }
    public SimpleStringProperty nomProperty() { return nom; }
    public SimpleStringProperty pcognomProperty() { return pcognom; }
    public SimpleStringProperty scognomProperty() { return scognom; }
    public SimpleIntegerProperty telefon_mobilProperty() { return telefon_mobil; }
    public SimpleIntegerProperty telefon_fixeProperty() { return telefon_fixe; }
    public SimpleStringProperty emailProperty() { return email; }
    public SimpleStringProperty contrasenyaProperty() { return contrasenya; }
    public SimpleIntegerProperty estatProperty() { return estat; }
    public SimpleIntegerProperty saltProperty() { return salt; }
    public SimpleIntegerProperty esAdminProperty() { return esAdmin; }
    public SimpleStringProperty nivellProperty() { return nivell; }

}
