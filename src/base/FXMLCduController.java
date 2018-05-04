package base;

import bbdd.CduDAO;
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
import objecte.Cdu;

/**
 * FXML Controller class
 *
 * @author AdriaLlop
 */
public class FXMLCduController extends GenericPopUp implements Initializable {
    
    @FXML
    private Label lblCDU;
    @FXML
    private TextField tfCDU;
    @FXML
    private Button btnCrearCDU;

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    public static FXMLCduController crear(Window owner, boolean isModal) throws IOException{        
        
        return crearPopUp("/fxml/FXMLCdu.fxml", FXMLCduController.class, owner, isModal);
    }

    @FXML
    private void crearCDU(ActionEvent event) {
        
        Cdu cdu = null;
        
        try {
            String nomCDU = tfCDU.getText();
            
            CduDAO cduDAO = new CduDAO();
            
            int id = cduDAO.nextId();
            
            cdu = new Cdu(id,1, nomCDU);                    
            cduDAO.insert(cdu);
            
            cduDAO.close();
            
        } catch (ClassNotFoundException ex) {
            System.out.println("ClassNotFoundException: "+ex.getMessage());
        } catch (SQLException ex) {
            System.out.println("SQLException: "+ex.getMessage());
        }finally{
            //obraPare.actualitzarCDU();
            if(onAcceptCallBack!= null){
                onAcceptCallBack.accept(cdu);
            }
        }       
    }
}