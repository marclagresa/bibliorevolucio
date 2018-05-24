/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package base;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import org.apache.commons.dbcp2.BasicDataSource;

/**
 * Classe per obtenir una conexió oberta a la BBDD
 * @author sergiclotas
 */
public class ConnectionFactory {
    private static ConnectionFactory instance=null;
    private BasicDataSource dataSource;
    private String usuari;
    private String contrasenya;
    private String url;
    private String driver;
    private ConnectionFactory(){
        this.usuari=null;
        this.contrasenya=null;
        this.url=null;
        this.driver=null;
        dataSource=null;
    }
    
    public static ConnectionFactory getInstance(){
        if(ConnectionFactory.instance==null){
            ConnectionFactory.instance=new ConnectionFactory();
        }
        return ConnectionFactory.instance;
    }
    public void configure(Path configFile) throws IOException,SQLException{   
        List<String> liniesFitxer=Files.readAllLines(configFile, Charset.forName("utf-8"));
        if(dataSource!=null){
            dataSource.close();
        }
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
        dataSource=new BasicDataSource();
        dataSource.setDriverClassName(driver);
        dataSource.setUsername(usuari);
        dataSource.setUrl(url);
        dataSource.setPassword(contrasenya);
    }
    
    
    /**
     * Funció static que ens retorna una Conexió oberta a la base de dades
     * 
     * @return Connection
     * @throws ClassNotFoundException Si no teniu la llibreria de jdbc mysql afegida al projecte
     * @throws SQLException Si les dades d' usuari password o url són incorrectes.
     */
    
    public Connection getConnection() throws ClassNotFoundException,SQLException{
        return dataSource.getConnection();
    }
    
}
