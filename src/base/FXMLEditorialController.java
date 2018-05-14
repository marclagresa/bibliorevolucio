package base;

import bbdd.EditorialDAO;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.Window;
import objecte.Editorial;
/**
 * FXML Controller class
 *
 * @author AdriaLlop
 */
public class FXMLEditorialController extends GenericPopUp implements Initializable {

    static TipusAccio tipusA;
    
    @FXML
    private TextField tfNomEditorial;
    @FXML
    private Button btnCrearEditorial;

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    
    
    public static FXMLEditorialController crear(Window owner, boolean isModal, TipusAccio tipus) throws IOException{  
        
        tipusA = tipus;
        
        return crearPopUp("FXMLEditorial.fxml", FXMLEditorialController.class, owner, isModal, tipus);
    }

    @FXML
    private void crearEditorial(ActionEvent event) {
        
        String nomEditorial = tfNomEditorial.getText();;
        
        Editorial editorial = null;
        EditorialDAO editorialDAO = new EditorialDAO();
        
        int id = -1;
        try {
            id = editorialDAO.nextId();
            
            editorial = new Editorial(id, nomEditorial);        
            editorialDAO.insert(editorial);
            
            editorialDAO.close();
            
        } catch (SQLException ex) {
            System.out.println("SQLException: "+ex.getMessage());
        } catch (ClassNotFoundException ex) {
            System.out.println("ClassNotFoundException: "+ex.getMessage());
        }finally{
            if(onAcceptCallBack!= null){
                onAcceptCallBack.accept(editorial);
            }
        }
        
        btnCrearEditorial.setOnAction((ActionEvent event1) -> {
            ((Stage) (btnCrearEditorial.getScene().getWindow())).close();
        });
    }

    @Override
    public void emplenarDades(Object obj, TipusAccio tipus) {
        
        Editorial editorial;
        EditorialDAO objEditorialDAO = new EditorialDAO();
        editorial = (Editorial) obj;
        
        if (editorial!=null){            
            tfNomEditorial.setText(editorial.getNom());
            
            switch(tipus){
                case Modificar:
                    btnCrearEditorial.setText("Modificar");     
                    break;
                case Deshabilitar:
                    btnCrearEditorial.setText("Deshabilitar");     
                    break;
                case Buscar:
                    btnCrearEditorial.setText("Buscar");
                    btnCrearEditorial.setDisable(true);
                    break;
            }           
        }    
    }
}