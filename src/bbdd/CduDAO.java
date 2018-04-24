package com.company.DAM2.Bibliorevolució.BBDD.dao;

import com.company.DAM2.Bibliorevolució.objecte.Cdu;
import com.company.DAM2.Bibliorevolució.BBDD.connector.ConnectorBD;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CduDAO implements IObjectDAO<Cdu> {
    ConnectorBD conn = new ConnectorBD();
    List<Cdu> list = new ArrayList<>();

    public List<Cdu> selectAll(){
        PreparedStatement ps = null;
        ResultSet rs = null;
        Cdu selectCdu;
        try {
            String sql = "Select id,nom,idPare from Cdu";
            ps = conn.connectar().prepareStatement(sql);
            rs = ps.executeQuery();
            list.clear();
            while(rs.next()){
                selectCdu = new Cdu();
                selectCdu.setId(rs.getInt(1));
                selectCdu.setNom(rs.getString(2));
                selectCdu.setIdPare(rs.getInt(3));
                list.add(selectCdu);
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
    public List<Cdu> select(Cdu cdu){
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            String sql = "Select * from Cdu where nom LIKE ? ";
            ps = conn.connectar().prepareStatement(sql);
            ps.setString(1,'%'+cdu.getNom()+'%');
            rs = ps.executeQuery();
            list.clear();
            while(rs.next()){
                cdu = new Cdu();
                cdu.setId(rs.getInt(1));
                cdu.setNom(rs.getString(2));
                cdu.setIdPare(rs.getInt(3));
                list.add(cdu);
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
    public boolean insert(Cdu cdu){
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            String insert = "Insert into Cdu values (?,?,?)";
            ps = conn.connectar().prepareStatement(insert);
            ps.setInt(1,nextId());
            ps.setString(2,cdu.getNom());
            ps.setInt(3,cdu.getIdPare());

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
    public boolean delete(Cdu cdu){
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            String delete = "Delete from Cdu where id = ?";
            ps = conn.connectar().prepareStatement(delete);
            ps.setInt(1,cdu.getId());
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
    public boolean update(Cdu cdu){
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            String update = "UPDATE from Cdu SET nom = ?, idPare = ? where id = ?";
            ps = conn.connectar().prepareStatement(update);
            ps.setString(1,cdu.getNom());
            ps.setInt(2,cdu.getIdPare());
            ps.setInt(3,cdu.getId());
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
            String sql = "SELECT max(id)+1 FROM Cdu";
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
