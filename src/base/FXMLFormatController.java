package base;

import bbdd.FormatDAO;
import java.io.IOException;
import java.net.URL;
import java.nio.file.FileSystems;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.Window;
import objecte.Format;
/**
 * FXML Controller class
 *
 * @author AdriaLlop
 */
public class FXMLFormatController extends GenericPopUp implements Initializable {

    static TipusAccio tipusA;
    
    @FXML
    private TextField tfNomFormat;
    @FXML
    private Button btnCrearFormat;

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    
    
    public static FXMLFormatController crear(Window owner, boolean isModal, TipusAccio tipus) throws IOException{    
        
        tipusA = tipus;
        
        return crearPopUp("FXMLFormat.fxml", FXMLFormatController.class, owner, isModal, tipus);
    }

    @FXML
    private void crearFormat(ActionEvent event) {
        
        int id = -1;
        Format format = null;
        
        try {
            String nomFormat = tfNomFormat.getText();
            
            FormatDAO formatDAO = new FormatDAO();
            
            id = formatDAO.nextId();
            
            format = new Format(id,nomFormat);              
            
            if(formatDAO.insert(format)){
                System.out.println("true");
            }else{
                System.out.println("false");
            }
         
            formatDAO.close();
            
        } catch (ClassNotFoundException ex) {
            System.out.println("ClassNotFoundException: "+ex.getMessage());
        } catch (SQLException ex) {
            System.out.println("SQLException: "+ex.getMessage());
        }finally{
            if(onAcceptCallBack!= null){
                onAcceptCallBack.accept(format);
            }
        }
        
        btnCrearFormat.setOnAction((ActionEvent event1) -> {
            ((Stage) (btnCrearFormat.getScene().getWindow())).close();
        });
    }   
    
    @Override
    public void emplenarDades(Object obj) {
        
        Format format;
        FormatDAO objFormatDAO = new FormatDAO();
        format = (Format) obj;
        
        if (format!=null){            
            tfNomFormat.setText(format.getNom());
            btnCrearFormat.setText("Modificar");            
        }    
    }
}