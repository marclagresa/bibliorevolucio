package com.company.DAM2.Bibliorevolució.Controladors;

import com.company.DAM2.Bibliorevolució.Classes.Nivell;
import com.company.DAM2.Bibliorevolució.Connector.ConnectorBD;
import com.company.DAM2.Bibliorevolució.DAO.NivellDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class NivellControlador implements NivellDAO {
    ConnectorBD conn = new ConnectorBD();
    ObservableList<Nivell> list = FXCollections.observableArrayList();
    Nivell nivell;

    public ObservableList selectTotsNivells(){
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
    public ObservableList selectNivellEspecific(Nivell selNivell){
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            String sql = "Select * from Nivell where nom LIKE ? ";
            ps = conn.connectar().prepareStatement(sql);
            ps.setString(1,'%'+selNivell.getNom()+'%');
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
    public boolean insertNivell(Nivell insNivell){
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            String insert = "Insert into Nivell values (?,?)";
            ps = conn.connectar().prepareStatement(insert);
            ps.setInt(1,nextIdNivell());
            ps.setString(2,insNivell.getNom());

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
    public boolean deleteNivell(Nivell delNivell){
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            String delete = "Delete from Nivell where id = ?";
            ps = conn.connectar().prepareStatement(delete);
            ps.setInt(1,delNivell.getId());
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
    public boolean updateNivell(Nivell uptNivell){
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            String update = "UPDATE from Nivell SET nom = ? where id = ?";
            ps = conn.connectar().prepareStatement(update);
            ps.setString(1,uptNivell.getNom());
            ps.setInt(2,uptNivell.getId());
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
    public int nextIdNivell(){
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
