package bbdd;

import base.ConnectionFactory;
import contract.ContractAutoria;
import objecte.Autoria;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.sql.*;
import java.util.*;

/**
 * @author AlbertCorominas
 */

public class AutoriaDAO implements IObjectDAO<Autoria> {
    private Connection conn;
    private ResultSet rs;
    private PreparedStatement ps;

    public AutoriaDAO(){
        conn=null;
        rs=null;
        ps=null;
    }

    @Override
    public List<Autoria> selectAll() throws ClassNotFoundException, SQLException {
        List<Autoria> list = new ArrayList<>();
        String sql;
        try {
            conn = ConnectionFactory.getInstance().getConnection();
            sql = "Select "
                    +ContractAutoria.ID+","
                    +ContractAutoria.ID_PRODUCTE+","
                    +ContractAutoria.ID_PERSONA+","
                    +ContractAutoria.DESCRIPCIO+" from "
                    +ContractAutoria.NOM_TAULA;
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
    public List<Autoria> select(HashMap<String, Object> dades, String campOrdre, Integer totalRegistres, Integer registreInicial, Boolean ascendent) throws SQLException, ClassNotFoundException {
        List<Autoria> autories = new ArrayList<>();
        Autoria objAutoria;
        ArrayList<Object>valors;
        String query;
        int i;
        try {
            conn=ConnectionFactory.getInstance().getConnection();
            valors=new ArrayList<>();
            query = "SELECT * FROM "+ContractAutoria.NOM_TAULA;
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
                autories.add(this.read());
            }
        } catch (SQLException ex) {
            throw new SQLException(ex.getMessage(), ex.getSQLState() , ex.getErrorCode(), ex.getCause());
        } catch(ClassNotFoundException ex){
            throw new ClassNotFoundException(ex.getMessage(), ex.getCause());
        }finally{
            this.close();
        }
        return autories;
    }
    @Override
    public Autoria select(int id) throws ClassNotFoundException, SQLException{
        Autoria autoria = new Autoria();
        String sql;
        try {
            conn = ConnectionFactory.getInstance().getConnection();
            sql = "Select "+ContractAutoria.ID+","+ContractAutoria.ID_PRODUCTE+","+ContractAutoria.ID_PERSONA+","
                    +ContractAutoria.DESCRIPCIO+ " from "+ ContractAutoria.NOM_TAULA + " where " +
                    ContractAutoria.ID + " = ? ";
            ps = conn.prepareStatement(sql);
            ps.setInt(1,id);
            rs = ps.executeQuery();
            if(rs.next()){
                autoria=this.read();
            }
        } catch (SQLException ex) {
            throw new SQLException(ex.getMessage(),ex.getSQLState(),ex.getErrorCode(),ex.getCause());

        } catch(ClassNotFoundException ex){
            throw new ClassNotFoundException(ex.getMessage(), ex.getCause());
        } finally {
            this.close();
        }
        return autoria;
    }
    @Override
    public boolean insert(Autoria autoria) throws ClassNotFoundException, SQLException{
        String insert;
        boolean inserit;
        try {
            conn = ConnectionFactory.getInstance().getConnection();
            insert = "Insert into "+ContractAutoria.NOM_TAULA+" values (?,?,?,?)";
            ps = conn.prepareStatement(insert);
            ps.setInt(1,autoria.getId());
            ps.setInt(2,autoria.getProducte().getId());
            ps.setInt(2,autoria.getPersona().getId());
            ps.setString(4,autoria.getDescripcio());
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
    public boolean update(Autoria autoria) throws ClassNotFoundException, SQLException{
        String update;
        boolean actualitzat;
        try {
            conn = ConnectionFactory.getInstance().getConnection();
            update = "UPDATE "+ContractAutoria.NOM_TAULA+" SET "
                    +ContractAutoria.ID_PRODUCTE+" = ?,"
                    +ContractAutoria.ID_PERSONA+" = ?,"
                    +ContractAutoria.DESCRIPCIO+" = ?  where "
                    +ContractAutoria.ID+" = ?";
            ps = conn.prepareStatement(update);
            ps.setInt(1,autoria.getPersona().getId());
            ps.setInt(2,autoria.getProducte().getId());
            ps.setString(3,autoria.getDescripcio());
            ps.setInt(4,autoria.getId());
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
            sql = "SELECT max("+ContractAutoria.ID+") FROM "+ContractAutoria.NOM_TAULA;
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
    private Autoria read() throws SQLException,ClassNotFoundException{
        Autoria objAutoria = new Autoria();
        objAutoria.setId(rs.getInt(ContractAutoria.ID));
        objAutoria.setProducte(new ProducteDAO().select(rs.getInt(ContractAutoria.ID_PRODUCTE)));
        objAutoria.setPersona(new PersonaDAO().select(rs.getInt(ContractAutoria.ID_PERSONA)));
        objAutoria.setDescripcio(rs.getString(ContractAutoria.DESCRIPCIO));
        return objAutoria;
    }

    public static void main(String[] args) throws SQLException, ClassNotFoundException, IOException {
        ConnectionFactory.getInstance().configure(FileSystems.getDefault().getPath("src/base", "configBibliotecari"));
        AutoriaDAO a = new AutoriaDAO();
        HashMap<String,Object>consulta=new HashMap<>();
        List<Autoria> autories;
        consulta.put(ContractAutoria.DESCRIPCIO, "test");
        autories=a.select(consulta, ContractAutoria.DESCRIPCIO, 20, 0, true);
        autories.forEach(autoria->{System.out.println(autoria.toString());});
    }
}
