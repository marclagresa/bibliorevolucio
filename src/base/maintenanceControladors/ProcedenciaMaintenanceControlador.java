package base.maintenanceControladors;

import base.FXMLProcedenciaController;
import base.GenericMaintenanceControlador;
import base.GenericPopUp;
import bbdd.ProcedenciaDAO;
import contract.ContractProcedencia;
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

/**
 * @author Albert Corominas
 */
public class ProcedenciaMaintenanceControlador extends GenericMaintenanceControlador implements AttributeWall {

    public ProcedenciaMaintenanceControlador() {
        super( "Procedencia", 15);
    }
    
    // AttributeWall
    @Override
    public List<AttributeBrick> getAttributeWall() {

        List<AttributeBrick> attributeWall = Arrays.asList(
            new AttributeBrick( "id", "ID", true, ContractProcedencia.ID, AttributeBrick.allowedFormats.Integer ),
            new AttributeBrick( "nom", "Nom", true, ContractProcedencia.NOM, AttributeBrick.allowedFormats.String )
        );
        
        return attributeWall;
    }

    @Override
    public Integer parseObject( String contractName, Object object ) throws MaintenanceException {
        return 0;
    }

    @Override
    public List searchOcurrences( HashMap<String, Object> data, Integer startItem, Integer limitXPage, String attribToOrder, Boolean sortType ) throws SQLException, ClassNotFoundException, IllegalArgumentException {
        return new ProcedenciaDAO().select( data, attribToOrder, limitXPage, startItem, sortType );
    }

    @Override
    public Integer getTotalItems( HashMap<String, Object> data ) throws ClassNotFoundException, SQLException {
        return new ProcedenciaDAO().selectCount( data );
    }
    
    // GenericMaintenanceControlador
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        super.initialize(url, rb); // optional custom init
    }

    @Override
    public GenericPopUp createPopUpObject(GenericPopUp.TipusAccio tipusAccio) throws IOException {
        return FXMLProcedenciaController.crear(this.getScene().getWindow(), true, tipusAccio);
    }

    @Override
    public GenericPopUp createPopUpAdvSearch(GenericPopUp.TipusAccio tipusAccio) throws IOException {
        return FXMLProcedenciaController.crear( this.getScene().getWindow(), true, tipusAccio );
    }

}
