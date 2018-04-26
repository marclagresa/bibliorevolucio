package com.company.DAM2.Bibliorevolució.BBDD.dao;

import com.company.DAM2.Bibliorevolució.BBDD.contract.ContractEditorial;
import com.company.DAM2.Bibliorevolució.BBDD.connector.ConnectionFactory;
import com.company.DAM2.Bibliorevolució.BBDD.contract.ContractEditorial;
import com.company.DAM2.Bibliorevolució.BBDD.dao.IObjectDAO;
import com.company.DAM2.Bibliorevolució.objecte.Editorial;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * @author sergiclotas
 */

public class EditorialDAO implements IObjectDAO<Editorial> {
    
    private Connection c;
    private Statement st;
    private ResultSet rs;
    private PreparedStatement pst;

    public EditorialDAO() {
        c=null;
        st=null;
        rs=null;
        pst=null;
    }

    @Override
    public boolean update(Editorial e) throws SQLException,ClassNotFoundException{
        boolean updated=false;
        String query;
        
        try{
            c= ConnectionFactory.getConnection();
            query="UPDATE " + ContractEditorial.NOM_TAULA + " SET "
                + ContractEditorial.NOM + " = ?, "
                + ContractEditorial.PAIS + " = ?, "
                + ContractEditorial.ADRECA + " = ?"
                + "WHERE " + ContractEditorial.ID + " = ?";
            pst=c.prepareStatement(query);
            pst.setString(1, e.getNom());
            pst.setString(2, e.getPais());
            pst.setString(3, e.getAdreca());
            pst.setInt(4, e.getId());
            updated=pst.executeUpdate() == 1;
        } catch(SQLException | ClassNotFoundException ex){
            System.out.println(ex.getMessage());
        } finally {
            this.close();
        }
        
        return updated;
    }

    /**
     * Funcio que retorna una llista de totes les editorials
     * @return 
     */
    @Override
    public List<Editorial> selectAll() {
        List <Editorial> editorials= new ArrayList<>();
        Editorial editorial;
        String q,nom,adreca,pais;
        int id;
        
        try {
            c=ConnectionFactory.getConnection();
            q= "SELECT "+ContractEditorial.ID + ", "
                + ContractEditorial.NOM + ", "
                + ContractEditorial.ADRECA + ", "
                + ContractEditorial.PAIS  
            + " FROM "+ContractEditorial.NOM_TAULA  
            + " ORDER BY "+ContractEditorial.ID;
            st=c.createStatement();
            rs=st.executeQuery(q);
            while(rs.next()){
                nom=rs.getString(ContractEditorial.NOM);
                pais=rs.getString(ContractEditorial.PAIS);
                adreca=rs.getString(ContractEditorial.ADRECA);
                id=rs.getInt(ContractEditorial.ID);
                if(nom==null){
                    nom="";
                }
                if(adreca==null){
                    adreca="";
                }
                if(pais==null){
                    pais="";
                }
                editorial=new Editorial(
                    id,
                    nom,
                    adreca,
                    pais
                );
                editorials.add(editorial);
            }
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("aaaa"+e.getMessage());
        } finally{
            this.close();
        }
        return editorials;
    }
   
    
    /**
     * Funció per esborarr una editorial de la BBDD
     * @param e Editorial a borrar
     * @return cert si s' ha pogut eliminar altrament retorna fals
     * @throws  SQLException 
     * @throws ClassNotFoundException
     */
    @Override
    public boolean delete(Editorial e) throws SQLException,ClassNotFoundException{
        boolean deletejat = false;
        String query;
        try {
            c= ConnectionFactory.getConnection();
            query=" DELETE FROM " + ContractEditorial.NOM_TAULA 
                + " WHERE " + ContractEditorial.ID + " = ?";
            pst=c.prepareStatement(query);
            pst.setInt(1, e.getId());
            deletejat = pst.executeUpdate() == 1;
        } catch (SQLException ex) {
            throw new SQLException(ex.getMessage(),ex.getSQLState(),ex.getErrorCode(),ex.getCause());
        
        } catch(ClassNotFoundException ex){
            throw new ClassNotFoundException(ex.getMessage(), ex.getCause());
        } 
        finally{
            this.close();
        }
        return deletejat;
    }
    
