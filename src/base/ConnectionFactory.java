/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package base;

import com.sun.deploy.config.Platform;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

/**
 * Classe per obtenir una conexió oberta a la BBDD
 * @author sergiclotas
 */
public class ConnectionFactory {
    private static ConnectionFactory instance=null;
    private String usuari;
    private String contrasenya;
    public String url;
    public String driver;
    private ConnectionFactory(){
        this.usuari=null;
        this.contrasenya=null;
        this.url=null;
        this.driver=null;
    }
    
    public static ConnectionFactory getInstance(){
        if(ConnectionFactory.instance==null){
            ConnectionFactory.instance=new ConnectionFactory();
        }
        return ConnectionFactory.instance;
    }
    public void configure(Path configFile) throws IOException{   
        List<String> liniesFitxer=Files.readAllLines(configFile, Charset.forName("utf-8"));
        liniesFitxer.forEach(linia->{
            String []dadesLinia=linia.split(";");
            switch(dadesLinia[0]){
                case "user":
                    this.usuari=dadesLinia[1];
                    break;
                case "password":
                    this.contrasenya=dadesLinia[1];
                    break;
                case "driver":
                    this.driver=dadesLinia[1];
                    break;
                case "url":
                    this.url=dadesLinia[1];
                    break;
            }
        });
    }
    
    
    /**
     * Funció static que ens retorna una Conexió oberta a la base de dades
     * 
     * @return Connection
     * @throws ClassNotFoundException Si no teniu la llibreria de jdbc mysql afegida al projecte
     * @throws SQLException Si les dades d' usuari password o url són incorrectes.
     */
    
    public Connection getConnection() throws ClassNotFoundException,SQLException{
        Connection c = null;
        Class.forName(driver);
        c=DriverManager.getConnection(url,usuari,contrasenya);
        
        return c;
    }
    
}
