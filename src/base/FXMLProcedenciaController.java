package base;

import static base.GenericPopUp.crearPopUp;
import bbdd.ProcedenciaDAO;
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
import objecte.Procedencia;

/**
 * FXML Controller class
 *
 * @author AdriaLlop
 */
public class FXMLProcedenciaController extends GenericPopUp implements Initializable {

    static TipusAccio tipusA;
    
    @FXML
    private Label lblProcedencia;
    @FXML
    private TextField tfProcedencia;
    @FXML
    private Button btnCrearProcedencia;

    /**
     * Initializes the controller class.
     * @param owner
     * @param isModal
     * @param tipus
     * @return 
     * @throws java.io.IOException 
     */
    
        public static FXMLProcedenciaController crear(Window owner, boolean isModal, TipusAccio tipus) throws IOException{  
        
        tipusA = tipus;
        
        return crearPopUp("FXMLProcedencia.fxml", FXMLProcedenciaController.class, owner, isModal, tipus);
    } 
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void crearProcedencia(ActionEvent event) {
        
        Procedencia procedencia = null;
        
        try {
            String nomProcedencia = tfProcedencia.getText();
            
            ProcedenciaDAO procedenciaDAO = new ProcedenciaDAO();
            
            int id = -1
                    ;
                id = procedenciaDAO.nextId();
                procedencia = new Procedencia(id, nomProcedencia);                    
                procedenciaDAO.insert(procedencia);
                procedenciaDAO.close();
            
        } catch (ClassNotFoundException ex) {
            System.out.println("ClassNotFoundException: "+ex.getMessage());
        } catch (SQLException ex) {
            System.out.println("SQLException: "+ex.getMessage());
        }finally{
            if(onAcceptCallBack!= null){
                onAcceptCallBack.accept(procedencia);
            }
        }
        
        btnCrearProcedencia.setOnAction((ActionEvent event1) -> {
            ((Stage) (btnCrearProcedencia.getScene().getWindow())).close();
        });
    }

    @Override
    public void emplenarDades(Object obj) {
        
        Procedencia procedencia;
        ProcedenciaDAO objProcedenciaDAO = new ProcedenciaDAO();
        procedencia = (Procedencia) obj;
        
        if (procedencia!=null){            
            tfProcedencia.setText(procedencia.getNom());
            switch(tipusA){
                case Modificar:
                    btnCrearProcedencia.setText("Modificar");     
                    break;
                case Deshabilitar:
                    btnCrearProcedencia.setText("Deshabilitar");     
                    break;
                case Buscar:
                    btnCrearProcedencia.setText("Buscar");
                    btnCrearProcedencia.setDisable(true);
                    break;
            }
                   
        }          
    }    
}