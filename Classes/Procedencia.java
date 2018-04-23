package com.company.DAM2.Bibliorevolució.Classes;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Procedencia {
    private SimpleIntegerProperty id;
    private SimpleStringProperty nom;

    //Constructor Buit

    public Procedencia(){
        this.id = new SimpleIntegerProperty();
        this.nom = new SimpleStringProperty("");
    }

    //Constructor Total

    public Procedencia(Integer id, String nom){
        this.id = new SimpleIntegerProperty(id);
        this.nom = new SimpleStringProperty(nom);
    }

    //Getters

    public int getId() { return id.get(); }
    public String getNom() { return nom.get(); }

    //Setters

    public void setId(int id) { this.id.set(id); }
    public void setNom(String nom) { this.nom.set(nom); }

    //Properties

    public SimpleIntegerProperty idProperty() { return id; }
    public SimpleStringProperty nomProperty() { return nom; }
}
