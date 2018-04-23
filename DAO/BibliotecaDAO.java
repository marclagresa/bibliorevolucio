package com.company.DAM2.Bibliorevolució.DAO;

import com.company.DAM2.Bibliorevolució.Classes.Biblioteca;
import javafx.collections.ObservableList;

public interface BibliotecaDAO {

    //Funció: Seleccionar totes les biblioteques. Parametres: - . Return: ObservableList.
    public ObservableList selectTotesBiblioteques();

    //Funció: Seleccionar totes les biblioteques amb certs parametres. Parametres: selBiblioteca. Return: ObservableList.
    public ObservableList selectBibliotecaEspecifica(Biblioteca selBiblioteca);

    //Funció: Insertar una biblioteca. Parametres: insBiblioteca. Return: ObservableList.
    public boolean insertBiblioteca(Biblioteca insBiblioteca);

    //Funció: Borrar una biblioteca. Parametres: delBiblioteca. Return: ObservableList.
    public boolean deleteBiblioteca(Biblioteca delBiblioteca);

    //Funció: Actualitzar una biblioteca. Parametres: uptBiblioteca. Return: ObservableList.
    public boolean updateBiblioteca(Biblioteca uptBiblioteca);

    //Funció: Obtenir el seguent id per la nova biblioteca. Parametres: - . Return: int.
    public int nextIdBiblioteca();
}
