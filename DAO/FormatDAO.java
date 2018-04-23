package com.company.DAM2.Bibliorevolució.DAO;

import com.company.DAM2.Bibliorevolució.Classes.Format;
import javafx.collections.ObservableList;

public interface FormatDAO {

    //Funció: Seleccionar tots els formats. Parametres: - . Return: ObservableList.
    public ObservableList selectTotsFormats();

    //Funció: Seleccionar tots els formats amb certs parametres. Parametres: selFormat. Return: ObservableList.
    public ObservableList selectFormatEspecific(Format selFormat);

    //Funció: Insertar un format. Parametres: insFormat. Return: ObservableList.
    public boolean insertFormat(Format insFormat);

    //Funció: Borrar un format. Parametres: delFormat. Return: ObservableList.
    public boolean deleteFormat(Format delFormat);

    //Funció: Actualitzar un format. Parametres: uptFormat. Return: ObservableList.
    public boolean updateFormat(Format uptFormat);

    //Funció: Obtenir el seguent id pel nou format. Parametres: - . Return: int.
    public int nextIdFormat();
}
