/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package base;

import bbdd.UsuariDAO;
import java.io.IOException;
import java.net.URL;
import java.nio.file.FileSystems;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import objecte.Usuari;

/**
 *
 * @author sergiclotas
 */
public class FXMLLoginController extends GenericControlador implements Initializable{
    
    
    @FXML
    private TextField txfEmail;
    @FXML
    private PasswordField pwfContrasenya;
    @FXML
    private Button btnEntrar;
    @FXML
    private Button btnSortir;
    public static FXMLLoginController Crear()throws IOException{
        return crearFinestre("/fxml/FXMLLogin.fxml",FXMLLoginController.class,"BENVINGUT/DA");
    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try{
            System.out.println(FileSystems.getDefault().getPath(".", "").toAbsolutePath().toString());
            ConnectionFactory.getInstance().configure( FileSystems.getDefault().getPath("src/base", "configLector.txt"));
            
        }catch(IOException e){
            
        }
        
    }

    @FXML
    private void entrar(ActionEvent event) throws IOException{
        UsuariDAO usrDao;
        Usuari usr;
        System.out.println(ConnectionFactory.getInstance().driver);
        System.out.println(ConnectionFactory.getInstance().url);
        try {
            usrDao=new UsuariDAO();
            usr=new Usuari();
            usr.setEmail(txfEmail.getText());
            usr=usrDao.select(usr).get(0);
            
            System.out.println(usr.toString());
            if(usr.isAdmin()){
                MenuPrincipalControlador menu = MenuPrincipalControlador.Crear();
                pare.setFinestraCentre(menu);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        } catch(ClassNotFoundException e ){
            System.err.println("hola poma");
        } catch(IndexOutOfBoundsException e){
            System.err.println(e.getMessage());
        }
    
    }
    
}
