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
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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
    @FXML
    private Label lblLoginFail;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try{
            System.out.println(FileSystems.getDefault().getPath(".", "").toAbsolutePath().toString());
            ConnectionFactory.getInstance().configure( FileSystems.getDefault().getPath("src/base", "configLector.txt"));
            
        }catch(IOException e){
            System.err.println(e.getMessage());
        }
        
    }

    @FXML
    private void entrar(ActionEvent event) throws IOException{
       
        try {
            UsuariDAO usrDAO = new UsuariDAO();
            Usuari usr = usrDAO.select(txfEmail.getText());
            if(usr.getId()==-1){
                lblLoginFail.setText("email incorrecte");
            }
            else{
                if(usr.getContrasenya().equals(HashGenerator.generateHash(pwfContrasenya.getText()+usr.getSalt()))){
                    if(usr.isAdmin()){
                        ConnectionFactory.getInstance().configure(FileSystems.getDefault().getPath("src/base", "configBibliotecari"));
                        MenuPrincipalControlador menu = MenuPrincipalControlador.Crear();
                        pare.setFinestraCentre(menu);
                        pare.setLblUsuariText("Login as:"+usr.toString());
                    }
                    else{
                        ConnectionFactory.getInstance().configure( FileSystems.getDefault().getPath("src/base", "configLector.txt"));
                        //falta pantalla menu usuari.
                    }
                }
                else{
                    lblLoginFail.setText("contrasenya incorrecte");
                }
            }
        } catch (SQLException | ClassNotFoundException | IndexOutOfBoundsException | NoSuchAlgorithmException e) {
            System.err.println(e.getMessage());
        }
    
    }
    
}
