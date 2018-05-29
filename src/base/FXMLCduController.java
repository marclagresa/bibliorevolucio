package base;

import bbdd.CduDAO;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
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
    private Button btnCrearCDU;
    @FXML
    private Button btnCancelar;
    @FXML
    private TextField tfIdCdu;
    @FXML
    private TextField tfNomCdu;
    @FXML
    private ComboBox<Cdu> cbCduPare;

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        CduDAO objCduDAO = new CduDAO();
        
        ObservableList<Cdu> opcionsCDU = null;
        try{
            objCduDAO= new CduDAO();       
            opcionsCDU = FXCollections.observableArrayList(objCduDAO.selectAll());
            cbCduPare.setItems(opcionsCDU);
        }catch(ClassNotFoundException ex){
            System.out.println("ClassNotFoundException: "+ex.getMessage());
        } catch (SQLException ex) {
            System.out.println("SQLException: "+ex.getMessage());
        }
    }    
    
    public static FXMLCduController crear(Window owner, boolean isModal, TipusAccio tipus) throws IOException{        
        tipusA = tipus;
        
        return crearPopUp("/fxml/FXMLCdu.fxml", FXMLCduController.class, owner, isModal, tipus);
    }

    @FXML
    private void crearCDU(ActionEvent event) {
        
        Cdu cdu = null;
        Cdu cduPare = null;
        String id = null;
        String idPare = null;
        String nomCDU = tfNomCdu.getText();
        CduDAO cduDAO = new CduDAO();
        
        switch(tipusA){
            case Crear:
                try {                  
                    cduPare = cbCduPare.getValue();
                    idPare = cduPare.getId();
                    cdu = new Cdu(id, idPare, nomCDU);
                    cduDAO.insert(cdu);

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
                id = cduRebut.getId();
                cduPare = cbCduPare.getValue();
                idPare = cduPare.getId();
                cdu = new Cdu(id, idPare, nomCDU);
        
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
                }      
                break;
            case Deshabilitar:
                cduRebut.setActiva(false);
            
                try {
                    cduDAO.update(cduRebut);
                } catch (SQLException  ex) {
                    System.out.println("Exception: "+ex.getMessage());
                } catch (ClassNotFoundException ex){
                    System.out.println(ex.getMessage());
                }      
                break;
        }
        
        ((Stage) (btnCrearCDU.getScene().getWindow())).close();
    }

    @Override
    public void emplenarDades(Object obj) {
        
        Cdu cdu;
        cdu = (Cdu) obj;
        
        if (cdu!=null){
            cduRebut = cdu;
            tfIdCdu.setText(cdu.getId());
            tfNomCdu.setText(cdu.getNom());
            Cdu cduPare = new Cdu(cdu.getId(), null, null);
            cbCduPare.setValue(cduPare);
            
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