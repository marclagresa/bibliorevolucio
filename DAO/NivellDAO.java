package com.company.DAM2.Bibliorevolució.DAO;

import com.company.DAM2.Bibliorevolució.Classes.Nivell;
import javafx.collections.ObservableList;

public interface NivellDAO {

    //Funció: Seleccionar tots els nivells. Parametres: - . Return: ObservableList.
    public ObservableList selectTotsNivells();

    //Funció: Seleccionar totes els nivells amb certs parametres. Parametres: selNivell. Return: ObservableList.
    public ObservableList selectNivellEspecific(Nivell selNivell);

    //Funció: Insertar un nivell. Parametres: insNivell. Return: ObservableList.
    public boolean insertNivell(Nivell insNivell);

    //Funció: Borrar un nivell. Parametres: delNivell. Return: ObservableList.
    public boolean deleteNivell(Nivell delNivell);

    //Funció: Actualitzar un nivell. Parametres: uptNivell. Return: ObservableList.
    public boolean updateNivell(Nivell uptNivell);

    //Funció: Obtenir el seguent id pel nou nivell. Parametres: - . Return: int.
    public int nextIdNivell();
}
