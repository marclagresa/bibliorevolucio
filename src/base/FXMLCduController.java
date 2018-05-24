
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
import javafx.stage.Stage;
import javafx.stage.Window;
import objecte.Cdu;

/**
 * FXML Controller class
 *
 * @author AdriaLlop
 */
public class FXMLCduController extends GenericPopUp implements Initializable {
    
    static TipusAccio tipusA;
    static Cdu cduRebut;
    
    @FXML
    private Label lblCDU;
    @FXML
    private TextField tfCDU;
    @FXML
    private Button btnCrearCDU;
    @FXML
    private Button btnCancelar;

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    public static FXMLCduController crear(Window owner, boolean isModal, TipusAccio tipus) throws IOException{        
        tipusA = tipus;
        
        return crearPopUp("/fxml/FXMLCdu.fxml", FXMLCduController.class, owner, isModal, tipus);
    }

    @FXML
    private void crearCDU(ActionEvent event) {
        
        Cdu cdu = null;
        int id = -1;
        int idPare = -1;
        String nomCDU = tfCDU.getText();
        CduDAO cduDAO = new CduDAO();
        
        switch(tipusA){
            case Crear:
                try {
                  //   id = cduDAO.nextId();
                  //  cdu = new Cdu(id, idPare, nomCDU);
                    cduDAO.insert(cdu);
                    cduDAO.close();

                } catch (SQLException  ex) {
                    System.out.println("Exception: "+ex.getMessage());
                } catch (ClassNotFoundException ex){
                    System.out.println(ex.getMessage());
                }finally{
                   if(onAcceptCallBack!= null){
                        onAcceptCallBack.accept(cdu);
                    }     
                }
                break;
            case Modificar:
            //    id = cduRebut.getId();
            //    cdu = new Cdu(id, idPare, nomCDU);
        
                try {
                    cduDAO.update(cdu);
                } catch (SQLException  ex) {
                    System.out.println("Exception: "+ex.getMessage());
                } catch (ClassNotFoundException ex){
                    System.out.println(ex.getMessage());
                }finally{
                   if(onAcceptCallBack!= null){
                        onAcceptCallBack.accept(cdu);
                    }
                    cduDAO.close();
                }      
                break;
            case Deshabilitar:
           //     id = cduRebut.getId();
                cdu = new Cdu();
    //            cdu.setId(id);
                
                //cduDAO.delete(cdu);
                cduDAO.close();     
                break;
        }
    }

    @Override
    public void emplenarDades(Object obj) {
        
        Cdu cdu;
        cdu = (Cdu) obj;
        
        if (cdu!=null){
            cduRebut = cdu;
            tfCDU.setText(cdu.getNom());
            
            switch(tipusA){
                case Modificar:
                    btnCrearCDU.setText("Modificar");     
                    break;
                case Deshabilitar:
                    btnCrearCDU.setText("Deshabilitar");     
                    break;
                case Buscar:
                    btnCrearCDU.setText("Buscar");
                    btnCrearCDU.setDisable(true);
                    break;
            }          
        }
    }
    
    @FXML
    public void cancelar(){
       ((Stage) (btnCancelar.getScene().getWindow())).close();
    }
}