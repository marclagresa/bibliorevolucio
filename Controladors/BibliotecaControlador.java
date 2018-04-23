package com.company.DAM2.Bibliorevolució.Controladors;

import com.company.DAM2.Bibliorevolució.Classes.Biblioteca;
import com.company.DAM2.Bibliorevolució.Classes.Usuari;
import com.company.DAM2.Bibliorevolució.Connector.ConnectorBD;
import com.company.DAM2.Bibliorevolució.DAO.BibliotecaDAO;
import com.company.DAM2.Bibliorevolució.DAO.UsuariDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BibliotecaControlador implements BibliotecaDAO {
    ConnectorBD conn = new ConnectorBD();
    ObservableList<Biblioteca> list = FXCollections.observableArrayList();
    Biblioteca biblioteca;

    public ObservableList selectTotesBiblioteques(){
        PreparedStatement ps = null;
        ResultSet rs = null;
        Biblioteca selectBiblioteca;
        try {
            String sql = "Select id,nom from Biblioteca";
            ps = conn.connectar().prepareStatement(sql);
            rs = ps.executeQuery();
            list.clear();
            while(rs.next()){
                selectBiblioteca = new Biblioteca();
                selectBiblioteca.setId(rs.getInt(1));
                selectBiblioteca.setNom(rs.getString(2));
                list.add(selectBiblioteca);
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
    public ObservableList selectBibliotecaEspecifica(Biblioteca selBiblioteca){
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            String sql = "Select * from Biblioteca where nom LIKE ? ";
            ps = conn.connectar().prepareStatement(sql);
            ps.setString(1,'%'+selBiblioteca.getNom()+'%');
            rs = ps.executeQuery();
            list.clear();
            while(rs.next()){
                biblioteca = new Biblioteca();
                biblioteca.setId(rs.getInt(1));
                biblioteca.setNom(rs.getString(2));
                list.add(biblioteca);
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
    public boolean insertBiblioteca(Biblioteca insBiblioteca){
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            String insert = "Insert into Biblioteca values (?,?)";
            ps = conn.connectar().prepareStatement(insert);
            ps.setInt(1,nextIdBiblioteca());
            ps.setString(2,insBiblioteca.getNom());

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
    public boolean deleteBiblioteca(Biblioteca delBiblioteca){
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            String delete = "Delete from Biblioteca where id = ?";
            ps = conn.connectar().prepareStatement(delete);
            ps.setInt(1,delBiblioteca.getId());
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
    public boolean updateBiblioteca(Biblioteca uptBiblioteca){
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            String update = "UPDATE from Biblioteca SET nom = ? where id = ?";
            ps = conn.connectar().prepareStatement(update);
            ps.setString(1,uptBiblioteca.getNom());
            ps.setInt(2,uptBiblioteca.getId());
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
    public int nextIdBiblioteca(){
        PreparedStatement ps = null;
        ResultSet rs = null;
        int id = 0;
        try {
            String sql = "SELECT max(id)+1 FROM Biblioteca";
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
