package bbdd;



import base.ConnectionFactory;
import contract.ContractBiblioteca;
import objecte.Biblioteca;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author AlbertCorominas
 */

public class BibliotecaDAO implements IObjectDAO<Biblioteca> {

    private Connection conn;
    private ResultSet rs;
    private PreparedStatement ps;

    public BibliotecaDAO(){
        conn=null;
        rs=null;
        ps=null;
    }
    @Override
    public List<Biblioteca> selectAll() throws ClassNotFoundException, SQLException{
        List<Biblioteca> list = new ArrayList<>();
        String sql;
        Biblioteca selectBiblioteca;
        try {
            conn = ConnectionFactory.getInstance().getConnection();
            sql = "Select "+ ContractBiblioteca.ID+","+ContractBiblioteca.NOM+" from "+ContractBiblioteca.NOM_TAULA;
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while(rs.next()){
                selectBiblioteca = new Biblioteca();
                selectBiblioteca.setId(rs.getInt(1));
                selectBiblioteca.setNom(rs.getString(2));
                list.add(selectBiblioteca);
            }
        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
        } finally {
            this.close();
        }
        return list;
    }
    @Override
    public List<Biblioteca> select(HashMap <String,Object> biblioteca) throws ClassNotFoundException, SQLException{
        List<Biblioteca> list = new ArrayList<>();
        String sql;
        try {
            conn = ConnectionFactory.getInstance().getConnection();
            sql = "Select "+ContractBiblioteca.ID+","+ContractBiblioteca.NOM+
                    " from "+ContractBiblioteca.NOM_TAULA+" where "+ContractBiblioteca.NOM+" LIKE ? ";
            ps = conn.prepareStatement(sql);
            ps.setString(1,'%'+biblioteca.getNom()+'%');
            rs = ps.executeQuery();
            while(rs.next()){
                biblioteca = new Biblioteca();
                biblioteca.setId(rs.getInt(1));
                biblioteca.setNom(rs.getString(2));
                list.add(biblioteca);
            }
        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
        } finally {
            this.close();
        }
        return list;
    }
    @Override
    public Biblioteca select(int id) throws ClassNotFoundException, SQLException{
        Biblioteca biblioteca = new Biblioteca();
        String sql;
        try {
            conn = ConnectionFactory.getInstance().getConnection();
            sql = "Select "+ContractBiblioteca.ID+","+ContractBiblioteca.NOM+
                    " from "+ContractBiblioteca.NOM_TAULA+" where "+ContractBiblioteca.ID+" = ? ";
            ps = conn.prepareStatement(sql);
            ps.setInt(1,id);
            rs = ps.executeQuery();
            biblioteca = new Biblioteca();
            biblioteca.setId(rs.getInt(1));
            biblioteca.setNom(rs.getString(2));
        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
        } finally {
            this.close();
        }
        return biblioteca;
    }
    @Override
    public boolean insert(Biblioteca biblioteca) throws ClassNotFoundException, SQLException{
        String insert;
        boolean inserit = false;
        int id;
        try {
            conn = ConnectionFactory.getInstance().getConnection();
            id = nextId();
            insert = "Insert into "+ContractBiblioteca.NOM_TAULA+" values (?,?)";
            ps = conn.prepareStatement(insert);
            ps.setInt(1,id);
            ps.setString(2,biblioteca.getNom());
            ps.executeUpdate();
            inserit=true;
        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
        } finally {
            this.close();
        }
        return inserit;
    }
    @Override
    public boolean delete(Biblioteca biblioteca) throws ClassNotFoundException, SQLException{
        String delete;
        boolean borrat = false;
        try {
            conn = ConnectionFactory.getInstance().getConnection();
            delete = "Delete from "+ContractBiblioteca.NOM_TAULA+" where "+ContractBiblioteca.ID+" = ?";
            ps = conn.prepareStatement(delete);
            ps.setInt(1,biblioteca.getId());
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
    public boolean update(Biblioteca biblioteca) throws ClassNotFoundException, SQLException{
        String update;
        boolean actualitzat = false;
        try {
            conn = ConnectionFactory.getInstance().getConnection();
            update = "UPDATE "+ContractBiblioteca.NOM_TAULA+" SET "+ContractBiblioteca.NOM+" = ? " +
                    "where "+ContractBiblioteca.ID+" = ?";
            ps = conn.prepareStatement(update);
            ps.setString(1,biblioteca.getNom());
            ps.setInt(2,biblioteca.getId());
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
            sql = "SELECT max("+ContractBiblioteca.ID+") FROM "+ContractBiblioteca.NOM_TAULA;
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
