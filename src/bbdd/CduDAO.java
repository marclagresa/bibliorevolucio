package bbdd;

import base.ConnectionFactory;
import contract.ContractCdu;
import objecte.Cdu;

import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Classe encarregade de la lectura i escritura de la taula
 * bibliorevolucio.cdu
 * @author albertCorominas,sergiclotas
 */

public class CduDAO extends BDObject{

    public CduDAO(){
        super();
    }
    public List<Cdu> selectAll() throws ClassNotFoundException, SQLException{
        List<Cdu> list = new ArrayList<>();
        Cdu selectCdu;
        String sql;
        try {
            super.conn = ConnectionFactory.getInstance().getConnection();
            sql = "Select "+ContractCdu.ID+","+ContractCdu.NOM+","+ContractCdu.IDPARE+" from "+ ContractCdu.NOM_TAULA;
            super.ps = conn.prepareStatement(sql);
            super.rs = ps.executeQuery();
            while(super.rs.next()){
                list.add(read());
            }
        } catch (SQLException ex) {
            throw new SQLException(ex.getMessage(),ex.getSQLState(),ex.getErrorCode(),ex.getCause());
        } catch(ClassNotFoundException ex){
            throw new ClassNotFoundException(ex.getMessage(), ex.getCause());
        }
        finally {
            close();
        }
        return list;
    }
    public List<Cdu> select(HashMap <String,Object> dades,String campOrdre,Integer totalRegistres,Integer registreInicial,Boolean ascendent) throws ClassNotFoundException, SQLException,IllegalArgumentException{
        List<Cdu> list = new ArrayList<>();
        String sql;
        List<Object> valors;
        int i;
        boolean dadesCorrecte = super.comprovarDadesConsulta(dades, ContractCdu.DEFINICIO);
        if(dadesCorrecte){
            try {
                conn = ConnectionFactory.getInstance().getConnection();
                sql = "SELECT * FROM "+ContractCdu.NOM_TAULA;
                i=0;
                valors=new ArrayList<>();
                for (Map.Entry<String, Object> entry : dades.entrySet()) {
                    String key = entry.getKey();
                    Object value = entry.getValue();
                    if(i ==0){
                        sql += " WHERE ";
                    }
                    else{
                        sql += " AND ";
                    }
                    sql += key;
                    if(value.getClass().equals(String.class)){
                        sql+= " LIKE ?";
                        valors.add("%"+value+"%");
                    }
                    else{
                        sql += " = ?";
                        valors.add(value);
                    }
                    i++;
                    if(campOrdre!=null){
                    sql+=" ORDER BY " + campOrdre;
                    
                    if(ascendent){
                        sql+=" ASC ";
                    }
                    else{
                        sql+= " DESC ";
                    }
                }
                if(registreInicial!=null || totalRegistres!=null){
                        sql += " LIMIT ";
                        if(registreInicial!=null){
                            sql += " ?, ";
                            valors.add(registreInicial);
                        }
                        if(totalRegistres==null){
                            sql +=" 18446744073709551615";
                        }
                        else{
                            sql +=" ?";
                            valors.add(totalRegistres);
                        }

                    }

                }
                ps = conn.prepareStatement(sql);
                for(i=0;i<valors.size();i++){
                    ps.setObject(i+1, valors.get(i));
                }
                rs = ps.executeQuery();
                while(rs.next()){
                    list.add(read());
                }
            } catch (SQLException ex){
                throw new SQLException(ex.getMessage(),ex.getSQLState(),ex.getErrorCode(),ex.getCause());
            }catch(  ClassNotFoundException ex) {
                throw new ClassNotFoundException(ex.getMessage(), ex.getCause());
            } finally {
                close();
            }
            
        }
        else{
            throw new IllegalArgumentException("Els tipus de dades de la consutla no són correctes.");
        }
        return list;
    }
    public List<Cdu> selectFills(String idCduPare) throws ClassNotFoundException,SQLException{
        List<Cdu> cdus=new ArrayList<>();
        String sQuery;
        try {
            conn=ConnectionFactory.getInstance().getConnection();
            sQuery="SELECT * FROM "+ContractCdu.NOM_TAULA+" "
                 + "WHERE " +ContractCdu.IDPARE + " LIKE ?";
            ps=conn.prepareStatement(sQuery);
            ps.setString(1, idCduPare);
            rs=ps.executeQuery();
            while(rs.next()){
                cdus.add(read());
            }
        } catch (SQLException e) {
            throw new SQLException(e.getMessage(),e.getSQLState(),e.getErrorCode(),e.getCause());
        } catch(ClassNotFoundException e){
            throw new ClassNotFoundException(e.getMessage(), e.getCause());
        } finally{
            this.close();
        }
        return cdus;
    }
    public Cdu select(String id) throws ClassNotFoundException, SQLException{
        Cdu cdu = new Cdu();
        String sql;
        try {
            conn = ConnectionFactory.getInstance().getConnection();
            sql = "Select "+ContractCdu.ID+","+ContractCdu.NOM+","+ContractCdu.IDPARE+
                " from "+ContractCdu.NOM_TAULA+" where "+ContractCdu.ID+" like ? ";
            ps = conn.prepareStatement(sql);
            ps.setString(1,id);
            rs = ps.executeQuery();
            cdu = new Cdu();
            cdu.setId(rs.getString(ContractCdu.ID));
            cdu.setNom(rs.getString(ContractCdu.NOM));
            cdu.setIdPare(rs.getString(ContractCdu.IDPARE));
        } catch (SQLException ex){
            throw new SQLException(ex.getMessage(),ex.getSQLState(),ex.getErrorCode(),ex.getCause());
        }catch( ClassNotFoundException ex) {
            throw new ClassNotFoundException(ex.getMessage(),ex.getCause());
        } finally {
            this.close();
        }
        return cdu;
    }
    public boolean insert(Cdu cdu) throws ClassNotFoundException, SQLException{
        String insert;
        boolean inserit = false;
        int id;
        try {
            conn=ConnectionFactory.getInstance().getConnection();
            insert = "Insert into "+ContractCdu.NOM_TAULA+"( "
                +ContractCdu.ID+ ", "
                +ContractCdu.NOM+", "
                +ContractCdu.IDPARE+") values (?,?,?)";
            ps = conn.prepareStatement(insert);
            ps.setString(1,cdu.getId());
            ps.setString(2,cdu.getNom());
            if(cdu.getIdPare().isEmpty()){
                ps.setNull(3, Types.VARCHAR);
            }
            else{
                ps.setString(3,cdu.getIdPare());
            }
            inserit =ps.executeUpdate()==1;
             
        } catch (SQLException ex){
            throw new SQLException(ex.getMessage(),ex.getSQLState(),ex.getErrorCode(),ex.getCause());
        }catch( ClassNotFoundException ex) {
            throw new ClassNotFoundException(ex.getMessage(),ex.getCause());
        } finally {
            close();
        } 
        return inserit;
    }
   
