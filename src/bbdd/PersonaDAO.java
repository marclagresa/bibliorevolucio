package bbdd;

import base.ConnectionFactory;
import contract.ContractPersona;
import objecte.Persona;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PersonaDAO implements IObjectDAO<Persona> {
    private Connection conn;
    private ResultSet rs;
    private PreparedStatement ps;

    public PersonaDAO(){
        conn=null;
        rs=null;
        ps=null;
    }

    @Override
    public List<Persona> selectAll() throws ClassNotFoundException, SQLException{
        List<Persona> list = new ArrayList<>();
        String sql;
        Persona selectPersona;
        try {
            conn = ConnectionFactory.getInstance().getConnection();
            sql = "Select "+ ContractPersona.ID+","+ContractPersona.NOM+" from "+ContractPersona.NOM_TAULA;
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while(rs.next()){
                selectPersona = new Persona();
                selectPersona.setId(rs.getInt(1));
                selectPersona.setNom(rs.getString(2));
                list.add(selectPersona);
            }
        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
        } finally {
            this.close();
        }
        return list;
    }
    @Override
    public List<Persona> select(HashMap <String,Object> persona) throws ClassNotFoundException, SQLException{
        List<Persona> list = new ArrayList<>();
        String sql;
        try {
            conn = ConnectionFactory.getInstance().getConnection();
            sql = "Select "+ContractPersona.ID+","+ContractPersona.NOM+" from "+
                    ContractPersona.NOM_TAULA+" where "+ContractPersona.NOM+" LIKE ? ";
            ps = conn.prepareStatement(sql);
           
            rs = ps.executeQuery();
            list.clear();
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
    public Persona select(int id) throws ClassNotFoundException, SQLException{
        Persona persona = new Persona();
        String sql;
        try {
            conn = ConnectionFactory.getInstance().getConnection();
            sql = "Select "+ContractPersona.ID+","+ContractPersona.NOM+" from "+
                    ContractPersona.NOM_TAULA+" where "+ContractPersona.NOM+" LIKE ? ";
            ps = conn.prepareStatement(sql);
            ps.setString(1,'%'+persona.getNom()+'%');
            rs = ps.executeQuery();
            persona.setId(rs.getInt(1));
            persona.setNom(rs.getString(2));
        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
        } finally {
            this.close();
        }
        return persona;
    }
    @Override
    public boolean insert(Persona persona) throws ClassNotFoundException, SQLException{
        String insert;
        boolean inserit = false;
        int id;
        try {
            id = nextId();
            conn = ConnectionFactory.getInstance().getConnection();
            insert = "Insert into "+ContractPersona.NOM_TAULA+" values (?,?)";
            ps = conn.prepareStatement(insert);
            ps.setInt(1,id);
            ps.setString(2,persona.getNom());
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
    public boolean update(Persona persona) throws ClassNotFoundException, SQLException{
        String update;
        boolean actualitzat = false;
        try {
            conn = ConnectionFactory.getInstance().getConnection();
            update = "UPDATE "+ContractPersona.NOM_TAULA+" SET "+ContractPersona.NOM+" = ? " +
                    "where "+ContractPersona.ID+" = ?";
            ps = conn.prepareStatement(update);
            ps.setString(1,persona.getNom());
            ps.setInt(2,persona.getId());
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
            sql = "SELECT max("+ContractPersona.ID+") FROM "+ContractPersona.NOM_TAULA;
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
