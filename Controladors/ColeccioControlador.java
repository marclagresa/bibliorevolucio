package com.company.DAM2.Bibliorevolució.Controladors;

import com.company.DAM2.Bibliorevolució.Classes.Coleccio;
import com.company.DAM2.Bibliorevolució.Connector.ConnectorBD;
import com.company.DAM2.Bibliorevolució.DAO.ColeccioDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ColeccioControlador implements ColeccioDAO {
    ConnectorBD conn = new ConnectorBD();
    ObservableList<Coleccio> list = FXCollections.observableArrayList();
    Coleccio coleccio;

    public ObservableList selectTotesColeccions(){
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
    public ObservableList selectColeccioEspecifica(Coleccio selColeccio){
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            String sql = "Select * from Coleccio where nom LIKE ? ";
            ps = conn.connectar().prepareStatement(sql);
            ps.setString(1,'%'+selColeccio.getNom()+'%');
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
    public boolean insertColeccio(Coleccio insColeccio){
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            String insert = "Insert into Coleccio values (?,?)";
            ps = conn.connectar().prepareStatement(insert);
            ps.setInt(1,nextIdColeccio());
            ps.setString(2,insColeccio.getNom());

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
    public boolean deleteColeccio(Coleccio delColeccio){
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            String delete = "Delete from Coleccio where id = ?";
            ps = conn.connectar().prepareStatement(delete);
            ps.setInt(1,delColeccio.getId());
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
    public boolean updateColeccio(Coleccio uptColeccio){
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            String update = "UPDATE from Biblioteca SET nom = ? where id = ?";
            ps = conn.connectar().prepareStatement(update);
            ps.setString(1,uptColeccio.getNom());
            ps.setInt(2,uptColeccio.getId());
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
    public int nextIdColeccio(){
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
