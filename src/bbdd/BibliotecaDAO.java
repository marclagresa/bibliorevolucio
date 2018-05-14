package bbdd;

import base.ConnectionFactory;
import contract.ContractBiblioteca;
import objecte.Biblioteca;
import java.sql.*;
import java.util.*;

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
    public List<Biblioteca> selectAll() throws ClassNotFoundException, SQLException {
        List<Biblioteca> list = new ArrayList<>();
        String sql;
        try {
            conn = ConnectionFactory.getInstance().getConnection();
            sql = "Select "
                    +ContractBiblioteca.ID+","
                    +ContractBiblioteca.NOM+" from "
                    +ContractBiblioteca.NOM_TAULA;
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            list.clear();
            while(rs.next()){
                list.add(this.read());
            }
        } catch (SQLException ex){
            throw new SQLException (ex.getMessage(),ex.getSQLState(),ex.getErrorCode(),ex.getCause());
        }catch( ClassNotFoundException ex) {
            throw new ClassNotFoundException(ex.getMessage(),ex.getCause());
        } finally {
            this.close();
        }
        return list;
    }
    @Override
    public List<Biblioteca> select(HashMap <String,Object> dades,String campOrdre,Integer totalRegistres,Integer registreInicial,Boolean ascendent)throws SQLException,ClassNotFoundException{
        List<Biblioteca> editorials=new ArrayList<>();
        Biblioteca objBiblioteca;
        ArrayList<Object>valors;
        String query;
        int i;
        try {
            conn=ConnectionFactory.getInstance().getConnection();
            valors=new ArrayList<>();
            query = "SELECT * FROM "+ContractBiblioteca.NOM_TAULA;
            i=0;
            for(String camp:dades.keySet()){
                if(i ==0){
                    query += " WHERE ";
                }
                else{
                    query += " AND ";
                }
                if(dades.get(camp).getClass().equals(String.class)){
                    query += camp+" LIKE ?";
                    valors.add("%"+dades.get(camp)+"%");
                }
                else{
                    query += camp+ " = ?";
                    valors.add(dades.get(camp));
                }

            }
            if(campOrdre!=null){
                query+=" ORDER BY ? ";
                valors.add(campOrdre);
            }
            if(ascendent){
                query+=" ASC ";
            }
            else{
                query+= " DESC ";
            }
            if(registreInicial!=null || totalRegistres!=null){
                query += " LIMIT ";
                if(registreInicial!=null){
                    query += " ?, ";
                    valors.add(registreInicial);
                }
                if(totalRegistres==null){
                    query +=" 18446744073709551615";
                }
                else{
                    query +=" ?";
                    valors.add(totalRegistres);
                }

            }
            ps=conn.prepareStatement(query);
            for(Object valor:valors){
                ps.setObject(valors.indexOf(valor)+1, valor);
            }
            rs=ps.executeQuery();
            while(rs.next()){
                objBiblioteca= new Biblioteca(
                        rs.getInt(ContractBiblioteca.ID),
                        rs.getString(ContractBiblioteca.NOM)
                );
                editorials.add(objBiblioteca);
            }
        } catch (SQLException ex) {
            throw new SQLException(ex.getMessage(), ex.getSQLState() , ex.getErrorCode(), ex.getCause());
        } catch(ClassNotFoundException ex){
            throw new ClassNotFoundException(ex.getMessage(), ex.getCause());
        }finally{
            this.close();
        }

        return editorials;
    }
    @Override
    public Biblioteca select(int id) throws ClassNotFoundException, SQLException{
        Biblioteca biblioteca = new Biblioteca();
        String sql;
        try {
            conn = ConnectionFactory.getInstance().getConnection();
            sql = "Select "
                    +ContractBiblioteca.ID+","
                    +ContractBiblioteca.NOM+" from "
                    +ContractBiblioteca.NOM_TAULA + " where " +
                    ContractBiblioteca.ID + " = ? ";
            ps = conn.prepareStatement(sql);
            ps.setInt(1,id);
            rs = ps.executeQuery();
            if(rs.next()){
                biblioteca=this.read();
            }
        } catch (SQLException ex) {
            throw new SQLException(ex.getMessage(),ex.getSQLState(),ex.getErrorCode(),ex.getCause());

        } catch(ClassNotFoundException ex){
            throw new ClassNotFoundException(ex.getMessage(), ex.getCause());
        } finally {
            this.close();
        }
        return biblioteca;
    }
    @Override
    public boolean insert(Biblioteca biblioteca) throws ClassNotFoundException, SQLException{
        String insert;
        boolean inserit;
        try {
            conn = ConnectionFactory.getInstance().getConnection();
            insert = "Insert into "+ContractBiblioteca.NOM_TAULA+" values (?,?)";
            ps = conn.prepareStatement(insert);
            ps.setInt(1,biblioteca.getId());
            ps.setString(2,biblioteca.getNom());
            inserit=ps.executeUpdate()==1;
        } catch (SQLException ex) {
            throw new SQLException (ex.getMessage(),ex.getSQLState(),ex.getErrorCode(),ex.getCause());
        } catch( ClassNotFoundException ex){
            throw new ClassNotFoundException(ex.getMessage(), ex.getCause());
        }finally {
            this.close();
        }
        return inserit;
    }
    @Override
    public boolean update(Biblioteca biblioteca) throws ClassNotFoundException, SQLException{
        String update;
        boolean actualitzat;
        try {
            conn = ConnectionFactory.getInstance().getConnection();
            update = "UPDATE "+ContractBiblioteca.NOM_TAULA+" SET "
                    +ContractBiblioteca.NOM+" = ? where "
                    +ContractBiblioteca.ID+" = ? ";
            ps = conn.prepareStatement(update);
            ps.setString(1,biblioteca.getNom());
            ps.setInt(2,biblioteca.getId());
            actualitzat=ps.executeUpdate()==1;
        } catch (SQLException ex){
            throw new SQLException(ex.getMessage(),ex.getSQLState(),ex.getErrorCode(),ex.getCause());
        }catch( ClassNotFoundException ex) {
            throw new ClassNotFoundException(ex.getMessage(),ex.getCause());
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
        } catch (SQLException ex){
            throw new SQLException(ex.getMessage(),ex.getSQLState(),ex.getErrorCode(),ex.getCause());
        }catch( ClassNotFoundException ex) {
            throw new ClassNotFoundException (ex.getMessage(),ex.getCause());
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

    private Biblioteca read() throws SQLException,ClassNotFoundException{
        Biblioteca objBiblioteca = new Biblioteca();
        objBiblioteca.setId(rs.getInt(ContractBiblioteca.ID));
        objBiblioteca.setNom(rs.getString(ContractBiblioteca.NOM));
        return objBiblioteca;
    }

    public static void main(String[] args) throws SQLException, ClassNotFoundException {

    }
}