    /**
     * Funcio per afegir una nova editorial
     * @param e nova editorial a inserir
     * @return true si s' ha pogut afegit fals si hi ha hagut algun error.
     * @throws ClassNotFoundException si el driver de la conexio està mal implementat
     * @throws SQLException si l' id de l' editorial rebut per parametre esta a la BBDD o hi ha algun error amb la conexio.
     */
     @Override
    public boolean insert(Editorial e) throws ClassNotFoundException,SQLException {
        boolean inserit=false;
        String query;
        try {
            c=ConnectionFactory.getConnection();
            query="INSERT INTO "+ContractEditorial.NOM_TAULA + " ("
                    + ContractEditorial.ID + ", "
                    + ContractEditorial.NOM + ", "
                    + ContractEditorial.PAIS + ", "
                    + ContractEditorial.ADRECA + " ) "
            + "VALUES (?,?,?,?)";
            pst=c.prepareStatement(query);
            pst.setInt(1, e.getId());
            
            if(e.getNom().equals("")){
                pst.setString(2,null);
            }
            else{
                pst.setString(2,e.getNom());
            }
            if(e.getAdreca().equals("")){
                pst.setString(4, null);
            }
            else{
                pst.setString(4, e.getAdreca());
            }
            if(e.getPais().equals("")){
                pst.setString(3, null);
            }
            else{
                pst.setString(3, e.getPais());
            }
            inserit=pst.executeUpdate()==1;
        } catch (ClassNotFoundException ex) {
            throw new ClassNotFoundException(ex.getMessage(),ex.getCause());
        } catch (SQLException ex){
            throw new SQLException (ex.getMessage(),ex.getSQLState(),ex.getErrorCode(),ex.getCause());
        } 
        finally{
            this.close();
        }
        
        return inserit;
    }
    
    /**
     * Retorna una llista amb totes les editorial que coinicideix amb la rebuda per paràmetre
     * @param e Editorial que te els valors de busqueda
     * @return List of Editorial
     * @throws SQLException si hi ha hagut algún problema al conectarse a la BBDD o al executar la query
     * @throws ClassNotFoundException Si no s' ha pogut carregar el driver jdbc
     */
    @Override 
    public List<Editorial> select(Editorial e)throws SQLException,ClassNotFoundException{
        List<Editorial> editorials=new ArrayList<>();
        Editorial objEditorial;
        try {
            c=ConnectionFactory.getConnection();
            pst=c.prepareStatement("SELECT * FROM " + ContractEditorial.NOM_TAULA 
                    + "WHERE " + ContractEditorial.NOM + "LIKE '%?%' "
                    + "AND " + ContractEditorial.PAIS + "LIKE '%?%' "
                    + "AND " + ContractEditorial.ADRECA + "LIKE '%?%' "
                );
            pst.setString(1, e.getNom());
            pst.setString(2, e.getPais());
            pst.setString(3, e.getAdreca());
            rs=pst.executeQuery();
            while(rs.next()){
                objEditorial= new Editorial(
                    rs.getInt(ContractEditorial.ID), 
                    rs.getString(ContractEditorial.NOM),
                    rs.getString(ContractEditorial.PAIS),
                    rs.getString(ContractEditorial.ADRECA)
                );
                editorials.add(e);
            }
        } catch (SQLException ex) {
            throw new SQLException(ex.getMessage(), ex.getSQLState() , ex.getErrorCode(), ex.getCause());
        } catch(ClassNotFoundException ex){
            throw new ClassNotFoundException(ex.getMessage(), ex.getCause());
        }finally{
            this.close();
        }
        
        return editorials;
    }
    
    
    /**
     * Funció per seleccionar un nou id
     * @return return MAX id + 1 from taula editorial.
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    @Override
    public int nextId()throws SQLException,ClassNotFoundException{
        int id=1;
        try {
            c= ConnectionFactory.getConnection();
            st=c.createStatement();
            rs=st.executeQuery("SELECT MAX("+ContractEditorial.ID+") FROM "+ContractEditorial.NOM_TAULA);
            if(rs.next()){
                id=rs.getInt(1) + 1;
            }
        } catch (SQLException ex) {
            throw new SQLException(ex.getMessage(),ex.getSQLState(),ex.getErrorCode(),ex.getCause());
        } catch(ClassNotFoundException ex){
            throw new ClassNotFoundException(ex.getMessage(),ex.getCause());
        } 
        finally{
            this.close();
        }
        return id;
    }
    
    /**
     * Métode per tancar les conexions obertes amb la BBDD.
     */
    public void close(){
        
        if(this.c!=null){
            try {
                this.c.close();
                this.c=null;
            } catch (SQLException ex) {
                
            }
        }
        if(this.st!=null){
            try {
                this.st.close();
                this.st=null;
            } catch (SQLException ex) {
                
            }
            
        }
        if(this.pst!=null){
            try {
                this.pst.close();
                this.pst=null;
            } catch (SQLException ex) {
                
            }
        }
        if(this.rs!=null){
            try{
                this.rs.close();
                this.rs=null;
            } catch (SQLException ex) {
                
            }
        }
    }
    
}
