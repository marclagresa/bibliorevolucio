/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bbdd;

import base.ConnectionFactory;
import contract.ContractProducteNivell;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;
import objecte.Nivell;
import objecte.Producte;

/**
 *
 * @author sergiclotas
 */
public class ProducteNivellDAO {
    Connection c;
    PreparedStatement ps;
    ResultSet rs;
    public ProducteNivellDAO() {
        c=null;
        ps=null;
        rs=null;
    }
    public Set <Nivell> selectNivells(int idProducte)throws SQLException,ClassNotFoundException{
        Set<Nivell> nivellsSet=new HashSet<>(0);
        NivellDAO nivellDAOObj;
        String query;
        try{
            c=ConnectionFactory.getInstance().getConnection();
            query="SELECT "+ContractProducteNivell.ID_NIVELL + " "
                + "FROM " +ContractProducteNivell.NOM_TAULA + " "
                + "WHERE " +ContractProducteNivell.ID_PRODUCTE + " = ?";
            ps=c.prepareStatement(query);
            ps.setInt(1, idProducte);
            rs=ps.executeQuery();
            if(rs.next()){
                nivellDAOObj = new NivellDAO();
                nivellsSet.add(nivellDAOObj.select(rs.getInt(ContractProducteNivell.ID_PRODUCTE)));
                while(rs.next()){
                    nivellsSet.add(nivellDAOObj.select(rs.getInt(ContractProducteNivell.ID_PRODUCTE)));
                }
            }
        } catch(SQLException e){
            throw new SQLException(e.getMessage(),e.getSQLState(),e.getErrorCode(),e.getCause());
        } catch(ClassNotFoundException e){
            throw new ClassNotFoundException(e.getMessage(), e.getCause());
        } finally{
            this.close();
        }
        return nivellsSet;
            
    }
    public Set <Producte> selectProductes(int idNivell)throws SQLException,ClassNotFoundException{
        Set <Producte> productesSet=new HashSet<>(0);
        ProducteDAO producteDAOObj;
        String query;
        try {
            c=ConnectionFactory.getInstance().getConnection();
            query="SELECT "+ContractProducteNivell.ID_PRODUCTE+" "
                + "FROM " +ContractProducteNivell.NOM_TAULA +" "
                + "WHERE " +ContractProducteNivell.ID_NIVELL + " = ?";
            ps=c.prepareStatement(query);
            ps.setInt(1, idNivell);
            rs=ps.executeQuery();
            if(rs.next()){
                producteDAOObj=new ProducteDAO();
                productesSet.add(producteDAOObj.select(rs.getInt(ContractProducteNivell.ID_PRODUCTE)));
                while(rs.next()){
                    productesSet.add(producteDAOObj.select(rs.getInt(ContractProducteNivell.ID_PRODUCTE)));
                }
            }
        } catch (SQLException e) {
            throw new SQLException(e.getMessage(),e.getSQLState(),e.getErrorCode(),e.getCause());
        } catch(ClassNotFoundException e){
            throw new ClassNotFoundException(e.getMessage(), e.getCause());
        } finally{
            this.close();
        }
        return productesSet;
    }
    public boolean insert(int idProducte,int idNivell)throws SQLException,ClassNotFoundException{
        boolean inserit=false;
        String query;
        try{
            c=ConnectionFactory.getInstance().getConnection();
            query="INSERT INTO " +ContractProducteNivell.NOM_TAULA +" ("
                + ContractProducteNivell.ID_NIVELL + ", "
                + ContractProducteNivell.ID_PRODUCTE +")"
                +" VALUES (?,?)";
            ps=c.prepareStatement(query);
            ps.setInt(1, idNivell);
            ps.setInt(2, idProducte);
            inserit=ps.executeUpdate()==1;
            
        } catch(SQLException e){
            throw new SQLException(e.getMessage(), e.getSQLState(), e.getErrorCode(), e.getCause());
        } catch(ClassNotFoundException e){
            throw new ClassNotFoundException(e.getMessage(),e.getCause());
        } finally{
            this.close();
                    
        }
        return inserit;
    }
    private void close(){
        if(this.c!=null){
            try {
                c.close();
                c=null;
            } catch (SQLException ex) {
                System.err.println(ex.getMessage());
            }
        }
        if(ps!=null){
            try {
                ps.close();
                ps=null;
            } catch (SQLException e) {
                System.err.println(e.getMessage());
            }
        }
        if(rs!=null){
            try{
                rs.close();
                rs=null;
            } catch(SQLException e){
                System.err.println(e.getMessage());
            }
        }
    }
}
