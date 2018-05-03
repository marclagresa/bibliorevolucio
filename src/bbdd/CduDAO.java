package bbdd;

import base.ConnectionFactory;
import contract.ContractCdu;
import objecte.Cdu;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author albertCorominas
 */

public class CduDAO implements IObjectDAO<Cdu> {
    private Connection conn;
    private ResultSet rs;
    private PreparedStatement ps;

    public CduDAO(){
        conn = null;
        rs = null;
        ps = null;
    }

    @Override
    public List<Cdu> selectAll() throws ClassNotFoundException, SQLException{
        List<Cdu> list = new ArrayList<>();
        Cdu selectCdu;
        String sql;
        try {
            conn = ConnectionFactory.getInstance().getConnection();
            sql = "Select "+ContractCdu.ID+","+ContractCdu.NOM+","+ContractCdu.IDPARE+" from "+ ContractCdu.NOM_TAULA;
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while(rs.next()){
                selectCdu = new Cdu();
                selectCdu.setId(rs.getInt(1));
                selectCdu.setNom(rs.getString(2));
                selectCdu.setIdPare(rs.getInt(3));
                list.add(selectCdu);
            }
        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
        } finally {
            this.close();
        }
        return list;
    }
    @Override
    public List<Cdu> select(HashMap <String,Object> cdu) throws ClassNotFoundException, SQLException{
        List<Cdu> list = new ArrayList<>();
        String sql;
        try {
            conn = ConnectionFactory.getInstance().getConnection();
            sql = "Select "+ContractCdu.ID+","+ContractCdu.NOM+","+ContractCdu.IDPARE+
                    " from "+ContractCdu.NOM_TAULA+" where "+ContractCdu.NOM+" LIKE ? ";
            ps = conn.prepareStatement(sql);
           // ps.setString(1,'%'+cdu.getNom()+'%');
            rs = ps.executeQuery();
            while(rs.next()){
             
            }
        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
        } finally {
            this.close();
        }
        return list;
    }
    @Override
    public Cdu select(int id) throws ClassNotFoundException, SQLException{
        Cdu cdu = new Cdu();
        String sql;
        try {
            conn = ConnectionFactory.getInstance().getConnection();
            sql = "Select "+ContractCdu.ID+","+ContractCdu.NOM+","+ContractCdu.IDPARE+
                    " from "+ContractCdu.NOM_TAULA+" where "+ContractCdu.ID+" = ? ";
            ps = conn.prepareStatement(sql);
            ps.setInt(1,id);
            rs = ps.executeQuery();
            cdu = new Cdu();
            cdu.setId(rs.getInt(1));
            cdu.setNom(rs.getString(2));
            cdu.setIdPare(rs.getInt(3));
        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
        } finally {
            this.close();
        }
        return cdu;
    }
    @Override
    public boolean insert(Cdu cdu) throws ClassNotFoundException, SQLException{
        String insert;
        boolean inserit = false;
        int id;
        try {
            conn=ConnectionFactory.getInstance().getConnection();
            id = nextId();
            insert = "Insert into "+ContractCdu.NOM_TAULA+" values (?,?,?)";
            ps = conn.prepareStatement(insert);
            ps.setInt(1,id);
            ps.setString(2,cdu.getNom());
            ps.setInt(3,cdu.getIdPare());
            ps.executeUpdate();
            inserit = true;
        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        return inserit;
    }
   
    @Override
    public boolean update(Cdu cdu) throws ClassNotFoundException, SQLException{
        String update;
        boolean actualitzat = false;
        try {
            conn = ConnectionFactory.getInstance().getConnection();
            update = "UPDATE "+ContractCdu.NOM_TAULA+" SET "+ContractCdu.NOM+" = ?, "+
                    ContractCdu.IDPARE+ " = ? where "+ContractCdu.ID+" = ?";
            ps = conn.prepareStatement(update);
            ps.setString(1,cdu.getNom());
            ps.setInt(2,cdu.getIdPare());
            ps.setInt(3,cdu.getId());
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
            sql = "SELECT max("+ContractCdu.ID+") FROM "+ContractCdu.NOM_TAULA;
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
