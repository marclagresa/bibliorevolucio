/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bibliotecasintesi.principal2MantenimentBiblio;

import bibliotecasintesi.GenericControlador;
import bibliotecasintesi.MenuPrincipalController;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;

/**
 * FXML Controller class
 *
 * @author fcase
 */
public class MantBiblioController extends GenericControlador implements Initializable {
     
    public static MantBiblioController Crear() throws IOException{
        return crearFinestre("MantBiblio.fxml",MantBiblioController.class,"MANTENIMENT");
    
    }
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       
    }    
    
}
