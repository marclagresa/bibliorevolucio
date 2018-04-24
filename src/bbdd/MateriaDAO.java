package com.company.DAM2.Bibliorevolució.BBDD.dao;

import com.company.DAM2.Bibliorevolució.objecte.Materia;
import com.company.DAM2.Bibliorevolució.BBDD.connector.ConnectorBD;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MateriaDAO implements IObjectDAO<Materia> {
    ConnectorBD conn = new ConnectorBD();
    ObservableList<Materia> list = FXCollections.observableArrayList();

    public ObservableList selectAll(){
        PreparedStatement ps = null;
        ResultSet rs = null;
        Materia selectMateria;
        try {
            String sql = "Select id,nom from Materia";
            ps = conn.connectar().prepareStatement(sql);
            rs = ps.executeQuery();
            list.clear();
            while(rs.next()){
                selectMateria = new Materia();
                selectMateria.setId(rs.getInt(1));
                selectMateria.setNom(rs.getString(2));
                list.add(selectMateria);
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
    public ObservableList select(Materia materia){
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            String sql = "Select * from Materia where nom LIKE ? ";
            ps = conn.connectar().prepareStatement(sql);
            ps.setString(1,'%'+materia.getNom()+'%');
            rs = ps.executeQuery();
            list.clear();
            while(rs.next()){
                materia = new Materia();
                materia.setId(rs.getInt(1));
                materia.setNom(rs.getString(2));
                list.add(materia);
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
    public boolean insert(Materia materia){
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            String insert = "Insert into Materia values (?,?)";
            ps = conn.connectar().prepareStatement(insert);
            ps.setInt(1,nextId());
            ps.setString(2,materia.getNom());

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
    public boolean delete(Materia materia){
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            String delete = "Delete from Materia where id = ?";
            ps = conn.connectar().prepareStatement(delete);
            ps.setInt(1,materia.getId());
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
    public boolean update(Materia materia){
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            String update = "UPDATE from Materia SET nom = ? where id = ?";
            ps = conn.connectar().prepareStatement(update);
            ps.setString(1,materia.getNom());
            ps.setInt(2,materia.getId());
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
            String sql = "SELECT max(id)+1 FROM Materia";
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
