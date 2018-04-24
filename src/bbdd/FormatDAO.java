package com.company.DAM2.Bibliorevolució.BBDD.dao;

import com.company.DAM2.Bibliorevolució.objecte.Format;
import com.company.DAM2.Bibliorevolució.BBDD.connector.ConnectorBD;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FormatDAO implements IObjectDAO<Format>{
    ConnectorBD conn = new ConnectorBD();
    List<Format> list = new ArrayList<>();

    public List<Format> selectAll(){
        PreparedStatement ps = null;
        ResultSet rs = null;
        Format selectFormat;
        try {
            String sql = "Select id,nom from Format";
            ps = conn.connectar().prepareStatement(sql);
            rs = ps.executeQuery();
            list.clear();
            while(rs.next()){
                selectFormat = new Format();
                selectFormat.setId(rs.getInt(1));
                selectFormat.setNom(rs.getString(2));
                list.add(selectFormat);
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
    public List<Format> select(Format format){
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            String sql = "Select * from Format where nom LIKE ? ";
            ps = conn.connectar().prepareStatement(sql);
            ps.setString(1,'%'+format.getNom()+'%');
            rs = ps.executeQuery();
            list.clear();
            while(rs.next()){
                format = new Format();
                format.setId(rs.getInt(1));
                format.setNom(rs.getString(2));
                list.add(format);
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
    public boolean insert(Format format){
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            String insert = "Insert into Format values (?,?)";
            ps = conn.connectar().prepareStatement(insert);
            ps.setInt(1,nextId());
            ps.setString(2,format.getNom());

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
    public boolean delete(Format format){
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            String delete = "Delete from Format where id = ?";
            ps = conn.connectar().prepareStatement(delete);
            ps.setInt(1,format.getId());
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
    public boolean update(Format format){
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            String update = "UPDATE from Format SET nom = ? where id = ?";
            ps = conn.connectar().prepareStatement(update);
            ps.setString(1,format.getNom());
            ps.setInt(2,format.getId());
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
            String sql = "SELECT max(id)+1 FROM Format";
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
