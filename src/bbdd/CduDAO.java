package bbdd;

import base.ConnectionFactory;
import contract.ContractCdu;
import objecte.Cdu;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author albertCorominas
 */

public class CduDAO{
    private Connection conn;
    private ResultSet rs;
    private PreparedStatement ps;

    public CduDAO(){
        conn = null;
        rs = null;
        ps = null;
    }
    public List<Cdu> selectAll() throws ClassNotFoundException, SQLException{
        List<Cdu> list = new ArrayList<>();
        Cdu selectCdu;
        String sql;
        try {
            conn = ConnectionFactory.getInstance().getConnection();
            sql = "Select "+ContractCdu.ID+","+ContractCdu.NOM+","+ContractCdu.IDPARE+" from "+ ContractCdu.NOM_TAULA;
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while(rs.next()){
                selectCdu = new Cdu();
                selectCdu.setId(rs.getString(ContractCdu.ID));
                selectCdu.setNom(rs.getString(2));
                selectCdu.setIdPare(rs.getString(ContractCdu.IDPARE));
                list.add(selectCdu);
            }
        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
        } finally {
            this.close();
        }
        return list;
    }
    
    public List<Cdu> select(HashMap <String,Object> cdu) throws ClassNotFoundException, SQLException{
        List<Cdu> list = new ArrayList<>();
        String sql;
        Object []valors;
        int i;
        boolean dadaCorrecte = true;
        try {
            conn = ConnectionFactory.getInstance().getConnection();
            sql = "Select * from "+ContractCdu.NOM_TAULA;
            i=0;
            valors= new Object[cdu.size()];
            for (Map.Entry<String, Object> entry : cdu.entrySet()) {
                String key = entry.getKey();
                Object value = entry.getValue();
                switch (ContractCdu.DEFINICIO.get(key)){
                    case Types.VARCHAR:
                        dadaCorrecte=value.getClass().equals(String.class);
                        break;
                }
                if(!dadaCorrecte){
                    throw new IllegalArgumentException("Tipus de dades erroni");
                }
                else{
                    if(i ==0){
                        sql += " WHERE ";
                    }
                    else{
                        sql += " AND ";
                    }
                    sql += key;
                    if(value.getClass().equals(String.class)){
                        sql+= " LIKE ?";
                        valors[i]="%"+value+"%";
                    }
                    else{
                        sql += " = ?";
                        valors[i]=value;
                    }
                    i++;
                }
            }
            ps = conn.prepareStatement(sql);
            for(i=0;i<valors.length;i++){
                ps.setObject(i+1, valors[i]);
            }
            rs = ps.executeQuery();
            while(rs.next()){
                list.add(new Cdu(
                    rs.getString(ContractCdu.ID),
                    rs.getString(ContractCdu.IDPARE),
                    rs.getString(ContractCdu.NOM))
                );
            }
        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
        } finally {
            this.close();
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
                cdus.add(new Cdu(
                    rs.getString(ContractCdu.ID), 
                    rs.getString(ContractCdu.IDPARE), 
                    rs.getString(ContractCdu.NOM))
                );
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
        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
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
            ps.executeUpdate();
            inserit = true;
        } catch (SQLException | ClassNotFoundException ex) {
            System.out.println(cdu.getId()+" \t "+cdu.getIdPare()+" \t "+cdu.getNom());
            ex.printStackTrace();
        } finally {
            this.close();
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
            ps.executeUpdate();
            actualitzat = true;
        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
        } finally {
            this.close();
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
            this.close();
        }
        return count;
    }
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

   public List<Cdu> select(HashMap <String,Object> dades,String campOrdre,Integer totalRegistres,Integer registreInicial,Boolean ascendent)throws SQLException,ClassNotFoundException{
        List<Cdu> coleccions =new ArrayList<>();
        ArrayList<Object>valors;
        String query;
        int i;
        try {
            conn=ConnectionFactory.getInstance().getConnection();
            valors=new ArrayList<>();
            query = "SELECT * FROM "+ContractCdu.NOM_TAULA;
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
            for(i=0;i<valors.size();i++){
                ps.setObject(i+1, valors.get(i));
            }
            rs=ps.executeQuery();
            while(rs.next()){
                coleccions.add(new Cdu(rs.getString(ContractCdu.ID),
                    rs.getString(ContractCdu.IDPARE),
                    rs.getString(ContractCdu.NOM)));
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
}
