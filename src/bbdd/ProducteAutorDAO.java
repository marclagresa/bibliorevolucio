package bbdd;

import base.ConnectionFactory;
import contract.ContractProductePersona;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;



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
    
    public boolean insert(int idProducte,int idPersona,String descripcio,int idBiblioteca){
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
        }catch(ClassNotFoundException | SQLException e){
            System.err.println(e.getMessage());
        }
        return inserit;
    }
    public static void main(String[] args) throws SQLException, ClassNotFoundException {

    }
}
