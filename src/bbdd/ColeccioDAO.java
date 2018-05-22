package bbdd;

import base.ConnectionFactory;
import contract.ContractColeccio;
import objecte.Coleccio;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ColeccioDAO implements IObjectDAO<Coleccio> {
    private Connection conn;
    private ResultSet rs;
    private PreparedStatement ps;

    public ColeccioDAO(){
        conn = null;
        rs = null;
        ps = null;
    }
    public Coleccio select(String nom) throws ClassNotFoundException,SQLException{
        Coleccio colObj=new Coleccio();
        String sql;
        try {
            conn = ConnectionFactory.getInstance().getConnection();
            sql = "Select * from "
                    +ContractColeccio.NOM_TAULA+ " "
                + "WHERE "+ContractColeccio.NOM+ " LIKE ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, nom);
            rs = ps.executeQuery();
            
            if(rs.next()){
                colObj.setId(rs.getInt(ContractColeccio.ID));
                colObj.setNom(rs.getString(ContractColeccio.NOM));
            }
        } catch (SQLException ex){
            throw new SQLException (ex.getMessage(),ex.getSQLState(),ex.getErrorCode(),ex.getCause());
        }catch( ClassNotFoundException ex) {
            throw new ClassNotFoundException(ex.getMessage(),ex.getCause());
        } finally {
            this.close();
        }
        return colObj;
    }
    @Override
    public List<Coleccio> selectAll() throws ClassNotFoundException, SQLException {
        List<Coleccio> list = new ArrayList<>();
        String sql;
        try {
            conn = ConnectionFactory.getInstance().getConnection();
            sql = "Select "
                    +ContractColeccio.ID+","
                    +ContractColeccio.NOM+" from "
                    +ContractColeccio.NOM_TAULA;
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
    public List<Coleccio> select(HashMap <String,Object> dades,String campOrdre,Integer totalRegistres,Integer registreInicial,Boolean ascendent)throws SQLException,ClassNotFoundException{
        List<Coleccio> coleccions =new ArrayList<>();
        ArrayList<Object>valors;
        String query;
        int i;
        try {
            conn=ConnectionFactory.getInstance().getConnection();
            valors=new ArrayList<>();
            query = "SELECT * FROM "+ContractColeccio.NOM_TAULA;
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
                if(ascendent){
                    query+=" ASC ";
                }
                else{
                    query+= " DESC ";
                }
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
                coleccions.add(this.read());
            }
        } catch (SQLException ex) {
            throw new SQLException(ex.getMessage(), ex.getSQLState() , ex.getErrorCode(), ex.getCause());
        } catch(ClassNotFoundException ex){
            throw new ClassNotFoundException(ex.getMessage(), ex.getCause());
        }finally{
            this.close();
        }

        return coleccions;
    }

    public int selectCount(HashMap <String,Object> dades)throws SQLException,ClassNotFoundException{
        int count=0;
        ArrayList<Object> valors;
        int i;
        String query;
        try {
            conn=ConnectionFactory.getInstance().getConnection();
            valors=new ArrayList<>();
            query = "SELECT COUNT(*) FROM "+ ContractColeccio.NOM_TAULA;
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
            ps=conn.prepareStatement(query);
            for(Object valor:valors){
                ps.setObject(valors.indexOf(valor)+1, valor);
            }
            rs=ps.executeQuery();
            if(rs.next()){
                count=rs.getInt(1);
            }
        } catch (SQLException ex) {
            throw new SQLException(ex.getMessage(), ex.getSQLState() , ex.getErrorCode(), ex.getCause());
        } catch(ClassNotFoundException ex){
            throw new ClassNotFoundException(ex.getMessage(), ex.getCause());
        }finally{
            this.close();
        }
        return count;
    }

    public List<Coleccio> select(HashMap<String,Object> dades) throws ClassNotFoundException, SQLException{
        List<Coleccio> list = new ArrayList<>();
        String sql;
        Object [] valors;
        int i;
        boolean dadaCorrecte=false;

        try {
            conn = ConnectionFactory.getInstance().getConnection();
            sql = "SELECT * FROM "+ContractColeccio.NOM_TAULA;
            i=0;
            valors=new Object[dades.size()];
            for(String camp:dades.keySet()){
                valors=new Object[dades.size()];
                switch(ContractColeccio.DEFINICIO.get(camp)){
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
    public Coleccio select(int id) throws ClassNotFoundException, SQLException{
        Coleccio coleccio = new Coleccio();
        String sql;
        try {
            conn = ConnectionFactory.getInstance().getConnection();
            sql = "Select "
                    +ContractColeccio.ID+","
                    +ContractColeccio.NOM+" from "
                    +ContractColeccio.NOM_TAULA + " where " +
                    ContractColeccio.ID + " = ? ";
            ps = conn.prepareStatement(sql);
            ps.setInt(1,id);
            rs = ps.executeQuery();
            if(rs.next()){
                coleccio=this.read();
            }
        } catch (SQLException ex) {
            throw new SQLException(ex.getMessage(),ex.getSQLState(),ex.getErrorCode(),ex.getCause());

        } catch(ClassNotFoundException ex){
            throw new ClassNotFoundException(ex.getMessage(), ex.getCause());
        } finally {
            this.close();
        }
        return coleccio;
    }
    @Override
    public boolean insert(Coleccio coleccio) throws ClassNotFoundException, SQLException{
        String insert;
        boolean inserit;
        try {
            conn = ConnectionFactory.getInstance().getConnection();
            insert = "Insert into "+ContractColeccio.NOM_TAULA+" values (?,?)";
            ps = conn.prepareStatement(insert);
            ps.setInt(1,coleccio.getId());
            ps.setString(2,coleccio.getNom());
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
    public boolean update(Coleccio coleccio) throws ClassNotFoundException, SQLException{
        String update;
        boolean actualitzat;
        try {
            conn = ConnectionFactory.getInstance().getConnection();
            update = "UPDATE "+ContractColeccio.NOM_TAULA+" SET "
                    +ContractColeccio.NOM+" = ? where "
                    +ContractColeccio.ID+" = ? ";
            ps = conn.prepareStatement(update);
            ps.setString(1,coleccio.getNom());
            ps.setInt(2,coleccio.getId());
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
            sql = "SELECT max("+ContractColeccio.ID+") FROM "+ContractColeccio.NOM_TAULA+" "
                + "WHERE " + ContractColeccio.ID + " > 0";
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
    private Coleccio read() throws SQLException,ClassNotFoundException{
        Coleccio objColeccio = new Coleccio();
        objColeccio.setId(rs.getInt(ContractColeccio.ID));
        objColeccio.setNom(rs.getString(ContractColeccio.NOM));
        return objColeccio;
    }

    public static void main(String[] args) throws SQLException, ClassNotFoundException {

    }
}
