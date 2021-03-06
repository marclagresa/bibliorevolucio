/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package base;

import base.GenericControlador;
import base.MantBiblioControlador;
import base.PantallaPrincipalControlador;
import base.maintenanceControladors.ProducteMaintenanceControlador;
import base.maintenanceControladors.UsuariMaintenanceControlador;
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

/** * FXML Controller class
 *
 * @author fcase
 */
public class MenuPrincipalControlador extends GenericControlador implements Initializable {

   
    public static MenuPrincipalControlador Crear()throws IOException{
        return crearFinestre("/fxml/FXMLmenuPrincipal.fxml",MenuPrincipalControlador.class,"Menú Principal");
    }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void SubmenuManteniment(MouseEvent event) {
      try {
         MantBiblioControlador   menuPrincipal = MantBiblioControlador.Crear();
            pare.setFinestraCentre(menuPrincipal);
        } 
      catch (IOException ex) {
            Logger.getLogger(PantallaPrincipalControlador.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    @FXML
    private void ClicMantBiblioteca(MouseEvent event) throws IOException {
    
        
    }


    @FXML
    private void SubmenuPrestecs(MouseEvent event) {
         
        
           
    }

    @FXML
    private void SubmenuProductes(MouseEvent event) {
         GenericMaintenanceControlador finestra;
        try {
            finestra = GenericMaintenanceControlador.crearFinestre( new ProducteMaintenanceControlador(), "Productes!" );
             pare.setFinestraCentre( finestra );
        } catch (IOException ex) {
            Logger.getLogger(MenuPrincipalControlador.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
}
