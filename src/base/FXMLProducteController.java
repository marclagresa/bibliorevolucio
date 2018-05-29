package base;

import base.EmplanarComboBox.ClCdu;
import base.EmplanarComboBox.ClColeccio;
import base.EmplanarComboBox.ClEditorial;
import base.EmplanarComboBox.ClFormat;
import base.EmplanarComboBox.ClIdioma;
import base.EmplanarComboBox.ClMateria;
import base.EmplanarComboBox.ClNivell;
import base.EmplanarComboBox.ClPersona;
import base.EmplanarComboBox.ClProcedencia;
import base.EmplanarComboBox.OpcionsListView;
import static base.GenericPopUp.TipusAccio.Crear;
import bbdd.CduDAO;
import bbdd.ColeccioDAO;
import bbdd.EditorialDAO;
import bbdd.FormatDAO;
import bbdd.IdiomaDAO;
import bbdd.MateriaDAO;
import bbdd.NivellDAO;
import bbdd.PersonaDAO;
import bbdd.ProcedenciaDAO;
import bbdd.ProducteDAO;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;
import objecte.Cdu;
import objecte.Coleccio;
import objecte.Editorial;
import objecte.Exemplar;
import objecte.Format;
import objecte.Idioma;
import objecte.Materia;
import objecte.Nivell;
import objecte.Persona;
import objecte.Procedencia;
import objecte.Producte;

/**
 *
 * @author AdriaLlop
 */
public class FXMLProducteController extends GenericPopUp implements Initializable  {   
    
    EditorialDAO objEditorialDAO;
    FormatDAO objFormatDAO;
    CduDAO objCduDAO;
    PersonaDAO objPersonaDAO;
    NivellDAO objNivellDAO;
    IdiomaDAO objIdiomaDAO;
    ColeccioDAO objColeccioDAO;
    MateriaDAO objMateriaDAO;
    ProcedenciaDAO objProcedenciaDAO;
    ProducteDAO objProducteDAO;
    
    static TipusAccio tipusA;
    static String pathImatge = "";
    static Producte producteRebut;
    
    public  ObservableList<Persona> itemsAutors = FXCollections.observableArrayList();
    public  ObservableList<Materia> itemsMateries = FXCollections.observableArrayList();
    public  ObservableList<Nivell> itemsNivells = FXCollections.observableArrayList();
    public  ObservableList<Idioma> itemsIdiomes = FXCollections.observableArrayList();
    public  ObservableList<String> itemsCdus = FXCollections.observableArrayList();
    
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
    private TextField tfDimensions;      
    @FXML
    private TextField tfAdresaWeb;
    @FXML
    private Button btnCancelar;
    @FXML
    private Button btnImatge;
    @FXML
    private ImageView imgProducte;
    @FXML
    private ComboBox<Persona> cbAutor;
    @FXML
    private ComboBox<Nivell> cbNivell;
    @FXML
    private ComboBox<Editorial> cbEditorial;
    @FXML
    private ComboBox<Cdu> cbCDU;
    @FXML
    private ComboBox<Format> cbFormat;
    @FXML
    private ComboBox<Idioma> cbIdioma;
    @FXML
    private ComboBox<Coleccio> cbColeccio; 
    @FXML
    private ComboBox<Materia> cbMateria;
    @FXML
    private ComboBox<Procedencia> cbProcedencia;
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private TextField tfPais;
    @FXML
    private TextField tfLloc;
    @FXML
    private TextArea taCaracteristiques;
    @FXML
    private ListView LvAutors;
    @FXML
    private ListView LvNivells;
    @FXML
    private ListView LvMateries;
     @FXML
    private ListView LvIdiomes;
    @FXML
    private ListView LvCdus;
    @FXML
    private Button btnCrear;
    @FXML
    private CheckBox chbActiu;
    
    public static FXMLProducteController crear(Window owner, boolean isModal, TipusAccio tipus) throws IOException{

        tipusA = tipus;
        
        return crearPopUp("/fxml/FXMLProducte.fxml", FXMLProducteController.class, owner, isModal, tipus);
    } 
    
