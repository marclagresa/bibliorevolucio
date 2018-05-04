/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package base;

import static base.GenericControlador.crearFinestre;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;

/**
 * FXML Controller class
 *
 * @author fcase
 */
public class MenuPrincipalUsuariControlador extends GenericControlador implements Initializable {
public static MenuPrincipalUsuariControlador Crear() throws IOException{
        return crearFinestre("/fxml/FXMLMenuPrincipalUsuari.fxml",MenuPrincipalUsuariControlador.class,"Menú Principal");
    
    }
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
