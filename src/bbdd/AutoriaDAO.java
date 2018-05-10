package bbdd;

import base.ConnectionFactory;
import contract.ContractAutoria;
import objecte.Autoria;
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
    public List<Autoria> select(HashMap<String,Object> dades) throws ClassNotFoundException, SQLException{
        List<Autoria> list = new ArrayList<>();
        String sql;
        Object [] valors;
        int i;
        boolean dadaCorrecte=false;

        try {
            conn = ConnectionFactory.getInstance().getConnection();
            sql = "SELECT * FROM "+ContractAutoria.NOM_TAULA;
            i=0;
            valors=new Object[dades.size()];
            for(String camp:dades.keySet()){
                valors=new Object[dades.size()];
                switch(ContractAutoria.DEFINICIO.get(camp)){
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

    public static void main(String[] args) throws SQLException, ClassNotFoundException {

    }

}
