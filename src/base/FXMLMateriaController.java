package base;

import bbdd.MateriaDAO;
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
import objecte.Materia;

/**
 * FXML Controller class
 *
 * @author AdriaLlop
 */
public class FXMLMateriaController extends GenericPopUp implements Initializable {

    static TipusAccio tipusA;
    static Materia materiaRebuda;
    
    @FXML
    private TextField tfMateria;
    @FXML
    private Button btnCrearMateria;
    @FXML
    private Label lblMateria;
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
    
    public static FXMLMateriaController crear(Window owner, boolean isModal, TipusAccio tipus) throws IOException{  
        
        tipusA = tipus;
        
        return crearPopUp("/fxml/FXMLMateria.fxml", FXMLMateriaController.class, owner, isModal, tipus);
    }   

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void crearMateria(ActionEvent event) {
        
        Materia materia = null;
        int id = -1;
        String nomMateria = tfMateria.getText();
        MateriaDAO materiaDAO = new MateriaDAO();
        
        switch(tipusA){
            case Crear:
                try {
                    id = materiaDAO.nextId();
                    materia = new Materia(id, nomMateria);
                    materiaDAO.insert(materia);

                } catch (SQLException  ex) {
                    System.out.println("Exception: "+ex.getMessage());
                } catch (ClassNotFoundException ex){
                    System.out.println(ex.getMessage());
                }finally{
                   if(onAcceptCallBack!= null){
                        onAcceptCallBack.accept(materia);
                    }     
                }
                break;
            case Modificar:
                id = materiaRebuda.getId();
                materia = new Materia(id, nomMateria);
        
                try {
                    materiaDAO.update(materia);
                } catch (SQLException  ex) {
                    System.out.println("Exception: "+ex.getMessage());
                } catch (ClassNotFoundException ex){
                    System.out.println(ex.getMessage());
                }finally{
                   if(onAcceptCallBack!= null){
                        onAcceptCallBack.accept(materia);
                    }
                }      
                break;
            case Deshabilitar:
                materiaRebuda.setActiva(false);           
          
                try {
                    materiaDAO.update(materiaRebuda);
                } catch (SQLException  ex) {
                    System.out.println("Exception: "+ex.getMessage());
                } catch (ClassNotFoundException ex){
                    System.out.println(ex.getMessage());
                }        
                break;
        }
        
        ((Stage) (btnCrearMateria.getScene().getWindow())).close();  
    }
    
    @Override
    public void emplenarDades(Object obj) {
        
        Materia materia;
        materia = (Materia) obj;
        
        if (materia!=null){            
            tfMateria.setText(materia.getNom());
            
            switch(tipusA){
                case Modificar:
                    btnCrearMateria.setText("Modificar");     
                    break;
                case Deshabilitar:
                    btnCrearMateria.setText("Deshabilitar");     
                    break;
                case Buscar:
                    btnCrearMateria.setText("Buscar");
                    btnCrearMateria.setDisable(true);
                    break;
            }                  
        } 
    }
    
    @FXML
    public void cancelar(){
       ((Stage) (btnCancelar.getScene().getWindow())).close();
    }
}