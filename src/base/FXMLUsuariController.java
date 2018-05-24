package base;

import base.EmplanarComboBox.ClNivell;
import bbdd.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import javafx.stage.Window;
import objecte.*;
import java.io.IOException;
import java.net.URL;
import java.nio.file.FileSystems;
import java.sql.SQLException;
import java.util.ResourceBundle;

import static base.GenericPopUp.TipusAccio.Crear;

/**
 *
 * @author AdriaLlop
 */
public class FXMLUsuariController extends GenericPopUp implements Initializable  {

    NivellDAO objNivellDAO;
    
    static TipusAccio tipusA;

    @FXML
    private TextField tfNom;
    @FXML
    private TextField tfCognom1;
    @FXML
    private TextField tfCognom2;
    @FXML
    private TextField tfEmail;
    @FXML
    private TextField tfTelefonFixe;
    @FXML
    private TextField tfTelefonMobil;
    @FXML
    private TextField tfPassword;
    @FXML
    private ComboBox<Nivell> cbNivell;
    @FXML
    private Button btnCrearNivell;
    @FXML
    private Button btnCancelar;
    
    public static FXMLUsuariController crear(Window owner, boolean isModal, TipusAccio tipus) throws IOException{
        tipusA = tipus;
        return crearPopUp("/fxml/FXMLUsuari.fxml", FXMLUsuariController.class, owner, isModal, tipus);
    }    
    
    @FXML
    public void guardar() {
        
        //Falta afegir comprovadors de els contenidors de text no estan buits
        int telefonfixe = 0,telefonmobil = 0;
        String nivell = "";
        Nivell ojbNivell;
        
        //Nivell lectura->comprovació de que no estigui buit el cbNivellLectura
        boolean cbNivellBuit = (cbNivell.getValue() == null);
        
        if(!cbNivellBuit){
            ojbNivell = cbNivell.getValue();
            nivell = ojbNivell.getNom();
            System.out.println("Nivell lectura: "+nivell);
        }
        
        //Telefon Fixe
        String validacioTelefonFixe = tfTelefonFixe.getText();
        if(!"".equals(validacioTelefonFixe)){
            if(isNumeric(validacioTelefonFixe)){
                 telefonfixe = Integer.valueOf(validacioTelefonFixe);
            }else{
                tfTelefonFixe.setStyle("-fx-border-color: red; -fx-background-color:#FFCDD2");
                tfTelefonFixe.setText("");
            }             
        }

        //Telefon Fixe
        String validacioTelefonMobil = tfTelefonMobil.getText();
        if(!"".equals(validacioTelefonMobil)){
            if(isNumeric(validacioTelefonMobil)){
                telefonmobil = Integer.valueOf(validacioTelefonMobil);
            }else{
                tfTelefonMobil.setStyle("-fx-border-color: red; -fx-background-color:#FFCDD2");
                tfTelefonMobil.setText("");
            }
        }

        String nom = tfNom.getText();
        String cognom1 = tfCognom1.getText();
        String cognom2 = tfCognom2.getText();
        String email = tfEmail.getText();
        String password = tfPassword.getText();
       
        //Comprovem que tots aquests camps que no poden estar buits no ho estan
       if(nom.equals("") || cognom1.equals("") || cognom2.equals("") || email.equals("") || password.equals("")){
           
           //Tots aquests camps si estan buit els marquem en vermell, sino els posem amb el seu color original
           if(nom.equals("")){
               tfNom.setStyle("-fx-border-color: red;");
           }else{
               tfNom.setStyle("-fx-border-color: #BDBDBD; -fx-background-color:white");
           }
           if(cognom1.equals("")){
               tfCognom1.setStyle("-fx-border-color: red;");
           }else{
               tfCognom1.setStyle("-fx-border-color: #BDBDBD; -fx-background-color:white");
           }
           if(cognom2.equals("")){
               tfCognom2.setStyle("-fx-border-color: red;");
           }else{
               tfCognom2.setStyle("-fx-border-color: #BDBDBD; -fx-background-color:white");
           }
           if(email.equals("")){
               tfEmail.setStyle("-fx-border-color: red;");
           }else{
               tfEmail.setStyle("-fx-border-color: #BDBDBD; -fx-background-color:white");
           }
           if(password.equals("")){
               tfPassword.setStyle("-fx-border-color: red;");
           }else{
               tfPassword.setStyle("-fx-border-color: #BDBDBD; -fx-background-color:white");
           }           
           if(nivell.equals("")){
               cbNivell.setStyle("-fx-border-color: red;");
           }else{
               cbNivell.setStyle("-fx-border-color: #BDBDBD; -fx-background-color:white");
           }
           
            Alert alerta = new Alert(AlertType.WARNING);
            alerta.setTitle("Alerta");
            alerta.setHeaderText("Has d'emplenar tots els camps necessaris");
            alerta.showAndWait().ifPresent(rs -> {            
                //Quan pulses ok acceptar de l'alert box s'entra dins l'if
                if (rs == ButtonType.OK) {                
                }
            });
       }else{

       }
    }
    
    @FXML
    public void cancelar(){
        ((Stage) btnCancelar.getScene().getWindow()).close();
        System.out.println("Cancel·lar");
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            ConnectionFactory.getInstance().configure(FileSystems.getDefault().getPath("src/base", "configBibliotecari"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        //Ara omplim els combobox a partir del text que s'ha escrit en ells(busqueda)
        
        //Listener Nivell        
        ClNivell clNivell = new ClNivell(cbNivell);
        cbNivell.getEditor().textProperty().addListener(clNivell);
    }
    
    //Comprovem que un string nomes conte numeros
    private static boolean isNumeric(String cadena){
	try {
            Integer.parseInt(cadena);
            return true;
	} catch (NumberFormatException nfe){
            return false;
	}
    }

    @FXML
    private void crearNivell() throws IOException{
        
        FXMLNivellController c = FXMLNivellController.crear(this,true, Crear);
        c.onAccept( (Object o)-> actualitzarNivell());
        c.onCancel(this::cancelar);
        c.show();
    }
    
    public void actualitzarNivell(){
        
        objNivellDAO= new NivellDAO();
        ObservableList<Nivell> opcionsNivell;
        try {
            opcionsNivell = FXCollections.observableArrayList(objNivellDAO.selectAll());
            cbNivell.setItems(opcionsNivell);
        } catch(ClassNotFoundException ex){
            System.out.println("ClassNotFoundException: "+ex.getMessage());
        } catch (SQLException ex) {
            System.out.println("SQLException: "+ex.getMessage());
        }finally{
            objNivellDAO.close();
        }
    }

    @Override
    public void emplenarDades(Object obj) {

    }
}