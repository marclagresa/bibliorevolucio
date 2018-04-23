package com.company.DAM2.Bibliorevolució.Controladors;

import com.company.DAM2.Bibliorevolució.Classes.Format;
import com.company.DAM2.Bibliorevolució.Connector.ConnectorBD;
import com.company.DAM2.Bibliorevolució.DAO.FormatDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class FormatControlador implements FormatDAO {
    ConnectorBD conn = new ConnectorBD();
    ObservableList<Format> list = FXCollections.observableArrayList();
    Format format;

    public ObservableList selectTotsFormats(){
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
    public ObservableList selectFormatEspecific(Format selFormat){
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            String sql = "Select * from Format where nom LIKE ? ";
            ps = conn.connectar().prepareStatement(sql);
            ps.setString(1,'%'+selFormat.getNom()+'%');
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
    public boolean insertFormat(Format insFormat){
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            String insert = "Insert into Format values (?,?)";
            ps = conn.connectar().prepareStatement(insert);
            ps.setInt(1,nextIdFormat());
            ps.setString(2,insFormat.getNom());

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
    public boolean deleteFormat(Format delFormat){
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            String delete = "Delete from Format where id = ?";
            ps = conn.connectar().prepareStatement(delete);
            ps.setInt(1,delFormat.getId());
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
    public boolean updateFormat(Format uptFormat){
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            String update = "UPDATE from Format SET nom = ? where id = ?";
            ps = conn.connectar().prepareStatement(update);
            ps.setString(1,uptFormat.getNom());
            ps.setInt(2,uptFormat.getId());
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
    public int nextIdFormat(){
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
