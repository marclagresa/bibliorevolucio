/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bbdd;

import base.ConnectionFactory;
import contract.ContractMateriaProducte;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;
import objecte.Materia;
import objecte.Producte;

/**
 *
 * @author sergiclotas
 */
public class ProducteMateriaDAO {
    Connection c;
    ResultSet rs;
    PreparedStatement ps;
    public ProducteMateriaDAO() {
        c=null;
        rs=null;
        ps=null;
    }
    public boolean insert (int idProducte,int idMateria)throws SQLException,ClassNotFoundException{
        boolean inserit= false;
        String query;
        try {
            c=ConnectionFactory.getInstance().getConnection();
            query="INSERT INTO " + ContractMateriaProducte.NOM_TAULA + "( "
                + ContractMateriaProducte.ID_PRODUCTE + " , "
                + ContractMateriaProducte.ID_MATERIA +" ) VALUES (?,?)";
            ps=c.prepareStatement(query);
            ps.setInt(1, idProducte);
            ps.setInt(2, idMateria);
            inserit=ps.executeUpdate()==1;
        } catch (ClassNotFoundException e) {
            throw new ClassNotFoundException(e.getMessage(),e.getCause());
        } catch(SQLException e){
            throw new SQLException(e.getMessage(),e.getSQLState(),e.getErrorCode(),e.getCause());
        } finally{
            this.close();
        }
        return inserit;
    }
    public void update(Producte producte){
        
        
        deleteMateries(producte.getId());
        producte.getMateries().forEach(action->{
            try{
                this.insert(producte.getId(), action.getId());
            }catch(ClassNotFoundException | SQLException e){
                System.err.println(e.getMessage());
            }
        });
            
        
    }
    public void deleteMateries(int idProducte){
        String query;
        try{
            c=ConnectionFactory.getInstance().getConnection();
            query="DELETE FROM "+ ContractMateriaProducte.NOM_TAULA+ " "
                + "WHERE "+ContractMateriaProducte.ID_PRODUCTE+" = ?";
            ps=c.prepareStatement(query);
            ps.setInt(1, idProducte);
            ps.executeUpdate();
        }catch(ClassNotFoundException | SQLException e){
            System.err.println(e.getMessage());
        }
    }
    public void deleteMateria(int idProducte,int idMateria){
        String query;
        try{
            c=ConnectionFactory.getInstance().getConnection();
            query="DELETE FROM "+ ContractMateriaProducte.NOM_TAULA+ " "
                + "WHERE "+ContractMateriaProducte.ID_PRODUCTE+" = ? "
                + "AND "+ContractMateriaProducte.ID_MATERIA+" = ?";
            ps=c.prepareStatement(query);
            ps.setInt(1, idProducte);
            ps.setInt(2, idMateria);
            ps.executeUpdate();
        }catch(ClassNotFoundException | SQLException e){
            System.err.println(e.getMessage());
        }
    }
    public Set<Materia> selectMateries(int idProducte) throws ClassNotFoundException,SQLException{
        Set<Materia> materias = new HashSet<>();
        String query;
        MateriaDAO materiaDAOObj;
        try {
            c=ConnectionFactory.getInstance().getConnection();
            query="SELECT " + ContractMateriaProducte.ID_MATERIA + " "
                + "FROM " + ContractMateriaProducte.NOM_TAULA + " "
                + "WHERE " +ContractMateriaProducte.ID_PRODUCTE + " = ? ";
            ps=c.prepareStatement(query);
            ps.setInt(1, idProducte);
            rs=ps.executeQuery();
            if(rs.next()){
                materiaDAOObj=new MateriaDAO();
                materias.add(materiaDAOObj.select(rs.getInt(ContractMateriaProducte.ID_MATERIA)));
                while(rs.next()){
                    materias.add(materiaDAOObj.select(rs.getInt(ContractMateriaProducte.ID_MATERIA)));
                }
            }
            
        } catch (ClassNotFoundException e){
            throw new ClassNotFoundException(e.getMessage(),e.getCause());
        } catch ( SQLException e) {
            throw new SQLException(e.getMessage(),e.getSQLState(),e.getErrorCode(),e.getCause());
        } finally{
            this.close();
        }
        return materias;
    }
    public Set<Producte> selectProductes(int idMateria)throws ClassNotFoundException,SQLException{
        Set<Producte> productesSet = new HashSet<>();
        String query;
        ProducteDAO producteDAOObj;
        try {
            c=ConnectionFactory.getInstance().getConnection();
            query="SELECT " + ContractMateriaProducte.ID_PRODUCTE + " "
                + "FROM " +ContractMateriaProducte.NOM_TAULA + " "
                + "WHERE " +ContractMateriaProducte.ID_MATERIA + " = ?";
            ps=c.prepareStatement(query);
            ps.setInt(1, idMateria);
            rs=ps.executeQuery();
            if(rs.next()){
                producteDAOObj=new ProducteDAO();
                while(rs.next()){
                    productesSet.add(producteDAOObj.select(rs.getInt(ContractMateriaProducte.ID_PRODUCTE)));
                }
            }
        } catch (ClassNotFoundException e){
            throw new ClassNotFoundException(e.getMessage(), e.getCause());
        } catch( SQLException e) {
            throw new SQLException(e.getMessage(), e.getSQLState(), e.getErrorCode(), e.getCause());
        } finally{
            this.close();
        }
        return productesSet;
    }
    private void close(){
        if(this.c!=null){
            try{
                c.close();
                c=null;
            }catch(SQLException e){
                System.err.println(e.getMessage());
            }
        }
        if(this.rs!=null){
            try{
                rs.close();
                rs=null;
            }catch(SQLException e){
                System.err.println(e.getMessage());
            }
        }
        if(this.ps!=null){
            try{
                ps.close();
                ps=null;
            }catch(SQLException e){
                System.err.println(e.getMessage());
            }
        }
    }
}
