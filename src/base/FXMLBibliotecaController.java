package base;

import base.GenericPopUp.TipusAccio;
import static base.GenericPopUp.crearPopUp;
import bbdd.BibliotecaDAO;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.Window;
import objecte.Biblioteca;

/**
 * FXML Controller class
 *
 * @author AdriaLlop
 */
public class FXMLBibliotecaController extends GenericPopUp implements Initializable {

    static TipusAccio tipusA;
    static Biblioteca bibliotecaRebuda;
    
    @FXML
    private Label lblBiblioteca;
    @FXML
    private TextField tfBiblioteca;
    @FXML
    private Button btnCrearBiblioteca;
    @FXML
    private Button btnCancelar;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    public static FXMLBibliotecaController crear(Window owner, boolean isModal, TipusAccio tipus) throws IOException{        
        tipusA = tipus;
        
        return crearPopUp("/fxml/FXMLBiblioteca.fxml", FXMLBibliotecaController.class , owner, isModal, tipus);
    }

    @FXML
    private void crearBiblioteca(ActionEvent event) {
        
        Biblioteca biblioteca = null;
        int id = -1;
        String nomBiblioteca = tfBiblioteca.getText();
        BibliotecaDAO bibliotecaDAO = new BibliotecaDAO();
        
        switch(tipusA){
            case Crear:
                try {
                    id = bibliotecaDAO.nextId();
                    biblioteca = new Biblioteca(id, nomBiblioteca);
                    bibliotecaDAO.insert(biblioteca);

                } catch (SQLException  ex) {
                    System.out.println("Exception: "+ex.getMessage());
                } catch (ClassNotFoundException ex){
                    System.out.println(ex.getMessage());
                }finally{
                   if(onAcceptCallBack!= null){
                        onAcceptCallBack.accept(biblioteca);
                    }     
                }
                break;
            case Modificar:
                id = bibliotecaRebuda.getId();
                biblioteca = new Biblioteca(id, nomBiblioteca);
        
                try {
                    bibliotecaDAO.update(biblioteca);
                } catch (SQLException  ex) {
                    System.out.println("Exception: "+ex.getMessage());
                } catch (ClassNotFoundException ex){
                    System.out.println(ex.getMessage());
                }finally{
                   if(onAcceptCallBack!= null){
                        onAcceptCallBack.accept(biblioteca);
                    }
                }      
                break;
            case Deshabilitar:
                bibliotecaRebuda.setActiva(false);                
           
                try {
                    bibliotecaDAO.update(bibliotecaRebuda);
                } catch (SQLException  ex) {
                    System.out.println("Exception: "+ex.getMessage());
                } catch (ClassNotFoundException ex){
                    System.out.println(ex.getMessage());
                }
        
                break;
        }
        
        ((Stage) (btnCrearBiblioteca.getScene().getWindow())).close();
    }

    @Override
    public void emplenarDades(Object obj) {

        Biblioteca biblioteca;
        biblioteca = (Biblioteca) obj;    
        
        if (biblioteca!=null){
            bibliotecaRebuda = biblioteca;
            tfBiblioteca.setText(biblioteca.getNom());
            
            switch(tipusA){
                case Modificar:
                    btnCrearBiblioteca.setText("Modificar");     
                    break;
                case Deshabilitar:
                    btnCrearBiblioteca.setText("Deshabilitar");     
                    break;
                case Buscar:
                    btnCrearBiblioteca.setText("Buscar");
                    btnCrearBiblioteca.setDisable(true);
                    break;
            }          
        }
    }    

    @FXML
    private void cancelar(ActionEvent event) {
        ((Stage) (btnCancelar.getScene().getWindow())).close();
    }
}