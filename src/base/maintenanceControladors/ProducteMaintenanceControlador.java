package base.maintenanceControladors;

import base.EmplanarComboBox.ClCdu;
import base.EmplanarComboBox.ClColeccio;
import base.EmplanarComboBox.ClEditorial;
import base.EmplanarComboBox.ClFormat;
import base.EmplanarComboBox.ClIdioma;
import base.EmplanarComboBox.ClMateria;
import base.EmplanarComboBox.ClNivell;
import base.EmplanarComboBox.ClPersona;
import base.EmplanarComboBox.ClProcedencia;
import base.FXMLProducteController;
import base.GenericMaintenanceControlador;
import base.GenericPopUp;
import bbdd.ProducteAutorDAO;
import bbdd.ProducteDAO;
import bbdd.ProducteIdiomaDAO;
import bbdd.ProducteMateriaDAO;
import bbdd.ProducteNivellDAO;
import contract.ContractExemplar;
import contract.ContractMateriaProducte;
import contract.ContractProducte;
import contract.ContractProducteIdioma;
import contract.ContractProducteNivell;
import excepcions.MaintenanceException;
import maintenance.AttributeBrick;
import maintenance.AttributeWall;
import objecte.*;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;
import javafx.scene.control.ComboBox;

/**
 * @author Albert Corominas
 */
public class ProducteMaintenanceControlador extends GenericMaintenanceControlador implements AttributeWall {

    private final String nivells = "nivells";
    private final String idiomes = "idiomes";
    private final String materies = "materias";
    private final String coleccio = "coleccio";
    private final String autors = "autors";
    private final String editorial = "editorial";
    private final String format = "format";
    private final String procedencia = "procedencia";
    private final String cdu = "cdu";
    
    public ProducteMaintenanceControlador() {
        super( "Productes", 15);
    }
    
    // AttributeWall
    @Override
    public List<AttributeBrick> getAttributeWall() {

        List<AttributeBrick> attributeWall = Arrays.asList(
            new AttributeBrick( "id", "ID", true, ContractProducte.ID, AttributeBrick.allowedFormats.Integer),
            new AttributeBrick( "ISBN", "ISBN", true, ContractProducte.ISBN, AttributeBrick.allowedFormats.String),
            new AttributeBrick( "nom", "Nom", true, ContractProducte.NOM, AttributeBrick.allowedFormats.String),
            new AttributeBrick( "numPag", "Numero Pagines", false, ContractProducte.NUM_PAG, AttributeBrick.allowedFormats.Integer),
            new AttributeBrick( "dimensions", "Dimensions", false, ContractProducte.DIMENSIONS, AttributeBrick.allowedFormats.String),
            new AttributeBrick( "anyPublicacio", "Any Publicacio", false, ContractProducte.ANY_PUBLICACIO, AttributeBrick.allowedFormats.String),
            new AttributeBrick( "resum", "Resum", false, ContractProducte.RESUM, AttributeBrick.allowedFormats.String),
            new AttributeBrick( "caracterisitques", "Caracteristiques", false, ContractProducte.CARACTERISTIQUES, AttributeBrick.allowedFormats.String),
            new AttributeBrick( "urlPortada", "Url Portada", false, ContractProducte.URL_PORTADA, AttributeBrick.allowedFormats.String),
            new AttributeBrick( "adrecaWeb", "Adreca Web", false, ContractProducte.ADRECA_WEB, AttributeBrick.allowedFormats.String),
            new AttributeBrick( "lloc", "Lloc", false, ContractProducte.LLOC, AttributeBrick.allowedFormats.String),
            new AttributeBrick( "pais", "Païs", false, ContractProducte.PAIS, AttributeBrick.allowedFormats.String),
            new AttributeBrick( "estat", "Estat", false, ContractProducte.ESTAT, AttributeBrick.allowedFormats.Boolean),
            new AttributeBrick( "coleccio", "Coleccio", false, ContractProducte.COLECCIO_ID, AttributeBrick.allowedFormats.Object),
            new AttributeBrick( idiomes, "Idioma", false, ContractProducte.IDIOMA_ID, AttributeBrick.allowedFormats.List),
            new AttributeBrick( editorial, "Editorial", false, ContractProducte.EDITORIAL_ID, AttributeBrick.allowedFormats.Object),
            new AttributeBrick( format, "Format", false, ContractProducte.FORMAT_ID, AttributeBrick.allowedFormats.Object),
            new AttributeBrick( procedencia, "Procedencia", false, ContractProducte.PROCEDENCIA_ID, AttributeBrick.allowedFormats.Object),
            new AttributeBrick( nivells, "Nivell", false, ContractProducte.NIVELL, AttributeBrick.allowedFormats.List),
            new AttributeBrick( autors, "Autors", false, ContractProducte.AUTORS, AttributeBrick.allowedFormats.List),
            new AttributeBrick( cdu, "Cdu", false, ContractProducte.CDU, AttributeBrick.allowedFormats.String),
            new AttributeBrick( materies, "Materia", false, ContractProducte.MATERIA, AttributeBrick.allowedFormats.List)
        );
        
        return attributeWall;
    }

