package base;

import static base.GenericPopUp.TipusAccio.Crear;
import bbdd.CduDAO;
import bbdd.ColeccioDAO;
import bbdd.EditorialDAO;
import bbdd.FormatDAO;
import bbdd.IdiomaDAO;
import bbdd.NivellDAO;
import bbdd.PersonaDAO;
import contract.ContractEditorial;
import contract.ContractFormat;
import contract.ContractIdioma;
import contract.ContractNivell;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;
import objecte.Cdu;
import objecte.Coleccio;
import objecte.Editorial;
import objecte.Format;
import objecte.Idioma;
import objecte.Nivell;
import objecte.Persona;

/**
 *
 * @author AdriaLlop
 */
public class FXMLObraController extends GenericPopUp implements Initializable  {   
    
    EditorialDAO objEditorialDAO;
    FormatDAO objFormatDAO;
    CduDAO objCduDAO;
    PersonaDAO objPersonaDAO;
    NivellDAO objNivellDAO;
    IdiomaDAO objIdiomaDAO;
    ColeccioDAO objColeccioDAO;
    static TipusAccio tipusA;
    
    @FXML
    private TextArea taResum;
    @FXML
    private TextField tfISBN;
    @FXML
    private TextField tfAny;
    @FXML
    private TextField tfNumPag;
    @FXML
    private TextField tfTitol;
    @FXML
    private TextField tfCategoria;
    @FXML
    private TextField tfDimensions;
    @FXML
    private ComboBox<Persona> cbPersona;
    @FXML
    private ComboBox<Editorial> cbEditorial;
    @FXML
    private TextField tfNumExemplars;
    @FXML
    private ComboBox<Nivell> cbNivellLectura;
    @FXML
    private TextField tfAdresaWeb;
    @FXML
    private ComboBox<Cdu> cbCDU;
    @FXML
    private ComboBox<Format> cbFormat;
    @FXML
    private ComboBox<Idioma> cbIdioma;
    @FXML
    private ComboBox<Coleccio> cbColeccio;    
    @FXML
    private Button btnNouFormat;
    @FXML
    private Button btnNouCDU;
    @FXML
    private Button btnNouAutor;
    @FXML
    private Button btnNouEditorial;
    @FXML
    private Button btnNouNivell;
    @FXML
    private Button btnNouIdioma;
    @FXML
    private Button btnCancelar;
    @FXML
    private Button btnImatge;
    @FXML
    private VBox vBox;
    @FXML
    private ImageView imgProducte;
    
    public static FXMLObraController crear(Window owner, boolean isModal, TipusAccio tipus) throws IOException{

        tipusA = tipus;
        
        return crearPopUp("FXMLObra.fxml", FXMLObraController.class, owner, isModal, tipus);
    }    
    
