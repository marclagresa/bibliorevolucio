package bbdd;

import base.ConnectionFactory;
import contract.ContractExemplar;
import objecte.Exemplar;

import java.io.IOException;
import java.nio.file.FileSystems;
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
    public List<Exemplar> select(HashMap <String,Object> dades,String campOrdre,Integer totalRegistres,Integer registreInicial,Boolean ascendent)throws SQLException,ClassNotFoundException{
        List<Exemplar> exemplars =new ArrayList<>();
        ArrayList<Object>valors;
        String query;
        int i;
        try {
            conn=ConnectionFactory.getInstance().getConnection();
            valors=new ArrayList<>();
            query = "SELECT * FROM "+ContractExemplar.NOM_TAULA;
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
                exemplars.add(this.read());
            }
        } catch (SQLException ex) {
            throw new SQLException(ex.getMessage(), ex.getSQLState() , ex.getErrorCode(), ex.getCause());
        } catch(ClassNotFoundException ex){
            throw new ClassNotFoundException(ex.getMessage(), ex.getCause());
        }finally{
            this.close();
        }

        return exemplars;
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

    public static void main(String[] args) throws SQLException, ClassNotFoundException, IOException {
        ConnectionFactory.getInstance().configure(FileSystems.getDefault().getPath("src/base", "configBibliotecari"));
        ExemplarDAO e = new ExemplarDAO();
        HashMap<String,Object>consulta=new HashMap<>();
        List<Exemplar> exemplars;
        consulta.put(ContractExemplar.ESTAT, true);
        exemplars=e.select(consulta, ContractExemplar.ESTAT, 20, 0, true);
        exemplars.forEach(biblioteca->{System.out.println(biblioteca.toString());});
    }
}
