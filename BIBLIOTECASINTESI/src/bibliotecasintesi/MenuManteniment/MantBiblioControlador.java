/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bibliotecasintesi.MenuManteniment;

import bibliotecasintesi.GenericControlador;
import bibliotecasintesi.MenuPrincipal.MenuPrincipalControlador;
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
public class MantBiblioControlador extends GenericControlador implements Initializable {
     
    public static MantBiblioControlador Crear() throws IOException{
        return crearFinestre("FXMLMantBiblio.fxml",MantBiblioControlador.class,"MANTENIMENT");
    
    }
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       
    }    
    
}
