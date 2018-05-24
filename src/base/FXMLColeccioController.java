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
import javafx.stage.Stage;
import javafx.stage.Window;
import objecte.Coleccio;

/**
 * FXML Controller class
 *
 * @author AdriaLlop
 */
public class FXMLColeccioController extends GenericPopUp implements Initializable {
    
    static TipusAccio tipusA;
    static Coleccio coleccioRebuda;
   
    @FXML
    private TextField tfColeccio;
    @FXML
    private Button btnCrearColeccio;
    @FXML
    private Label lblColeccio;
    @FXML
    private Button btnCancelar;

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
        
        return crearPopUp("/fxml/FXMLColeccio.fxml", FXMLColeccioController.class, owner, isModal, tipus);
    }

    @FXML
    private void crearColeccio(ActionEvent event) {
        
        Coleccio coleccio = null;
        int id = -1;
        String nomColeccio = tfColeccio.getText();            
        ColeccioDAO coleccioDAO = new ColeccioDAO();
        
        switch(tipusA){
            case Crear:
                try {
                    id = coleccioDAO.nextId();
                    coleccio = new Coleccio(id, nomColeccio);
                    coleccioDAO.insert(coleccio);
                    coleccioDAO.close();

                } catch (SQLException  ex) {
                    System.out.println("Exception: "+ex.getMessage());
                } catch (ClassNotFoundException ex){
                    System.out.println(ex.getMessage());
                }finally{
                   if(onAcceptCallBack!= null){
                        onAcceptCallBack.accept(coleccio);
                    }     
                }
                break;
            case Modificar:
                id = coleccioRebuda.getId();
                coleccio = new Coleccio(id, nomColeccio);
        
                try {
                    coleccioDAO.update(coleccio);
                } catch (SQLException  ex) {
                    System.out.println("Exception: "+ex.getMessage());
                } catch (ClassNotFoundException ex){
                    System.out.println(ex.getMessage());
                }finally{
                   if(onAcceptCallBack!= null){
                        onAcceptCallBack.accept(coleccio);
                    }
                    coleccioDAO.close();
                }      
                break;
            case Deshabilitar:
                id = coleccioRebuda.getId();
                coleccio = new Coleccio();
                coleccio.setId(id);
                
                //coleccioDAO.delete(coleccio);
                coleccioDAO.close();     
                break;
        }
    }

    @Override
    public void emplenarDades(Object obj) {
        
        Coleccio coleccio;
        coleccio = (Coleccio) obj;        

        if (coleccio!=null){
            tfColeccio.setText(coleccio.getNom());
            
            switch(tipusA){
                case Modificar:
                    btnCrearColeccio.setText("Modificar");     
                    break;
                case Deshabilitar:
                    btnCrearColeccio.setText("Deshabilitar");     
                    break;
                case Buscar:
                    btnCrearColeccio.setText("Buscar");
                    btnCrearColeccio.setDisable(true);
                    break;
            }           
        }    
    }
    
    @FXML
    public void cancelar(){
       ((Stage) (btnCancelar.getScene().getWindow())).close();
    }
}