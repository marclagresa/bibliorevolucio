package base;

import bbdd.PersonaDAO;
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
import objecte.Persona;

/**
 * FXML Controller class
 *
 * @author AdriaLlop
 */
public class FXMLAutorController extends GenericPopUp implements Initializable {

    static TipusAccio tipusA;
    static Persona personaRebuda;
    
    @FXML
    private Label lblAutor;
    @FXML
    private TextField tfAutor;
    @FXML
    private Button btnCrearAutor;
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
    
    public static FXMLAutorController crear(Window owner, boolean isModal, TipusAccio tipus) throws IOException{     
        
        tipusA = tipus;
        
        return crearPopUp("/fxml/FXMLAutor.fxml", FXMLAutorController.class, owner, isModal, tipus);
    }

    @FXML
    private void crearAutor(ActionEvent event) {
        
        Persona persona = null;
        int id = -1;
        String nomAutor = tfAutor.getText();
        PersonaDAO personaDAO = new PersonaDAO();
        
        switch(tipusA){
            case Crear:
                try {
                    id = personaDAO.nextId();
                    persona = new Persona(id, nomAutor);
                    personaDAO.insert(persona);

                } catch (SQLException  ex) {
                    System.out.println("Exception: "+ex.getMessage());
                } catch (ClassNotFoundException ex){
                    System.out.println(ex.getMessage());
                }finally{
                   if(onAcceptCallBack!= null){
                        onAcceptCallBack.accept(persona);
                    }     
                }
                break;
            case Modificar:
                id = personaRebuda.getId();
                persona = new Persona(id, nomAutor);
        
                try {
                    personaDAO.update(persona);
                } catch (SQLException  ex) {
                    System.out.println("Exception: "+ex.getMessage());
                } catch (ClassNotFoundException ex){
                    System.out.println(ex.getMessage());
                }finally{
                   if(onAcceptCallBack!= null){
                        onAcceptCallBack.accept(persona);
                    }
                }      
                break;
            case Deshabilitar:
                id = personaRebuda.getId();
                persona = new Persona();
                persona.setId(id);   
                persona.setActiva(false);
        
            try {
                personaDAO.update(persona);
            } catch (SQLException  ex) {
                    System.out.println("Exception: "+ex.getMessage());
                } catch (ClassNotFoundException ex){
                    System.out.println(ex.getMessage());
                }           
                break;
        }
        
        ((Stage) (btnCrearAutor.getScene().getWindow())).close();
    }

    @Override
    public void emplenarDades(Object obj) {
        
        Persona autor;
        autor = (Persona) obj;        
        
        if (autor!=null){
            personaRebuda = autor;
            tfAutor.setText(autor.getNom());
            
            switch(tipusA){
                case Modificar:
                    btnCrearAutor.setText("Modificar");     
                    break;
                case Deshabilitar:
                    btnCrearAutor.setText("Deshabilitar");     
                    break;
                case Buscar:
                    btnCrearAutor.setText("Buscar");
                    btnCrearAutor.setDisable(true);
                    break;
            }          
        }
    }
    
    @FXML
    public void cancelar(){
       ((Stage) (btnCancelar.getScene().getWindow())).close();
    }
}