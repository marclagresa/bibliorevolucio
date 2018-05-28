/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bbdd;

import base.ConnectionFactory;
import contract.ContractEditorial;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import objecte.Editorial;


/**
 * Classe encarregade de la lectura i escritura de la taula
 * bibliorevolucio.editorial
 * @author albertCorominas,sergiclotas
 */
public class EditorialDAO extends BDObject implements IObjectDAO<Editorial>{
    public EditorialDAO() {
        super();
    }

    @Override
    public boolean update(Editorial e) throws ClassNotFoundException,SQLException{
        boolean updated=false;
        String query;
        
        try{
            conn=ConnectionFactory.getInstance().getConnection();
            query="UPDATE " + ContractEditorial.NOM_TAULA + " SET "
                + ContractEditorial.NOM + " = ? "
                + "WHERE " + ContractEditorial.ID + " = ?";
            ps=conn.prepareStatement(query);
            ps.setString(1, e.getNom());
            ps.setInt(2, e.getId());
            updated=ps.executeUpdate() == 1;
        } catch(SQLException ex){
            throw new SQLException(ex.getMessage(),ex.getSQLState(),ex.getErrorCode(),ex.getCause());
        }catch( ClassNotFoundException ex){
            throw new ClassNotFoundException(ex.getMessage(),ex.getCause());
        } finally {
            close();
        }
        return updated;
    }

    @Override
    public List<Editorial> selectAll() throws SQLException,ClassNotFoundException{
        List <Editorial> editorials= new ArrayList<>();
        String query;
        try{
            conn=ConnectionFactory.getInstance().getConnection();
            query="SELECT * FROM "+ ContractEditorial.NOM_TAULA;
            rs=conn.createStatement().executeQuery(query);
            while(rs.next()){
                editorials.add(read());
            }
        }catch(SQLException e){
            throw new SQLException(e.getMessage(),e.getSQLState(),e.getErrorCode(),e.getCause());
        }catch(ClassNotFoundException e){
            throw new ClassNotFoundException(e.getMessage(),e.getCause());
        }
        return editorials;
    }

    @Override
    public boolean insert(Editorial e) throws ClassNotFoundException,SQLException {
        boolean inserit=false;
        String query;
        try {
            conn=ConnectionFactory.getInstance().getConnection();
            query="INSERT INTO "+ContractEditorial.NOM_TAULA + " ("
                    + ContractEditorial.ID + ", "
                    + ContractEditorial.NOM + ") "
            + "VALUES (?,?)";
            ps=conn.prepareStatement(query);
            ps.setInt(1, e.getId());
            if(e.getNom().equals("")){
                ps.setString(2,null);
            }
            else{
                ps.setString(2,e.getNom());
            }
            inserit=ps.executeUpdate()==1;
        } catch (ClassNotFoundException ex) {
            throw new ClassNotFoundException(ex.getMessage(),ex.getCause());
        } catch (SQLException ex){
            throw new SQLException (ex.getMessage(),ex.getSQLState(),ex.getErrorCode(),ex.getCause());
        } 
        finally{
            close();
        }
        return inserit;
    }
    
    @Override
    public int selectCount(HashMap <String,Object> dades)throws SQLException,ClassNotFoundException,IllegalArgumentException{
        int count=0;
        ArrayList<Object> valors;
        int i;
        String query;
        boolean dadesCorrectes=super.comprovarDadesConsulta(dades, ContractEditorial.DEFINICIO);
        if(dadesCorrectes){
            try {
                conn=ConnectionFactory.getInstance().getConnection();
                valors=new ArrayList<>();
                query = "SELECT COUNT(*) FROM "+ContractEditorial.NOM_TAULA;
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
                close();
            }
        }
        else{
            throw new IllegalArgumentException("Els tipus de dades de la consulta no són correctes.");
        }
        return count;
    }
    @Override 
    public List<Editorial> select(HashMap <String,Object> dades,String campOrdre,Integer totalRegistres,Integer registreInicial,Boolean ascendent)throws IllegalArgumentException,SQLException,ClassNotFoundException{
        List<Editorial> editorials=new ArrayList<>();
        Editorial objEditorial;
        ArrayList<Object>valors;
        String query;
        int i;
        boolean dadesCorrectes=super.comprovarDadesConsulta(dades, ContractEditorial.DEFINICIO);
        if(dadesCorrectes){
            try {
                conn=ConnectionFactory.getInstance().getConnection();
                valors=new ArrayList<>();
                query = "SELECT * FROM "+ContractEditorial.NOM_TAULA;
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
                    editorials.add(read());
                }
            } catch (SQLException ex) {
                throw new SQLException(ex.getMessage(), ex.getSQLState() , ex.getErrorCode(), ex.getCause());
            } catch(ClassNotFoundException ex){
                throw new ClassNotFoundException(ex.getMessage(), ex.getCause());
            }finally{
                close();
            }
        }
        else{
            throw new IllegalArgumentException("Els tipus de dades de la consulta no són correctes.");
        }
        return editorials;
    }
    
    @Override
    public int nextId()throws SQLException,ClassNotFoundException{
        int id=1;
        try {
            conn=ConnectionFactory.getInstance().getConnection();
            rs=conn.createStatement().executeQuery("SELECT MAX("+ContractEditorial.ID +") FROM "+ContractEditorial.NOM_TAULA +" "
                    + "WHERE "+ContractEditorial.ID +" > 0");
            if(rs.next()){
                id=rs.getInt(1) + 1;
            }
        } catch (SQLException ex) {
            throw new SQLException(ex.getMessage(),ex.getSQLState(),ex.getErrorCode(),ex.getCause());
        } catch(ClassNotFoundException ex){
            throw new ClassNotFoundException(ex.getMessage(),ex.getCause());
        } 
        finally{
            close();
        }
        return id;
    }
    @Override
    public Editorial select(int id) throws ClassNotFoundException, SQLException {
        Editorial objEditorial = new Editorial();
        String query;
        try{
            conn=ConnectionFactory.getInstance().getConnection();
            query= "SELECT * FROM "+ContractEditorial.NOM_TAULA 
                 + " WHERE " + ContractEditorial.ID + " = ?";
            ps=conn.prepareStatement(query);
            ps.setInt(1, id);
            rs=ps.executeQuery();
            if(rs.next()){
               objEditorial=read();
            }
        } catch(SQLException e){
            throw new SQLException (e.getMessage(),e.getSQLState(),e.getErrorCode(),e.getCause());
        } catch(ClassNotFoundException e){
            throw new ClassNotFoundException (e.getMessage(),e.getCause());
        }
        finally{
            close();
        }
        return objEditorial;
    }
 
    @Override
    protected Editorial read() throws SQLException {
        return new Editorial(rs.getInt(ContractEditorial.ID), rs.getString(ContractEditorial.NOM));
    }
}
