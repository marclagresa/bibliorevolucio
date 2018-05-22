/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package base;

import base.GenericControlador;
import base.MenuPrincipalControlador;
import base.maintenanceControladors.CduMaintenanceControlador;
import base.maintenanceControladors.ColeccioMaintenanceControlador;
import base.maintenanceControladors.EditorialMaintenanceControlador;
import base.maintenanceControladors.FormatMaintenanceControlador;
import base.maintenanceControladors.PersonaMaintenanceControlador;
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
    private void obrirMantenimentUsuaris(MouseEvent event) throws IOException {
         GenericMaintenanceControlador finestra = GenericMaintenanceControlador.crearFinestre( new UsuariMaintenanceControlador(), "Manteniment Usuaris" );
         pare.setFinestraCentre( finestra );
    }

    @FXML
    private void obrirMantenimentAutor(MouseEvent event) {
        GenericMaintenanceControlador finestra;
        try {
            finestra = GenericMaintenanceControlador.crearFinestre( new PersonaMaintenanceControlador(), " Autors" );
             pare.setFinestraCentre( finestra );
        } catch (IOException ex) {
            Logger.getLogger(MenuPrincipalControlador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void obrirMantenimentEditorial(MouseEvent event) {
      GenericMaintenanceControlador finestra;
        try {
            finestra = GenericMaintenanceControlador.crearFinestre( new EditorialMaintenanceControlador(), " Editrorial" );
             pare.setFinestraCentre( finestra );
        } catch (IOException ex) {
            Logger.getLogger(MenuPrincipalControlador.class.getName()).log(Level.SEVERE, null, ex);
        } 
        
    }

    @FXML
    private void obrirMantenimentProducte(MouseEvent event) {
        GenericMaintenanceControlador finestra;
        try {
            finestra = GenericMaintenanceControlador.crearFinestre( new ProducteMaintenanceControlador(), " Productes" );
             pare.setFinestraCentre( finestra );
        } catch (IOException ex) {
            Logger.getLogger(MenuPrincipalControlador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void obrirMantenimentColecions(MouseEvent event) {
        GenericMaintenanceControlador finestra;
        try {
            finestra = GenericMaintenanceControlador.crearFinestre( new ColeccioMaintenanceControlador(), "Col·leccions" );
             pare.setFinestraCentre( finestra );
        } catch (IOException ex) {
            Logger.getLogger(MenuPrincipalControlador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void obrirMantenimentCdu(MouseEvent event) {
        GenericMaintenanceControlador finestra;
        try {
            finestra = GenericMaintenanceControlador.crearFinestre( new CduMaintenanceControlador(), " CDU " );
             pare.setFinestraCentre( finestra );
        } catch (IOException ex) {
            Logger.getLogger(MenuPrincipalControlador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void obrirMantenimentFormat(MouseEvent event) {
        GenericMaintenanceControlador finestra;
        try {
            finestra = GenericMaintenanceControlador.crearFinestre( new FormatMaintenanceControlador(), " Formats " );
             pare.setFinestraCentre( finestra );
        } catch (IOException ex) {
            Logger.getLogger(MenuPrincipalControlador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
