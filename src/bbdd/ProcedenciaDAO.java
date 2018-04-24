package com.company.DAM2.Bibliorevolució.BBDD.dao;

import com.company.DAM2.Bibliorevolució.objecte.Procedencia;
import com.company.DAM2.Bibliorevolució.BBDD.connector.ConnectorBD;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ProcedenciaDAO implements IObjectDAO<Procedencia> {
    ConnectorBD conn = new ConnectorBD();
    ObservableList<Procedencia> list = FXCollections.observableArrayList();

    public ObservableList selectAll(){
        PreparedStatement ps = null;
        ResultSet rs = null;
        Procedencia selectProcedencia;
        try {
            String sql = "Select id,nom from Procedencia";
            ps = conn.connectar().prepareStatement(sql);
            rs = ps.executeQuery();
            list.clear();
            while(rs.next()){
                selectProcedencia = new Procedencia();
                selectProcedencia.setId(rs.getInt(1));
                selectProcedencia.setNom(rs.getString(2));
                list.add(selectProcedencia);
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
    public ObservableList select(Procedencia procedencia){
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            String sql = "Select * from Procedencia where nom LIKE ? ";
            ps = conn.connectar().prepareStatement(sql);
            ps.setString(1,'%'+procedencia.getNom()+'%');
            rs = ps.executeQuery();
            list.clear();
            while(rs.next()){
                procedencia = new Procedencia();
                procedencia.setId(rs.getInt(1));
                procedencia.setNom(rs.getString(2));
                list.add(procedencia);
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
    public boolean insert(Procedencia procedencia){
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            String insert = "Insert into Procedencia values (?,?)";
            ps = conn.connectar().prepareStatement(insert);
            ps.setInt(1,nextId());
            ps.setString(2,procedencia.getNom());

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
    public boolean delete(Procedencia procedencia){
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            String delete = "Delete from Procedencia where id = ?";
            ps = conn.connectar().prepareStatement(delete);
            ps.setInt(1,procedencia.getId());
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
    public boolean update(Procedencia procedencia){
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            String update = "UPDATE from Procedencia SET nom = ? where id = ?";
            ps = conn.connectar().prepareStatement(update);
            ps.setString(1,procedencia.getNom());
            ps.setInt(2,procedencia.getId());
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
            String sql = "SELECT max(id)+1 FROM Procedencia";
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
