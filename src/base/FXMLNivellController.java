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
    static Nivell nivellRebut;
    
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
        int id = -1;
        String nomNivell = tfNivell.getText();
        NivellDAO nivellDAO = new NivellDAO();
        
        switch(tipusA){
            case Crear:
                try {
                    id = nivellDAO.nextId();
                    nivell = new Nivell(id, nomNivell);
                    nivellDAO.insert(nivell);
                    nivellDAO.close();

                } catch (SQLException  ex) {
                    System.out.println("Exception: "+ex.getMessage());
                } catch (ClassNotFoundException ex){
                    System.out.println(ex.getMessage());
                }finally{
                   if(onAcceptCallBack!= null){
                        onAcceptCallBack.accept(nivell);
                    }     
                }
                break;
            case Modificar:
                id = nivellRebut.getId();
                nivell = new Nivell(id, nomNivell);
        
                try {
                    nivellDAO.update(nivell);
                } catch (SQLException  ex) {
                    System.out.println("Exception: "+ex.getMessage());
                } catch (ClassNotFoundException ex){
                    System.out.println(ex.getMessage());
                }finally{
                   if(onAcceptCallBack!= null){
                        onAcceptCallBack.accept(nivell);
                    }
                    nivellDAO.close();
                }      
                break;
            case Deshabilitar:
                id = nivellRebut.getId();
                nivell = new Nivell();
                nivell.setId(id);
                
                //nivellDAO.delete(nivell);
                nivellDAO.close();     
                break;
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

    @Override
    public void emplenarDades(Object obj) {
        
        Nivell nivell;
        nivell = (Nivell) obj;
        
        if (nivell!=null){            
            tfNivell.setText(nivell.getNom());
            
            switch(tipusA){
                case Modificar:
                    btnCrearNivell.setText("Modificar");     
                    break;
                case Deshabilitar:
                    btnCrearNivell.setText("Deshabilitar");     
                    break;
                case Buscar:
                    btnCrearNivell.setText("Buscar");
                    btnCrearNivell.setDisable(true);
                    break;
            }         
        }    
    }   
}