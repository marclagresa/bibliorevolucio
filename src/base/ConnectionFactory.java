/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package base;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
/**
 * Classe per obtenir una conexió oberta a la BBDD
 * @author sergiclotas
 */
public class ConnectionFactory {
    private static final String MYSQL_DRIVER="com.mysql.jdbc.Driver";
    private static final String USER="root";
    private static final String PASS="admin";
    /**
     * Per conectarse com a local host descomentar la linia de baix i comentar 
     * la següent 
     * 
     * Per conectarse a la BBDD de l' ordinador de la clase fer operació inversa
     */
    
    //private static final String URL="jdbc:mysql://localhost:3306/bibliorevolucio";
    private static final String URL="jdbc:mysql://172.16.201.11:3306/bibliorevolucio";
    
    
    /**
     * Funció static que ens retorna una Conexió oberta a la base de dades
     * @return Connection
     * @throws ClassNotFoundException Si no teniu la llibreria de jdbc mysql afegida al projecte
     * @throws SQLException Si les dades d' usuari password o url són incorrectes.
     */
    public static Connection getConnection() throws ClassNotFoundException,SQLException{
        Connection c = null;
        Class.forName(MYSQL_DRIVER);
        c=DriverManager.getConnection(URL, USER,PASS);
        return c;
    }
}
