package base.maintenanceControladors;

import base.FXMLNivellController;
import base.GenericMaintenanceControlador;
import base.GenericPopUp;
import bbdd.NivellDAO;
import contract.ContractNivell;
import excepcions.MaintenanceException;
import maintenance.AttributeBrick;
import maintenance.AttributeWall;
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
public class NivellMaintenanceControlador extends GenericMaintenanceControlador implements AttributeWall {

    public NivellMaintenanceControlador() {
        super( "Nivell", 15);
    }
    
    // AttributeWall
    @Override
    public List<AttributeBrick> getAttributeWall() {

        List<AttributeBrick> attributeWall = Arrays.asList(
            new AttributeBrick( "id", "ID", true, ContractNivell.ID, AttributeBrick.allowedFormats.Integer ),
            new AttributeBrick( "nom", "Nom", true, ContractNivell.NOM, AttributeBrick.allowedFormats.String )
        );
        
        return attributeWall;
    }

    @Override
    public Object parseObject( String contractName, Object object ) throws MaintenanceException {
        
        Object id = 0;
        if ( object != null ) {
            
            switch( contractName ) {
                // If add some object consider the return statement
                default:
                    throw new MaintenanceException( "This object: " + contractName + " not parsed in a parseObject" );
            }

        } else {
            
            throw new IllegalArgumentException( "Invalid data" );
            
        }
        
        //return id;
        
    }
    
    @Override
    public void parseCombo(String contractName, ComboBox combo) throws MaintenanceException {
        
        switch( contractName ) {
            default:
                throw new MaintenanceException( "This object: " + contractName + " not parsed in a parseCombo" );
        }
        
    }

    @Override
    public List searchOcurrences( HashMap<String, Object> data, Integer startItem, Integer limitXPage, String attribToOrder, Boolean sortType ) throws SQLException, ClassNotFoundException, IllegalArgumentException {
        return new NivellDAO().select( data, attribToOrder, limitXPage, startItem, sortType );
    }

    @Override
    public Integer getTotalItems( HashMap<String, Object> data ) throws ClassNotFoundException, SQLException {
        return new NivellDAO().selectCount( data );
    }
    
    // GenericMaintenanceControlador
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        super.initialize(url, rb); // optional custom init
        
        _lblAdvancedSearch.setDisable( true );
        _lblAdvancedSearch.setVisible( false );
        
    }

    @Override
    public GenericPopUp createPopUpObject(GenericPopUp.TipusAccio tipusAccio) throws IOException {
        return FXMLNivellController.crear(this.getScene().getWindow(), true, tipusAccio);
    }

    @Override
    public GenericPopUp createPopUpAdvSearch() throws MaintenanceException {
        throw new MaintenanceException( "Not implemented" );
    }

}
