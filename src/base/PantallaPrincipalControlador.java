/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package base;

import base.MenuPrincipalControlador;
import base.MantBiblioControlador;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

/**
 *
 * @author fcase
 */
public class PantallaPrincipalControlador  implements Initializable {
    
    private Stack<GenericControlador> pilaBtenrere = new Stack<GenericControlador>();
    private GenericControlador activa=null;
    private Label label;
    @FXML
    public  BorderPane BordPn;
    @FXML
    private Label lbCapcalera;
 
    public void setFinestraCentre(GenericControlador generic){
        BordPn.setCenter(generic);
        lbCapcalera.setText(generic.getCapcalera());
        
        generic.setPare(this);
        
        if(activa!=null){
            pilaBtenrere.push(activa);
        }
        activa=generic;
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb)  {         
       try {
         MenuPrincipalControlador   menuPrincipal = MenuPrincipalControlador.Crear();
              setFinestraCentre(menuPrincipal);
        } 
        catch (IOException ex) {
            Logger.getLogger(PantallaPrincipalControlador.class.getName()).log(Level.SEVERE, null, ex);
        }  
        
    }  

    @FXML
    private void ButoEnrere(MouseEvent event) {
          
           if(!pilaBtenrere.empty()){
                activa = null;
          setFinestraCentre(pilaBtenrere.pop());
           }
    }
  
}
