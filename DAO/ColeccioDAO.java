package com.company.DAM2.Bibliorevolució.DAO;

import com.company.DAM2.Bibliorevolució.Classes.Coleccio;
import javafx.collections.ObservableList;

public interface ColeccioDAO {

    //Funció: Seleccionar totes les coleccions. Parametres: - . Return: ObservableList.
    public ObservableList selectTotesColeccions();

    //Funció: Seleccionar totes les coleccions amb certs parametres. Parametres: selColeccio. Return: ObservableList.
    public ObservableList selectColeccioEspecifica(Coleccio selColeccio);

    //Funció: Insertar una coleccio. Parametres: insColeccio. Return: ObservableList.
    public boolean insertColeccio(Coleccio insColeccio);

    //Funció: Borrar una coleccio. Parametres: delColeccio. Return: ObservableList.
    public boolean deleteColeccio(Coleccio delColeccio);

    //Funció: Actualitzar una coleccio. Parametres: uptColeccio. Return: ObservableList.
    public boolean updateColeccio(Coleccio uptColeccio);

    //Funció: Obtenir el seguent id per la nova coleccio. Parametres: - . Return: int.
    public int nextIdColeccio();
}
