package com.company.DAM2.Bibliorevolució.Controladors;

import com.company.DAM2.Bibliorevolució.Classes.Producte;
import com.company.DAM2.Bibliorevolució.Connector.ConnectorBD;
import com.company.DAM2.Bibliorevolució.DAO.ProducteDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ProducteControlador implements ProducteDAO {
    ConnectorBD conn = new ConnectorBD();
    ObservableList<Producte> list = FXCollections.observableArrayList();
    Producte producte;

    public ObservableList selectTotsProductes(){
        PreparedStatement ps = null;
        ResultSet rs = null;
        Producte selectProducte;
        try {
            String sql = "Select * from Producte";
            ps = conn.connectar().prepareStatement(sql);
            rs = ps.executeQuery();
            list.clear();
            while(rs.next()){
                selectProducte = new Producte(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getInt(4),
                        rs.getString(5),
                        rs.getString(6),
                        rs.getString(7),
                        rs.getString(8),
                        rs.getString(9),
                        rs.getString(10),
                        rs.getInt(11),
                        rs.getInt(12),
                        rs.getInt(13),
                        rs.getInt(14),
                        rs.getInt(15),
                        rs.getInt(16),
                        rs.getInt(17),
                        rs.getInt(18),
                        rs.getInt(19)
                );
                list.add(selectProducte);
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
    public ObservableList selectTotsProductesFormat(String format){
        PreparedStatement ps = null;
        ResultSet rs = null;
        Producte selectProducte;
        try {
            String sql = "Select * from Producte p inner join format f on f.id=p.format_id WHERE f.nom = ? ";
            ps = conn.connectar().prepareStatement(sql);
            ps.setString(1,format);
            rs = ps.executeQuery();
            list.clear();
            while(rs.next()){
                selectProducte = new Producte(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getInt(4),
                        rs.getString(5),
                        rs.getString(6),
                        rs.getString(7),
                        rs.getString(8),
                        rs.getString(9),
                        rs.getString(10),
                        rs.getInt(11),
                        rs.getInt(12),
                        rs.getInt(13),
                        rs.getInt(14),
                        rs.getInt(15),
                        rs.getInt(16),
                        rs.getInt(17),
                        rs.getInt(18),
                        rs.getInt(19)
                );
                list.add(selectProducte);
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
    public ObservableList selectTotsProductesActivat(boolean activat){
        PreparedStatement ps = null;
        ResultSet rs = null;
        Producte selectProducte;
        try {
            String sql;
            if(activat) {
                sql = "Select * from Producte WHERE estat = 1";
            } else {
                sql = "Select * from Producte WHERE estat = 0";
            }
            ps = conn.connectar().prepareStatement(sql);
            rs = ps.executeQuery();
            list.clear();
            while(rs.next()){
                selectProducte = new Producte(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getInt(4),
                        rs.getString(5),
                        rs.getString(6),
                        rs.getString(7),
                        rs.getString(8),
                        rs.getString(9),
                        rs.getString(10),
                        rs.getInt(11),
                        rs.getInt(12),
                        rs.getInt(13),
                        rs.getInt(14),
                        rs.getInt(15),
                        rs.getInt(16),
                        rs.getInt(17),
                        rs.getInt(18),
                        rs.getInt(19)
                );
                list.add(selectProducte);
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
    public ObservableList selectProducteEspecific(Producte selProducte){
        PreparedStatement ps = null;
        ResultSet rs = null;
        Producte selectProducte;
        try {
            String sql = "Select * from Producte WHERE id = ?";
            ps = conn.connectar().prepareStatement(sql);
            ps.setInt(1,selProducte.getId());
            rs = ps.executeQuery();
            list.clear();
            producte = new Producte(
                    rs.getInt(1),
                    rs.getString(2),
                    rs.getString(3),
                    rs.getInt(4),
                    rs.getString(5),
                    rs.getString(6),
                    rs.getString(7),
                    rs.getString(8),
                    rs.getString(9),
                    rs.getString(10),
                    rs.getInt(11),
                    rs.getInt(12),
                    rs.getInt(13),
                    rs.getInt(14),
                    rs.getInt(15),
                    rs.getInt(16),
                    rs.getInt(17),
                    rs.getInt(18),
                    rs.getInt(19)
            );
            list.add(producte);

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
    public boolean insertProducte(Producte insProducte){
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            String insert = "Insert into Producte values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
            ps = conn.connectar().prepareStatement(insert);
            ps.setInt(1,nextIdProducte());
            ps.setString(2,insProducte.getISBN());
            ps.setString(3,insProducte.getNom());
            ps.setInt(4,insProducte.getNum_pag());
            ps.setString(5,insProducte.getDimensions());
            ps.setString(6,insProducte.getData());
            ps.setString(7,insProducte.getResum());
            ps.setString(8,insProducte.getCaracteristiques());
            ps.setString(9,insProducte.getUrlPortada());
            ps.setString(10,insProducte.getAdreçaWeb());
            ps.setInt(11,insProducte.getIdTipusProducte());
            ps.setInt(12,insProducte.getEstat());
            ps.setInt(13,insProducte.getIdIdioma());
            ps.setInt(14,insProducte.getIdEditorial());
            ps.setInt(15,insProducte.getIdFormat());
            ps.setInt(16,insProducte.getIdProcedencia());
            ps.setInt(17,insProducte.getIdNivell());
            ps.setInt(18,insProducte.getIdColeccio());
            ps.setInt(19,insProducte.getIdCDU());

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
    public boolean deleteProducte(Producte delProducte){
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            String delete = "Delete from Producte where id_producte = ?";
            ps = conn.connectar().prepareStatement(delete);
            ps.setInt(1,delProducte.getId());
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
    public boolean updateProducte(Producte uptProducte){
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            String update = "UPDATE from Producte SET ISBN = ?, nom = ?, num_pag = ?, dimensions(cm) = ?, data = ?, resum = ?," +
                    "caracteristiques = ?, url_portada = ?, adreça_web = ?, id_tipus_producte = ?, idioma_id = ?, editorial_id = ?," +
                    "format_id = ?, procedencia_id = ?, nivell_id = ?, coleccio_id = ?, cdu_id = ? where id_producte = ?";
            ps = conn.connectar().prepareStatement(update);
            ps.setString(1,uptProducte.getISBN());
            ps.setString(2,uptProducte.getNom());
            ps.setInt(3,uptProducte.getNum_pag());
            ps.setString(4,uptProducte.getDimensions());
            ps.setString(5,uptProducte.getData());
            ps.setString(6,uptProducte.getResum());
            ps.setString(7,uptProducte.getCaracteristiques());
            ps.setString(8,uptProducte.getUrlPortada());
            ps.setString(9,uptProducte.getAdreçaWeb());
            ps.setInt(10,uptProducte.getIdTipusProducte());
            ps.setInt(11,uptProducte.getIdIdioma());
            ps.setInt(12,uptProducte.getIdEditorial());
            ps.setInt(13,uptProducte.getIdFormat());
            ps.setInt(14,uptProducte.getIdProcedencia());
            ps.setInt(15,uptProducte.getIdNivell());
            ps.setInt(16,uptProducte.getIdColeccio());
            ps.setInt(17,uptProducte.getIdCDU());
            ps.setInt(18,uptProducte.getId());
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
    public boolean updateProducteActivar(int id, boolean activat){
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            String update;
            if(activat) {
                update = "UPDATE from Producte SET estat = 1 where id = ?";
            } else {
                update = "UPDATE from Producte SET estat = 0 where id = ?";
            }
            ps = conn.connectar().prepareStatement(update);
            ps.setInt(1,id);
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
    public int nextIdProducte(){
        PreparedStatement ps = null;
        ResultSet rs = null;
        int id = 0;
        try {
            String sql = "SELECT max(id_producte)+1 FROM Producte";
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
