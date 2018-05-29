package base;

import bbdd.IdiomaDAO;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.Window;
import objecte.Idioma;

/**
 * FXML Controller class
 *
 * @author AdriaLlop
 */
public class FXMLIdiomaController extends GenericPopUp implements Initializable {

    static TipusAccio tipusA;
    static Idioma idiomaRebut;
    
    @FXML
    private Label lblIdioma;
    @FXML
    private TextField tfNomIdioma;
    @FXML
    private Button btnCrearIdioma;
    
    public static FXMLIdiomaController crear(Window owner, boolean isModal, TipusAccio tipus) throws IOException{        
        
        tipusA = tipus;
        
        return crearPopUp("/fxml/FXMLIdioma.fxml", FXMLIdiomaController.class, owner, isModal, tipus);
    }
    @FXML
    private Button btnCancelar;

    @FXML
    public void crearIdioma(){
        
        Idioma idioma = null;
        int id = -1;
        String nomIdioma = tfNomIdioma.getText();
        IdiomaDAO idiomaDAO = new IdiomaDAO();
        
        switch(tipusA){
            case Crear:
                try {
                    id = idiomaDAO.nextId();
                    idioma = new Idioma(id, nomIdioma);
                    idiomaDAO.insert(idioma);

                } catch (SQLException  ex) {
                    System.out.println("Exception: "+ex.getMessage());
                } catch (ClassNotFoundException ex){
                    System.out.println(ex.getMessage());
                }finally{
                   if(onAcceptCallBack!= null){
                        onAcceptCallBack.accept(idioma);
                    }     
                }
                break;
            case Modificar:
                id = idiomaRebut.getId();
                idioma = new Idioma(id, nomIdioma);
        
                try {
                    idiomaDAO.update(idioma);
                } catch (SQLException  ex) {
                    System.out.println("Exception: "+ex.getMessage());
                } catch (ClassNotFoundException ex){
                    System.out.println(ex.getMessage());
                }finally{
                   if(onAcceptCallBack!= null){
                        onAcceptCallBack.accept(idioma);
                    }
                }      
                break;
            case Deshabilitar:
                idiomaRebut.setActiva(false);                
          
                try {
                    idiomaDAO.update(idiomaRebut);
                } catch (SQLException  ex) {
                    System.out.println("Exception: "+ex.getMessage());
                } catch (ClassNotFoundException ex){
                    System.out.println(ex.getMessage());
                }        
                break;
        }
        
        ((Stage) (btnCrearIdioma.getScene().getWindow())).close();
    }
    
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       
    }     

    @Override
    public void emplenarDades(Object obj) {
        
        Idioma idioma;
        idioma = (Idioma) obj;
        
        if (idioma!=null){            
            tfNomIdioma.setText(idioma.getNom());
            
            switch(tipusA){
                case Modificar:
                    btnCrearIdioma.setText("Modificar");     
                    break;
                case Deshabilitar:
                    btnCrearIdioma.setText("Deshabilitar");     
                    break;
                case Buscar:
                    btnCrearIdioma.setText("Buscar");
                    btnCrearIdioma.setDisable(true);
                    break;
            }         
        }   
    }
    
    @FXML
    public void cancelar(){
       ((Stage) (btnCancelar.getScene().getWindow())).close();
    }
}