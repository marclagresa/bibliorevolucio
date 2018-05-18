package base.maintenanceControladors;

import base.FXMLProducteController;
import base.GenericMaintenanceControlador;
import base.GenericPopUp;
import bbdd.ProducteDAO;
import contract.ContractExemplar;
import contract.ContractMateria;
import contract.ContractMateriaProducte;
import contract.ContractProducte;
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

/**
 * @author Albert Corominas
 */
public class ProducteMaintenanceControlador extends GenericMaintenanceControlador implements AttributeWall {

    public ProducteMaintenanceControlador() {
        super( "Producte", 15);
    }
    
    // AttributeWall
    @Override
    public List<AttributeBrick> getAttributeWall() {

        List<AttributeBrick> attributeWall = Arrays.asList(
            new AttributeBrick( "id", "ID", true, ContractProducte.ID, AttributeBrick.allowedFormats.Integer),
            new AttributeBrick( "ISBN", "ISBN", true, ContractProducte.ISBN, AttributeBrick.allowedFormats.String),
            new AttributeBrick( "nom", "Nom", true, ContractProducte.NOM, AttributeBrick.allowedFormats.String),
            new AttributeBrick( "numPag", "Numero Pagines", true, ContractProducte.NUM_PAG, AttributeBrick.allowedFormats.Integer),
            new AttributeBrick( "dimensions", "Dimensions", true, ContractProducte.DIMENSIONS, AttributeBrick.allowedFormats.String),
            new AttributeBrick( "anyPublicacio", "Any Publicacio", true, ContractProducte.ANY_PUBLICACIO, AttributeBrick.allowedFormats.String),
            new AttributeBrick( "resum", "Resum", true, ContractProducte.RESUM, AttributeBrick.allowedFormats.String),
            new AttributeBrick( "caracterisitques", "Caracteristiques", true, ContractProducte.CARACTERISTIQUES, AttributeBrick.allowedFormats.String),
            new AttributeBrick( "urlPortada", "Url Portada", true, ContractProducte.URL_PORTADA, AttributeBrick.allowedFormats.String),
            new AttributeBrick( "adrecaWeb", "Adreca Web", true, ContractProducte.ADRECA_WEB, AttributeBrick.allowedFormats.String),
            new AttributeBrick( "lloc", "Lloc", true, ContractProducte.LLOC, AttributeBrick.allowedFormats.String),
            new AttributeBrick( "estat", "Estat", true, ContractProducte.ESTAT, AttributeBrick.allowedFormats.Boolean),
            new AttributeBrick( "idioma", "Idioma", true, ContractProducte.IDIOMA_ID, AttributeBrick.allowedFormats.Object),
            new AttributeBrick( "editorial", "Editorial", true, ContractProducte.EDITORIAL_ID, AttributeBrick.allowedFormats.Object),
            new AttributeBrick( "format", "Format", true, ContractProducte.FORMAT_ID, AttributeBrick.allowedFormats.Object),
            new AttributeBrick( "procedencia", "Procedencia", true, ContractProducte.PROCEDENCIA_ID, AttributeBrick.allowedFormats.Object),
            new AttributeBrick( "nivell", "Nivell", true, ContractProducte.NIVELL_ID, AttributeBrick.allowedFormats.Object),
            new AttributeBrick( "coleccio", "Coleccio", true, ContractProducte.COLECCIO_ID, AttributeBrick.allowedFormats.Object),
            new AttributeBrick( "cdu", "Cdu", true, ContractProducte.CDU_ID, AttributeBrick.allowedFormats.Object),
            new AttributeBrick( "exemplar", "Exemplar", true, ContractExemplar.ID, AttributeBrick.allowedFormats.Object),
            new AttributeBrick( "materia", "Materia", true, ContractMateriaProducte.ID_MATERIA, AttributeBrick.allowedFormats.Object)
        );
        
        return attributeWall;
    }

    @Override
    public Integer parseObject( String contractName, Object object ) throws MaintenanceException {

        int id = 0;
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

        return id;

    }

    @Override
    public List searchOcurrences( HashMap<String, Object> data, Integer startItem, Integer limitXPage, String attribToOrder, Boolean sortType ) throws SQLException, ClassNotFoundException, IllegalArgumentException {
        return new ProducteDAO().select( data, attribToOrder, limitXPage, startItem, sortType );
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
