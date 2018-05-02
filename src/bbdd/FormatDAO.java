package bbdd;

import base.ConnectionFactory;
import contract.ContractFormat;
import objecte.Format;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FormatDAO implements IObjectDAO<Format>{
    private Connection conn;
    private ResultSet rs;
    private PreparedStatement ps;

    public FormatDAO(){
        conn=null;
        rs=null;
        ps=null;
    }

    @Override
    public List<Format> selectAll() throws ClassNotFoundException, SQLException{
        List<Format> list = new ArrayList<>();
        String sql;
        Format selectFormat;
        try {
            conn = ConnectionFactory.getInstance().getConnection();
            sql = "Select "+ ContractFormat.ID+","+ContractFormat.NOM+" from "+ContractFormat.NOM_TAULA;
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while(rs.next()){
                selectFormat = new Format();
                selectFormat.setId(rs.getInt(1));
                selectFormat.setNom(rs.getString(2));
                list.add(selectFormat);
            }
        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
        } finally {
            this.close();
        }
        return list;
    }
    @Override
    public List<Format> select(HashMap <String,Object> format) throws ClassNotFoundException, SQLException{
        List<Format> list = new ArrayList<>();
        String sql;
        try {
            conn = ConnectionFactory.getInstance().getConnection();
            sql = "Select "+ContractFormat.ID+","+ContractFormat.NOM+" from "+
                    ContractFormat.NOM_TAULA+" where "+ContractFormat.NOM+" LIKE ? ";
            ps = conn.prepareStatement(sql);
            ps.setString(1,'%'+format.getNom()+'%');
            rs = ps.executeQuery();
            while(rs.next()){
                format = new Format();
                format.setId(rs.getInt(1));
                format.setNom(rs.getString(2));
                list.add(format);
            }
        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
        } finally {
            this.close();
        }
        return list;
    }
    @Override
    public Format select(int id) throws ClassNotFoundException, SQLException{
        Format format = new Format();
        String sql;
        try {
            conn = ConnectionFactory.getInstance().getConnection();
            sql = "Select "+ContractFormat.ID+","+ContractFormat.NOM+" from "+
                    ContractFormat.NOM_TAULA+" where "+ContractFormat.ID+" = ? ";
            ps = conn.prepareStatement(sql);
            ps.setInt(1,id);
            rs = ps.executeQuery();
            format.setId(rs.getInt(1));
            format.setNom(rs.getString(2));
        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
        } finally {
            this.close();
        }
        return format;
    }
    @Override
    public boolean insert(Format format) throws ClassNotFoundException, SQLException{
        String insert;
        boolean inserit = false;
        int id;
        try {
            id = nextId();
            conn = ConnectionFactory.getInstance().getConnection();
            insert = "Insert into "+ContractFormat.NOM_TAULA+" values (?,?)";
            ps = conn.prepareStatement(insert);
            ps.setInt(1,id);
            ps.setString(2,format.getNom());
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
    public boolean delete(Format format) throws ClassNotFoundException, SQLException{
        String delete;
        boolean borrat = false;
        try {
            conn = ConnectionFactory.getInstance().getConnection();
            delete = "Delete from "+ContractFormat.NOM_TAULA+" where "+ContractFormat.ID+" = ?";
            ps = conn.prepareStatement(delete);
            ps.setInt(1,format.getId());
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
    public boolean update(Format format) throws ClassNotFoundException, SQLException{
        String update;
        boolean actualitzat = false;
        try {
            conn = ConnectionFactory.getInstance().getConnection();
            update = "UPDATE "+ContractFormat.NOM_TAULA+" SET "+ContractFormat.NOM+" = ? " +
                    "where "+ContractFormat.ID+" = ?";
            ps = conn.prepareStatement(update);
            ps.setString(1,format.getNom());
            ps.setInt(2,format.getId());
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
            sql = "SELECT max("+ ContractFormat.ID+") FROM "+ContractFormat.NOM_TAULA;
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
