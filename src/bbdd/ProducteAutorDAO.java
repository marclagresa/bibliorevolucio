package bbdd;

import base.ConnectionFactory;
import contract.ContractProductePersona;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.HashSet;
import java.util.Set;
import objecte.Persona;
import objecte.Producte;



/**
 * @author AlbertCorominas
 */

public class ProducteAutorDAO {
    private Connection conn;
    private ResultSet rs;
    private PreparedStatement ps;

    public ProducteAutorDAO(){
        conn=null;
        rs=null;
        ps=null;
    }
    public boolean delete(int idProducte,int idPersona)throws SQLException,ClassNotFoundException{
        boolean deleted=false;
        String query;
        try{
            conn=ConnectionFactory.getInstance().getConnection();
            query="DELETE FROM " + ContractProductePersona.NOM_TAULA + " "
                + "WHERE " + ContractProductePersona.ID_PERSONA + " = ? "
                + "AND " + ContractProductePersona.ID_PRODUCTE + " = ? ";
            ps=conn.prepareStatement(query);
            ps.setInt(1, idPersona);
            ps.setInt(2, idProducte);
            deleted=ps.executeUpdate()==1;
            
        } catch(SQLException e){
            throw new SQLException (e.getMessage(),e.getSQLState(),e.getErrorCode(),e.getCause());
        } catch(ClassNotFoundException e){
            throw new ClassNotFoundException(e.getMessage(), e.getCause());
        }finally{
            this.close();
        }
        return deleted;
    }
    public Set<Persona> selectPersones(int idProducte,String descripcio)throws SQLException,ClassNotFoundException{
        Set <Persona> persones=new HashSet<>();
        String query;
        try{
            conn=ConnectionFactory.getInstance().getConnection();
            query="SELECT " +ContractProductePersona.ID_PERSONA +" "
                + "FROM " +ContractProductePersona.NOM_TAULA + " "
                + "WHERE " +ContractProductePersona.ID_PRODUCTE+ " = ? ";
            if(descripcio!=null){
                query+= " AND " + ContractProductePersona.DESCRIPCIO + " LIKE ?";
            }
            ps=conn.prepareStatement(query);
            ps.setInt(1, idProducte);
            if(descripcio!=null){
                ps.setString(2, "%"+descripcio+"%");
            }
            rs=ps.executeQuery();
            while(rs.next()){
                persones.add(new PersonaDAO().select(rs.getInt(ContractProductePersona.ID_PERSONA)));
            }
        }catch(SQLException e){
            throw new SQLException(e.getMessage(),e.getSQLState(),e.getErrorCode(),e.getCause());
        }catch(ClassNotFoundException e){
            throw new ClassNotFoundException(e.getMessage(), e.getCause());
        }finally{
            this.close();
        }
        return persones;
    }
    public Set<Producte> selectProductes(int idPersona,String descripcio)throws SQLException,ClassNotFoundException{
        Set <Producte> productes = new HashSet<>();
        String query;
        try {
            conn=ConnectionFactory.getInstance().getConnection();
            query="SELECT " + ContractProductePersona.ID_PRODUCTE + " WHERE "
                + ContractProductePersona.ID_PERSONA + " = ? ";
            if(descripcio!=null){
                query+= " AND " +ContractProductePersona.DESCRIPCIO + " LIKE ?";
            }
            ps=conn.prepareStatement(query);
            ps.setInt(1, idPersona);
            if(descripcio!=null){
                ps.setString(2, "%" + descripcio + "%");
            }
            rs=ps.executeQuery();
            while(rs.next()){
                productes.add(new ProducteDAO().select(rs.getInt(ContractProductePersona.ID_PRODUCTE)));
            }
        } catch (SQLException e) {
            throw new SQLException(e.getMessage(),e.getSQLState(),e.getErrorCode(),e.getCause());
        } catch(ClassNotFoundException e){
            throw new ClassNotFoundException(e.getMessage(),e.getCause());
        }finally{
            this.close();
        }
        return productes;
        
    }
    public boolean insert(int idProducte,int idPersona,String descripcio,int idBiblioteca)throws SQLException,ClassNotFoundException{
        boolean inserit = false;
        String query;
        try{
            conn=ConnectionFactory.getInstance().getConnection();
            query="INSERT INTO "+ContractProductePersona.NOM_TAULA+" ( "
                + ContractProductePersona.ID_PERSONA+ ", "
                +ContractProductePersona.ID_PRODUCTE+ ", "
                +ContractProductePersona.DESCRIPCIO +") VALUES (?,?,?)";
            ps=conn.prepareStatement(query);
            ps.setInt(1, idPersona);
            ps.setInt(2, idProducte);
            if(descripcio==null||descripcio.isEmpty()){
                ps.setNull(3, Types.VARCHAR);
            }
            else{
                ps.setString(3, descripcio);
            }
            inserit=ps.executeUpdate()==1;
        }catch(SQLException e){
            throw new SQLException(e.getMessage(), e.getSQLState(), e.getErrorCode(), e.getCause());
        }catch(ClassNotFoundException e){
            throw new ClassNotFoundException (e.getMessage(),e.getCause());
        }finally{
            this.close();
        }
        return inserit;
    }
    public void close(){
        if(this.conn!=null){
            try {
                this.conn.close();
                this.conn=null;
            } catch (SQLException ex) {
                System.err.println(ex.getMessage());
            }
        }
        if(this.ps!=null){
            try {
                this.ps.close();
                this.ps=null;
            } catch (SQLException ex) {
                System.err.println(ex.getMessage());
            }
        }
        if(this.rs!=null){
            try{
                this.rs.close();
                this.rs=null;
            } catch (SQLException ex) {
                System.err.println(ex.getMessage());
            }
        }
    }
}
