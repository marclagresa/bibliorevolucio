/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package base;

import static base.GenericControlador.crearFinestre;
import base.maintenanceControladors.IdiomaMaintenanceControlador;
import base.maintenanceControladors.MateriaMaintenanceControlador;
import base.maintenanceControladors.NivellMaintenanceControlador;
import base.maintenanceControladors.ProcedenciaMaintenanceControlador;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

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

    private void obrirMantenimentIdioma(KeyEvent event) {
          
    }

    @FXML
    private void obrirMantenimentNivells(MouseEvent event) {
           GenericMaintenanceControlador finestra;
        try {
            finestra = GenericMaintenanceControlador.crearFinestre( new NivellMaintenanceControlador(), " Nivell" );
             pare.setFinestraCentre( finestra );
        } catch (IOException ex) {
            Logger.getLogger(MenuPrincipalControlador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void obrirMantenimentProcedencia(MouseEvent event) {
           GenericMaintenanceControlador finestra;
        try {
            finestra = GenericMaintenanceControlador.crearFinestre( new ProcedenciaMaintenanceControlador(), " Procedéncia" );
             pare.setFinestraCentre( finestra );
        } catch (IOException ex) {
            Logger.getLogger(MenuPrincipalControlador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void obrirMantenimentMaterias(MouseEvent event) {
          
        try { 
            GenericMaintenanceControlador finestra;        
            finestra = GenericMaintenanceControlador.crearFinestre( new MateriaMaintenanceControlador(), " Materies" );
             pare.setFinestraCentre( finestra );
        } catch (IOException ex) {
            Logger.getLogger(MenuPrincipalControlador.class.getName()).log(Level.SEVERE, null, ex);
        }
        
          
    }

    @FXML
    private void obrirMantenimentIdioma(MouseEvent event) {
         GenericMaintenanceControlador finestra;
        try {
            finestra = GenericMaintenanceControlador.crearFinestre( new IdiomaMaintenanceControlador(), " Idioma " );
             pare.setFinestraCentre( finestra );
        } catch (IOException ex) {
            Logger.getLogger(MenuPrincipalControlador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
