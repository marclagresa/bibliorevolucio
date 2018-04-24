package com.company.DAM2.Bibliorevolució.objecte;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Biblioteca {
    private SimpleIntegerProperty id;
    private SimpleStringProperty nom;

    //Constructor Buit

    public Biblioteca(){
        this.id = new SimpleIntegerProperty();
        this.nom = new SimpleStringProperty("");
    }

    //Constructor Total

    public Biblioteca(Integer id, String nom){
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