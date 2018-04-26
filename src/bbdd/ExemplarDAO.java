package com.company.DAM2.Bibliorevolució.BBDD.dao;

import com.company.DAM2.Bibliorevolució.objecte.Biblioteca;
import com.company.DAM2.Bibliorevolució.objecte.Exemplar;
import com.company.DAM2.Bibliorevolució.objecte.Producte;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ExemplarDAO implements IObjectDAO<Exemplar> {
    ConnectorBD conn = new ConnectorBD();
    List<Exemplar> list = new ArrayList<>();

    public List<Exemplar> selectAll(){
        PreparedStatement ps = null;
        ResultSet rs = null;
        Exemplar selectExemplar;
        try {
            String sql = "Select * from Exemplar";
            ps = conn.connectar().prepareStatement(sql);
            rs = ps.executeQuery();
            list.clear();
            while(rs.next()){
                selectExemplar = new Exemplar();
                selectExemplar.setId(rs.getInt(1));
                selectExemplar.setEstat(rs.getBoolean(2));
                selectExemplar.setNumprestecs(rs.getInt(3));
                selectExemplar.setIdProducte((Producte) rs.getObject(4));
                selectExemplar.setIdBiblioteca((Biblioteca) rs.getObject(5));
                list.add(selectExemplar);
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
    public List<Exemplar> select(Exemplar exemplar){
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            String sql = "Select * from Exemplar where numero_prestec = ? ";
            ps = conn.connectar().prepareStatement(sql);
            ps.setInt(1,exemplar.getNumprestecs());
            rs = ps.executeQuery();
            list.clear();
            while(rs.next()){
                exemplar = new Exemplar();
                exemplar.setId(rs.getInt(1));
                exemplar.setEstat(rs.getBoolean(2));
                exemplar.setNumprestecs(rs.getInt(3));
                exemplar.setIdProducte((Producte) rs.getObject(4));
                exemplar.setIdBiblioteca((Biblioteca) rs.getObject(5));
                list.add(exemplar);
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
    public boolean insert(Exemplar exemplar){
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            String insert = "Insert into Exemplar values (?,?,?,?,?)";
            ps = conn.connectar().prepareStatement(insert);
            ps.setInt(1,nextId());
            ps.setObject(2,exemplar.getProducte());
            ps.setObject(3,exemplar.getBiblioteca());
            ps.setInt(4,exemplar.getNumprestecs());
            ps.setBoolean(5,exemplar.getEstat());

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
    public boolean delete(Exemplar exemplar){
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            String delete = "Delete from Exemplar where id = ?";
            ps = conn.connectar().prepareStatement(delete);
            ps.setInt(1,exemplar.getId());
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
    public boolean update(Exemplar exemplar){
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            String update = "UPDATE from Exemplar SET producte = ?, biblioteca = ?  where id = ?";
            ps = conn.connectar().prepareStatement(update);
            ps.setObject(1,exemplar.getProducte());
            ps.setObject(2,exemplar.getBiblioteca());
            ps.setInt(3,exemplar.getId());
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
            String sql = "SELECT max(id)+1 FROM Exemplar";
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
