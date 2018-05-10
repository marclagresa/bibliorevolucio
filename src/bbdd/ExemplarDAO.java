package bbdd;

import base.ConnectionFactory;
import contract.ContractExemplar;
import objecte.Biblioteca;
import objecte.Exemplar;

import java.sql.*;
import java.util.*;

/**
 * @author albertCorominas
 */

public class ExemplarDAO implements IObjectDAO<Exemplar> {
    private Connection conn;
    private ResultSet rs;
    private PreparedStatement ps;

    public ExemplarDAO() {
        conn=null;
        rs=null;
        ps=null;
    }
    @Override
    public List<Exemplar> selectAll() throws ClassNotFoundException, SQLException {
        List<Exemplar> list = new ArrayList<>();
        String sql;
        try {
            conn = ConnectionFactory.getInstance().getConnection();
            sql = "Select "
                    +ContractExemplar.ID+","
                    +ContractExemplar.ID_PRODUCTE+","
                    +ContractExemplar.ID_BIBLIOTECA+","
                    +ContractExemplar.ESTAT+","
                    +ContractExemplar.DATA_COMPRA
                    +" from "
                    +ContractExemplar.NOM_TAULA;
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
    public List<Exemplar> select(HashMap<String,Object> dades) throws ClassNotFoundException, SQLException{
        List<Exemplar> list = new ArrayList<>();
        String sql;
        Object [] valors;
        int i;
        boolean dadaCorrecte=false;

        try {
            conn = ConnectionFactory.getInstance().getConnection();
            sql = "SELECT * FROM "+ContractExemplar.NOM_TAULA;
            i=0;
            valors=new Object[dades.size()];
            for(String camp:dades.keySet()){
                valors=new Object[dades.size()];
                switch(ContractExemplar.DEFINICIO.get(camp)){
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
    public Exemplar select(int id) throws ClassNotFoundException, SQLException{
        Exemplar exemplar = new Exemplar();
        String sql;
        try {
            conn = ConnectionFactory.getInstance().getConnection();
            sql = "Select "
                    +ContractExemplar.ID+","
                    +ContractExemplar.ID_PRODUCTE+","
                    +ContractExemplar.ID_BIBLIOTECA+","
                    +ContractExemplar.ESTAT+","
                    +ContractExemplar.DATA_COMPRA +" from "
                    +ContractExemplar.NOM_TAULA + " where "
                    + ContractExemplar.ID + " = ? ";
            ps = conn.prepareStatement(sql);
            ps.setInt(1,id);
            rs = ps.executeQuery();
            if(rs.next()){
                exemplar=this.read();
            }
        } catch (SQLException ex) {
            throw new SQLException(ex.getMessage(),ex.getSQLState(),ex.getErrorCode(),ex.getCause());

        } catch(ClassNotFoundException ex){
            throw new ClassNotFoundException(ex.getMessage(), ex.getCause());
        } finally {
            this.close();
        }
        return exemplar;
    }
    @Override
    public boolean insert(Exemplar exemplar) throws ClassNotFoundException, SQLException{
        String insert;
        boolean inserit;
        try {
            conn = ConnectionFactory.getInstance().getConnection();
            insert = "Insert into "+ContractExemplar.NOM_TAULA+" values (?,?,?,?,?)";
            ps = conn.prepareStatement(insert);
            ps.setInt(1,exemplar.getId());
            ps.setInt(2,exemplar.getProducte().getId());
            ps.setInt(3,exemplar.getBiblioteca().getId());
            ps.setBoolean(4,exemplar.getEstat());
            ps.setString(5,exemplar.getDataCompra());
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
    public boolean update(Exemplar exemplar) throws ClassNotFoundException, SQLException{
        String update;
        boolean actualitzat;
        try {
            conn = ConnectionFactory.getInstance().getConnection();
            update = "UPDATE "+ContractExemplar.NOM_TAULA+" SET "
                    +ContractExemplar.ID_PRODUCTE+" = ?,"
                    +ContractExemplar.ID_BIBLIOTECA+" = ?,"
                    +ContractExemplar.ESTAT+" = ?,"
                    +ContractExemplar.DATA_COMPRA+" = ?,"
                    +" where "
                    +ContractExemplar.ID+" = ? ";
            ps = conn.prepareStatement(update);
            ps.setInt(1,exemplar.getId());
            ps.setInt(2,exemplar.getProducte().getId());
            ps.setInt(3,exemplar.getBiblioteca().getId());
            ps.setBoolean(4,exemplar.getEstat());
            ps.setString(5,exemplar.getDataCompra());
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
            sql = "SELECT max("+ContractExemplar.ID+") FROM "+ContractExemplar.NOM_TAULA;
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
    private Exemplar read() throws SQLException,ClassNotFoundException{
        Exemplar objExemplar = new Exemplar();
        objExemplar.setId(rs.getInt(ContractExemplar.ID));
        objExemplar.setIdProducte(new ProducteDAO().select(rs.getInt(ContractExemplar.ID_PRODUCTE)));
        objExemplar.setIdBiblioteca(new BibliotecaDAO().select(rs.getInt(ContractExemplar.ID_BIBLIOTECA)));
        objExemplar.setEstat(rs.getBoolean(ContractExemplar.ESTAT));
        objExemplar.setDataCompra(rs.getString(ContractExemplar.DATA_COMPRA));
        return objExemplar;
    }

    public static void main(String[] args) throws SQLException, ClassNotFoundException {

    }
}
