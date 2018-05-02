package bbdd;

import base.ConnectionFactory;
import contract.ContractExemplar;
import objecte.Biblioteca;
import objecte.Exemplar;
import objecte.Producte;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ExemplarDAO implements IObjectDAO<Exemplar> {
    private Connection conn;
    private ResultSet rs;
    private PreparedStatement ps;

    public ExemplarDAO() {
        conn=null;
        rs=null;
        ps=null;
    }

    @Override
    public List<Exemplar> selectAll() throws ClassNotFoundException, SQLException{
        List<Exemplar> list = new ArrayList<>();
        String sql;
        Exemplar selectExemplar;
        try {
            conn = ConnectionFactory.getConnection();
            sql = "Select "+ ContractExemplar.ID+","+ContractExemplar.ID_PRODUCTE+","+ContractExemplar.ID_BIBLIOTECA+","+
                    ContractExemplar.NUMERO_PRESTEC+","+ContractExemplar.ESTAT+" from "+ContractExemplar.NOM_TAULA;
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while(rs.next()){
                selectExemplar = new Exemplar();
                selectExemplar.setId(rs.getInt(1));
                selectExemplar.setIdProducte((Producte) rs.getObject(2));
                selectExemplar.setIdBiblioteca((Biblioteca) rs.getObject(3));
                selectExemplar.setNumprestecs(rs.getInt(4));
                selectExemplar.setEstat(rs.getBoolean(5));

                list.add(selectExemplar);
            }
        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
        } finally {
            this.close();
        }
        return list;
    }
    @Override
    public List<Exemplar> select(Exemplar exemplar) throws ClassNotFoundException, SQLException{
        List<Exemplar> list = new ArrayList<>();
        String sql;
        try {
            conn = ConnectionFactory.getConnection();
            sql = "Select "+ContractExemplar.ID+","+ContractExemplar.ID_PRODUCTE+","+ContractExemplar.ID_BIBLIOTECA+","+
                    ContractExemplar.NUMERO_PRESTEC+","+ContractExemplar.ESTAT+" from "+ContractExemplar.NOM_TAULA+
                    " where "+ContractExemplar.NUMERO_PRESTEC+" = ? ";
            ps = conn.prepareStatement(sql);
            ps.setInt(1,exemplar.getNumprestecs());
            rs = ps.executeQuery();
            while(rs.next()){
                exemplar = new Exemplar();
                exemplar.setId(rs.getInt(1));
                exemplar.setIdProducte((Producte) rs.getObject(2));
                exemplar.setIdBiblioteca((Biblioteca) rs.getObject(3));
                exemplar.setNumprestecs(rs.getInt(4));
                exemplar.setEstat(rs.getBoolean(5));

                list.add(exemplar);
            }
        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
        } finally {
            this.close();
        }
        return list;
    }
    @Override
    public Exemplar select(int id) throws ClassNotFoundException, SQLException{
        Exemplar exemplar = new Exemplar();
        String sql;
        try {
            conn = ConnectionFactory.getConnection();
            sql = "Select "+ContractExemplar.ID+","+ContractExemplar.ID_PRODUCTE+","+ContractExemplar.ID_BIBLIOTECA+","+
                    ContractExemplar.NUMERO_PRESTEC+","+ContractExemplar.ESTAT+" from "+ContractExemplar.NOM_TAULA+
                    " where "+ContractExemplar.ID+" = ? ";
            ps = conn.prepareStatement(sql);
            ps.setInt(1,id);
            rs = ps.executeQuery();
            exemplar.setId(rs.getInt(1));
            exemplar.setIdProducte((Producte) rs.getObject(2));
            exemplar.setIdBiblioteca((Biblioteca) rs.getObject(3));
            exemplar.setNumprestecs(rs.getInt(4));
            exemplar.setEstat(rs.getBoolean(5));
        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
        } finally {
            this.close();
        }
        return exemplar;
    }
    @Override
    public boolean insert(Exemplar exemplar) throws ClassNotFoundException, SQLException{
        String insert;
        boolean inserit = false;
        int id;
        try {
            id = nextId();
            conn = ConnectionFactory.getConnection();
            insert = "Insert into "+ContractExemplar.NOM_TAULA+" values (?,?,?,?,?)";
            ps = conn.prepareStatement(insert);
            ps.setInt(1,id);
            ps.setInt(2,exemplar.getProducte().getId());
            ps.setInt(3,exemplar.getBiblioteca().getId());
            ps.setInt(4,exemplar.getNumprestecs());
            ps.setBoolean(5,exemplar.getEstat());
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
    public boolean delete(Exemplar exemplar) throws ClassNotFoundException, SQLException{
        String delete;
        boolean borrat = false;
        try {
            conn = ConnectionFactory.getConnection();
            delete = "Delete from "+ContractExemplar.NOM_TAULA+" where "+ContractExemplar.ID+" = ?";
            ps = conn.prepareStatement(delete);
            ps.setInt(1,exemplar.getId());
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
    public boolean update(Exemplar exemplar) throws ClassNotFoundException, SQLException{
        String update;
        boolean actualitzat = false;
        try {
            conn = ConnectionFactory.getConnection();
            update = "UPDATE "+ContractExemplar.NOM_TAULA+" SET "+ContractExemplar.ID_PRODUCTE+" = ?, "+
                    ContractExemplar.ID_BIBLIOTECA+" = ?  where "+ContractExemplar.ID+" = ?";
            ps = conn.prepareStatement(update);
            ps.setInt(1,exemplar.getProducte().getId());
            ps.setInt(2,exemplar.getBiblioteca().getId());
            ps.setInt(3,exemplar.getId());
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
            sql = "SELECT max("+ContractExemplar.ID+") FROM "+ContractExemplar.NOM_TAULA;
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
