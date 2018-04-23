package com.company.DAM2.Bibliorevolució.DAO;

import com.company.DAM2.Bibliorevolució.Classes.Producte;
import javafx.collections.ObservableList;

public interface ProducteDAO {

    //Funció: Seleccionar tots els productes. Parametres: - . Return: ObservableList.
    public ObservableList selectTotsProductes();

    //Funció: Seleccionar tots els productes de cert format. Parametres: format. Return: ObservableList.
    public ObservableList selectTotsProductesFormat(String format);

    //Funció: Seleccionar tots els productes activats o no activats. Parametres: activat. Return: ObservableList.
    public ObservableList selectTotsProductesActivat(boolean activat);

    //Funció: Seleccionar tots els productes amb certs parametres. Parametres: selProducte. Return: ObservableList.
    public ObservableList selectProducteEspecific(Producte selProducte);

    //Funció: Insertar un producte. Parametres: insProducte. Return: boolean.
    public boolean insertProducte(Producte insProducte);

    //Funció: Borrar un producte. Parametres: delProducte. Return: boolean.
    public boolean deleteProducte(Producte delProducte);

    //Funció: Actualitzar un producte. Parametres: uptProducte. Return: boolean.
    public boolean updateProducte(Producte uptProducte);

    //Funció: Actualitzar un producte a activat o desactivat. Parametres: id, activat. Return: boolean.
    public boolean updateProducteActivar(int id, boolean activat);

    //Funció: Obtenir el seguent id pel nou producte. Parametres: - . Return: int.
    public int nextIdProducte();
}
