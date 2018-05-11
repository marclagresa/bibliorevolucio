package base;

import bbdd.NivellDAO;
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
import objecte.Nivell;

/**
 * FXML Controller class
 *
 * @author AdriaLlop
 */
public class FXMLNivellController extends GenericPopUp implements Initializable {

    static TipusAccio tipusA;
    
    @FXML
    private Label lblNivell;
    @FXML
    private TextField tfNivell;
    @FXML
    private Button btnCrearNivell;
    
    public static FXMLNivellController crear(Window owner, boolean isModal, TipusAccio tipus) throws IOException{  
        
        tipusA = tipus;
        
        return crearPopUp("FXMLNivell.fxml", FXMLNivellController.class, owner, isModal, tipus);
    }
    
    @FXML
    public void crearNivell(){
        
        Nivell nivell = null;
        
        try {
            String nomNivell = tfNivell.getText();
            
            NivellDAO nivellDAO = new NivellDAO();
            
            int id = nivellDAO.nextId();
            
            nivell = new Nivell(id, nomNivell);                    
            nivellDAO.insert(nivell);
            
            nivellDAO.close();
            
        } catch (ClassNotFoundException ex) {
            System.out.println("ClassNotFoundException: "+ex.getMessage());
        } catch (SQLException ex) {
            System.out.println("SQLException: "+ex.getMessage());
        }finally{
            if(onAcceptCallBack!= null){
                onAcceptCallBack.accept(nivell);
            }
        }
        
        btnCrearNivell.setOnAction((ActionEvent event1) -> {
            ((Stage) (btnCrearNivell.getScene().getWindow())).close();
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
    
    
    /*void setParentController(FXMLObraController pare) {
        
        obraPare = pare;
    }*/

    @Override
    public void emplenarDades(Object obj) {
        
        Nivell nivell;
        NivellDAO objNivellDAO = new NivellDAO();
        nivell = (Nivell) obj;
        
        if (nivell!=null){            
            tfNivell.setText(nivell.getNom());
            btnCrearNivell.setText("Modificar");            
        }    
    }
   
}