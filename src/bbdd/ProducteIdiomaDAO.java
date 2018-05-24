/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bbdd;

import base.ConnectionFactory;
import contract.ContractProducteIdioma;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;
import objecte.Idioma;
import objecte.Producte;

/**
 *
 * @author sergiclotas
 */
public class ProducteIdiomaDAO {
    Connection c;
    ResultSet rs;
    PreparedStatement ps;
    public ProducteIdiomaDAO(){
        c=null;
        rs=null;
        ps=null;
    }
    public boolean insert(int id_producte,int id_idioma)throws SQLException,ClassNotFoundException{
        boolean inserit=false;
        String query;
        try{
            c=ConnectionFactory.getInstance().getConnection();
            query="INSERT INTO " + ContractProducteIdioma.NOM_TAULA + " ("
                + ContractProducteIdioma.ID_IDIOMA + ", "
                + ContractProducteIdioma.ID_PRODUCTE +") "
                +"VALUES (?,?)";
            ps=c.prepareStatement(query);
            ps.setInt(1, id_idioma);
            ps.setInt(2, id_producte);
            inserit = ps.executeUpdate()==1;
            
        }catch(SQLException e){
            throw new SQLException(e.getMessage(), e.getSQLState(),e.getErrorCode(),e.getCause());
        }catch(ClassNotFoundException e){
            throw new ClassNotFoundException(e.getMessage(), e.getCause());
        } finally{
            this.close();
        }
        return inserit;
    }
    public Set <Producte>selectProductes(int id_idioma)throws SQLException,ClassNotFoundException{
        Set <Producte> productesSet = new HashSet<>();
        ProducteDAO producteDAOObj;
        String query;
        try {
            c=ConnectionFactory.getInstance().getConnection();
            query="SELECT * FROM "+ ContractProducteIdioma.NOM_TAULA + " "
                + "WHERE " +ContractProducteIdioma.ID_IDIOMA + " = ?";
            ps=c.prepareStatement(query);
            ps.setInt(1, id_idioma);
            rs=ps.executeQuery();
            if(rs.next()){
                producteDAOObj=new ProducteDAO();
                productesSet.add(producteDAOObj.select(rs.getInt(ContractProducteIdioma.ID_PRODUCTE)));
                while(rs.next()){
                    productesSet.add(producteDAOObj.select(rs.getInt(ContractProducteIdioma.ID_PRODUCTE)));
                }
            }
        } catch (SQLException e) {
            throw new SQLException(e.getMessage(),e.getSQLState(),e.getErrorCode(),e.getCause());
        } catch(ClassNotFoundException e){
            throw new ClassNotFoundException(e.getMessage(),e.getCause());
        } finally {
            this.close();
        }
        return productesSet;
    }
     public Set <Idioma>selectIdiomes(int id_producte)throws SQLException,ClassNotFoundException{
        Set <Idioma> idiomesSet = new HashSet<>();
        IdiomaDAO idiomaDAOObj;
        
        String query;
        try {
            c=ConnectionFactory.getInstance().getConnection();
            query="SELECT * FROM "+ ContractProducteIdioma.NOM_TAULA + " "
                + "WHERE " +ContractProducteIdioma.ID_PRODUCTE + " = ?";
            ps=c.prepareStatement(query);
            ps.setInt(1, id_producte);
            rs=ps.executeQuery();
            if(rs.next()){
                idiomaDAOObj=new IdiomaDAO();
                idiomesSet.add(idiomaDAOObj.select(rs.getInt(ContractProducteIdioma.ID_IDIOMA)));
                while(rs.next()){
                    idiomesSet.add(idiomaDAOObj.select(rs.getInt(ContractProducteIdioma.ID_IDIOMA)));
                }
            }
        } catch (SQLException e) {
            throw new SQLException(e.getMessage(),e.getSQLState(),e.getErrorCode(),e.getCause());
        } catch(ClassNotFoundException e){
            throw new ClassNotFoundException(e.getMessage(),e.getCause());
        } finally {
            this.close();
        }
        return idiomesSet;
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
