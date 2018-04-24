package com.company.DAM2.Bibliorevolució.BBDD.dao;

import com.company.DAM2.Bibliorevolució.objecte.Nivell;
import com.company.DAM2.Bibliorevolució.BBDD.connector.ConnectorBD;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class NivellDAO implements IObjectDAO<Nivell> {
    ConnectorBD conn = new ConnectorBD();
    List<Nivell> list = new ArrayList<>();

    public List<Nivell> selectAll(){
        PreparedStatement ps = null;
        ResultSet rs = null;
        Nivell selectNivell;
        try {
            String sql = "Select id,nom from Nivell";
            ps = conn.connectar().prepareStatement(sql);
            rs = ps.executeQuery();
            list.clear();
            while(rs.next()){
                selectNivell = new Nivell();
                selectNivell.setId(rs.getInt(1));
                selectNivell.setNom(rs.getString(2));
                list.add(selectNivell);
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
    public List<Nivell> select(Nivell nivell){
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            String sql = "Select * from Nivell where nom LIKE ? ";
            ps = conn.connectar().prepareStatement(sql);
            ps.setString(1,'%'+nivell.getNom()+'%');
            rs = ps.executeQuery();
            list.clear();
            while(rs.next()){
                nivell = new Nivell();
                nivell.setId(rs.getInt(1));
                nivell.setNom(rs.getString(2));
                list.add(nivell);
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
    public boolean insert(Nivell nivell){
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            String insert = "Insert into Nivell values (?,?)";
            ps = conn.connectar().prepareStatement(insert);
            ps.setInt(1,nextId());
            ps.setString(2,nivell.getNom());

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
    public boolean delete(Nivell nivell){
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            String delete = "Delete from Nivell where id = ?";
            ps = conn.connectar().prepareStatement(delete);
            ps.setInt(1,nivell.getId());
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
    public boolean update(Nivell nivell){
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            String update = "UPDATE from Nivell SET nom = ? where id = ?";
            ps = conn.connectar().prepareStatement(update);
            ps.setString(1,nivell.getNom());
            ps.setInt(2,nivell.getId());
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
            String sql = "SELECT max(id)+1 FROM Nivell";
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
