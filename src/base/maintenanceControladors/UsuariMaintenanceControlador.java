package base.maintenanceControladors;

import base.FXMLCduController;
import base.GenericPopUp;
import base.EmplanarComboBox.ClNivell;
import base.FXMLUsuariController;
import bbdd.UsuariDAO;
import contract.ContractUsuari;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;
import objecte.Nivell;
import maintenance.AttributeBrick;
import base.GenericMaintenanceControlador;
import excepcions.MaintenanceException;
import javafx.scene.control.ComboBox;
import maintenance.AttributeWall;

/**
 *
 * @author Rafel
 */
public class UsuariMaintenanceControlador extends GenericMaintenanceControlador implements AttributeWall {

    private final String nivell = "nivell";
    
    public UsuariMaintenanceControlador() {
        super( "Usuaris", 15);
    }
    
    // AttributeWall
    @Override
    public List<AttributeBrick> getAttributeWall() {

        List<AttributeBrick> attributeWall = Arrays.asList(
            new AttributeBrick( "id", "ID", true, ContractUsuari.ID, AttributeBrick.allowedFormats.Integer ),
            new AttributeBrick( "nom", "Nom", true, ContractUsuari.NOM, AttributeBrick.allowedFormats.String ),
            new AttributeBrick( "pCognom", "Primer Cognom", false, ContractUsuari.PRIMER_COGNOM, AttributeBrick.allowedFormats.String ),
            new AttributeBrick( "sCognom", "Segon Cognom", false, ContractUsuari.SEGON_COGNOM, AttributeBrick.allowedFormats.String),
            new AttributeBrick( "telefonMobil", "T.Mòbil", false, ContractUsuari.TELEFON_MOBIL, AttributeBrick.allowedFormats.String ),
            new AttributeBrick( "telefonFixe", "T.Fixe", false, ContractUsuari.TELEFON_FIX, AttributeBrick.allowedFormats.String ),
            new AttributeBrick( "email", "Correu", false, ContractUsuari.EMAIL, AttributeBrick.allowedFormats.String ),
            new AttributeBrick( "actiu", "Actiu", false, ContractUsuari.ACTIU, AttributeBrick.allowedFormats.Boolean ),
            new AttributeBrick( "admin", "Administrador", false, ContractUsuari.ADMIN, AttributeBrick.allowedFormats.Boolean ),
            new AttributeBrick( nivell, "Nivell", false, ContractUsuari.ID_NIVELL, AttributeBrick.allowedFormats.Object )
        );
        
        return attributeWall;
        
    }

    @Override
    public Object parseObject( String name, Object object ) throws MaintenanceException, IllegalArgumentException{
        
        Object id = null;
        if ( object != null ) {
            
            switch( name ) {
                case nivell:
                    id = ((Nivell) object).getId();
                    break;
                default:
                    throw new MaintenanceException( "This object: " + name + " not parsed in a parseObject" );
            }
            
        } else {
            
            throw new IllegalArgumentException( "Invalid data" );
            
        }
        
        return id;

    }

    @Override
    public void parseCombo( String name, ComboBox combo ) throws MaintenanceException {
        
        switch( name ) {
            case nivell:
                combo.getEditor().textProperty().addListener( new ClNivell( combo ) );                        
                break;
            default:
                throw new MaintenanceException( "This object: " + name + " not parsed in a parseCombo" );
        }
        
    }
    
    @Override
    public List searchOcurrences( HashMap<String, Object> data, Integer startItem, Integer limitXPage, String attribToOrder, Boolean sortType ) throws SQLException, ClassNotFoundException, IllegalArgumentException {

        return new UsuariDAO().select( data, attribToOrder, limitXPage, startItem, sortType );    
    
    }

    @Override
    public Integer getTotalItems( HashMap<String, Object> data ) throws ClassNotFoundException, SQLException {
        
        return new UsuariDAO().selectCount( data );
        
    }
    
    // GenericMaintenanceControlador
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        super.initialize(url, rb); // optional custom init
        
    }

    @Override
    public GenericPopUp createPopUpObject(GenericPopUp.TipusAccio tipusAccio) throws IOException {
        
        return FXMLUsuariController.crear( this.getScene().getWindow(), true, tipusAccio );
        
    }

    @Override
    public GenericPopUp createPopUpAdvSearch() throws IOException {
        // ------- AREGLAR ------
        return null;// FXMLUsuariController.crear( this.getScene().getWindow(), true );
        
    }

}
