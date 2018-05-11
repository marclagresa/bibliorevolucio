package base;

import bbdd.IdiomaDAO;
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
import objecte.Idioma;

/**
 * FXML Controller class
 *
 * @author AdriaLlop
 */
public class FXMLIdiomaController extends GenericPopUp implements Initializable {

    static TipusAccio tipusA;
    
    @FXML
    private Label lblIdioma;
    @FXML
    private TextField tfNomIdioma;
    @FXML
    private Button btnCrearIdioma;
    
    public static FXMLIdiomaController crear(Window owner, boolean isModal, TipusAccio tipus) throws IOException{        
        
        tipusA = tipus;
        
        return crearPopUp("FXMLIdioma.fxml", FXMLIdiomaController.class, owner, isModal, tipus);
    }

    @FXML
    public void crearIdioma(){
        
        Idioma idioma = null;
        
        try {
            String nomIdioma = tfNomIdioma.getText();
            
            IdiomaDAO idiomaDAO = new IdiomaDAO();
            
            int id = idiomaDAO.nextId();
            
            idioma = new Idioma(id, nomIdioma);                    
            idiomaDAO.insert(idioma);
            
            idiomaDAO.close();
            
        } catch (ClassNotFoundException ex) {
            System.out.println("ClassNotFoundException: "+ex.getMessage());
        } catch (SQLException ex) {
            System.out.println("SQLException: "+ex.getMessage());
        }finally{
            if(onAcceptCallBack!= null){
                onAcceptCallBack.accept(idioma);
            }
        }
        
        btnCrearIdioma.setOnAction((ActionEvent event1) -> {
            ((Stage) (btnCrearIdioma.getScene().getWindow())).close();
        });
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
        IdiomaDAO objIdiomaDAO = new IdiomaDAO();
        idioma = (Idioma) obj;
        
        if (idioma!=null){            
            tfNomIdioma.setText(idioma.getNom());
            btnCrearIdioma.setText("Modificar");            
        }   
    }
}