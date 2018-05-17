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
public class MantBiblioControlador2 extends GenericControlador implements Initializable {
public static MantBiblioControlador2 Crear() throws IOException{
        return crearFinestre("/fxml/FXMLMantBiblio2.fxml",MantBiblioControlador2.class,"MANTENIMENT");
    
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
