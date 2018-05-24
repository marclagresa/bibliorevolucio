package base.maintenanceControladors;

import base.EmplanarComboBox.ClCdu;
import base.EmplanarComboBox.ClColeccio;
import base.EmplanarComboBox.ClEditorial;
import base.EmplanarComboBox.ClFormat;
import base.EmplanarComboBox.ClIdioma;
import base.EmplanarComboBox.ClNivell;
import base.EmplanarComboBox.ClProcedencia;
import base.FXMLProducteController;
import base.GenericMaintenanceControlador;
import base.GenericPopUp;
import bbdd.ProducteAutorDAO;
import bbdd.ProducteDAO;
import bbdd.ProducteIdiomaDAO;
import bbdd.ProducteMateriaDAO;
import bbdd.ProducteNivellDAO;
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
            new AttributeBrick( "estat", "Estat", false, ContractProducte.ESTAT, AttributeBrick.allowedFormats.Boolean),
            new AttributeBrick( "idiomes", "Idioma", true, ContractProducteIdioma.ID_IDIOMA, AttributeBrick.allowedFormats.List),
            new AttributeBrick( "editorial", "Editorial", false, ContractProducte.EDITORIAL_ID, AttributeBrick.allowedFormats.Object),
            new AttributeBrick( "format", "Format", false, ContractProducte.FORMAT_ID, AttributeBrick.allowedFormats.Object),
            new AttributeBrick( "procedencia", "Procedencia", false, ContractProducte.PROCEDENCIA_ID, AttributeBrick.allowedFormats.Object),
            new AttributeBrick( "nivells", "Nivell", true, ContractProducteNivell.ID_NIVELL, AttributeBrick.allowedFormats.List),
            new AttributeBrick( "coleccio", "Coleccio", false, ContractProducte.COLECCIO_ID, AttributeBrick.allowedFormats.Object),
            new AttributeBrick( "cdu", "Cdu", false, ContractProducte.CDU, AttributeBrick.allowedFormats.String),
            new AttributeBrick( "exemplars", "Exemplar", true, "--- Arreglar ---", AttributeBrick.allowedFormats.List),
            new AttributeBrick( "materias", "Materia", true, ContractMateriaProducte.ID_MATERIA, AttributeBrick.allowedFormats.List)
        );
        
        return attributeWall;
    }

    @Override
    public Object parseObject( String contractName, Object object ) throws MaintenanceException {

        Object id = 0;
        if ( object != null ) {
            
            switch( contractName ) {
                case "nivell":
                    id = ((Nivell) object).getId();
                    break;
                case "idioma":
                    id = ((Idioma) object).getId();
                    break;
                case "editorial":
                    id = ((Editorial) object).getId();
                    break;
                case "format":
                    id = ((Format) object).getId();
                    break;
                case "procedencia":
                    id = ((Procedencia) object).getId();
                    break;
                case "coleccio":
                    id = ((Coleccio) object).getId();
                    break;
                case "cdu":
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
    public List searchOcurrences( HashMap<String, Object> data, Integer startItem, Integer limitXPage, String attribToOrder, Boolean sortType ) throws SQLException, ClassNotFoundException, IllegalArgumentException {
        
        ProducteMateriaDAO materies = new ProducteMateriaDAO();
        ProducteIdiomaDAO idiomes = new ProducteIdiomaDAO();
        ProducteNivellDAO nivells = new ProducteNivellDAO();
        ProducteAutorDAO autors = new ProducteAutorDAO();
        
        List< Producte > llista = new ProducteDAO().select( data, attribToOrder, limitXPage, startItem, sortType );
        
        
            llista.forEach( ( producte ) -> {
                
                try {
                    producte.setIdiomes( idiomes.selectIdiomes( producte.getId() ) );
                    producte.setMateries( materies.selectMateries( producte.getId() ) );
                    producte.setNivells( nivells.selectNivells( producte.getId() ) );
                    //producte.setAutors( autors.);
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

    @Override
    public void parseCombo(String contractName, ComboBox combo) throws MaintenanceException {

        switch( contractName ) {
            case "nivell":
                combo.getEditor().textProperty().addListener( new ClNivell( combo ) );                        
                break;
            case "idioma":
                combo.getEditor().textProperty().addListener( new ClIdioma( combo ) );                        
                break;
            case "editorial":
                combo.getEditor().textProperty().addListener( new ClEditorial( combo ) );                        
                break;
            case "format":
                combo.getEditor().textProperty().addListener( new ClFormat( combo ) );                        
                break;
            case "procedencia":
                combo.getEditor().textProperty().addListener( new ClProcedencia( combo ) );                        
                break;
            case "coleccio":
                combo.getEditor().textProperty().addListener( new ClColeccio( combo ) );                        
                break;
            case "cdu":
                combo.getEditor().textProperty().addListener( new ClCdu( combo ) );                        
                break;
            default:
                throw new MaintenanceException( "This object: " + contractName + " not parsed in a parseCombo" );
        }
        
    }

}
