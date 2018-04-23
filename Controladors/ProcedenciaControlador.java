package com.company.DAM2.Bibliorevolució.Controladors;

import com.company.DAM2.Bibliorevolució.Classes.Procedencia;
import com.company.DAM2.Bibliorevolució.Connector.ConnectorBD;
import com.company.DAM2.Bibliorevolució.DAO.ProcedenciaDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ProcedenciaControlador implements ProcedenciaDAO {
    ConnectorBD conn = new ConnectorBD();
    ObservableList<Procedencia> list = FXCollections.observableArrayList();
    Procedencia procedencia;

    public ObservableList selectTotesProcedencies(){
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
    public ObservableList selectProcedenciaEspecifica(Procedencia selProcedencia){
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            String sql = "Select * from Procedencia where nom LIKE ? ";
            ps = conn.connectar().prepareStatement(sql);
            ps.setString(1,'%'+selProcedencia.getNom()+'%');
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
    public boolean insertProcedencia(Procedencia insProcedencia){
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            String insert = "Insert into Procedencia values (?,?)";
            ps = conn.connectar().prepareStatement(insert);
            ps.setInt(1,nextIdProcedencia());
            ps.setString(2,insProcedencia.getNom());

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
    public boolean deleteProcedencia(Procedencia delProcedencia){
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            String delete = "Delete from Procedencia where id = ?";
            ps = conn.connectar().prepareStatement(delete);
            ps.setInt(1,delProcedencia.getId());
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
    public boolean updateProcedencia(Procedencia uptProcedencia){
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            String update = "UPDATE from Procedencia SET nom = ? where id = ?";
            ps = conn.connectar().prepareStatement(update);
            ps.setString(1,uptProcedencia.getNom());
            ps.setInt(2,uptProcedencia.getId());
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
    public int nextIdProcedencia(){
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
