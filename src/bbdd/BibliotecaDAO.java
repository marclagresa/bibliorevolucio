package com.company.DAM2.Bibliorevolució.BBDD.dao;

import com.company.DAM2.Bibliorevolució.objecte.Biblioteca;
import com.company.DAM2.Bibliorevolució.BBDD.connector.ConnectorBD;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BibliotecaDAO implements IObjectDAO<Biblioteca> {
    ConnectorBD conn = new ConnectorBD();
    ObservableList<Biblioteca> list = FXCollections.observableArrayList();

    public ObservableList selectAll(){
        PreparedStatement ps = null;
        ResultSet rs = null;
        Biblioteca selectBiblioteca;
        try {
            String sql = "Select id,nom from Biblioteca";
            ps = conn.connectar().prepareStatement(sql);
            rs = ps.executeQuery();
            list.clear();
            while(rs.next()){
                selectBiblioteca = new Biblioteca();
                selectBiblioteca.setId(rs.getInt(1));
                selectBiblioteca.setNom(rs.getString(2));
                list.add(selectBiblioteca);
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
    public ObservableList select(Biblioteca biblioteca){
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            String sql = "Select * from Biblioteca where nom LIKE ? ";
            ps = conn.connectar().prepareStatement(sql);
            ps.setString(1,'%'+biblioteca.getNom()+'%');
            rs = ps.executeQuery();
            list.clear();
            while(rs.next()){
                biblioteca = new Biblioteca();
                biblioteca.setId(rs.getInt(1));
                biblioteca.setNom(rs.getString(2));
                list.add(biblioteca);
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
    public boolean insert(Biblioteca biblioteca){
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            String insert = "Insert into Biblioteca values (?,?)";
            ps = conn.connectar().prepareStatement(insert);
            ps.setInt(1,nextId());
            ps.setString(2,biblioteca.getNom());

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
    public boolean delete(Biblioteca biblioteca){
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            String delete = "Delete from Biblioteca where id = ?";
            ps = conn.connectar().prepareStatement(delete);
            ps.setInt(1,biblioteca.getId());
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
    public boolean update(Biblioteca biblioteca){
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            String update = "UPDATE from Biblioteca SET nom = ? where id = ?";
            ps = conn.connectar().prepareStatement(update);
            ps.setString(1,biblioteca.getNom());
            ps.setInt(2,biblioteca.getId());
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
            String sql = "SELECT max(id)+1 FROM Biblioteca";
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
