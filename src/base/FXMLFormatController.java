package base;

import bbdd.FormatDAO;
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
import objecte.Format;
/**
 * FXML Controller class
 *
 * @author AdriaLlop
 */
public class FXMLFormatController extends GenericPopUp implements Initializable {

    static TipusAccio tipusA;
    static Format formatRebut;
    
    @FXML
    private TextField tfNomFormat;
    @FXML
    private Button btnCrearFormat;
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
    
    public static FXMLFormatController crear(Window owner, boolean isModal, TipusAccio tipus) throws IOException{    
        
        tipusA = tipus;
        
        return crearPopUp("/fxml/FXMLFormat.fxml", FXMLFormatController.class, owner, isModal, tipus);
    }

    @FXML
    private void crearFormat(ActionEvent event) {
        
        Format format = null;
        int id = -1;
        String nomFormat = tfNomFormat.getText();
        FormatDAO formatDAO = new FormatDAO();
        
        switch(tipusA){
            case Crear:
                try {
                    id = formatDAO.nextId();
                    format = new Format(id, nomFormat);
                    formatDAO.insert(format);

                } catch (SQLException  ex) {
                    System.out.println("Exception: "+ex.getMessage());
                } catch (ClassNotFoundException ex){
                    System.out.println(ex.getMessage());
                }finally{
                   if(onAcceptCallBack!= null){
                        onAcceptCallBack.accept(format);
                    }     
                }
                break;
            case Modificar:
                id = formatRebut.getId();
                format = new Format(id, nomFormat);
        
                try {
                    formatDAO.update(format);
                } catch (SQLException  ex) {
                    System.out.println("Exception: "+ex.getMessage());
                } catch (ClassNotFoundException ex){
                    System.out.println(ex.getMessage());
                }finally{
                   if(onAcceptCallBack!= null){
                        onAcceptCallBack.accept(format);
                    }
                }      
                break;
            case Deshabilitar:
                id = formatRebut.getId();
                format = new Format();
                format.setId(id);
                format.setActiva(false);
                          
                try {
                    formatDAO.update(format);
                } catch (SQLException  ex) {
                    System.out.println("Exception: "+ex.getMessage());
                } catch (ClassNotFoundException ex){
                    System.out.println(ex.getMessage());
                }        
                break;
        }
        
        ((Stage) (btnCrearFormat.getScene().getWindow())).close();
    }   
    
    @Override
    public void emplenarDades(Object obj) {
        
        Format format;
        format = (Format) obj;
        
        if (format!=null){            
            tfNomFormat.setText(format.getNom());
            
            switch(tipusA){
                case Modificar:
                    btnCrearFormat.setText("Modificar");     
                    break;
                case Deshabilitar:
                    btnCrearFormat.setText("Deshabilitar");     
                    break;
                case Buscar:
                    btnCrearFormat.setText("Buscar");
                    btnCrearFormat.setDisable(true);
                    break;
            }          
        }    
    }
    
    @FXML
    public void cancelar(){
       ((Stage) (btnCancelar.getScene().getWindow())).close();
    }
}