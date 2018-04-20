package com.company.DAM2.Bibliorevolució.DAO;

import com.company.DAM2.Bibliorevolució.Classes.Usuari;
import javafx.collections.ObservableList;

public interface UsuariDAO {
    public ObservableList selectTotsUsers();
    public ObservableList selectTotsUsersAdmins();
    public ObservableList selectTotsUsersActivats();
    public ObservableList selectTotsUsersDesactivats();
    public ObservableList selectTotsUsersNoAdmins();
    public ObservableList selectUsersEspecific(Usuari selUsuari);
    public boolean insertUsers(Usuari insUsuari);
    public boolean deleteUsers(Usuari delUsuari);
    public boolean updateUsers(Usuari uptUsuari);
    public Usuari selectUsersEmail(String email);
    public int nextIdUsers();
}
