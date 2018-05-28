package bbdd;

import base.ConnectionFactory;
import contract.ContractMateria;
import contract.ContractNivell;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import objecte.Nivell;



/**
 * @author albertCorominas
 */

public class NivellDAO extends BDObject implements IObjectDAO<Nivell> {


    public NivellDAO(){
        super();
    }

    @Override
    public List<Nivell> selectAll() throws ClassNotFoundException, SQLException {
        List<Nivell> list = new ArrayList<>();
        String sql;
        try {
            conn = ConnectionFactory.getInstance().getConnection();
            sql = "Select "
                    +ContractNivell.ID+","
                    +ContractNivell.NOM+" from "
                    +ContractNivell.NOM_TAULA;
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
    public List<Nivell> select(HashMap <String,Object> dades,String campOrdre,Integer totalRegistres,Integer registreInicial,Boolean ascendent)throws SQLException,ClassNotFoundException{
        List<Nivell> nivells =new ArrayList<>();
        ArrayList<Object>valors;
        String query;
        int i;
        boolean dadesCorrectes=comprovarDadesConsulta(dades, ContractNivell.DEFINICIO);
        if(dadesCorrectes){
            try {
                conn=ConnectionFactory.getInstance().getConnection();
                valors=new ArrayList<>();
                query = "SELECT * FROM "+ContractNivell.NOM_TAULA;
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
                    query+=" ORDER BY "+campOrdre;
                    
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
                    nivells.add(this.read());
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
           throw new IllegalArgumentException("Els tipus de dades de la consulta no són correctes");
        }
        return nivells;
    }

    public List<Nivell> select(HashMap<String,Object> dades) throws ClassNotFoundException, SQLException,IllegalArgumentException{
        List<Nivell> list = new ArrayList<>();
        String sql;
        Object [] valors;
        int i;
        boolean dadesCorrecte=comprovarDadesConsulta(dades, ContractNivell.DEFINICIO);
        if(dadesCorrecte){
            try {
                conn = ConnectionFactory.getInstance().getConnection();
                sql = "SELECT * FROM "+ContractNivell.NOM_TAULA;
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
            throw new IllegalArgumentException("Els tipus de dades de la consulta no són correctes");
        }
        return list;
    }
    public Nivell select(String nom) throws SQLException,ClassNotFoundException{
        Nivell nivellObj=new Nivell();
        String query;
        try{
            conn=ConnectionFactory.getInstance().getConnection();
            query="SELECT * FROM " +ContractNivell.NOM_TAULA +" "
                + "WHERE " +ContractNivell.NOM + " LIKE   ?";
            ps=conn.prepareStatement(query);
            ps.setString(1, nom);
            rs=ps.executeQuery();
            if(rs.next()){
                nivellObj=read();
            }
        } catch(SQLException e){
            throw new SQLException (e.getMessage(),e.getSQLState(),e.getErrorCode(),e.getCause());
        } catch(ClassNotFoundException e){
            throw new ClassNotFoundException(e.getMessage(), e.getCause());
        } finally{
            this.close();
        }
        return nivellObj;
    }
    @Override
    public int selectCount(HashMap <String,Object> dades)throws SQLException,ClassNotFoundException{
        int count=0;
        ArrayList<Object> valors;
        int i;
        String query;
        boolean dadesCorrectes=comprovarDadesConsulta(dades, ContractNivell.DEFINICIO);
        if(dadesCorrectes){
            try {
                conn=ConnectionFactory.getInstance().getConnection();
                valors=new ArrayList<>();
                query = "SELECT COUNT(*) FROM "+ContractNivell.NOM_TAULA;
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
            throw new IllegalArgumentException("Els tipus de dades de la consulta no són correctes.");
        }
        
        return count;
    }
    @Override
    public Nivell select(int id) throws ClassNotFoundException, SQLException{
        Nivell nivell = new Nivell();
        String sql;
        try {
            conn = ConnectionFactory.getInstance().getConnection();
            sql = "Select "+ContractNivell.ID+","+ContractNivell.NOM+" from "+ ContractNivell.NOM_TAULA
                    +" where "+ContractNivell.ID +" = ? ";
            ps = conn.prepareStatement(sql);
            ps.setInt(1,id);
            rs = ps.executeQuery();
            if(rs.next()){
                nivell=this.read();
            }
        } catch (SQLException ex) {
            throw new SQLException(ex.getMessage(),ex.getSQLState(),ex.getErrorCode(),ex.getCause());

        } catch(ClassNotFoundException ex){
            throw new ClassNotFoundException(ex.getMessage(), ex.getCause());
        } finally {
            this.close();
        }
        return nivell;
    }
    @Override
    public boolean insert(Nivell nivell) throws ClassNotFoundException, SQLException{
        String insert;
        boolean inserit;
        try {
            conn = ConnectionFactory.getInstance().getConnection();
            insert = "Insert into "+ContractNivell.NOM_TAULA+"("+ContractNivell.ID+","+ContractNivell.NOM+") values (?,?)";
            ps = conn.prepareStatement(insert);
            ps.setInt(1,nivell.getId());
            ps.setString(2,nivell.getNom());
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
    public boolean update(Nivell nivell) throws ClassNotFoundException, SQLException{
        String update;
        boolean actualitzat;
        try {
            conn = ConnectionFactory.getInstance().getConnection();
            update = "UPDATE "+ContractNivell.NOM_TAULA+" SET "
                    +ContractNivell.NOM+" = ?, where "
                    +ContractNivell.ID+" = ?";
            ps = conn.prepareStatement(update);
            ps.setString(1,nivell.getNom());
            ps.setInt(2,nivell.getId());
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
            sql = "SELECT max("+ContractNivell.ID+") FROM "+ContractNivell.NOM_TAULA;
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
    protected Nivell read() throws SQLException,ClassNotFoundException{
        Nivell objNivell = new Nivell();
        objNivell.setId(rs.getInt(ContractNivell.ID));
        objNivell.setNom(rs.getString(ContractNivell.NOM));
        return objNivell;
    }

    public static void main(String[] args) throws SQLException, ClassNotFoundException, IOException {}
}
