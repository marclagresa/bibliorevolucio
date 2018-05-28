package bbdd;

import base.ConnectionFactory;
import contract.ContractFormat;
import java.sql.SQLException;
import objecte.Format;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FormatDAO extends BDObject implements IObjectDAO<Format>{


    public FormatDAO(){

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
            close();
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
            close();
        }
        return list;
    }
    @Override
    public List<Format> select(HashMap <String,Object> dades,String campOrdre,Integer totalRegistres,Integer registreInicial,Boolean ascendent)throws SQLException,ClassNotFoundException,IllegalArgumentException{
        List<Format> formats =new ArrayList<>();
        ArrayList<Object>valors;
        String query;
        int i;
        try {
            conn=ConnectionFactory.getInstance().getConnection();
            valors=new ArrayList<>();
            query = "SELECT * FROM "+ContractFormat.NOM_TAULA;
            i=0;
            if(super.comprovarDadesConsulta(dades, ContractFormat.DEFINICIO)){
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
                    i++;

                }
                if(campOrdre!=null){
                    query+=" ORDER BY " +campOrdre;
                    
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
                for(i=0;i<valors.size();i++){
                    ps.setObject(i+1, valors.get(i));
                }
                rs=ps.executeQuery();
                while(rs.next()){
                    formats.add(this.read());
                }
            }
            else{
                throw new IllegalArgumentException("Les dades de la consulta no corresponent amb els camps.");
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

    public List<Format> select(HashMap<String,Object> dades) throws ClassNotFoundException, SQLException,IllegalArgumentException{
        List<Format> list = new ArrayList<>();
        String sql;
        Object [] valors;
        int i;
        boolean dadesCorrecte=super.comprovarDadesConsulta(dades, ContractFormat.DEFINICIO);
        if(dadesCorrecte){
            try {
                conn = ConnectionFactory.getInstance().getConnection();
                sql = "SELECT * FROM "+ContractFormat.NOM_TAULA;
                i=0;
                valors=new Object[dades.size()];
                for(String camp:dades.keySet()){
                    valors=new Object[dades.size()];
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
                    i++;
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
        }
        else{
            throw new IllegalArgumentException("Les dades de la consulta no corresponent amb els camps.");
        }
        return list;
    }

    public int selectCount(HashMap <String,Object> dades)throws SQLException,ClassNotFoundException{
        int count=0;
        ArrayList<Object> valors;
        int i;
        String query;
        boolean dadesCorrectes=super.comprovarDadesConsulta(dades,ContractFormat.DEFINICIO);
        if(dadesCorrectes){
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
                    i++;

                }
                ps=conn.prepareStatement(query);
                for(i=0;i<valors.size();i++){
                    ps.setObject(i+1, valors.get(i));
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
        }
        else{
            throw new IllegalArgumentException("Les dades de la consulta no corresponent amb els camps.");
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
            sql = "SELECT max("+ContractFormat.ID+") FROM "+ContractFormat.NOM_TAULA+" "
                + "WHERE "+ContractFormat.ID+" > 0";
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
            super.close();
        }
        return id;
    }
    @Override
    protected Format read() throws SQLException,ClassNotFoundException{
        Format objFormat = new Format();
        objFormat.setId(rs.getInt(ContractFormat.ID));
        objFormat.setNom(rs.getString(ContractFormat.NOM));
        return objFormat;
    }
}
