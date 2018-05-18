package base.maintenanceControladors;

import base.FXMLCduController;
import base.FXMLColeccioController;
import base.GenericMaintenanceControlador;
import base.GenericPopUp;
import bbdd.CduDAO;
import bbdd.ColeccioDAO;
import contract.ContractCdu;
import contract.ContractColeccio;
import excepcions.MaintenanceException;
import maintenance.AttributeBrick;
import maintenance.AttributeWall;
import objecte.Nivell;

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
public class CduMaintenanceControlador extends GenericMaintenanceControlador implements AttributeWall {

    public CduMaintenanceControlador() {
        super( "Cdu", 15);
    }
    
    // AttributeWall
    @Override
    public List<AttributeBrick> getAttributeWall() {

        List<AttributeBrick> attributeWall = Arrays.asList(
            new AttributeBrick( "id", "ID", true, ContractCdu.ID, AttributeBrick.allowedFormats.Integer ),
            new AttributeBrick( "nom", "Nom", true, ContractCdu.NOM, AttributeBrick.allowedFormats.String ),
            new AttributeBrick( "idPare", "IDPare", true, ContractCdu.IDPARE, AttributeBrick.allowedFormats.Integer )
        );
        
        return attributeWall;
    }

    @Override
    public Integer parseObject( String contractName, Object object ) throws MaintenanceException {
        return 0;
    }

    @Override
    public List searchOcurrences( HashMap<String, Object> data, Integer startItem, Integer limitXPage, String attribToOrder, Boolean sortType ) throws SQLException, ClassNotFoundException, IllegalArgumentException {
        return new CduDAO().select( data, attribToOrder, limitXPage, startItem, sortType );
    }

    @Override
    public Integer getTotalItems( HashMap<String, Object> data ) throws ClassNotFoundException, SQLException {
        //return new CduDAO().selectCount( data );
        return 0;
    }
    
    // GenericMaintenanceControlador
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        super.initialize(url, rb); // optional custom init
    }

    @Override
    public GenericPopUp createPopUpObject(GenericPopUp.TipusAccio tipusAccio) throws IOException {
        return FXMLCduController.crear(this.getScene().getWindow(), true, tipusAccio);
    }

    @Override
    public GenericPopUp createPopUpAdvSearch(GenericPopUp.TipusAccio tipusAccio) throws IOException {
        return FXMLCduController.crear( this.getScene().getWindow(), true, tipusAccio );
    }

}