    @FXML
    public void guardar() {
    
        switch(tipusA){
            case Crear:
                
                //Falta afegir comprovadors de els contenidors de text no estan buits
                int num_pag = 0,volum = 0,num_exemplars = 0, idProducteRebut = -1;
                String cdu = "",editorial = "", persona = "",nivellLectura = "",format = "",idioma = "", coleccio = "", data = "",llistaCdu = "";
                Editorial objEditorial = null;
                Persona objAutor = null;
                Cdu objCDU = null;
                Format objFormat = null;
                Nivell ojbNivell = null;
                Idioma objIdioma = null;
                Coleccio objColeccio = null;
                Materia objMateria = null;
                Procedencia objProcedencia = null;

                //CDU->comprovació de que no estigui buit el cbCDU
                //boolean cbCDUBuit = (cbCDU.getValue() == null);       
                boolean cbCDUBuit = cbCDU.getSelectionModel().isEmpty();

                if(!cbCDUBuit){
                    objCDU = cbCDU.getValue();
                    cdu = objCDU.getNom();
                    System.out.println("CDU: "+cdu);    
                }

                //Autor->comprovació de que no estigui buit el cbEditorial
                boolean cbAutorBuit = (cbAutor.getValue() == null);
                if(!cbAutorBuit){ 
                    objAutor = cbAutor.getValue();
                    persona = objAutor.getNom();
                    System.out.println("Autor: "+persona);
                }     

                //Editorial->comprovació de que no estigui buit el cbEditorial
                boolean cbEditorialBuit = (cbEditorial.getValue() == null);       

                if(!cbEditorialBuit){
                    objEditorial = cbEditorial.getValue();
                    editorial = objEditorial.getNom();
                    System.out.println("Editorial: "+editorial);    
                }

                //Nivell lectura->comprovació de que no estigui buit el cbNivell
                boolean cbNivellBuit = (cbNivell.getValue() == null);       

                if(!cbNivellBuit){
                    ojbNivell = cbNivell.getValue();
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

                //Any
                String validacioData = tfAny.getText();    
                if (!"".equals(validacioData)) {
                    if(isNumeric(validacioData)){
                        data = validacioData;
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
                String dimensions = tfDimensions.getText();
                String resum = taResum.getText();
                String caracteristiques = taCaracteristiques.getText();
                String adresaWeb = tfAdresaWeb.getText();
                String pais = tfPais.getText();
                String lloc = tfLloc.getText();

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
                       cbNivell.setStyle("-fx-border-color: red;");
                   }else{
                       cbNivell.setStyle("-fx-border-color: #BDBDBD; -fx-background-color:white");
                   }
                   if(cdu.equals("")){
                       cbCDU.setStyle("-fx-border-color: red;");
                   }else{
                       cbCDU.setStyle("-fx-border-color: #BDBDBD; -fx-background-color:white");
                   }
                   if(persona.equals("")){
                       cbAutor.setStyle("-fx-border-color: red;");
                   }else{
                       cbAutor.setStyle("-fx-border-color: #BDBDBD; -fx-background-color:white");
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
                   ProducteDAO producteDAO = new ProducteDAO();
                   int id = -1;
                    try {
                        id = producteDAO.nextId();
                    } catch (ClassNotFoundException ex) {
                    System.out.println("ClassNotFoundException: "+ex.getMessage());
                    } catch (SQLException ex) {
                        System.out.println("SQLException: "+ex.getMessage());
                    }

                    HashSet<Nivell> setNivells = new HashSet<>();
                    setNivells.addAll(itemsNivells);

                    HashSet<Persona> setAutors = new HashSet<>();
                    setAutors.addAll(itemsAutors);

                    HashSet<Materia> setMateries = new HashSet<>();
                    setMateries.addAll(itemsMateries);

                    HashSet<Idioma> setIdiomes = new HashSet<>();
                    setIdiomes.addAll(itemsIdiomes);

                    HashSet<Exemplar> setExemplars = new HashSet<>();

                    for (int i = 0; i < itemsCdus.size(); i++) {

                        llistaCdu = llistaCdu+"+"+itemsCdus.get(i);
                   }

                   Producte producte = new Producte(id, ISBN, nom, num_pag, dimensions, data, resum, caracteristiques,pathImatge, adresaWeb, true, setIdiomes, objEditorial, objFormat, objProcedencia, setNivells, objColeccio, cdu, setExemplars, lloc, pais, setMateries, setAutors);

                    try {
                        producteDAO.insert(producte);
                    } catch (ClassNotFoundException ex) {
                        System.out.println("ClassNotFoundException-ProducteController: "+ex.getMessage());
                    } catch (SQLException ex) {
                        System.out.println("SQLException-ProducteController: "+ex.getMessage());
                    }

                    //Retornem el border dels textFields i combobox al color original
                    cbFormat.setStyle("-fx-border-color: #BDBDBD; -fx-background-color:white");
                    taResum.setStyle("-fx-border-color: #BDBDBD; -fx-background-color:white");
                    tfISBN.setStyle("-fx-border-color: #BDBDBD; -fx-background-color:white");
                    tfAny.setStyle("-fx-border-color: #BDBDBD; -fx-background-color:white");
                    tfNumPag.setStyle("-fx-border-color: #BDBDBD; -fx-background-color:white");
                    cbColeccio.setStyle("-fx-border-color: #BDBDBD; -fx-background-color:white");
                    tfTitol.setStyle("-fx-border-color: #BDBDBD; -fx-background-color:white");
                    cbNivell.setStyle("-fx-border-color: #BDBDBD; -fx-background-color:white");
                    tfDimensions.setStyle("-fx-border-color: #BDBDBD; -fx-background-color:white");
                    cbIdioma.setStyle("-fx-border-color: #BDBDBD; -fx-background-color:white");
                    cbAutor.setStyle("-fx-border-color: #BDBDBD; -fx-background-color:white");
                    cbCDU.setStyle("-fx-border-color: #BDBDBD; -fx-background-color:white");
                    cbEditorial.setStyle("-fx-border-color: #BDBDBD; -fx-background-color:white");
                }
                
                break;
            case Modificar:
                
                break;
            case Deshabilitar:                    
                    producteRebut.setEstat(false);                

                    try {
                        objProducteDAO.update(producteRebut);
                    } catch (SQLException  ex) {
                        System.out.println("Exception: "+ex.getMessage());
                    } catch (ClassNotFoundException ex){
                        System.out.println(ex.getMessage());
                    }
                break;
        }
        
        //Falta afegir comprovadors de els contenidors de text no estan buits
        int num_pag = 0,volum = 0,num_exemplars = 0;
        String cdu = "",editorial = "", persona = "",nivellLectura = "",format = "",idioma = "", coleccio = "", data = "",llistaCdu = "";
        Editorial objEditorial = null;
        Persona objAutor = null;
        Cdu objCDU = null;
        Format objFormat = null;
        Nivell ojbNivell = null;
        Idioma objIdioma = null;
        Coleccio objColeccio = null;
        Materia objMateria = null;
        Procedencia objProcedencia = null;
         
        //CDU->comprovació de que no estigui buit el cbCDU
        //boolean cbCDUBuit = (cbCDU.getValue() == null);       
        boolean cbCDUBuit = cbCDU.getSelectionModel().isEmpty();
        
        if(!cbCDUBuit){
            objCDU = cbCDU.getValue();
            cdu = objCDU.getNom();
            System.out.println("CDU: "+cdu);    
        }

        //Autor->comprovació de que no estigui buit el cbEditorial
        boolean cbAutorBuit = (cbAutor.getValue() == null);
        if(!cbAutorBuit){ 
            objAutor = cbAutor.getValue();
            persona = objAutor.getNom();
            System.out.println("Autor: "+persona);
        }     

        //Editorial->comprovació de que no estigui buit el cbEditorial
        boolean cbEditorialBuit = (cbEditorial.getValue() == null);       
        
        if(!cbEditorialBuit){
            objEditorial = cbEditorial.getValue();
            editorial = objEditorial.getNom();
            System.out.println("Editorial: "+editorial);    
        }
        
        //Nivell lectura->comprovació de que no estigui buit el cbNivell
        boolean cbNivellBuit = (cbNivell.getValue() == null);       
        
        if(!cbNivellBuit){
            ojbNivell = cbNivell.getValue();
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
        
        //Any
        String validacioData = tfAny.getText();    
        if (!"".equals(validacioData)) {
            if(isNumeric(validacioData)){
                data = validacioData;
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
        String dimensions = tfDimensions.getText();
        String resum = taResum.getText();
        String caracteristiques = taCaracteristiques.getText();
        String adresaWeb = tfAdresaWeb.getText();
        String pais = tfPais.getText();
        String lloc = tfLloc.getText();
       
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
               cbNivell.setStyle("-fx-border-color: red;");
           }else{
               cbNivell.setStyle("-fx-border-color: #BDBDBD; -fx-background-color:white");
           }
           if(cdu.equals("")){
               cbCDU.setStyle("-fx-border-color: red;");
           }else{
               cbCDU.setStyle("-fx-border-color: #BDBDBD; -fx-background-color:white");
           }
           if(persona.equals("")){
               cbAutor.setStyle("-fx-border-color: red;");
           }else{
               cbAutor.setStyle("-fx-border-color: #BDBDBD; -fx-background-color:white");
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
           ProducteDAO producteDAO = new ProducteDAO();
           int id = -1;
            try {
                id = producteDAO.nextId();
            } catch (ClassNotFoundException ex) {
            System.out.println("ClassNotFoundException: "+ex.getMessage());
            } catch (SQLException ex) {
                System.out.println("SQLException: "+ex.getMessage());
            }
            
            HashSet<Nivell> setNivells = new HashSet<>();
            setNivells.addAll(itemsNivells);
            
            HashSet<Persona> setAutors = new HashSet<>();
            setAutors.addAll(itemsAutors);
            
            HashSet<Materia> setMateries = new HashSet<>();
            setMateries.addAll(itemsMateries);
            
            HashSet<Idioma> setIdiomes = new HashSet<>();
            setIdiomes.addAll(itemsIdiomes);
            
            HashSet<Exemplar> setExemplars = new HashSet<>();
            
            for (int i = 0; i < itemsCdus.size(); i++) {
               
                llistaCdu = llistaCdu+"+"+itemsCdus.get(i);
           }
            
           Producte producte = new Producte(id, ISBN, nom, num_pag, dimensions, data, resum, caracteristiques,pathImatge, adresaWeb, true, setIdiomes, objEditorial, objFormat, objProcedencia, setNivells, objColeccio, cdu, setExemplars, lloc, pais, setMateries, setAutors);
           
            try {
                producteDAO.insert(producte);
            } catch (ClassNotFoundException ex) {
                System.out.println("ClassNotFoundException-ProducteController: "+ex.getMessage());
            } catch (SQLException ex) {
                System.out.println("SQLException-ProducteController: "+ex.getMessage());
            }
            
            //Retornem el border dels textFields i combobox al color original
            cbFormat.setStyle("-fx-border-color: #BDBDBD; -fx-background-color:white");
            taResum.setStyle("-fx-border-color: #BDBDBD; -fx-background-color:white");
            tfISBN.setStyle("-fx-border-color: #BDBDBD; -fx-background-color:white");
            tfAny.setStyle("-fx-border-color: #BDBDBD; -fx-background-color:white");
            tfNumPag.setStyle("-fx-border-color: #BDBDBD; -fx-background-color:white");
            cbColeccio.setStyle("-fx-border-color: #BDBDBD; -fx-background-color:white");
            tfTitol.setStyle("-fx-border-color: #BDBDBD; -fx-background-color:white");
            cbNivell.setStyle("-fx-border-color: #BDBDBD; -fx-background-color:white");
            tfDimensions.setStyle("-fx-border-color: #BDBDBD; -fx-background-color:white");
            cbIdioma.setStyle("-fx-border-color: #BDBDBD; -fx-background-color:white");
            cbAutor.setStyle("-fx-border-color: #BDBDBD; -fx-background-color:white");
            cbCDU.setStyle("-fx-border-color: #BDBDBD; -fx-background-color:white");
            cbEditorial.setStyle("-fx-border-color: #BDBDBD; -fx-background-color:white");
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {            
        
        //Posem els items que hi ha la BD dins dels combobox
        
        //Editorial
        try {
            objEditorialDAO= new EditorialDAO();
            cbEditorial.setItems(FXCollections.observableArrayList(objEditorialDAO.selectAll()));
        } catch (SQLException e) {
            System.out.println("SQLException: "+e.getMessage());
        } catch(ClassNotFoundException e){
            System.out.println("ClassNotFoundException: "+e.getMessage());
        }
        
        
        //Format       
        ObservableList<Format> opcionsFormat = null;
        try {
            objFormatDAO= new FormatDAO();
            opcionsFormat = FXCollections.observableArrayList(objFormatDAO.selectAll());
            cbFormat.setItems(opcionsFormat);
        } catch (ClassNotFoundException ex) {
            System.out.println("ClassNotFoundException: "+ex.getMessage());
        } catch (SQLException ex) {
            System.out.println("SQLException: "+ex.getMessage());
        }  
        
        //Persona              
        ObservableList<Persona> opcionsPersona;
        try {
            objPersonaDAO = new PersonaDAO(); 
            opcionsPersona = FXCollections.observableArrayList(objPersonaDAO.selectAll());
            cbAutor.setItems(opcionsPersona);
        } catch (ClassNotFoundException ex) {
            System.out.println("ClassNotFoundException: "+ex.getMessage());
        } catch (SQLException ex) {
            System.out.println("SQLException: "+ex.getMessage());
        }
        
        //Nivell              
        ObservableList<Nivell> opcionsNivell = null;
        try {
            objNivellDAO= new NivellDAO(); 
            opcionsNivell = FXCollections.observableArrayList(objNivellDAO.selectAll());
            cbNivell.setItems(opcionsNivell);
        } catch(ClassNotFoundException ex){
            System.out.println("ClassNotFoundException: "+ex.getMessage());
        } catch (SQLException ex) {
            System.out.println("SQLException: "+ex.getMessage());
        }
        
        //Idioma        
        ObservableList<Idioma> opcionsIdioma = null;
        try{
            objIdiomaDAO= new IdiomaDAO();
            opcionsIdioma = FXCollections.observableArrayList(objIdiomaDAO.selectAll());
            cbIdioma.setItems(opcionsIdioma);
        }catch(ClassNotFoundException ex){
            System.out.println("ClassNotFoundException: "+ex.getMessage());
        } catch (SQLException ex) {
            System.out.println("SQLException: "+ex.getMessage());
        }
        
        //CDU    
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
        
        //Materia  
        ObservableList<Materia> opcionsMateria = null;
        try{
            objMateriaDAO= new MateriaDAO();       
            opcionsMateria = FXCollections.observableArrayList(objMateriaDAO.selectAll());
            cbMateria.setItems(opcionsMateria);
        }catch(ClassNotFoundException ex){
            System.out.println("ClassNotFoundException: "+ex.getMessage());
        } catch (SQLException ex) {
            System.out.println("SQLException: "+ex.getMessage());
        }

        //Procedencia  
        ObservableList<Procedencia> opcionsProcedencia = null;
        try{
            objProcedenciaDAO= new ProcedenciaDAO(); 
            opcionsProcedencia = FXCollections.observableArrayList(objProcedenciaDAO.selectAll());
            cbProcedencia.setItems(opcionsProcedencia);
        }catch(ClassNotFoundException ex){
            System.out.println("ClassNotFoundException: "+ex.getMessage());
        } catch (SQLException ex) {
            System.out.println("SQLException: "+ex.getMessage());
        }
        
        //Col·lecció  
        ObservableList<Coleccio> opcionsColeccio = null;
        try{
            objColeccioDAO= new ColeccioDAO(); 
            opcionsColeccio = FXCollections.observableArrayList(objColeccioDAO.selectAll());
            cbColeccio.setItems(opcionsColeccio);
        }catch(ClassNotFoundException ex){
            System.out.println("ClassNotFoundException: "+ex.getMessage());
        } catch (SQLException ex) {
            System.out.println("SQLException: "+ex.getMessage());
        }
        
        //Ara omplim els combobox a partir del text que s'ha escrit en ells(búsqueda)
        
        //Listener Editorial        
        ClEditorial clEditorial = new ClEditorial(cbEditorial);  
        cbEditorial.getEditor().textProperty().addListener(clEditorial);
        
        //Listener Format       
        ClFormat clFormat = new ClFormat(cbFormat);      
        cbFormat.getEditor().textProperty().addListener(clFormat);
            
        //Listener Nivell        
        ClNivell clNivell = new ClNivell(cbNivell);     
        cbNivell.getEditor().textProperty().addListener(clNivell);
        
        //Listener Idioma        
        ClIdioma clIdioma = new ClIdioma(cbIdioma);      
        cbIdioma.getEditor().textProperty().addListener(clIdioma);
        
        //Listener CDU        
        ClCdu clCdu = new ClCdu(cbCDU);
        cbCDU.getEditor().textProperty().addListener(clCdu);
        
        //Listener Persona        
        ClPersona clPersona = new ClPersona(cbAutor);
        cbAutor.getEditor().textProperty().addListener(clPersona);
        
        //Listener Col·lecció        
        ClColeccio clColeccio = new ClColeccio(cbColeccio);
        cbColeccio.getEditor().textProperty().addListener(clColeccio);
        
        //Listener Materia        
        ClMateria clMateria = new ClMateria(cbMateria);
        cbMateria.getEditor().textProperty().addListener(clMateria);
        
        //Listener Procedència       
        ClProcedencia clProcedencia = new ClProcedencia(cbProcedencia);
        cbProcedencia.getEditor().textProperty().addListener(clProcedencia);
    }
    
    //Métode que s'executa quan cliquem el botó Insertar imatge
    @FXML
    public void btnImatge(){
        FileChooser fileChooser = new FileChooser();
        configureFileChooser(fileChooser);
        Stage stage = (Stage) scrollPane.getScene().getWindow();           
           
        File file = fileChooser.showOpenDialog(stage);
        if (file != null) {
            setImage(file);
        }
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

    //Seguit de mètodes que obren PopUps(els que comencen per crear) i actualitzen el contingut dels combobox un cop s'ha creat un nou registre a la taula d'on s'estiren les dades per aquell combobox
    @FXML
    private void crearFormat() throws IOException {
        
        FXMLFormatController c = FXMLFormatController.crear(this, true, Crear);
        
        c.onAccept( (Object o)->{
            actualitzarFormat();
        }); 
        
        c.onCancel(()-> {
            System.out.println("S'ha tancat/cancel·lat");
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
            cbAutor.setItems(opcionsPersona);
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
        try {
            objEditorialDAO= new EditorialDAO();
            cbEditorial.setItems(FXCollections.observableArrayList(objEditorialDAO.selectAll()));
        } catch (SQLException e) {
            System.out.println("SQLException: "+e.getMessage());
        } catch(ClassNotFoundException e){
            System.out.println("ClassNotFoundException"+e.getMessage());
        }
        
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
            cbNivell.setItems(opcionsNivell);
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
    
    @FXML
    private void crearColeccio(ActionEvent event) throws IOException {
        
        FXMLColeccioController c = FXMLColeccioController.crear(this, true, Crear);
        
        c.onAccept( (Object o)->{
            actualitzarColeccio();
        });
        
        c.show();
    }
    
    public void actualitzarColeccio(){
        
        objColeccioDAO= new ColeccioDAO();       
        ObservableList<Coleccio> opcionsColeccio;
        try{
            opcionsColeccio = FXCollections.observableArrayList(objColeccioDAO.selectAll());
            cbColeccio.setItems(opcionsColeccio);
        }catch(ClassNotFoundException ex){
            System.out.println("ClassNotFoundException: "+ex.getMessage());
        } catch (SQLException ex) {
            System.out.println("SQLException: "+ex.getMessage());
        }
    }

    @FXML
    private void crearMateria(ActionEvent event) throws IOException {
        
        FXMLMateriaController c = FXMLMateriaController.crear(this, true, Crear);
        
        c.onAccept( (Object o)->{
            actualitzarMateria();
        });
        
        c.show();
    }
    
    public void actualitzarMateria(){
        
        objMateriaDAO = new MateriaDAO();       
        ObservableList<Materia> opcionsMateria;
        try{
            opcionsMateria = FXCollections.observableArrayList(objMateriaDAO.selectAll());
            cbMateria.setItems(opcionsMateria);
        }catch(ClassNotFoundException ex){
            System.out.println("ClassNotFoundException: "+ex.getMessage());
        } catch (SQLException ex) {
            System.out.println("SQLException: "+ex.getMessage());
        }
    }
    
     @FXML
    private void crearProcedencia(ActionEvent event) throws IOException {
        
        FXMLProcedenciaController c = FXMLProcedenciaController.crear(this, true, Crear);
        
        c.onAccept( (Object o)->{
            actualitzarProcedencia();
        });
        
        c.show();
    }
    
    public void actualitzarProcedencia(){
        
        objProcedenciaDAO = new ProcedenciaDAO();       
        ObservableList<Procedencia> opcionsProcedencia;
        try{
            opcionsProcedencia = FXCollections.observableArrayList(objProcedenciaDAO.selectAll());
            cbProcedencia.setItems(opcionsProcedencia);
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
         
        pathImatge = file.toURI().toString(); 
        Image imatge = new Image(pathImatge);
        imgProducte.setImage(imatge);
    }
    
     //Mètode que s'executa quan cliquem el botó cancel·lar
    @FXML
    public void cancelar(){
       ((Stage) (btnCancelar.getScene().getWindow())).close();
        System.out.println("Cancel·lar");
    }

    @Override
    public void emplenarDades(Object obj) {  
        
        switch(tipusA){
                case Modificar:
                    btnCrear.setText("Modificar");     
                    break;
                case Deshabilitar:
                    btnCrear.setText("Deshabilitar");     
                    break;
                case Buscar:
                    btnCrear.setText("Visualitzar");
                    btnCrear.setDisable(true);
                    break;
            }     
               
        Producte producte = (Producte) obj;
        boolean nouCdu = true;
        
        if(producte!=null){
            cbFormat.setValue(producte.getFormat());
            tfISBN.setText(producte.getISBN());            
            
            String llistatCdus = producte.getCdu();
            String[] cdus = llistatCdus.split("+");
            
            for (int i = 0; i < cdus.length; i++) {
                itemsCdus.add(cdus[i]);
            }
            LvCdus.setItems(itemsCdus);           
            
            tfAny.setText(producte.getAnyPublicacio());
            tfNumPag.setText(String.valueOf(producte.getNumPag()));
            cbColeccio.setValue(producte.getColeccio());
            tfTitol.setText(producte.getNom());
            
            Set llistaAutors = producte.getAutors();
            llistaAutors.stream().forEach((objs) -> {
                Persona persona = (Persona) objs;                
                itemsAutors.add(persona);                
            });            
            LvAutors.setItems(itemsAutors);
                      
            //itemsAutors.addAll(producte.getAutors());
            
            cbEditorial.setValue(producte.getEditorial());
            
            Set llistaNivells = producte.getNivells();
            llistaNivells.stream().forEach((objs) -> {
                Nivell nivell = (Nivell) objs;
                itemsNivells.add(nivell);
            });
            LvNivells.setItems(itemsNivells);
                      
            //itemsNivells.addAll(producte.getNivells());
            
            tfDimensions.setText(producte.getDimensions());

            Set llistaMateries = producte.getMateries();
            llistaMateries.stream().forEach((objs) -> {
                Materia materia = (Materia) objs;
                itemsMateries.add(materia);
            });
            LvMateries.setItems(itemsMateries);
            
            //itemsMateries.addAll(producte.getMateries());
            
            cbProcedencia.setValue(producte.getProcedencia());
            
            Set llistaIdiomes = producte.getIdiomes();
            llistaIdiomes.stream().forEach((objs) -> {
                Idioma idioma = (Idioma) objs;
                itemsIdiomes.add(idioma);
            }); 
            LvIdiomes.setItems(itemsIdiomes);            
            
            tfAdresaWeb.setText(producte.getAdreçaWeb());
            tfPais.setText(producte.getPais());
            tfLloc.setText(producte.getLloc());
            taResum.setText(producte.getResum());
            taCaracteristiques.setText(producte.getCaracteristiques());
            chbActiu.setSelected(producte.getEstat());
        }        
    }

    @FXML
    private void afegirAutor(ActionEvent event) {
        OpcionsListView.afeguirDadeListView(cbAutor, LvAutors, itemsAutors);
    }

    @FXML
    private void eliminarAutor(ActionEvent event) {
        OpcionsListView.elimDadeListView(LvAutors,itemsAutors);
    }

    @FXML
    private void afegirNivell(ActionEvent event) {
        OpcionsListView.afeguirDadeListView(cbNivell, LvNivells, itemsNivells);
    }

    @FXML
    private void eliminarNivell(ActionEvent event) {
        OpcionsListView.elimDadeListView(LvNivells,itemsNivells);
    }

    @FXML
    private void afegirMateria(ActionEvent event) {
        OpcionsListView.afeguirDadeListView(cbMateria, LvMateries, itemsMateries);
    }

    @FXML
    private void eliminarMateria(ActionEvent event) {
        OpcionsListView.elimDadeListView(LvMateries,itemsMateries);
    }

    @FXML
    private void afegirIdioma(ActionEvent event) {
        OpcionsListView.afeguirDadeListView(cbIdioma, LvIdiomes, itemsIdiomes);
    }

    @FXML
    private void eliminarIdioma(ActionEvent event) {
        OpcionsListView.elimDadeListView(LvIdiomes,itemsIdiomes);
    }

    @FXML
    private void afegirCdu(ActionEvent event) {
        OpcionsListView.afeguirDadeListView(cbCDU, LvCdus, itemsCdus);
    }

    @FXML
    private void eliminarCdu(ActionEvent event) {
        OpcionsListView.elimDadeListView(LvCdus,itemsCdus);
    }
}