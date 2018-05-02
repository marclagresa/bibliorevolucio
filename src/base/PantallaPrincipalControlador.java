/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package base;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;

/**
 *
 * @author fcase
 */
public class PantallaPrincipalControlador  implements Initializable {
    
    private Stack<GenericControlador> pilaBtenrere = new Stack<GenericControlador>();
    private GenericControlador activa=null;
    
    @FXML
    public  BorderPane BordPn;
    @FXML
    private Label lbCapcalera;
    @FXML
    private Label lblUsuari;
    @FXML
    private Button btnEnrera;
    public void setLblUsuariText(String text){
        lblUsuari.setText(text);
    }
    public void setFinestraCentre(GenericControlador generic){
        BordPn.setCenter(generic);
        lbCapcalera.setText(generic.getCapcalera());
        
        generic.setPare(this);
        
        if(activa!=null){
            pilaBtenrere.push(activa);
            if(btnEnrera.isDisable()){
                btnEnrera.setDisable(false);
            }
        }
        activa=generic;
    }
    @FXML
    public void anarEnrere(ActionEvent event) {
        if(!pilaBtenrere.empty()){
            activa = null;
            setFinestraCentre(pilaBtenrere.pop());
        }
        if(pilaBtenrere.empty()){
            btnEnrera.setDisable(true);
            lblUsuari.setText("");
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle rb)  {         
       try {
            FXMLLoginController   login = FXMLLoginController.Crear();
            setFinestraCentre(login);
            btnEnrera.setDisable(true);
            lblUsuari.setText("");
        } 
        catch (IOException ex) {
            Logger.getLogger(PantallaPrincipalControlador.class.getName()).log(Level.SEVERE, null, ex);
        }  
        
    }
  
}