    @FXML
    public void guardar() {
        
        //Falta afegir comprovadors de els contenidors de text no estan buits
        int data = 0,num_pag = 0,volum = 0,num_exemplars = 0;
        String cdu = "",editorial = "", persona = "",nivellLectura = "",format = "",idioma = "", coleccio = "";
        Editorial objEditorial;
        Persona objPersona;
        Cdu objCDU;
        Format objFormat;
        Nivell ojbNivell;
        Idioma objIdioma;
        Coleccio objColeccio;
         
        //CDU->comprovació de que no estigui buit el cbCDU
        //boolean cbCDUBuit = (cbCDU.getValue() == null);       
        boolean cbCDUBuit = cbCDU.getSelectionModel().isEmpty();
        
        if(!cbCDUBuit){
            objCDU = cbCDU.getValue();
            cdu = objCDU.getNom();
            System.out.println("CDU: "+cdu);    
        }

        //Autor->comprovació de que no estigui buit el cbEditorial
        boolean cbPersonaBuit = (cbPersona.getValue() == null);
        if(!cbPersonaBuit){ 
            objPersona = cbPersona.getValue();
            persona = objPersona.getNom();
            System.out.println("Autor: "+persona);
        }     

        //Editorial->comprovació de que no estigui buit el cbEditorial
        boolean cbEditorialBuit = (cbEditorial.getValue() == null);       
        
        if(!cbEditorialBuit){
            objEditorial = cbEditorial.getValue();
            editorial = objEditorial.getNom();
            System.out.println("Editorial: "+editorial);    
        }
        
        //Nivell lectura->comprovació de que no estigui buit el cbNivellLectura
        boolean cbNivellLecturaBuit = (cbNivellLectura.getValue() == null);       
        
        if(!cbNivellLecturaBuit){
            ojbNivell = cbNivellLectura.getValue();
            nivellLectura = ojbNivell.getNom();
            System.out.println("Nivell lectura: "+nivellLectura);    
        }
        
        //Idioma->comprovació de que no estigui buit el cbIdioma
        boolean cbIdiomaBuit = (cbIdioma.getValue() == null);       
        
        if(!cbIdiomaBuit){
            objIdioma = cbIdioma.getValue();
            idioma = objIdioma.getNom();
            System.out.println("Nivell lectura: "+idioma);    
        }
        
        //Format->comprovació de que no estigui buit el cbFormat
        boolean cbFormatBuit = (cbFormat.getValue() == null);       
        
        if(!cbFormatBuit){
            objFormat = cbFormat.getValue();
            format = objFormat.getNom();
            System.out.println("Format: "+format);    
        }
        
        //Num exemplars
        String validacioNumExemplars = tfNumExemplars.getText();
        if(!"".equals(validacioNumExemplars)){
            if(isNumeric(validacioNumExemplars)){
                 num_exemplars = Integer.valueOf(validacioNumExemplars);
            }else{
                tfNumExemplars.setStyle("-fx-border-color: red; -fx-background-color:#FFCDD2");
                tfNumExemplars.setText("");
            }             
        }
        
        //Any
        String validacioData = tfAny.getText();    
        if (!"".equals(validacioData)) {
            if(isNumeric(validacioData)){
                data = Integer.valueOf(validacioData);
            }else{
                tfAny.setStyle("-fx-border-color: red; -fx-background-color:#FFCDD2");
                tfAny.setText("");
            }            
        }

        //Num pag
        String validacioNumPag = tfNumPag.getText();
        if(!"".equals(validacioNumPag)) {
            if(isNumeric(validacioData)){
                 num_pag = Integer.valueOf(validacioNumPag);
            }else{
                tfNumPag.setStyle("-fx-border-color: red; -fx-background-color:#FFCDD2");
                tfNumPag.setText("");
            }
        }

        //Col·lecció
        boolean cbColeccioBuit = (cbColeccio.getValue() == null);       
        
        if(!cbColeccioBuit){
            objColeccio = cbColeccio.getValue();
            coleccio = objColeccio.getNom();
            System.out.println("Col·lecció: "+coleccio);    
        }
        

        String nom = tfTitol.getText();
        String ISBN = tfISBN.getText();        
        String categoria = tfCategoria.getText();
        String dimensions = tfDimensions.getText();
        String resum = taResum.getText();
       
        //Comprovem que tots aquests camps que no poden estar buits no ho estan
       if(ISBN.equals("") || nom.equals("") || format.equals("") || idioma.equals("") || editorial.equals("") || nivellLectura.equals("") || cdu.equals("") || coleccio.equals("")){
           
           //Tots aquests camps si estan buit els marquem en vermell, sino els posem amb el seu color original
           if(ISBN.equals("")){
               tfISBN.setStyle("-fx-border-color: red;");
           }else{
               tfISBN.setStyle("-fx-border-color: #BDBDBD; -fx-background-color:white");
           }
           if(nom.equals("")){
               tfTitol.setStyle("-fx-border-color: red;");
           }else{
               tfTitol.setStyle("-fx-border-color: #BDBDBD; -fx-background-color:white");
           }
           if(editorial.equals("")){
               cbEditorial.setStyle("-fx-border-color: red;");
           }else{
               cbEditorial.setStyle("-fx-border-color: #BDBDBD; -fx-background-color:white");
           }
           if(format.equals("")){
               cbFormat.setStyle("-fx-border-color: red;");
           }else{
               cbFormat.setStyle("-fx-border-color: #BDBDBD; -fx-background-color:white");
           }
           if(idioma.equals("")){
               cbIdioma.setStyle("-fx-border-color: red;");
           }else{
               cbIdioma.setStyle("-fx-border-color: #BDBDBD; -fx-background-color:white");
           }           
           if(nivellLectura.equals("")){
               cbNivellLectura.setStyle("-fx-border-color: red;");
           }else{
               cbNivellLectura.setStyle("-fx-border-color: #BDBDBD; -fx-background-color:white");
           }
           if(cdu.equals("")){
               cbCDU.setStyle("-fx-border-color: red;");
           }else{
               cbCDU.setStyle("-fx-border-color: #BDBDBD; -fx-background-color:white");
           }
           if(persona.equals("")){
               cbPersona.setStyle("-fx-border-color: red;");
           }else{
               cbPersona.setStyle("-fx-border-color: #BDBDBD; -fx-background-color:white");
           }
           if(coleccio.equals("")){
               cbColeccio.setStyle("-fx-border-color: red;");
           }else{
               cbColeccio.setStyle("-fx-border-color: #BDBDBD; -fx-background-color:white");
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
            /*Producte producte = new Producte(id, ISBN, nom, num_pag, dimensions, data, resum, caracteristiques,                 urlPortada, adreçaWeb, estat, idTipusProducte, idIdioma, idEditorial, idFormat, idProcedencia, idNivell,            idColeccio, idCDU);*/
            
            //Retornem el border dels textFields i combobox al color original
            cbFormat.setStyle("-fx-border-color: #BDBDBD; -fx-background-color:white");
            taResum.setStyle("-fx-border-color: #BDBDBD; -fx-background-color:white");
            tfISBN.setStyle("-fx-border-color: #BDBDBD; -fx-background-color:white");
            tfAny.setStyle("-fx-border-color: #BDBDBD; -fx-background-color:white");
            tfNumPag.setStyle("-fx-border-color: #BDBDBD; -fx-background-color:white");
            cbColeccio.setStyle("-fx-border-color: #BDBDBD; -fx-background-color:white");
            tfTitol.setStyle("-fx-border-color: #BDBDBD; -fx-background-color:white");
            cbNivellLectura.setStyle("-fx-border-color: #BDBDBD; -fx-background-color:white");
            tfCategoria.setStyle("-fx-border-color: #BDBDBD; -fx-background-color:white");
            tfDimensions.setStyle("-fx-border-color: #BDBDBD; -fx-background-color:white");
            cbIdioma.setStyle("-fx-border-color: #BDBDBD; -fx-background-color:white");
            cbPersona.setStyle("-fx-border-color: #BDBDBD; -fx-background-color:white");
            cbCDU.setStyle("-fx-border-color: #BDBDBD; -fx-background-color:white");
            cbEditorial.setStyle("-fx-border-color: #BDBDBD; -fx-background-color:white");
            tfNumExemplars.setStyle("-fx-border-color: #BDBDBD; -fx-background-color:white");            
        }
    }
    
    @FXML
    public void cancelar(){
       ((Stage) (btnCancelar.getScene().getWindow())).close();
        System.out.println("Cancel·lar");
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {            
        
        //Posem els items que hi ha la BD dins dels combobox
        
        //Editorial
        objEditorialDAO= new EditorialDAO();
        final ObservableList<Editorial> opcionsEditorial = FXCollections.observableArrayList(objEditorialDAO.selectAll());
        cbEditorial.setItems(opcionsEditorial);
        
        //Format
        objFormatDAO= new FormatDAO();
        ObservableList<Format> opcionsFormat = null;
        try {
            opcionsFormat = FXCollections.observableArrayList(objFormatDAO.selectAll());
            cbFormat.setItems(opcionsFormat);
        } catch (ClassNotFoundException ex) {
            System.out.println("ClassNotFoundException: "+ex.getMessage());
        } catch (SQLException ex) {
            System.out.println("SQLException: "+ex.getMessage());
        }
        
        
        //Persona
        objPersonaDAO = new PersonaDAO();       
        ObservableList<Persona> opcionsPersona;
        try {
            opcionsPersona = FXCollections.observableArrayList(objPersonaDAO.selectAll());
            cbPersona.setItems(opcionsPersona);
        } catch (ClassNotFoundException ex) {
            System.out.println("ClassNotFoundException: "+ex.getMessage());
        } catch (SQLException ex) {
            System.out.println("SQLException: "+ex.getMessage());
        }
        
        //Nivell
        objNivellDAO= new NivellDAO();       
        ObservableList<Nivell> opcionsNivell = null;
        try {
            opcionsNivell = FXCollections.observableArrayList(objNivellDAO.selectAll());
            cbNivellLectura.setItems(opcionsNivell);
        } catch(ClassNotFoundException ex){
            System.out.println("ClassNotFoundException: "+ex.getMessage());
        } catch (SQLException ex) {
            System.out.println("SQLException: "+ex.getMessage());
        }
        
        //Idioma
        objIdiomaDAO= new IdiomaDAO();
        ObservableList<Idioma> opcionsIdioma = null;
        try{
            opcionsIdioma = FXCollections.observableArrayList(objIdiomaDAO.selectAll());
            cbIdioma.setItems(opcionsIdioma);
        }catch(ClassNotFoundException ex){
            System.out.println("ClassNotFoundException: "+ex.getMessage());
        } catch (SQLException ex) {
            System.out.println("SQLException: "+ex.getMessage());
        }
        
        //CDU
        objCduDAO= new CduDAO();      
        ObservableList<Cdu> opcionsCDU = null;
        try{
            objCduDAO= new CduDAO();       
            opcionsCDU = FXCollections.observableArrayList(objCduDAO.selectAll());
            cbCDU.setItems(opcionsCDU);
        }catch(ClassNotFoundException ex){
            System.out.println("ClassNotFoundException: "+ex.getMessage());
        } catch (SQLException ex) {
            System.out.println("SQLException: "+ex.getMessage());
        }
        
        //Ara omplim els combobox a partir del text que s'ha escrit en ells(busqueda)
        
        //Editorial
        cbEditorial.getEditor().textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            
            System.out.println("nou valor"+newValue);
           
            HashMap<String,Object> cercaEditorial=new HashMap<>();
            cercaEditorial.put(ContractEditorial.NOM, newValue);
            try {
                opcionsEditorial.setAll(FXCollections.observableArrayList(objEditorialDAO.select(cercaEditorial))); 
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }      
            cbEditorial.setItems(opcionsEditorial);
        });
        
        //Classe listener pel format
        class ClFormat implements ChangeListener<String>{

            ObservableList<Format> opcionsFormat;
            
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                
                HashMap<String,Object> cercaFormat=new HashMap<>();
                cercaFormat.put(ContractFormat.NOM, newValue);
                try {
                    opcionsFormat.setAll(FXCollections.observableArrayList(objFormatDAO.select(cercaFormat)));
                } catch (Exception e) {
                    System.err.println(e.getMessage());
                }                
                cbFormat.setItems(opcionsFormat);
            }            
        }
        
