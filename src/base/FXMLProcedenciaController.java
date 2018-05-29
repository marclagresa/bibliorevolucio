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
    static Procedencia procedenciaRebuda;
    
    @FXML
    private Label lblProcedencia;
    @FXML
    private TextField tfProcedencia;
    @FXML
    private Button btnCrearProcedencia;
    @FXML
    private Button btnCancelar;

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
        
        return crearPopUp("/fxml/FXMLProcedencia.fxml", FXMLProcedenciaController.class, owner, isModal, tipus);
    } 
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void crearProcedencia(ActionEvent event) {
        
        Procedencia procedencia = null;
        int id = -1;
        String nomProcedencia = tfProcedencia.getText();
        ProcedenciaDAO procedenciaDAO = new ProcedenciaDAO();
        
        switch(tipusA){
            case Crear:
                try {                 
                    id = procedenciaDAO.nextId();
                    procedencia = new Procedencia(id, nomProcedencia);                    
                    procedenciaDAO.insert(procedencia);

                } catch (ClassNotFoundException ex) {
                    System.out.println("ClassNotFoundException: "+ex.getMessage());
                } catch (SQLException ex) {
                    System.out.println("SQLException: "+ex.getMessage());
                }finally{
                    if(onAcceptCallBack!= null){
                        onAcceptCallBack.accept(procedencia);
                    }
                }
                break;
            case Modificar:
                id = procedenciaRebuda.getId();
                procedencia = new Procedencia(id, nomProcedencia);
        
                try {
                    procedenciaDAO.update(procedencia);
                } catch (SQLException  ex) {
                    System.out.println("Exception: "+ex.getMessage());
                } catch (ClassNotFoundException ex){
                    System.out.println(ex.getMessage());
                }finally{
                   if(onAcceptCallBack!= null){
                        onAcceptCallBack.accept(procedencia);
                    }
                }   
                break;
            case Deshabilitar:
                id = procedenciaRebuda.getId();
                procedencia = new Procedencia();
                procedencia.setId(id);
                procedencia.setActiva(false);
                          
                try {
                    procedenciaDAO.update(procedencia);
                } catch (SQLException  ex) {
                    System.out.println("Exception: "+ex.getMessage());
                } catch (ClassNotFoundException ex){
                    System.out.println(ex.getMessage());
                }      
                break;
        }

        ((Stage) (btnCrearProcedencia.getScene().getWindow())).close();
    }

    @Override
    public void emplenarDades(Object obj) {
        
        Procedencia procedencia;
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
    
    @FXML
    public void cancelar(){
       ((Stage) (btnCancelar.getScene().getWindow())).close();
    }
}