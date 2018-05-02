package bbdd;

import base.ConnectionFactory;
import contract.ContractColeccio;
import objecte.Coleccio;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ColeccioDAO implements IObjectDAO<Coleccio> {
    private Connection conn;
    private ResultSet rs;
    private PreparedStatement ps;

    public ColeccioDAO(){
        conn = null;
        rs = null;
        ps = null;
    }

    @Override
    public List<Coleccio> selectAll() throws ClassNotFoundException, SQLException{
        List<Coleccio> list = new ArrayList<>();
        Coleccio selectColeccio;
        String sql;
        try {
            conn = ConnectionFactory.getConnection();
            sql = "Select "+ ContractColeccio.ID+","+ContractColeccio.NOM+" from "+ContractColeccio.NOM_TAULA;
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while(rs.next()){
                selectColeccio = new Coleccio();
                selectColeccio.setId(rs.getInt(1));
                selectColeccio.setNom(rs.getString(2));
                list.add(selectColeccio);
            }
        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
        } finally {
            this.close();
        }
        return list;
    }
    @Override
    public List<Coleccio> select(Coleccio coleccio) throws ClassNotFoundException, SQLException{
        List<Coleccio> list = new ArrayList<>();
        String sql;
        try {
            conn = ConnectionFactory.getConnection();
            sql = "Select "+ContractColeccio.ID+","+ContractColeccio.NOM+
                    " from "+ContractColeccio.NOM_TAULA+" where "+ContractColeccio.NOM+" LIKE ? ";
            ps = conn.prepareStatement(sql);
            ps.setString(1,'%'+coleccio.getNom()+'%');
            rs = ps.executeQuery();
            while(rs.next()){
                coleccio = new Coleccio();
                coleccio.setId(rs.getInt(1));
                coleccio.setNom(rs.getString(2));
                list.add(coleccio);
            }
        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
        } finally {
            this.close();
        }
        return list;
    }
    @Override
    public Coleccio select(int id) throws ClassNotFoundException, SQLException{
        Coleccio coleccio = new Coleccio();
        String sql;
        try {
            conn = ConnectionFactory.getConnection();
            sql = "Select "+ContractColeccio.ID+","+ContractColeccio.NOM+
                    " from "+ContractColeccio.NOM_TAULA+" where "+ContractColeccio.ID+" = ? ";
            ps = conn.prepareStatement(sql);
            ps.setInt(1,id);
            rs = ps.executeQuery();
            coleccio.setId(rs.getInt(1));
            coleccio.setNom(rs.getString(2));
        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
        } finally {
            this.close();
        }
        return coleccio;
    }
    @Override
    public boolean insert(Coleccio coleccio) throws ClassNotFoundException, SQLException{
        String insert;
        boolean inserit = false;
        int id;
        try {
            id = nextId();
            conn = ConnectionFactory.getConnection();
            insert = "Insert into "+ContractColeccio.NOM_TAULA+" values (?,?)";
            ps = conn.prepareStatement(insert);
            ps.setInt(1,id);
            ps.setString(2,coleccio.getNom());
            ps.executeUpdate();
            inserit = true;
        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        return inserit;
    }
    @Override
    public boolean delete(Coleccio coleccio) throws ClassNotFoundException, SQLException{
        String delete;
        boolean borrat = false;
        try {
            conn = ConnectionFactory.getConnection();
            delete = "Delete from "+ContractColeccio.NOM_TAULA+" where "+ContractColeccio.ID+" = ?";
            ps = conn.prepareStatement(delete);
            ps.setInt(1,coleccio.getId());
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
    public boolean update(Coleccio coleccio) throws ClassNotFoundException, SQLException{
        String update;
        boolean actualitzat = false;
        try {
            conn = ConnectionFactory.getConnection();
            update = "UPDATE "+ ContractColeccio.NOM_TAULA+" SET "+ContractColeccio.NOM+" = ? " +
                    "where "+ContractColeccio.ID+" = ?";
            ps = conn.prepareStatement(update);
            ps.setString(1,coleccio.getNom());
            ps.setInt(2,coleccio.getId());
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
        String sql;
        int id = 1;
        try {
            conn = ConnectionFactory.getConnection();
            sql = "SELECT max("+ContractColeccio.ID+") FROM "+ContractColeccio.NOM_TAULA;
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
