package com.company.DAM2.Bibliorevolució.Controladors;

import com.company.DAM2.Bibliorevolució.Connector.ConnectorBD;
import com.company.DAM2.Bibliorevolució.Classes.Usuari;
import com.company.DAM2.Bibliorevolució.DAO.UsuariDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UsuariControlador implements UsuariDAO {
    ConnectorBD conn = new ConnectorBD();
    ObservableList<Usuari> list = FXCollections.observableArrayList();
    Usuari user;

    public ObservableList selectTotsUsers(){
        PreparedStatement ps = null;
        ResultSet rs = null;
        Usuari selectUsuari;
        try {
            String sql = "Select id,nom,p_cognom,s_cognom,email,telefon_mobil,telefon_fixe,contrasenya,esAdmin,estat,nivell" +
                    " from Usuari";
            ps = conn.connectar().prepareStatement(sql);
            rs = ps.executeQuery();
            list.clear();
            while(rs.next()){
                selectUsuari = new Usuari();
                selectUsuari.setId(rs.getInt(1));
                selectUsuari.setNom(rs.getString(2));
                selectUsuari.setPcognom(rs.getString(3));
                selectUsuari.setScognom(rs.getString(4));
                selectUsuari.setEmail(rs.getString(5));
                selectUsuari.setTelefon_mobil(rs.getInt(6));
                selectUsuari.setTelefon_fixe(rs.getInt(7));
                selectUsuari.setContrasenya(rs.getString(8));
                selectUsuari.setEsAdmin(rs.getInt(9));
                selectUsuari.setEstat(rs.getInt(10));
                selectUsuari.setNivell(rs.getString(11));
                list.add(selectUsuari);
            }
            ps.close();
            rs.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                if(ps != null) {
                    ps.close();
                }
                if(rs != null){
                    rs.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return list;
        }
    }
    public ObservableList selectTotsUsersAdmin(boolean admin){
        PreparedStatement ps = null;
        ResultSet rs = null;
        Usuari selectUsuari;
        try {
            String sql;
            if(admin) {
                sql = "Select id,nom,p_cognom,s_cognom,email,telefon_mobil,telefon_fixe,contrasenya,esAdmin,estat,nivell" +
                        " from Usuari where esAdmin=1";
            } else {
                sql = "Select id,nom,p_cognom,s_cognom,email,telefon_mobil,telefon_fixe,contrasenya,esAdmin,estat,nivell" +
                        " from Usuari where esAdmin=0";
            }
            ps = conn.connectar().prepareStatement(sql);
            rs = ps.executeQuery();
            list.clear();
            while(rs.next()){
                selectUsuari = new Usuari();
                selectUsuari.setId(rs.getInt(1));
                selectUsuari.setNom(rs.getString(2));
                selectUsuari.setPcognom(rs.getString(3));
                selectUsuari.setScognom(rs.getString(4));
                selectUsuari.setEmail(rs.getString(5));
                selectUsuari.setTelefon_mobil(rs.getInt(6));
                selectUsuari.setTelefon_fixe(rs.getInt(7));
                selectUsuari.setContrasenya(rs.getString(8));
                selectUsuari.setEsAdmin(rs.getInt(9));
                selectUsuari.setEstat(rs.getInt(10));
                selectUsuari.setNivell(rs.getString(11));
                list.add(selectUsuari);
            }
            ps.close();
            rs.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                if(ps != null) {
                    ps.close();
                }
                if(rs != null){
                    rs.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return list;
        }
    }
    public ObservableList selectTotsUsersActivat(boolean activat){
        PreparedStatement ps = null;
        ResultSet rs = null;
        Usuari selectUsuari;
        try {
            String sql;
            if(activat) {
                sql = "Select id,nom,p_cognom,s_cognom,email,telefon_mobil,telefon_fixe,contrasenya,esAdmin,estat,nivell" +
                        " from Usuari where estat=1";
            } else {
                sql = "Select id,nom,p_cognom,s_cognom,email,telefon_mobil,telefon_fixe,contrasenya,esAdmin,estat,nivell" +
                        " from Usuari where estat=0";
            }
            ps = conn.connectar().prepareStatement(sql);
            rs = ps.executeQuery();
            list.clear();
            while(rs.next()){
                selectUsuari = new Usuari();
                selectUsuari.setId(rs.getInt(1));
                selectUsuari.setNom(rs.getString(2));
                selectUsuari.setPcognom(rs.getString(3));
                selectUsuari.setScognom(rs.getString(4));
                selectUsuari.setEmail(rs.getString(5));
                selectUsuari.setTelefon_mobil(rs.getInt(6));
                selectUsuari.setTelefon_fixe(rs.getInt(7));
                selectUsuari.setContrasenya(rs.getString(8));
                selectUsuari.setEsAdmin(rs.getInt(9));
                selectUsuari.setEstat(rs.getInt(10));
                selectUsuari.setNivell(rs.getString(11));
                list.add(selectUsuari);
            }
            ps.close();
            rs.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                if(ps != null) {
                    ps.close();
                }
                if(rs != null){
                    rs.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return list;
        }
    }
    public ObservableList selectUsersEspecific(Usuari selUsuari){
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            String sql = "Select * from Usuari where nom LIKE ? AND p_cognom LIKE ? AND s_cognom LIKE ? AND email LIKE ? " +
                    "AND esAdmin = ? AND estat = ? AND nivell LIKE ?";
            ps = conn.connectar().prepareStatement(sql);
            ps.setString(1,'%'+selUsuari.getNom()+'%');
            ps.setString(2,'%'+selUsuari.getPcognom()+'%');
            ps.setString(3,'%'+selUsuari.getScognom()+'%');
            ps.setString(4,'%'+selUsuari.getEmail()+'%');
            ps.setInt(5,selUsuari.getEsAdmin());
            ps.setInt(6,selUsuari.getEstat());
            ps.setString(7,'%'+selUsuari.getNivell()+'%');
            rs = ps.executeQuery();
            list.clear();
            while(rs.next()){
                user = new Usuari();
                user.setId(rs.getInt(1));
                user.setNom(rs.getString(2));
                user.setPcognom(rs.getString(3));
                user.setScognom(rs.getString(4));
                user.setTelefon_mobil(rs.getInt(5));
                user.setTelefon_fixe(rs.getInt(6));
                user.setEmail(rs.getString(7));
                user.setContrasenya(rs.getString(8));
                user.setEstat(rs.getInt(9));
                user.setSalt(rs.getString(10));
                user.setEsAdmin(rs.getInt(11));
                user.setNivell(rs.getString(12));
                list.add(user);
            }
            ps.close();
            rs.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                if(ps != null) {
                    ps.close();
                }
                if(rs != null){
                    rs.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return list;
        }
    }
    public boolean insertUsers(Usuari insUsuari){
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            String insert = "Insert into Usuari values (?,?,?,?,?,?,?,?,?,?,?)";
            ps = conn.connectar().prepareStatement(insert);
            ps.setInt(1,nextIdUsers());
            ps.setString(2,insUsuari.getNom());
            ps.setString(3,insUsuari.getPcognom());
            ps.setString(4,insUsuari.getScognom());
            ps.setInt(5,insUsuari.getTelefon_mobil());
            ps.setInt(6,insUsuari.getTelefon_fixe());
            ps.setString(7,insUsuari.getEmail());
            ps.setString(8,insUsuari.getContrasenya());
            ps.setInt(9,insUsuari.getEstat());
            ps.setInt(10,insUsuari.getEsAdmin());
            ps.setString(11,insUsuari.getNivell());

            ps.close();
            rs.close();
            return true;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        } finally {
            try {
                if(ps != null) {
                    ps.close();
                }
                if(rs != null){
                    rs.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    public boolean deleteUsers(Usuari delUsuari){
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            String delete = "Delete from Usuari where id = ?";
            ps = conn.connectar().prepareStatement(delete);
            ps.setInt(1,delUsuari.getId());
            ps.executeUpdate();

            ps.close();
            rs.close();
            return true;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        } finally {
            try {
                if(ps != null) {
                    ps.close();
                }
                if(rs != null){
                    rs.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    public boolean updateUsers(Usuari uptUsuari){
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            String update = "UPDATE from Usuari SET nom = ?, p_cognom = ?, s_cognom = ?, telefon_mobil = ?, telefon_fixe = ?," +
                    " email = ?, nivell = ? where id = ?";
            ps = conn.connectar().prepareStatement(update);
            ps.setString(1,uptUsuari.getNom());
            ps.setString(2,uptUsuari.getPcognom());
            ps.setString(3,uptUsuari.getScognom());
            ps.setInt(4,uptUsuari.getTelefon_mobil());
            ps.setInt(5,uptUsuari.getTelefon_fixe());
            ps.setString(6,uptUsuari.getEmail());
            ps.setString(7,uptUsuari.getNivell());
            ps.setInt(8,uptUsuari.getId());
            ps.executeUpdate();

            ps.close();
            rs.close();
            return true;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        } finally {
            try {
                if(ps != null) {
                    ps.close();
                }
                if(rs != null){
                    rs.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    public boolean updateUsersActivat(int id, boolean activat){
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            String update;
            if(activat) {
                update = "UPDATE from Usuari SET estat = 1 where id = ?";
            } else {
                update = "UPDATE from Usuari SET estat = 0 where id = ?";
            }
            ps = conn.connectar().prepareStatement(update);
            ps.setInt(1,id);
            ps.executeUpdate();

            ps.close();
            rs.close();
            return true;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        } finally {
            try {
                if(ps != null) {
                    ps.close();
                }
                if(rs != null){
                    rs.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    public int nextIdUsers(){
        PreparedStatement ps = null;
        ResultSet rs = null;
        int id = 0;
        try {
            String sql = "SELECT max(id)+1 FROM usuari";
            ps = conn.connectar().prepareStatement(sql);
            rs = ps.executeQuery();
            id = rs.getInt(1);
            return id;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return id;
        } finally {
            try {
                if(ps != null) {
                    ps.close();
                }
                if(rs != null){
                    rs.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