        ClFormat clFormat = new ClFormat();
        clFormat.opcionsFormat = opcionsFormat;       
        cbFormat.getEditor().textProperty().addListener(clFormat);
         
        
        //Classe listener per nivell
        class ClNivell implements ChangeListener<String>{

            ObservableList<Nivell> opcionsNivell;
            
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                
                HashMap<String,Object> cercaNivell=new HashMap<>();
                cercaNivell.put(ContractNivell.NOM, newValue);
                try {
                    opcionsNivell.setAll(FXCollections.observableArrayList(objNivellDAO.select(cercaNivell)));
                } catch (Exception e) {
                    System.err.println(e.getMessage());
                }               
                cbNivellLectura.setItems(opcionsNivell);
            }            
        }
        
        ClNivell clNivell = new ClNivell();
        clNivell.opcionsNivell = opcionsNivell;       
        cbNivellLectura.getEditor().textProperty().addListener(clNivell);
        
        //Classe listener per idioma
        class ClIdioma implements ChangeListener<String>{

            ObservableList<Idioma> opcionsIdioma;
            
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                
                HashMap<String,Object> cercaIdioma=new HashMap<>();
                cercaIdioma.put(ContractIdioma.NOM, newValue);
                try {
                    opcionsIdioma.setAll(FXCollections.observableArrayList(objIdiomaDAO.select(cercaIdioma)));
                } catch (Exception e) {
                    System.err.println(e.getMessage());
                }               
                cbIdioma.setItems(opcionsIdioma);
            }            
        }
        
        ClIdioma clIdioma = new ClIdioma();
        clIdioma.opcionsIdioma = opcionsIdioma;       
        cbIdioma.getEditor().textProperty().addListener(clIdioma);
        
        //Classe listener per CDU
        class ClCdu implements ChangeListener<String>{

            ObservableList<Cdu> opcionsCdu;
            
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                
                HashMap<String,Object> cercaCdu=new HashMap<>();
                cercaCdu.put(ContractIdioma.NOM, newValue);
                try {
                    opcionsCdu.setAll(FXCollections.observableArrayList(objCduDAO.select(cercaCdu)));
                } catch (Exception e) {
                    System.err.println(e.getMessage());
                }               
                cbCDU.setItems(opcionsCdu);
            }            
        }
        
        ClCdu clCdu = new ClCdu();
        clCdu.opcionsCdu = opcionsCDU;  
        cbCDU.getEditor().textProperty().addListener(clCdu);
        
        //Persona
        /*cbPersona.getEditor().textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            //Persona DAO
            objPersonaDAO = new PersonaDAO();       
            ObservableList<Persona> opcionsPersona;
            try {
                opcionsPersona = FXCollections.observableArrayList(objPersonaDAO.selectAll());
                cbPersona.setItems(opcionsPersona);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(FXMLObraController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(FXMLObraController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });*/ 
        
        btnImatge.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            configureFileChooser(fileChooser);
            Stage stage = (Stage) vBox.getScene().getWindow();
            //fileChooser.showOpenDialog(stage);            
            
            File file = fileChooser.showOpenDialog(stage);
            if (file != null) {
                setImage(file);
            }
        });
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
    private void crearFormat() throws IOException {
        
        FXMLFormatController c = FXMLFormatController.crear(this, true, Crear);
        
        c.onAccept( (Object o)->{
            actualitzarFormat();
        });
        
        c.show();
    }
    
    public void actualitzarFormat(){
        
        objFormatDAO= new FormatDAO(); 
        ObservableList<Format> opcionsFormat;
        try {
            opcionsFormat = FXCollections.observableArrayList(objFormatDAO.selectAll());
            cbFormat.setItems(opcionsFormat);
        } catch (ClassNotFoundException ex) {
            System.out.println("ClassNotFoundException: "+ex.getMessage());
        } catch (SQLException ex) {
            System.out.println("SQLException: "+ex.getMessage());
        }
    }

    @FXML
    private void crearCDU() throws IOException {
        
        FXMLCduController c = FXMLCduController.crear(this, true, Crear);
        
        c.onAccept( (Object o)->{
            actualitzarCDU();
        });
        
        c.show();          
    }
    
    public void actualitzarCDU(){        
        
        try{
            //CDU DAO
            objCduDAO= new CduDAO();
            ObservableList<Cdu> opcionsCDU = FXCollections.observableArrayList(objCduDAO.selectAll());
            cbCDU.setItems(opcionsCDU);
        }catch(ClassNotFoundException ex){
            System.out.println("ClassNotFoundException: "+ex.getMessage());
        } catch (SQLException ex) {
            System.out.println("SQLException: "+ex.getMessage());
        }  
    }

    @FXML
    private void crearAutor() throws IOException {
        
        FXMLAutorController c = FXMLAutorController.crear(this, true, Crear);
        
        c.onAccept( (Object o)->{
            actualitzarAutor();
        });
        
        c.show();
    }
    
    public void actualitzarAutor(){        
        
        try {
            objPersonaDAO = new PersonaDAO();   
            ObservableList<Persona> opcionsPersona = FXCollections.observableArrayList(objPersonaDAO.selectAll());
            cbPersona.setItems(opcionsPersona);
        } catch(ClassNotFoundException ex){
            System.out.println("ClassNotFoundException: "+ex.getMessage());
        } catch (SQLException ex) {
            System.out.println("SQLException: "+ex.getMessage());
        }  
    }

    @FXML
    private void crearEditorial() throws IOException {
        
        FXMLEditorialController c = FXMLEditorialController.crear(this, true, Crear);
        
        c.onAccept( (Object o)->{
            actualitzarEditorial();
        });
        
        c.show();
    }
    
    public void actualitzarEditorial(){
        
        objEditorialDAO= new EditorialDAO();
        final ObservableList<Editorial> opcionsEditorial = FXCollections.observableArrayList(objEditorialDAO.selectAll());
        cbEditorial.setItems(opcionsEditorial);
    }

    @FXML
    private void crearNivell() throws IOException{
        
        FXMLNivellController c = FXMLNivellController.crear(this, true, Crear);
        
        c.onAccept( (Object o)->{
            actualitzarNivell();
        });
        
        c.onCancel(() -> {            
        });    
        
        c.show();
    }
    
    public void actualitzarNivell(){
        
        objNivellDAO= new NivellDAO();       
        ObservableList<Nivell> opcionsNivell;
        try {
            opcionsNivell = FXCollections.observableArrayList(objNivellDAO.selectAll());
            cbNivellLectura.setItems(opcionsNivell);
        } catch(ClassNotFoundException ex){
            System.out.println("ClassNotFoundException: "+ex.getMessage());
        } catch (SQLException ex) {
            System.out.println("SQLException: "+ex.getMessage());
        }
    }

    @FXML
    private void crearIdioma() throws IOException {
        
        FXMLIdiomaController c = FXMLIdiomaController.crear(this, true, Crear);
        
        c.onAccept( (Object o)->{
            actualitzarIdioma();
        });
        
        c.show();
    }
    
    public void actualitzarIdioma(){
        
        objIdiomaDAO= new IdiomaDAO();       
        ObservableList<Idioma> opcionsIdioma;
        try{
            opcionsIdioma = FXCollections.observableArrayList(objIdiomaDAO.selectAll());
            cbIdioma.setItems(opcionsIdioma);
        }catch(ClassNotFoundException ex){
            System.out.println("ClassNotFoundException: "+ex.getMessage());
        } catch (SQLException ex) {
            System.out.println("SQLException: "+ex.getMessage());
        }
    }
    
     private static void configureFileChooser(final FileChooser fileChooser){   
         
        fileChooser.setTitle("Imatges");
        fileChooser.setInitialDirectory(
            new File(System.getProperty("user.home"))
        );
        
        //Li dic quin tipus de format vull que pugui buscar
        fileChooser.getExtensionFilters().addAll(
            new FileChooser.ExtensionFilter("All Images", "*.*"),
            new FileChooser.ExtensionFilter("JPG", "*.jpg"),
            new FileChooser.ExtensionFilter("PNG", "*.png")
        );
    }
     
     private void setImage(File file) { 
         
        Image imatge = new Image(file.toURI().toString());
        imgProducte.setImage(imatge);
    }

    @Override
    public void emplenarDades(Object obj) {
        
    }
}