package bbdd;

import base.ConnectionFactory;
import contract.ContractProcedencia;
import objecte.Procedencia;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.sql.SQLException;
import java.sql.Types;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ProcedenciaDAO extends BDObject implements IObjectDAO<Procedencia> {


    public ProcedenciaDAO(){
        super();
    }
    public Procedencia select(String nom) throws ClassNotFoundException,SQLException{
        Procedencia procedenciaObj = new Procedencia();
        String query;
        try{
            conn=ConnectionFactory.getInstance().getConnection();
            query="SELECT * FROM " + ContractProcedencia.NOM_TAULA + " "
                + "WHERE "+ContractProcedencia.NOM + " LIKE ?";
            ps=conn.prepareStatement(query);
            ps.setString(1, nom);
            rs=ps.executeQuery();
            if(rs.next()){
                procedenciaObj=read();
            }
        } catch(ClassNotFoundException e){
            throw new ClassNotFoundException(e.getMessage(),e.getCause());
        } catch(SQLException e){
            throw new SQLException(e.getMessage(), e.getSQLState(), e.getErrorCode(), e.getCause());
        } finally{
            this.close();
        }
        return procedenciaObj;
    }
    @Override
    public List<Procedencia> selectAll() throws ClassNotFoundException, SQLException {
        List<Procedencia> list = new ArrayList<>();
        String sql;
        try {
            conn = ConnectionFactory.getInstance().getConnection();
            sql = "Select "
                    +ContractProcedencia.ID+","
                    +ContractProcedencia.NOM+" from "
                    +ContractProcedencia.NOM_TAULA;
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
    public List<Procedencia> select(HashMap <String,Object> dades,String campOrdre,Integer totalRegistres,Integer registreInicial,Boolean ascendent)throws SQLException,ClassNotFoundException{
        List<Procedencia> procedencies =new ArrayList<>();
        ArrayList<Object> valors;
        String query;
        int i;
        boolean dadesCorrectes=comprovarDadesConsulta(dades, ContractProcedencia.DEFINICIO);
        if(dadesCorrectes){
            try {
                conn=ConnectionFactory.getInstance().getConnection();
                valors=new ArrayList<>();
                query = "SELECT * FROM "+ContractProcedencia.NOM_TAULA;
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
                if(campOrdre!=null){
                    query+=" ORDER BY " + campOrdre;
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
                    procedencies.add(this.read());
                }
            } catch (SQLException ex) {
                throw new SQLException(ex.getMessage(), ex.getSQLState() , ex.getErrorCode(), ex.getCause());
            } catch(ClassNotFoundException ex){
                throw new ClassNotFoundException(ex.getMessage(), ex.getCause());
            }finally{
                this.close();
            }

        }else{
            throw new IllegalArgumentException("El tipus de dades de la consulta no són correctes.");
        }
        return procedencies;
    }

    public List<Procedencia> select(HashMap<String,Object> dades) throws ClassNotFoundException, SQLException,IllegalArgumentException{
        List<Procedencia> list = new ArrayList<>();
        String sql;
        Object [] valors;
        int i;
        boolean dadesCorrecte=comprovarDadesConsulta(dades, ContractProcedencia.DEFINICIO);
        if(dadesCorrecte){
            try {
                conn = ConnectionFactory.getInstance().getConnection();
                sql = "SELECT * FROM "+ContractProcedencia.NOM_TAULA;
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
        }else{
            throw new IllegalArgumentException("Els tipus de dades de la consulta no són correctes.");
        }
        
        return list;
    }
    @Override
    public int selectCount(HashMap <String,Object> dades)throws SQLException,ClassNotFoundException,IllegalArgumentException{
        int count=0;
        ArrayList<Object> valors;
        int i;
        String query;
        boolean dadesCorrectes=comprovarDadesConsulta(dades, ContractProcedencia.DEFINICIO);
        if(dadesCorrectes){
            try {
                conn=ConnectionFactory.getInstance().getConnection();
                valors=new ArrayList<>();
                query = "SELECT COUNT(*) FROM "+ContractProcedencia.NOM_TAULA;
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
        }else{
            throw new IllegalArgumentException("Els tipus de dades de la consulta no són correctes.");
        }
        return count;
    }

    @Override
    public Procedencia select(int id) throws ClassNotFoundException, SQLException{
        Procedencia procedencia = new Procedencia();
        String sql;
        try {
            conn = ConnectionFactory.getInstance().getConnection();
            sql = "Select "+ContractProcedencia.ID+","+ContractProcedencia.NOM+" from "+ ContractProcedencia.NOM_TAULA
                    +" where "+ContractProcedencia.ID +" = ? ";
            ps = conn.prepareStatement(sql);
            ps.setInt(1,id);
            rs = ps.executeQuery();
            if(rs.next()){
                procedencia=this.read();
            }
        } catch (SQLException ex) {
            throw new SQLException(ex.getMessage(),ex.getSQLState(),ex.getErrorCode(),ex.getCause());

        } catch(ClassNotFoundException ex){
            throw new ClassNotFoundException(ex.getMessage(), ex.getCause());
        } finally {
            this.close();
        }
        return procedencia;
    }
    @Override
    public boolean insert(Procedencia procedencia) throws ClassNotFoundException, SQLException{
        String insert;
        boolean inserit;
        try {
            conn = ConnectionFactory.getInstance().getConnection();
            insert = "Insert into "+ContractProcedencia.NOM_TAULA+" values (?,?)";
            ps = conn.prepareStatement(insert);
            ps.setInt(1,procedencia.getId());
            ps.setString(2,procedencia.getNom());
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
    public boolean update(Procedencia procedencia) throws ClassNotFoundException, SQLException{
        String update;
        boolean actualitzat;
        try {
            conn = ConnectionFactory.getInstance().getConnection();
            update = "UPDATE "+ContractProcedencia.NOM_TAULA+" SET "
                    +ContractProcedencia.NOM+" = ?, where "
                    +ContractProcedencia.ID+" = ?";
            ps = conn.prepareStatement(update);
            ps.setString(1,procedencia.getNom());
            ps.setInt(2,procedencia.getId());
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
            sql = "SELECT max("+ContractProcedencia.ID+") FROM "+ContractProcedencia.NOM_TAULA+" "
                + "WHERE "+ContractProcedencia.ID+" > 0";
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
    protected Procedencia read() throws SQLException,ClassNotFoundException{
        Procedencia objProcedencia = new Procedencia();
        objProcedencia.setId(rs.getInt(ContractProcedencia.ID));
        objProcedencia.setNom(rs.getString(ContractProcedencia.NOM));
        return objProcedencia;
    }
}
