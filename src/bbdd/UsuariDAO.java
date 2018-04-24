package com.company.DAM2.Bibliorevolució.BBDD.dao;

import com.company.DAM2.Bibliorevolució.BBDD.connector.ConnectorBD;
import com.company.DAM2.Bibliorevolució.objecte.Usuari;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UsuariDAO implements IObjectDAO<Usuari> {
    ConnectorBD conn = new ConnectorBD();
    ObservableList<Usuari> list = FXCollections.observableArrayList();

    public ObservableList selectAll(){
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
                selectUsuari.setEsAdmin(rs.getBoolean(9));
                selectUsuari.setEstat(rs.getBoolean(10));
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
                        " from Usuari where esAdmin= true";
            } else {
                sql = "Select id,nom,p_cognom,s_cognom,email,telefon_mobil,telefon_fixe,contrasenya,esAdmin,estat,nivell" +
                        " from Usuari where esAdmin= false";
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
                selectUsuari.setEsAdmin(rs.getBoolean(9));
                selectUsuari.setEstat(rs.getBoolean(10));
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
                        " from Usuari where estat= true";
            } else {
                sql = "Select id,nom,p_cognom,s_cognom,email,telefon_mobil,telefon_fixe,contrasenya,esAdmin,estat,nivell" +
                        " from Usuari where estat= false";
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
                selectUsuari.setEsAdmin(rs.getBoolean(9));
                selectUsuari.setEstat(rs.getBoolean(10));
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
    public ObservableList select(Usuari usuari){
        PreparedStatement ps = null;
        ResultSet rs = null;
        Usuari user;
        try {
            String sql = "Select * from Usuari where nom LIKE ? AND p_cognom LIKE ? AND s_cognom LIKE ? AND email LIKE ? " +
                    "AND esAdmin = ? AND estat = ? AND nivell LIKE ?";
            ps = conn.connectar().prepareStatement(sql);
            ps.setString(1,'%'+usuari.getNom()+'%');
            ps.setString(2,'%'+usuari.getPcognom()+'%');
            ps.setString(3,'%'+usuari.getScognom()+'%');
            ps.setString(4,'%'+usuari.getEmail()+'%');
            ps.setBoolean(5,usuari.getEsAdmin());
            ps.setBoolean(6,usuari.getEstat());
            ps.setString(7,'%'+usuari.getNivell()+'%');
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
                user.setEstat(rs.getBoolean(9));
                user.setSalt(rs.getString(10));
                user.setEsAdmin(rs.getBoolean(11));
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
    public boolean insert(Usuari usuari){
        PreparedStatement ps = null;
        try {
            String insert = "Insert into Usuari values (?,?,?,?,?,?,?,?,?,?,?,?)";
            ps = conn.connectar().prepareStatement(insert);
            ps.setInt(1,nextId());
            ps.setString(2,usuari.getNom());
            ps.setString(3,usuari.getPcognom());
            ps.setString(4,usuari.getScognom());
            ps.setInt(5,usuari.getTelefon_mobil());
            ps.setInt(6,usuari.getTelefon_fixe());
            ps.setString(7,usuari.getEmail());
            ps.setString(8,usuari.getContrasenya());
            ps.setBoolean(9,usuari.getEstat());
            ps.setString(10,usuari.getSalt());
            ps.setBoolean(11,usuari.getEsAdmin());
            ps.setString(12,usuari.getNivell());
            ps.executeUpdate();
            ps.close();
            return true;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        } finally {
            try {
                if(ps != null) {
                    ps.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    public boolean delete(Usuari usuari){
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            String delete = "Delete from Usuari where id = ?";
            ps = conn.connectar().prepareStatement(delete);
            ps.setInt(1,usuari.getId());
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
    public boolean update(Usuari usuari){
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            String update = "UPDATE from Usuari SET nom = ?, p_cognom = ?, s_cognom = ?, telefon_mobil = ?, telefon_fixe = ?," +
                    " email = ?, nivell = ? where id = ?";
            ps = conn.connectar().prepareStatement(update);
            ps.setString(1,usuari.getNom());
            ps.setString(2,usuari.getPcognom());
            ps.setString(3,usuari.getScognom());
            ps.setInt(4,usuari.getTelefon_mobil());
            ps.setInt(5,usuari.getTelefon_fixe());
            ps.setString(6,usuari.getEmail());
            ps.setString(7,usuari.getNivell());
            ps.setInt(8,usuari.getId());
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
                update = "UPDATE from Usuari SET estat = true where id = ?";
            } else {
                update = "UPDATE from Usuari SET estat = false where id = ?";
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
    public int nextId(){
        PreparedStatement ps = null;
        ResultSet rs = null;
        int id = 0;
        try {
            String sql = "SELECT max(id)+1 FROM usuari";
            ps = conn.connectar().prepareStatement(sql);
            rs = ps.executeQuery();
            if(!rs.next()){
                id = 1;
            } else {
                id = rs.getInt(1);
            }
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
