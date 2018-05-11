package base;

import bbdd.ColeccioDAO;
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
import javafx.stage.Window;
import objecte.Coleccio;

/**
 * FXML Controller class
 *
 * @author AdriaLlop
 */
public class FXMLColeccioController extends GenericPopUp implements Initializable {
    
    static TipusAccio tipusA;
   
    @FXML
    private TextField tfColeccio;
    @FXML
    private Button btnCrearColeccio;
    @FXML
    private Label lblColeccio;

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       
    }    
    
    public static FXMLColeccioController crear(Window owner, boolean isModal, TipusAccio tipus) throws IOException{        
        
        tipusA = tipus;
        
        return crearPopUp("FXMLColeccio.fxml", FXMLColeccioController.class, owner, isModal, tipus);
    }

    @FXML
    private void crearColeccio(ActionEvent event) {
        
        Coleccio coleccio = null;
        
        try {
            String nomCDU = tfColeccio.getText();
            
            ColeccioDAO coleccioDAO = new ColeccioDAO();
            
            int id = coleccioDAO.nextId();
            
            coleccio = new Coleccio(id, nomCDU);                    
            coleccioDAO.insert(coleccio);
            
            coleccioDAO.close();
            
        } catch (ClassNotFoundException ex) {
            System.out.println("ClassNotFoundException: "+ex.getMessage());
        } catch (SQLException ex) {
            System.out.println("SQLException: "+ex.getMessage());
        }finally{           
            if(onAcceptCallBack!= null){
                onAcceptCallBack.accept(coleccio);
            }
        }
    }

    @Override
    public void emplenarDades(Object obj) {
        
        Coleccio coleccio;
        ColeccioDAO objColeccioDAO = new ColeccioDAO();
        coleccio = (Coleccio) obj;        

        if (coleccio!=null){
            tfColeccio.setText(coleccio.getNom());
            btnCrearColeccio.setText("Modificar");            
        }    
    }
}