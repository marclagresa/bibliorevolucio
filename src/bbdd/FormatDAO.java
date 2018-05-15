package bbdd;

import base.ConnectionFactory;
import contract.ContractFormat;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import objecte.Format;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FormatDAO implements IObjectDAO<Format>{
    private Connection conn;
    private ResultSet rs;
    private PreparedStatement ps;

    public FormatDAO(){
        conn=null;
        rs=null;
        ps=null;
    }
     public Format select(String nom)throws ClassNotFoundException,SQLException{
        Format format=new Format();
        String sql;
        try{
            conn=ConnectionFactory.getInstance().getConnection();
            sql="SELECT * FROM "+ContractFormat.NOM_TAULA+" WHERE "+ContractFormat.NOM+" LIKE ?";
            ps=conn.prepareStatement(sql);
            ps.setString(1, nom);
            rs=ps.executeQuery();
            if(rs.next()){
                format.setId(rs.getInt(ContractFormat.ID));
                format.setNom(rs.getString(ContractFormat.NOM));
            }
        }catch (ClassNotFoundException e){
            throw new ClassNotFoundException(e.getMessage(),e.getCause());
        }catch(SQLException e){
            throw new SQLException(e.getMessage(),e.getSQLState(),e.getErrorCode(),e.getCause());
        }finally{
            this.close();
        }
        return format;
    }
    @Override
    public List<Format> selectAll() throws ClassNotFoundException, SQLException {
        List<Format> list = new ArrayList<>();
        String sql;
        try {
            conn = ConnectionFactory.getInstance().getConnection();
            sql = "Select "
                    +ContractFormat.ID+","
                    +ContractFormat.NOM+" from "
                    +ContractFormat.NOM_TAULA;
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
    public List<Format> select(HashMap <String,Object> dades,String campOrdre,Integer totalRegistres,Integer registreInicial,Boolean ascendent)throws SQLException,ClassNotFoundException{
        List<Format> formats =new ArrayList<>();
        ArrayList<Object>valors;
        String query;
        int i;
        try {
            conn=ConnectionFactory.getInstance().getConnection();
            valors=new ArrayList<>();
            query = "SELECT * FROM "+ContractFormat.NOM_TAULA;
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
                formats.add(this.read());
            }
        } catch (SQLException ex) {
            throw new SQLException(ex.getMessage(), ex.getSQLState() , ex.getErrorCode(), ex.getCause());
        } catch(ClassNotFoundException ex){
            throw new ClassNotFoundException(ex.getMessage(), ex.getCause());
        }finally{
            this.close();
        }

        return formats;
    }

    public List<Format> select(HashMap<String,Object> dades) throws ClassNotFoundException, SQLException{
        List<Format> list = new ArrayList<>();
        String sql;
        Object [] valors;
        int i;
        boolean dadaCorrecte=false;

        try {
            conn = ConnectionFactory.getInstance().getConnection();
            sql = "SELECT * FROM "+ContractFormat.NOM_TAULA;
            i=0;
            valors=new Object[dades.size()];
            for(String camp:dades.keySet()){
                valors=new Object[dades.size()];
                switch(ContractFormat.DEFINICIO.get(camp)){
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

    public int selectCount(HashMap <String,Object> dades)throws SQLException,ClassNotFoundException{
        int count=0;
        ArrayList<Object> valors;
        int i;
        String query;
        try {
            conn=ConnectionFactory.getInstance().getConnection();
            valors=new ArrayList<>();
            query = "SELECT COUNT(*) FROM "+ContractFormat.NOM_TAULA;
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
    @Override
    public Format select(int id) throws ClassNotFoundException, SQLException{
        Format format = new Format();
        String sql;
        try {
            conn = ConnectionFactory.getInstance().getConnection();
            sql = "Select "
                    +ContractFormat.ID+","
                    +ContractFormat.NOM+" from "
                    +ContractFormat.NOM_TAULA + " where " +
                    ContractFormat.ID + " = ? ";
            ps = conn.prepareStatement(sql);
            ps.setInt(1,id);
            rs = ps.executeQuery();
            if(rs.next()){
                format=this.read();
            }
        } catch (SQLException ex) {
            throw new SQLException(ex.getMessage(),ex.getSQLState(),ex.getErrorCode(),ex.getCause());

        } catch(ClassNotFoundException ex){
            throw new ClassNotFoundException(ex.getMessage(), ex.getCause());
        } finally {
            this.close();
        }
        return format;
    }
    @Override
    public boolean insert(Format format) throws ClassNotFoundException, SQLException{
        String insert;
        boolean inserit;
        try {
            conn = ConnectionFactory.getInstance().getConnection();
            insert = "Insert into "+ContractFormat.NOM_TAULA+" values (?,?)";
            ps = conn.prepareStatement(insert);
            ps.setInt(1,format.getId());
            ps.setString(2,format.getNom());
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
    public boolean update(Format format) throws ClassNotFoundException, SQLException{
        String update;
        boolean actualitzat;
        try {
            conn = ConnectionFactory.getInstance().getConnection();
            update = "UPDATE "+ContractFormat.NOM_TAULA+" SET "
                    +ContractFormat.NOM+" = ? where "
                    +ContractFormat.ID+" = ? ";
            ps = conn.prepareStatement(update);
            ps.setString(1,format.getNom());
            ps.setInt(2,format.getId());
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
            sql = "SELECT max("+ContractFormat.ID+") FROM "+ContractFormat.NOM_TAULA;
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

    private Format read() throws SQLException,ClassNotFoundException{
        Format objFormat = new Format();
        objFormat.setId(rs.getInt(ContractFormat.ID));
        objFormat.setNom(rs.getString(ContractFormat.NOM));
        return objFormat;
    }

    public static void main(String[] args) throws SQLException, ClassNotFoundException {

    }
}
