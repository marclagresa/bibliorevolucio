package com.company.DAM2.Bibliorevolució.BBDD.dao;

import com.company.DAM2.Bibliorevolució.objecte.Producte;
import com.company.DAM2.Bibliorevolució.BBDD.connector.ConnectorBD;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProducteDAO implements IObjectDAO<Producte> {
    ConnectorBD conn = new ConnectorBD();
    List<Producte> list = new ArrayList<>();

    public List<Producte> selectAll(){
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
                        rs.getBoolean(11),
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
    public List<Producte> selectTotsProductesFormat(String format){
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
                        rs.getBoolean(11),
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
    public List<Producte> selectTotsProductesActivat(boolean activat){
        PreparedStatement ps = null;
        ResultSet rs = null;
        Producte selectProducte;
        try {
            String sql;
            if(activat) {
                sql = "Select * from Producte WHERE estat = true";
            } else {
                sql = "Select * from Producte WHERE estat = false";
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
                        rs.getBoolean(11),
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
    public List<Producte> select(Producte producte){
        PreparedStatement ps = null;
        ResultSet rs = null;
        Producte selectProducte;
        try {
            String sql = "Select * from Producte WHERE id = ?";
            ps = conn.connectar().prepareStatement(sql);
            ps.setInt(1,producte.getId());
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
                    rs.getBoolean(11),
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
    public boolean insert(Producte producte){
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            String insert = "Insert into Producte values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
            ps = conn.connectar().prepareStatement(insert);
            ps.setInt(1,nextId());
            ps.setString(2,producte.getISBN());
            ps.setString(3,producte.getNom());
            ps.setInt(4,producte.getNum_pag());
            ps.setString(5,producte.getDimensions());
            ps.setString(6,producte.getData());
            ps.setString(7,producte.getResum());
            ps.setString(8,producte.getCaracteristiques());
            ps.setString(9,producte.getUrlPortada());
            ps.setString(10,producte.getAdreçaWeb());
            ps.setInt(11,producte.getIdTipusProducte());
            ps.setBoolean(12,producte.getEstat());
            ps.setInt(13,producte.getIdIdioma());
            ps.setInt(14,producte.getIdEditorial());
            ps.setInt(15,producte.getIdFormat());
            ps.setInt(16,producte.getIdProcedencia());
            ps.setInt(17,producte.getIdNivell());
            ps.setInt(18,producte.getIdColeccio());
            ps.setInt(19,producte.getIdCDU());

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
    public boolean delete(Producte producte){
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            String delete = "Delete from Producte where id_producte = ?";
            ps = conn.connectar().prepareStatement(delete);
            ps.setInt(1,producte.getId());
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
    public boolean update(Producte producte){
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            String update = "UPDATE from Producte SET ISBN = ?, nom = ?, num_pag = ?, dimensions(cm) = ?, data = ?, resum = ?," +
                    "caracteristiques = ?, url_portada = ?, adreça_web = ?, id_tipus_producte = ?, idioma_id = ?, editorial_id = ?," +
                    "format_id = ?, procedencia_id = ?, nivell_id = ?, coleccio_id = ?, cdu_id = ? where id_producte = ?";
            ps = conn.connectar().prepareStatement(update);
            ps.setString(1,producte.getISBN());
            ps.setString(2,producte.getNom());
            ps.setInt(3,producte.getNum_pag());
            ps.setString(4,producte.getDimensions());
            ps.setString(5,producte.getData());
            ps.setString(6,producte.getResum());
            ps.setString(7,producte.getCaracteristiques());
            ps.setString(8,producte.getUrlPortada());
            ps.setString(9,producte.getAdreçaWeb());
            ps.setInt(10,producte.getIdTipusProducte());
            ps.setInt(11,producte.getIdIdioma());
            ps.setInt(12,producte.getIdEditorial());
            ps.setInt(13,producte.getIdFormat());
            ps.setInt(14,producte.getIdProcedencia());
            ps.setInt(15,producte.getIdNivell());
            ps.setInt(16,producte.getIdColeccio());
            ps.setInt(17,producte.getIdCDU());
            ps.setInt(18,producte.getId());
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
                update = "UPDATE from Producte SET estat = true where id = ?";
            } else {
                update = "UPDATE from Producte SET estat = false where id = ?";
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
    public int nextId(){
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
