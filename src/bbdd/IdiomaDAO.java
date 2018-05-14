package bbdd;

import base.ConnectionFactory;
import contract.ContractIdioma;
import objecte.Idioma;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class IdiomaDAO implements IObjectDAO<Idioma> {
    private Connection conn;
    private ResultSet rs;
    private PreparedStatement ps;

    public IdiomaDAO(){
        conn=null;
        rs=null;
        ps=null;
    }

    @Override
    public List<Idioma> selectAll() throws ClassNotFoundException, SQLException{
        List<Idioma> list = new ArrayList<>();
        String sql;
        Idioma idioma;
        try {
            conn = ConnectionFactory.getInstance().getConnection();
            sql = "Select "+ ContractIdioma.ID+","+ContractIdioma.NOM+" from "+ContractIdioma.NOM_TAULA;
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while(rs.next()){
                idioma = new Idioma();
                idioma.setId(rs.getInt(1));
                idioma.setNom(rs.getString(2));
                list.add(idioma);
            }
        } catch (SQLException ex){
            throw new SQLException(ex.getMessage(),ex.getSQLState(),ex.getErrorCode(),ex.getCause());
        } catch ( ClassNotFoundException ex) {
            throw new ClassNotFoundException(ex.getMessage(), ex.getCause());
        } finally {
            this.close();
        }
        return list;
    }
    
    public List<Idioma> select(HashMap <String,Object> idioma) throws ClassNotFoundException, SQLException{
        List<Idioma> list = new ArrayList<>();
        String sql;
        try {
            conn = ConnectionFactory.getInstance().getConnection();
            sql = "Select "+ContractIdioma.ID+","+ContractIdioma.NOM+" from "+ContractIdioma.NOM_TAULA+
                    " where "+ContractIdioma.NOM+" LIKE ? ";
            ps = conn.prepareStatement(sql);
           
            rs = ps.executeQuery();
            while(rs.next()){
              list.add(new Idioma(rs.getInt(ContractIdioma.ID), rs.getString(ContractIdioma.NOM)));
            }
        } catch (SQLException ex){
            throw new SQLException (ex.getMessage(),ex.getSQLState(),ex.getErrorCode(),ex.getCause());
        } catch( ClassNotFoundException ex) {
            throw new ClassNotFoundException(ex.getMessage(),ex.getCause());
        } finally {
            this.close();
        }
        return list;
    }
    @Override
    public Idioma select(int id) throws ClassNotFoundException, SQLException{
        Idioma idioma = new Idioma();
        String sql;
        try {
            conn = ConnectionFactory.getInstance().getConnection();
            sql = "Select "+ContractIdioma.ID+","+ContractIdioma.NOM+" from "+ContractIdioma.NOM_TAULA+
                    " where "+ContractIdioma.ID+" = ? ";
            ps = conn.prepareStatement(sql);
            ps.setInt(1,id);
            rs = ps.executeQuery();
            if(rs.next()){
                idioma.setId(rs.getInt(1));
                idioma.setNom(rs.getString(2));
            }
        } catch (SQLException ex){
            throw new SQLException(ex.getMessage(),ex.getSQLState(),ex.getErrorCode(),ex.getCause());
        }catch (ClassNotFoundException ex) {
            throw new ClassNotFoundException(ex.getMessage(),ex.getCause());
        } finally {
            this.close();
        }
        return idioma;
    }
    @Override
    public boolean insert(Idioma idioma) throws ClassNotFoundException, SQLException{
        String insert;
        boolean inserit = false;
        int id;
        try {
            conn = ConnectionFactory.getInstance().getConnection();
            insert = "Insert into "+ContractIdioma.NOM_TAULA+"("
                    +ContractIdioma.ID+", "
                    +ContractIdioma.NOM
                    +") values (?,?)";
            ps = conn.prepareStatement(insert);
            ps.setInt(1,idioma.getId());
            ps.setString(2,idioma.getNom());
            
            inserit=ps.executeUpdate()==1;
            
        } catch (SQLException e){
            throw new SQLException(e.getMessage(),e.getSQLState(),e.getErrorCode(),e.getCause());
        } catch( ClassNotFoundException ex) {
            throw new ClassNotFoundException(ex.getMessage(),ex.getCause());
        } finally {
            this.close();
        }
        return inserit;
    }
    public Idioma select(String nom) throws ClassNotFoundException,SQLException{
        Idioma idiomaObj=new Idioma();
        String query;
        try {
            conn=ConnectionFactory.getInstance().getConnection();
            query="SELECT * FROM "+ContractIdioma.NOM_TAULA
                + " WHERE LOWER("+ContractIdioma.NOM + ") = ?"; 
            ps=conn.prepareStatement(query);
            ps.setString(1, nom);
            rs=ps.executeQuery();
            if(rs.next()){
                idiomaObj.setId(rs.getInt(ContractIdioma.ID));
                idiomaObj.setNom(rs.getString(ContractIdioma.NOM));
            }
        } catch (SQLException e) {
            throw new SQLException(e.getMessage(),e.getSQLState(),e.getErrorCode(),e.getCause());
        } catch(ClassNotFoundException e){
            throw new ClassNotFoundException(e.getMessage(),e.getCause());
        } finally{
            this.close();
        }
        return idiomaObj;
    }
    @Override
    public boolean update(Idioma idioma) throws ClassNotFoundException, SQLException{
        String update;
        boolean actualitzat = false;
        try {
            conn = ConnectionFactory.getInstance().getConnection();
            update = "UPDATE "+ContractIdioma.NOM_TAULA+" SET "+
                    ContractIdioma.NOM+" = ? where "+ContractIdioma.ID+" = ?";
            ps = conn.prepareStatement(update);
            ps.setString(1,idioma.getNom());
            ps.setInt(2,idioma.getId());
            actualitzat=ps.executeUpdate()==1;
            
        } catch (SQLException ex){
            throw new SQLException(ex.getMessage(),ex.getSQLState(),ex.getErrorCode(),ex.getCause());
        } catch( ClassNotFoundException ex) {
            throw new ClassNotFoundException(ex.getMessage(), ex.getCause());
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
            sql = "SELECT max("+ContractIdioma.ID+") FROM "+ContractIdioma.NOM_TAULA;
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            if(rs.next()){
                id = rs.getInt(1)+1;
            }
        } catch (SQLException e){
            throw new SQLException(e.getMessage(),e.getSQLState(),e.getErrorCode(),e.getCause());
        }catch( ClassNotFoundException ex) {
            throw new ClassNotFoundException(ex.getMessage(),ex.getCause());
        } finally{
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

    @Override
    public List<Idioma> select(HashMap<String, Object> dades, String campOrdre, Integer totalRegistres, Integer registreInicial, Boolean ascendent) throws SQLException, ClassNotFoundException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
