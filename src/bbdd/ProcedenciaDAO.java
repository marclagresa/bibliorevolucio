package bbdd;

import base.ConnectionFactory;
import contract.ContractProcedencia;
import objecte.Procedencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProcedenciaDAO implements IObjectDAO<Procedencia> {
    private Connection conn;
    private ResultSet rs;
    private PreparedStatement ps;

    public ProcedenciaDAO(){
        conn=null;
        rs=null;
        ps=null;
    }

    @Override
    public List<Procedencia> selectAll() throws ClassNotFoundException, SQLException{
        List<Procedencia> list = new ArrayList<>();
        String sql;
        Procedencia selectProcedencia;
        try {
            conn = ConnectionFactory.getConnection();
            sql = "Select "+ ContractProcedencia.ID+","+ContractProcedencia.NOM+
                    " from "+ContractProcedencia.NOM_TAULA;
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while(rs.next()){
                selectProcedencia = new Procedencia();
                selectProcedencia.setId(rs.getInt(1));
                selectProcedencia.setNom(rs.getString(2));
                list.add(selectProcedencia);
            }
        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
        } finally {
            this.close();
        }
        return list;
    }
    @Override
    public List<Procedencia> select(Procedencia procedencia) throws ClassNotFoundException, SQLException{
        List<Procedencia> list = new ArrayList<>();
        String sql;
        try {
            conn = ConnectionFactory.getConnection();
            sql = "Select "+ContractProcedencia.ID+","+ContractProcedencia.NOM+
                    "from "+ContractProcedencia.NOM_TAULA+" where "+ContractProcedencia.NOM+" LIKE ? ";
            ps = conn.prepareStatement(sql);
            ps.setString(1,'%'+procedencia.getNom()+'%');
            rs = ps.executeQuery();
            while(rs.next()){
                procedencia = new Procedencia();
                procedencia.setId(rs.getInt(1));
                procedencia.setNom(rs.getString(2));
                list.add(procedencia);
            }
        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
        } finally {
            this.close();
        }
        return list;
    }
    @Override
    public Procedencia select(int id) throws ClassNotFoundException, SQLException{
        Procedencia procedencia = new Procedencia();
        String sql;
        try {
            conn = ConnectionFactory.getConnection();
            sql = "Select "+ContractProcedencia.ID+","+ContractProcedencia.NOM+
                    "from "+ContractProcedencia.NOM_TAULA+" where "+ContractProcedencia.ID+" = ? ";
            ps = conn.prepareStatement(sql);
            ps.setInt(1,id);
            rs = ps.executeQuery();
            procedencia = new Procedencia();
            procedencia.setId(rs.getInt(1));
            procedencia.setNom(rs.getString(2));
        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
        } finally {
            this.close();
        }
        return procedencia;
    }
    @Override
    public boolean insert(Procedencia procedencia) throws ClassNotFoundException, SQLException{
        String insert;
        boolean inserit = false;
        int id;
        try {
            id = nextId();
            conn = ConnectionFactory.getConnection();
            insert = "Insert into "+ContractProcedencia.NOM_TAULA+" values (?,?)";
            ps = conn.prepareStatement(insert);
            ps.setInt(1,id);
            ps.setString(2,procedencia.getNom());
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
    public boolean delete(Procedencia procedencia) throws ClassNotFoundException, SQLException{
        String delete;
        boolean borrat = false;
        try {
            conn = ConnectionFactory.getConnection();
            delete = "Delete from "+ContractProcedencia.NOM_TAULA+" where "+ContractProcedencia.ID+" = ?";
            ps = conn.prepareStatement(delete);
            ps.setInt(1,procedencia.getId());
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
    public boolean update(Procedencia procedencia) throws ClassNotFoundException, SQLException{
        String update;
        boolean actualitzat = false;
        try {
            conn = ConnectionFactory.getConnection();
            update = "UPDATE "+ContractProcedencia.NOM_TAULA+" SET "+ContractProcedencia.NOM+" = ? " +
                    "where "+ContractProcedencia.ID+" = ?";
            ps = conn.prepareStatement(update);
            ps.setString(1,procedencia.getNom());
            ps.setInt(2,procedencia.getId());
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
            sql = "SELECT max("+ContractProcedencia.ID+") FROM "+ContractProcedencia.NOM_TAULA;
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
