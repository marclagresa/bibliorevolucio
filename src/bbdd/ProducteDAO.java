package bbdd;

import base.ConnectionFactory;
import contract.ContractProducte;
import objecte.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
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
            conn = ConnectionFactory.getInstance().getConnection();
            sql = "Select "+ ContractProducte.ID+","+ContractProducte.ISBN+","+ContractProducte.NOM+","
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
    public List<Producte> select(HashMap <String,Object> producte) throws ClassNotFoundException, SQLException{
        List<Producte> list = new ArrayList<>();
        String sql;
        try {
            conn = ConnectionFactory.getInstance().getConnection();
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
    public Producte select(int id) throws ClassNotFoundException, SQLException{
        Producte producte = new Producte();
        IdiomaDAO idioma = new IdiomaDAO();
        EditorialDAO editorial = new EditorialDAO();
        FormatDAO format = new FormatDAO();
        String sql;
        try {
            conn = ConnectionFactory.getInstance().getConnection();
            sql = "Select "+ContractProducte.ID+","+ContractProducte.ISBN+","+ContractProducte.NOM+","
                    +ContractProducte.NUM_PAG+","+ContractProducte.DIMENSIONS+","+ContractProducte.DATA+","
                    +ContractProducte.RESUM+","+ContractProducte.CARACTERISTIQUES+","
                    +ContractProducte.URL_PORTADA+","+ContractProducte.ADRECA_WEB+","+ContractProducte.ESTAT+","
                    +ContractProducte.IDIOMA_ID+","+ContractProducte.EDITORIAL_ID+","
                    +ContractProducte.FORMAT_ID+","+ContractProducte.PROCEDENCIA_ID+","
                    +ContractProducte.NIVELL_ID+","+ContractProducte.COLECCIO_ID+","+ContractProducte.CDU_ID+
                    " from "+ ContractProducte.NOM_TAULA + " where " + ContractProducte.ID + " = ? ";
            ps = conn.prepareStatement(sql);
            ps.setInt(1,id);
            rs = ps.executeQuery();
            producte.setId(rs.getInt(1));
            producte.setISBN(rs.getString(2));
            producte.setNom(rs.getString(3));
            producte.setNum_pag(rs.getInt(4));
            producte.setDimensions(rs.getString(5));
            producte.setData(rs.getString(6));
            producte.setResum(rs.getString(7));
            producte.setCaracteristiques(rs.getString(8));
            producte.setUrlPortada(rs.getString(9));
            producte.setAdreçaWeb(rs.getString(10));
            producte.setEstat(rs.getBoolean(11));
            producte.setIdioma(idioma.select(rs.getInt(12)));

        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
        } finally {
            this.close();
        }
        return producte;
    }
    @Override
    public boolean insert(Producte producte) throws ClassNotFoundException, SQLException{
        String insert;
        boolean inserit = false;
        int id;
        try {
            conn = ConnectionFactory.getInstance().getConnection();
            id = nextId();
            insert = "Insert into "+ContractProducte.NOM_TAULA+
                    " values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
            ps = conn.prepareStatement(insert);
            ps.setInt(1,id);
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
            ps.setInt(12,producte.getIdioma().getId());
            ps.setInt(13,producte.getEditorial().getId());
            ps.setInt(14,producte.getFormat().getId());
            ps.setInt(15,producte.getProcedencia().getId());
            ps.setInt(16,producte.getNivell().getId());
            ps.setInt(17,producte.getColeccio().getId());
            ps.setInt(18,producte.getCDU().getId());
            ps.executeUpdate();
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
            conn = ConnectionFactory.getInstance().getConnection();
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
            conn = ConnectionFactory.getInstance().getConnection();
            update = "UPDATE "+ContractProducte.NOM_TAULA+" SET "+ContractProducte.ISBN+" = ?,"
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
            ps.setInt(10,producte.getIdioma().getId());
            ps.setInt(11,producte.getEditorial().getId());
            ps.setInt(12,producte.getFormat().getId());
            ps.setInt(13,producte.getProcedencia().getId());
            ps.setInt(14,producte.getNivell().getId());
            ps.setInt(15,producte.getColeccio().getId());
            ps.setInt(16,producte.getCDU().getId());
            ps.setInt(17,producte.getId());
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
            conn = ConnectionFactory.getInstance().getConnection();
            sql = "SELECT max("+ContractProducte.ID+") FROM "+ContractProducte.NOM_TAULA;
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            if(rs.next()){
                id = rs.getInt(1)+1;
            }
        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
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
}
