package bbdd;

import base.ConnectionFactory;
import contract.ContractIdioma;
import java.sql.SQLException;
import objecte.Idioma;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class IdiomaDAO extends BDObject implements IObjectDAO<Idioma> {


    public IdiomaDAO(){
    }

    @Override
    public List<Idioma> selectAll() throws ClassNotFoundException, SQLException{
        List<Idioma> list = new ArrayList<>();
        String sql;
        Idioma idioma;
        try {
            conn = ConnectionFactory.getInstance().getConnection();
            sql = "Select "+ ContractIdioma.ID+","+ContractIdioma.NOM+" from "+ContractIdioma.NOM_TAULA;
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while(rs.next()){
                idioma = new Idioma();
                idioma.setId(rs.getInt(1));
                idioma.setNom(rs.getString(2));
                list.add(idioma);
            }
        } catch (SQLException ex){
            throw new SQLException(ex.getMessage(),ex.getSQLState(),ex.getErrorCode(),ex.getCause());
        } catch ( ClassNotFoundException ex) {
            throw new ClassNotFoundException(ex.getMessage(), ex.getCause());
        } finally {
            this.close();
        }
        return list;
    }
    @Override
    public List<Idioma> select(HashMap <String,Object> dades,String campOrdre,Integer totalRegistres,Integer registreInicial,Boolean ascendent)throws SQLException,ClassNotFoundException,IllegalArgumentException{
        List<Idioma> idiomes =new ArrayList<>();
        ArrayList<Object>valors;
        String query;
        int i;
        boolean dadesCorrectes=super.comprovarDadesConsulta(dades, ContractIdioma.DEFINICIO);
        if(dadesCorrectes){
            try {
                conn=ConnectionFactory.getInstance().getConnection();
                valors=new ArrayList<>();
                query = "SELECT * FROM "+ContractIdioma.NOM_TAULA;
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
                    idiomes.add(this.read());
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
            throw new IllegalArgumentException("El tipus de dades de la consulta no són correctes.");
        }
        return idiomes;
    }
    
    public List<Idioma> select(HashMap <String,Object> dades) throws ClassNotFoundException, SQLException{
        List<Idioma> list = new ArrayList<>();
        String sql;
        Object [] valors;
        int i;
        boolean dadesCorrecte=super.comprovarDadesConsulta(dades, ContractIdioma.DEFINICIO);
        if(dadesCorrecte){
           try {
                conn = ConnectionFactory.getInstance().getConnection();
                sql = "SELECT * FROM "+ContractIdioma.NOM_TAULA;
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
    public int selectCount(HashMap <String,Object> dades)throws SQLException,ClassNotFoundException{
        int count=0;
        ArrayList<Object> valors;
        int i;
        String query;
        boolean dadesCorrectes=super.comprovarDadesConsulta(dades, ContractIdioma.DEFINICIO);
        if(dadesCorrectes){
            try {
                conn=ConnectionFactory.getInstance().getConnection();
                valors=new ArrayList<>();
                query = "SELECT COUNT(*) FROM "+ContractIdioma.NOM_TAULA;
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
    public Idioma select(int id) throws ClassNotFoundException, SQLException{
        Idioma idioma = new Idioma();
        String sql;
        try {
            conn = ConnectionFactory.getInstance().getConnection();
            sql = "Select "+ContractIdioma.ID+","+ContractIdioma.NOM+" from "+ContractIdioma.NOM_TAULA+
                    " where "+ContractIdioma.ID+" = ? ";
            ps = conn.prepareStatement(sql);
            ps.setInt(1,id);
            rs = ps.executeQuery();
            if(rs.next()){
                idioma.setId(rs.getInt(1));
                idioma.setNom(rs.getString(2));
            }
        } catch (SQLException ex){
            throw new SQLException(ex.getMessage(),ex.getSQLState(),ex.getErrorCode(),ex.getCause());
        }catch (ClassNotFoundException ex) {
            throw new ClassNotFoundException(ex.getMessage(),ex.getCause());
        } finally {
            this.close();
        }
        return idioma;
    }
    @Override
    public boolean insert(Idioma idioma) throws ClassNotFoundException, SQLException{
        String insert;
        boolean inserit = false;
        int id;
        try {
            conn = ConnectionFactory.getInstance().getConnection();
            insert = "Insert into "+ContractIdioma.NOM_TAULA+"("
                    +ContractIdioma.ID+", "
                    +ContractIdioma.NOM
                    +") values (?,?)";
            ps = conn.prepareStatement(insert);
            ps.setInt(1,idioma.getId());
            ps.setString(2,idioma.getNom());
            
            inserit=ps.executeUpdate()==1;
            
        } catch (SQLException e){
            throw new SQLException(e.getMessage(),e.getSQLState(),e.getErrorCode(),e.getCause());
        } catch( ClassNotFoundException ex) {
            throw new ClassNotFoundException(ex.getMessage(),ex.getCause());
        } finally {
            this.close();
        }
        return inserit;
    }

    public Idioma select(String nom) throws ClassNotFoundException,SQLException{
        Idioma idiomaObj=new Idioma();
        String query;
        try {
            conn=ConnectionFactory.getInstance().getConnection();
            query="SELECT * FROM "+ContractIdioma.NOM_TAULA
                + " WHERE LOWER("+ContractIdioma.NOM + ") = ?"; 
            ps=conn.prepareStatement(query);
            ps.setString(1, nom);
            rs=ps.executeQuery();
            if(rs.next()){
                idiomaObj.setId(rs.getInt(ContractIdioma.ID));
                idiomaObj.setNom(rs.getString(ContractIdioma.NOM));
            }
        } catch (SQLException e) {
            throw new SQLException(e.getMessage(),e.getSQLState(),e.getErrorCode(),e.getCause());
        } catch(ClassNotFoundException e){
            throw new ClassNotFoundException(e.getMessage(),e.getCause());
        } finally{
            this.close();
        }
        return idiomaObj;
    }
    @Override
    public boolean update(Idioma idioma) throws ClassNotFoundException, SQLException{
        String update;
        boolean actualitzat = false;
        try {
            conn = ConnectionFactory.getInstance().getConnection();
            update = "UPDATE "+ContractIdioma.NOM_TAULA+" SET "+
                    ContractIdioma.NOM+" = ? where "+ContractIdioma.ID+" = ?";
            ps = conn.prepareStatement(update);
            ps.setString(1,idioma.getNom());
            ps.setInt(2,idioma.getId());
            actualitzat=ps.executeUpdate()==1;
            
        } catch (SQLException ex){
            throw new SQLException(ex.getMessage(),ex.getSQLState(),ex.getErrorCode(),ex.getCause());
        } catch( ClassNotFoundException ex) {
            throw new ClassNotFoundException(ex.getMessage(), ex.getCause());
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
            sql = "SELECT max("+ContractIdioma.ID+") FROM "+ContractIdioma.NOM_TAULA;
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            if(rs.next()){
                id = rs.getInt(1)+1;
            }
        } catch (SQLException e){
            throw new SQLException(e.getMessage(),e.getSQLState(),e.getErrorCode(),e.getCause());
        }catch( ClassNotFoundException ex) {
            throw new ClassNotFoundException(ex.getMessage(),ex.getCause());
        } finally{
            this.close();
        }
        return id;
    }
    @Override
    protected Idioma read() throws SQLException,ClassNotFoundException{
        Idioma objIdioma = new Idioma();
        objIdioma.setId(rs.getInt(ContractIdioma.ID));
        objIdioma.setNom(rs.getString(ContractIdioma.NOM));
        return objIdioma;
    }
}
