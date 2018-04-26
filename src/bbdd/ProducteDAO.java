package com.company.DAM2.Bibliorevolució.BBDD.dao;

import com.company.DAM2.Bibliorevolució.BBDD.connector.ConnectionFactory;
import com.company.DAM2.Bibliorevolució.BBDD.contract.ContractProducte;
import com.company.DAM2.Bibliorevolució.objecte.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProducteDAO implements IObjectDAO<Producte> {
    private Connection conn;
    private ResultSet rs;
    private PreparedStatement ps;

    public ProducteDAO(){
        conn=null;
        rs=null;
        ps=null;
    }

    @Override
    public List<Producte> selectAll() throws ClassNotFoundException, SQLException{
        List<Producte> list = new ArrayList<>();
        String sql;
        Producte selectProducte;
        try {
            conn = ConnectionFactory.getConnection();
            sql = "Select "+ContractProducte.ID+","+ContractProducte.ISBN+","+ContractProducte.NOM+","
                    +ContractProducte.NUM_PAG+","+ContractProducte.DIMENSIONS+","+ContractProducte.DATA+","
                    +ContractProducte.RESUM+","+ContractProducte.CARACTERISTIQUES+","
                    +ContractProducte.URL_PORTADA+","+ContractProducte.ADRECA_WEB+","+ContractProducte.ESTAT+","
                    +ContractProducte.IDIOMA_ID+","+ContractProducte.EDITORIAL_ID+","
                    +ContractProducte.FORMAT_ID+","+ContractProducte.PROCEDENCIA_ID+","
                    +ContractProducte.NIVELL_ID+","+ContractProducte.COLECCIO_ID+","+ContractProducte.CDU_ID+
                    "from "+ ContractProducte.NOM_TAULA;
            ps = conn.prepareStatement(sql);
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
                        (Idioma) rs.getObject(12),
                        (Editorial) rs.getObject(13),
                        (Format) rs.getObject(14),
                        (Procedencia) rs.getObject(15),
                        (Nivell) rs.getObject(16),
                        (Coleccio) rs.getObject(17),
                        (Cdu) rs.getObject(18)
                );
                list.add(selectProducte);
            }
        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
        } finally {
            this.close();
        }
        return list;
    }
    @Override
    public List<Producte> select(Producte producte) throws ClassNotFoundException, SQLException{
        List<Producte> list = new ArrayList<>();
        String sql;
        try {
            conn = ConnectionFactory.getConnection();
            sql = "Select "+ContractProducte.ID+","+ContractProducte.ISBN+","+ContractProducte.NOM+","
                    +ContractProducte.NUM_PAG+","+ContractProducte.DIMENSIONS+","+ContractProducte.DATA+","
                    +ContractProducte.RESUM+","+ContractProducte.CARACTERISTIQUES+","
                    +ContractProducte.URL_PORTADA+","+ContractProducte.ADRECA_WEB+","+ContractProducte.ESTAT+","
                    +ContractProducte.IDIOMA_ID+","+ContractProducte.EDITORIAL_ID+","
                    +ContractProducte.FORMAT_ID+","+ContractProducte.PROCEDENCIA_ID+","
                    +ContractProducte.NIVELL_ID+","+ContractProducte.COLECCIO_ID+","+ContractProducte.CDU_ID+
                    " from "+ ContractProducte.NOM_TAULA + " where " + ContractProducte.ID + " = ? ";
            ps = conn.prepareStatement(sql);
            ps.setInt(1,producte.getId());
            rs = ps.executeQuery();
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
                    (Idioma) rs.getObject(12),
                    (Editorial) rs.getObject(13),
                    (Format) rs.getObject(14),
                    (Procedencia) rs.getObject(15),
                    (Nivell) rs.getObject(16),
                    (Coleccio) rs.getObject(17),
                    (Cdu) rs.getObject(18)
            );
            list.add(producte);
        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
        } finally {
            this.close();
        }
        return list;
    }
    @Override
    public boolean insert(Producte producte) throws ClassNotFoundException, SQLException{
        String insert;
        boolean inserit = false;
        try {
            conn = ConnectionFactory.getConnection();
            insert = "Insert into "+ContractProducte.NOM_TAULA+
                    " values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
            ps = conn.prepareStatement(insert);
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
            ps.setBoolean(11,producte.getEstat());
            ps.setObject(12,producte.getIdioma());
            ps.setObject(13,producte.getEditorial());
            ps.setObject(14,producte.getFormat());
            ps.setObject(15,producte.getProcedencia());
            ps.setObject(16,producte.getNivell());
            ps.setObject(17,producte.getColeccio());
            ps.setObject(18,producte.getCDU());
            inserit = true;
        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
        } finally {
            this.close();
        }
        return inserit;
    }
    @Override
    public boolean delete(Producte producte) throws ClassNotFoundException, SQLException{
        String delete;
        boolean borrat = false;
        try {
            conn = ConnectionFactory.getConnection();
            delete = "Delete from "+ContractProducte.NOM_TAULA+" where "+ContractProducte.ID+" = ?";
            ps = conn.prepareStatement(delete);
            ps.setInt(1,producte.getId());
            ps.executeUpdate();
            borrat = true;
        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
        } finally {
            this.close();
        }
        return borrat;
    }
    @Override
    public boolean update(Producte producte) throws ClassNotFoundException, SQLException{
        String update;
        boolean actualitzat = false;
        try {
            conn = ConnectionFactory.getConnection();
            update = "UPDATE from "+ContractProducte.NOM_TAULA+" SET "+ContractProducte.ISBN+" = ?,"
                    +ContractProducte.NOM+" = ?,"+ContractProducte.NUM_PAG+" = ?,"+ContractProducte.DIMENSIONS+" = ?,"
                    +ContractProducte.DATA+" = ?,"+ContractProducte.RESUM+" = ?,"+ContractProducte.CARACTERISTIQUES+" = ?,"
                    +ContractProducte.URL_PORTADA+" = ?,"+ContractProducte.ADRECA_WEB+" = ?,"+ContractProducte.IDIOMA_ID+" = ?,"
                    +ContractProducte.EDITORIAL_ID+" = ?,"+ContractProducte.FORMAT_ID+" = ?,"+ContractProducte.PROCEDENCIA_ID+" = ?,"
                    +ContractProducte.NIVELL_ID+" = ?,"+ContractProducte.COLECCIO_ID+" = ?,"+ContractProducte.CDU_ID+" = ?" +
                    " where "+ContractProducte.ID+" = ?";
            ps = conn.prepareStatement(update);
            ps.setString(1,producte.getISBN());
            ps.setString(2,producte.getNom());
            ps.setInt(3,producte.getNum_pag());
            ps.setString(4,producte.getDimensions());
            ps.setString(5,producte.getData());
            ps.setString(6,producte.getResum());
            ps.setString(7,producte.getCaracteristiques());
            ps.setString(8,producte.getUrlPortada());
            ps.setString(9,producte.getAdreçaWeb());
            ps.setObject(11,producte.getIdioma());
            ps.setObject(12,producte.getEditorial());
            ps.setObject(13,producte.getFormat());
            ps.setObject(14,producte.getProcedencia());
            ps.setObject(15,producte.getNivell());
            ps.setObject(16,producte.getColeccio());
            ps.setObject(17,producte.getCDU());
            ps.setInt(18,producte.getId());
            ps.executeUpdate();
            actualitzat = true;
        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
        } finally {
            this.close();
        }
        return actualitzat;
    }
    @Override
    public int nextId() throws ClassNotFoundException, SQLException{
        int id = 1;
        String sql;
        try {
            conn = ConnectionFactory.getConnection();
            sql = "SELECT max("+ContractProducte.ID+") FROM "+ContractProducte.NOM_TAULA;
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            if(rs.next()){
                id = rs.getInt(1)+1;
            }
        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
        } finally {
            this.close();
        }
        return id;
    }
    @Override
    public void close(){
        if(this.conn!=null){
            try {
                this.conn.close();
                this.conn=null;
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        if(this.ps!=null){
            try {
                this.ps.close();
                this.ps=null;
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        if(this.rs!=null){
            try{
                this.rs.close();
                this.rs=null;
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    /*
    public List<Producte> selectTotsProductesFormat(String format) throws ClassNotFoundException, SQLException{
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
                        (Idioma) rs.getObject(12),
                        (Editorial) rs.getObject(13),
                        (Format) rs.getObject(14),
                        (Procedencia) rs.getObject(15),
                        (Nivell) rs.getObject(16),
                        (Coleccio) rs.getObject(17),
                        (Cdu) rs.getObject(18)
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
    public List<Producte> selectTotsProductesActivat(boolean activat) throws ClassNotFoundException, SQLException{
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
                        (Idioma) rs.getObject(12),
                        (Editorial) rs.getObject(13),
                        (Format) rs.getObject(14),
                        (Procedencia) rs.getObject(15),
                        (Nivell) rs.getObject(16),
                        (Coleccio) rs.getObject(17),
                        (Cdu) rs.getObject(18)
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
    public boolean updateProducteActivar(int id, boolean activat) throws ClassNotFoundException, SQLException{
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
    */
}
