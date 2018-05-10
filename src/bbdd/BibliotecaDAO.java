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
    public List<Biblioteca> select(HashMap<String,Object> dades) throws ClassNotFoundException, SQLException{
        List<Biblioteca> list = new ArrayList<>();
        String sql;
        Object [] valors;
        int i;
        boolean dadaCorrecte=false;

        try {
            conn = ConnectionFactory.getInstance().getConnection();
            sql = "SELECT * FROM "+ContractBiblioteca.NOM_TAULA;
            i=0;
            valors=new Object[dades.size()];
            for(String camp:dades.keySet()){
                valors=new Object[dades.size()];
                switch(ContractBiblioteca.DEFINICIO.get(camp)){
                    case Types.INTEGER:
                        dadaCorrecte=dades.get(camp).getClass().equals(Integer.class);
                        break;
                    case Types.CHAR:
                    case Types.VARCHAR:
                        dadaCorrecte=dades.get(camp).getClass().equals(String.class);
                        break;
                    case Types.BOOLEAN:
                        dadaCorrecte=dades.get(camp).getClass().equals(Boolean.class);
                        break;
                }
                if(i ==0){
                    sql += " WHERE " ;
                }
                else{
                    sql += " AND ";
                }
                sql+=camp;
                if(dades.get(camp).getClass().equals(String.class)){
                    sql +=" LIKE ? ";
                    valors[i]="%"+dades.get(camp)+"%";
                }
                else{
                    sql+=" = ?";
                    valors[i]=dades.get(camp);
                }
                if(dadaCorrecte){
                    i++;
                }
                else{
                    throw new SQLException("Error tipus de dades incorrectes!!");
                }
            }
            ps = conn.prepareStatement(sql);
            for(i=0;i<valors.length;i++){
                ps.setObject(i+1, valors[i]);
            }
            rs = ps.executeQuery();
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
