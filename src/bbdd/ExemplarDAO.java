package bbdd;

import base.ConnectionFactory;
import contract.ContractExemplar;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import objecte.Exemplar;


/**
 * Classe encarregade de la lectura i escritura de la taula
 * bibliorevolucio.exemplar
 * @author albertCorominas,sergiclotas
 */

public class ExemplarDAO extends BDObject implements IObjectDAO<Exemplar> {
    public ExemplarDAO() {
      super();
    }
    @Override
    public List<Exemplar> selectAll() throws ClassNotFoundException, SQLException {
        List<Exemplar> list = new ArrayList<>();
        String sql;
        try {
            conn = ConnectionFactory.getInstance().getConnection();
            sql = "SELECT * FROM "
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

    public List<Exemplar> select(HashMap<String,Object> dades) throws ClassNotFoundException, SQLException{
        List<Exemplar> list = new ArrayList<>();
        String sql;
        Object [] valors;
        int i;
       boolean dadesCorrectes = super.comprovarDadesConsulta(dades,ContractExemplar.DEFINICIO);
        if(dadesCorrectes){
            try {
                conn = ConnectionFactory.getInstance().getConnection();
                sql = "SELECT * FROM "+ContractExemplar.NOM_TAULA;
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
            throw new IllegalArgumentException("El tipus de dades de la consulta no són correctes.");
        }
        return list;
    }

    @Override
    public int selectCount(HashMap <String,Object> dades)throws SQLException,ClassNotFoundException{
        int count=0;
        ArrayList<Object> valors;
        int i;
        String query;
        boolean dadesCorrectes=super.comprovarDadesConsulta(dades, ContractExemplar.DEFINICIO);
        if(dadesCorrectes){
            try {
                conn=ConnectionFactory.getInstance().getConnection();
                valors=new ArrayList<>();
                query = "SELECT COUNT(*) FROM "+ContractExemplar.NOM_TAULA;
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
    public Exemplar select(int id) throws ClassNotFoundException, SQLException{
        Exemplar exemplar = new Exemplar();
        String sql;
        try {
            conn = ConnectionFactory.getInstance().getConnection();
            sql = "Select * from "
                    +ContractExemplar.NOM_TAULA + " where "
                    +ContractExemplar.ID + " = ? ";
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
            insert = "Insert into "+ContractExemplar.NOM_TAULA+" values (?,?,?,?)";
            ps = conn.prepareStatement(insert);
            ps.setInt(1,exemplar.getId());
            ps.setInt(2,exemplar.getProducte().getId());
            ps.setInt(3,exemplar.getBiblioteca().getId());
            ps.setBoolean(4,exemplar.getEstat());
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
                    +ContractExemplar.ACTIVA+" = ?"
                    +" WHERE "
                    +ContractExemplar.ID+" = ? ";
            ps = conn.prepareStatement(update);
            ps.setInt(1,exemplar.getId());
            ps.setInt(2,exemplar.getProducte().getId());
            ps.setInt(3,exemplar.getBiblioteca().getId());
            ps.setBoolean(4,exemplar.getEstat());

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
    protected Exemplar read() throws SQLException,ClassNotFoundException{
        Exemplar objExemplar = new Exemplar();
        
        objExemplar.setId(rs.getInt(ContractExemplar.ID));
        objExemplar.setIdProducte(new ProducteDAO().select(rs.getInt(ContractExemplar.ID_PRODUCTE)));
        objExemplar.setIdBiblioteca(new BibliotecaDAO().select(rs.getInt(ContractExemplar.ID_BIBLIOTECA)));
        objExemplar.setEstat(rs.getBoolean(ContractExemplar.ACTIVA));
        return objExemplar;
    }
}
