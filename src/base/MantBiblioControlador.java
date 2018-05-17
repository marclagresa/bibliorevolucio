/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package base;

import base.GenericControlador;
import base.MenuPrincipalControlador;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class
 *
 * @author fcase
 */
public class MantBiblioControlador extends GenericControlador implements Initializable {
     
    public static MantBiblioControlador Crear() throws IOException{
        return crearFinestre("/fxml/FXMLMantBiblio.fxml",MantBiblioControlador.class,"MANTENIMENT");
    
    }
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       
    }    

    @FXML
    private void SubmenuMantenimentMes(MouseEvent event) {
          try {
         MantBiblioControlador2   menuPrincipal = MantBiblioControlador2.Crear();
            pare.setFinestraCentre(menuPrincipal);
        } 
      catch (IOException ex) {
            Logger.getLogger(PantallaPrincipalControlador.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }

    @FXML
    private void obrirMantenimentUsuaris(MouseEvent event) {
        /* GenericMaintenanceControlador finestra = GenericMaintenanceControlador.crearFinestre( new UsuariMaintenanceControlador(), "Manteniment Usuaris" );
         pare.setFinestraCentre( finestra );*/
    }
    
}
