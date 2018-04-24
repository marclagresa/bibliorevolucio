/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bibliotecasintesi;

import bibliotecasintesi.principal2MantenimentBiblio.MantBiblioController;
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
public class MenuPrincipalController extends GenericControlador implements Initializable {

   
    public static MenuPrincipalController Crear()throws IOException{
        return crearFinestre("menuPrincipal.fxml",MenuPrincipalController.class,"Menú Principal");
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
         MantBiblioController   menuPrincipal = MantBiblioController.Crear();
            pare.setFinestraCentre(menuPrincipal);
        } catch (IOException ex) {
            Logger.getLogger(PantallaPrincipalControlador.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    @FXML
    private void ClicMantBiblioteca(MouseEvent event) {
    }

    @FXML
    private void SubmenuUsuaris(MouseEvent event) {
    }

    @FXML
    private void SubmenuPrestecs(MouseEvent event) {
    }
    
}
