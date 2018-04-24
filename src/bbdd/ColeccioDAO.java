package com.company.DAM2.Bibliorevolució.BBDD.dao;

import com.company.DAM2.Bibliorevolució.objecte.Coleccio;
import com.company.DAM2.Bibliorevolució.BBDD.connector.ConnectorBD;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ColeccioDAO implements IObjectDAO<Coleccio> {
    ConnectorBD conn = new ConnectorBD();
    ObservableList<Coleccio> list = FXCollections.observableArrayList();

    public ObservableList selectAll(){
        PreparedStatement ps = null;
        ResultSet rs = null;
        Coleccio selectColeccio;
        try {
            String sql = "Select id,nom from Coleccio";
            ps = conn.connectar().prepareStatement(sql);
            rs = ps.executeQuery();
            list.clear();
            while(rs.next()){
                selectColeccio = new Coleccio();
                selectColeccio.setId(rs.getInt(1));
                selectColeccio.setNom(rs.getString(2));
                list.add(selectColeccio);
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
    public ObservableList select(Coleccio coleccio){
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            String sql = "Select * from Coleccio where nom LIKE ? ";
            ps = conn.connectar().prepareStatement(sql);
            ps.setString(1,'%'+coleccio.getNom()+'%');
            rs = ps.executeQuery();
            list.clear();
            while(rs.next()){
                coleccio = new Coleccio();
                coleccio.setId(rs.getInt(1));
                coleccio.setNom(rs.getString(2));
                list.add(coleccio);
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
    public boolean insert(Coleccio coleccio){
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            String insert = "Insert into Coleccio values (?,?)";
            ps = conn.connectar().prepareStatement(insert);
            ps.setInt(1,nextId());
            ps.setString(2,coleccio.getNom());

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
    public boolean delete(Coleccio coleccio){
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            String delete = "Delete from Coleccio where id = ?";
            ps = conn.connectar().prepareStatement(delete);
            ps.setInt(1,coleccio.getId());
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
    public boolean update(Coleccio coleccio){
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            String update = "UPDATE from Biblioteca SET nom = ? where id = ?";
            ps = conn.connectar().prepareStatement(update);
            ps.setString(1,coleccio.getNom());
            ps.setInt(2,coleccio.getId());
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
            String sql = "SELECT max(id)+1 FROM Coleccio";
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
