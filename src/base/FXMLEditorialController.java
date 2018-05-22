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
    static Editorial editorialRebutda;
    
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
        
        Editorial editorial = null;
        int id = -1;
        String nomEditorial = tfNomEditorial.getText();
        EditorialDAO editorialDAO = new EditorialDAO();
        
        switch(tipusA){
            case Crear:
                try {
                    id = editorialDAO.nextId();
                    editorial = new Editorial(id, nomEditorial);
                    editorialDAO.insert(editorial);
                    editorialDAO.close();

                } catch (SQLException  ex) {
                    System.out.println("Exception: "+ex.getMessage());
                } catch (ClassNotFoundException ex){
                    System.out.println(ex.getMessage());
                }finally{
                   if(onAcceptCallBack!= null){
                        onAcceptCallBack.accept(editorial);
                    }     
                }
                break;
            case Modificar:
                id = editorialRebutda.getId();
                editorial = new Editorial(id, nomEditorial);
        
                try {
                    editorialDAO.update(editorial);
                } catch (SQLException  ex) {
                    System.out.println("Exception: "+ex.getMessage());
                } catch (ClassNotFoundException ex){
                    System.out.println(ex.getMessage());
                }finally{
                   if(onAcceptCallBack!= null){
                        onAcceptCallBack.accept(editorial);
                    }
                    editorialDAO.close();
                }      
                break;
            case Deshabilitar:
                id = editorialRebutda.getId();
                editorial = new Editorial();
                editorial.setId(id);
                
                //editorialDAO.delete(editorial);
                editorialDAO.close();     
                break;
        }        
        
        btnCrearEditorial.setOnAction((ActionEvent event1) -> {
            ((Stage) (btnCrearEditorial.getScene().getWindow())).close();
        });
    }

    @Override
    public void emplenarDades(Object obj) {
        
        Editorial editorial;
        editorial = (Editorial) obj;
        
        if (editorial!=null){            
            tfNomEditorial.setText(editorial.getNom());
            
            switch(tipusA){
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