    @Override
    public Object parseObject( String contractName, Object object ) throws MaintenanceException {

        Object id = 0;
        if ( object != null ) {
            
            switch( contractName ) {
                case nivells:
                    id = ((Nivell) object).getId();
                    break;
                case idiomes:
                    id = ((Idioma) object).getId();
                    break;
                case materies:
                    id = ((Materia) object).getId();
                    break;
                case autors:
                    id = ((Persona) object).getId();
                    break;
                case editorial:
                    id = ((Editorial) object).getId();
                    break;
                case format:
                    id = ((Format) object).getId();
                    break;
                case procedencia:
                    id = ((Procedencia) object).getId();
                    break;
                case coleccio:
                    id = ((Coleccio) object).getId();
                    break;
                case cdu:
                    id = ((Cdu) object).getId();
                    break;
                default:
                    throw new MaintenanceException( "This object: " + contractName + " not parsed in a parseObject" );
            }

        } else {
            
            throw new IllegalArgumentException( "Invalid data" );
            
        }
        
        return id;

    }

    @Override
    public void parseCombo(String contractName, ComboBox combo) throws MaintenanceException {

        switch( contractName ) {
            case nivells:
                combo.getEditor().textProperty().addListener( new ClNivell( combo ) );                        
                break;
            case idiomes:
                combo.getEditor().textProperty().addListener( new ClIdioma( combo ) );                        
                break;
            case materies:
                combo.getEditor().textProperty().addListener( new ClMateria( combo ) );
                break;
            case autors:
                combo.getEditor().textProperty().addListener( new ClPersona( combo ) );
                break;
            case editorial:
                combo.getEditor().textProperty().addListener( new ClEditorial( combo ) );                        
                break;
            case format:
                combo.getEditor().textProperty().addListener( new ClFormat( combo ) );                        
                break;
            case procedencia:
                combo.getEditor().textProperty().addListener( new ClProcedencia( combo ) );                        
                break;
            case coleccio:
                combo.getEditor().textProperty().addListener( new ClColeccio( combo ) );                        
                break;
            case cdu:
                combo.getEditor().textProperty().addListener( new ClCdu( combo ) );                        
                break;
            default:
                throw new MaintenanceException( "This object: " + contractName + " not parsed in a parseCombo" );
        }
        
    }
    
    @Override
    public List searchOcurrences( HashMap<String, Object> data, Integer startItem, Integer limitXPage, String attribToOrder, Boolean sortType ) throws SQLException, ClassNotFoundException, IllegalArgumentException {
        
        ProducteMateriaDAO matDAO = new ProducteMateriaDAO();
        ProducteIdiomaDAO idiDAO = new ProducteIdiomaDAO();
        ProducteNivellDAO nivDAO     = new ProducteNivellDAO();
        ProducteAutorDAO autDAO = new ProducteAutorDAO();
        
        List< Producte > llista = new ProducteDAO().select( data, attribToOrder, limitXPage, startItem, sortType );
        
        
            llista.forEach( ( producte ) -> {
                
                try {
                    producte.setIdiomes( idiDAO.selectIdiomes( producte.getId() ) );
                    producte.setMateries( matDAO.selectMateries( producte.getId() ) );
                    producte.setNivells( nivDAO.selectNivells( producte.getId() ) );
                    //producte.setAutors( autors );
                } catch (SQLException | ClassNotFoundException ex) {
                    // Set in logger
                    // Open a generic alert
                }
                
            });

        return llista;
        
    }

    @Override
    public Integer getTotalItems( HashMap<String, Object> data ) throws ClassNotFoundException, SQLException {
        return new ProducteDAO().selectCount( data );
    }
    
    // GenericMaintenanceControlador
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        super.initialize(url, rb); // optional custom init
    }

    @Override
    public GenericPopUp createPopUpObject(GenericPopUp.TipusAccio tipusAccio) throws IOException {
        return FXMLProducteController.crear(this.getScene().getWindow(), true, tipusAccio);
    }

    @Override
    public GenericPopUp createPopUpAdvSearch(GenericPopUp.TipusAccio tipusAccio) throws IOException {
        return FXMLProducteController.crear( this.getScene().getWindow(), true, tipusAccio );
    }

}
