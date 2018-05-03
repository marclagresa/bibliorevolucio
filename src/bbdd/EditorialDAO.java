/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bbdd;

import objecte.Editorial;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import base.ConnectionFactory;
import contract.ContractEditorial;
import java.util.HashMap;

/**
 *
 * @author sergiclotas
 */
public class EditorialDAO implements IObjectDAO<Editorial>{
    
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

    /**
     * Funcio per actualitzar l' informació d' una editorial.
     * @param e Editorial a actualitzar
     * @return cert si es pot actualitzar altrament retorna fals.
     * @throws ClassNotFoundException
     * @throws SQLException 
     */
    @Override
    public boolean update(Editorial e) throws ClassNotFoundException,SQLException{
        boolean updated=false;
        String query;
        
        try{
            c=ConnectionFactory.getInstance().getConnection();
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
            c=ConnectionFactory.getInstance().getConnection();
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
            c=ConnectionFactory.getInstance().getConnection();
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
     * @param dades Mapa amb els valors que estem buscant 
     * @return List of Editorial
     * @throws SQLException si hi ha hagut algún problema al conectarse a la BBDD o al executar la query
     * @throws ClassNotFoundException Si no s' ha pogut carregar el driver jdbc
     */
    @Override 
    public List<Editorial> select(HashMap <String,Object> dades)throws SQLException,ClassNotFoundException{
        List<Editorial> editorials=new ArrayList<>();
        Editorial objEditorial;
        Object[]valors;
        String query;
        int i;
        try {
            c=ConnectionFactory.getInstance().getConnection();
            valors=new Object[dades.size()];
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
                    valors[i]=dades.get(camp);
                }
                else{
                    query += camp+ " = ?";
                    valors[i]=dades.get(camp);
                }
                i++;
            }
            pst=c.prepareStatement(query);
            for(i=0;i<valors.length;i++){
                pst.setObject(i+1, valors[i]);
            }
            rs=pst.executeQuery();
            while(rs.next()){
                objEditorial= new Editorial(
                    rs.getInt(ContractEditorial.ID), 
                    rs.getString(ContractEditorial.NOM),
                    rs.getString(ContractEditorial.PAIS),
                    rs.getString(ContractEditorial.ADRECA)
                );
                editorials.add(objEditorial);
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
            c=ConnectionFactory.getInstance().getConnection();
            st=c.createStatement();
            rs=st.executeQuery("SELECT MAX("+ContractEditorial.ID +") FROM "+ContractEditorial.NOM_TAULA);
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
     * 
     * @param id
     * @return Editorial
     * @throws ClassNotFoundException
     * @throws SQLException 
     */
    @Override
    public Editorial select(int id) throws ClassNotFoundException, SQLException {
        Editorial objEditorial = new Editorial();
        String query;
        try{
            c=ConnectionFactory.getInstance().getConnection();
            query= "SELECT * FROM "+ContractEditorial.NOM_TAULA 
                 + "WHERE " + ContractEditorial.ID + " = ?";
            pst=c.prepareStatement(query);
            pst.setInt(1, id);
            rs=pst.executeQuery();
            if(rs.next()){
                objEditorial.setId(rs.getInt(ContractEditorial.ID));
                objEditorial.setNom(rs.getString(ContractEditorial.NOM));
                objEditorial.setPais(rs.getString(ContractEditorial.PAIS));
                objEditorial.setAdreca(rs.getString(ContractEditorial.ADRECA));
            }
        } catch(SQLException e){
            throw new SQLException (e.getMessage(),e.getSQLState(),e.getErrorCode(),e.getCause());
        } catch(ClassNotFoundException e){
            throw new ClassNotFoundException (e.getMessage(),e.getCause());
        }
        finally{
            
        }
        return objEditorial;
    }
    
    /**
     * Métode per tancar les conexions obertes amb la BBDD.
     */
    @Override
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