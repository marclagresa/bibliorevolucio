package com.company.DAM2.Bibliorevolució.DAO;

import com.company.DAM2.Bibliorevolució.Classes.Procedencia;
import javafx.collections.ObservableList;

public interface ProcedenciaDAO {

    //Funció: Seleccionar totes les procedencies. Parametres: - . Return: ObservableList.
    public ObservableList selectTotesProcedencies();

    //Funció: Seleccionar totes les procedencies amb certs parametres. Parametres: selProcedencia. Return: ObservableList.
    public ObservableList selectProcedenciaEspecifica(Procedencia selProcedencia);

    //Funció: Insertar una procedencia. Parametres: insProcedencia. Return: ObservableList.
    public boolean insertProcedencia(Procedencia insProcedencia);

    //Funció: Borrar una procedencia. Parametres: delProcedencia. Return: ObservableList.
    public boolean deleteProcedencia(Procedencia delProcedencia);

    //Funció: Actualitzar una procedencia. Parametres: uptProcedencia. Return: ObservableList.
    public boolean updateProcedencia(Procedencia uptProcedencia);

    //Funció: Obtenir el seguent id per la nova procedencia. Parametres: - . Return: int.
    public int nextIdProcedencia();
}