    public boolean update(Cdu cdu) throws ClassNotFoundException, SQLException{
        String update;
        boolean actualitzat = false;
        try {
            conn = ConnectionFactory.getInstance().getConnection();
            update = "UPDATE "+ContractCdu.NOM_TAULA+" SET "+ContractCdu.NOM+" = ?, "+
                    ContractCdu.IDPARE+ " = ? where "+ContractCdu.ID+" = ?";
            ps = conn.prepareStatement(update);
            ps.setString(1,cdu.getNom());
            ps.setString(2,cdu.getIdPare());
            ps.setString(3,cdu.getId());
            
            actualitzat = ps.executeUpdate()==1;
        } catch (SQLException ex){
            throw new SQLException(ex.getMessage(),ex.getSQLState(),ex.getErrorCode(),ex.getCause());
        } catch( ClassNotFoundException ex) {
            throw new ClassNotFoundException(ex.getMessage(),ex.getCause());
        } finally {
            close();
        }
        return actualitzat;
    }
     public int selectCount(HashMap <String,Object> dades)throws SQLException,ClassNotFoundException{
        int count=0;
        ArrayList<Object> valors;
        int i;
        String query;
        try {
            conn=ConnectionFactory.getInstance().getConnection();
            valors=new ArrayList<>();
            query = "SELECT COUNT(*) FROM "+ ContractCdu.NOM_TAULA;
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
            close();
        }
        return count;
    }
    @Override
    protected Cdu read() throws SQLException {
        return new Cdu (
            rs.getString(ContractCdu.ID),
            rs.getString(ContractCdu.IDPARE),
            rs.getString(ContractCdu.NOM));
    }
}
