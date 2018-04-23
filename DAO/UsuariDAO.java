package com.company.DAM2.Bibliorevolució.DAO;

import com.company.DAM2.Bibliorevolució.Classes.Usuari;
import javafx.collections.ObservableList;

public interface UsuariDAO {

    //Funció: Seleccionar tots els usuaris. Parametres: - . Return: ObservableList.
    public ObservableList selectTotsUsers();

    //Funció: Seleccionar tots els usuaris admins o no admins. Parametres: admin . Return: ObservableList.
    public ObservableList selectTotsUsersAdmin(boolean admin);

    //Funció: Seleccionar tots els usuaris activats o no activats. Parametres: activats . Return: ObservableList.
    public ObservableList selectTotsUsersActivat(boolean activats);

    //Funció: Seleccionar tots els usuaris amb certs parametres. Parametres: selUsuari . Return: ObservableList.
    public ObservableList selectUsersEspecific(Usuari selUsuari);

    //Funció: Insertar un usuari. Parametres: insUsuari. Return: ObservableList.
    public boolean insertUsers(Usuari insUsuari);

    //Funció: Borrar un usuari. Parametres: delUsuari. Return: ObservableList.
    public boolean deleteUsers(Usuari delUsuari);

    //Funció: Actualitzar un usuari. Parametres: uptUsuari. Return: ObservableList.
    public boolean updateUsers(Usuari uptUsuari);

    //Funció: Actualitzar un usuari a activat o desactivat. Parametres: id, activat. Return: boolean.
    public boolean updateUsersActivat(int id, boolean activat);

    //Funció: Obtenir el seguent id pel nou usuari. Parametres: - . Return: int.
    public int nextIdUsers();
}
