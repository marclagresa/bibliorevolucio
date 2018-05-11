package base.maintenanceControladors;

import base.FXMLCduController;
import base.GenericPopUp;
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
import maintenance.AttributeWall;

/**
 *
 * @author Rafel
 */
public class UsuariMaintenanceControlador extends GenericMaintenanceControlador implements AttributeWall {
   
    @Override
    public String titleList() {
        return "Usuaris";
    }
    
    

    @Override
    public List<AttributeBrick> getAttributeWall() {

        List<AttributeBrick> attributeWall = Arrays.asList(
            new AttributeBrick( "id", "ID", true, ContractUsuari.ID, AttributeBrick.allowedFormats.Integer ),
            new AttributeBrick( "nom", "Nom", true, ContractUsuari.NOM, AttributeBrick.allowedFormats.String ),
            new AttributeBrick( "pcognom", "Primer Cognom", false, ContractUsuari.PRIMER_COGNOM, AttributeBrick.allowedFormats.String ),
            new AttributeBrick( "scognom", "Segon Cognom", false, ContractUsuari.SEGON_COGNOM, AttributeBrick.allowedFormats.String),
            new AttributeBrick( "telefonMobil", "T.Mòbil", false, ContractUsuari.TELEFON_MOBIL, AttributeBrick.allowedFormats.String ),
            new AttributeBrick( "telefonFixe", "T.Fixe", false, ContractUsuari.TELEFON_FIX, AttributeBrick.allowedFormats.String ),
            new AttributeBrick( "email", "Correu", false, ContractUsuari.EMAIL, AttributeBrick.allowedFormats.String ),
            new AttributeBrick( "estat", "Estat", false, ContractUsuari.ACTIU, AttributeBrick.allowedFormats.Boolean ),
            new AttributeBrick( "esAdmin", "Administrador", false, ContractUsuari.ADMIN, AttributeBrick.allowedFormats.Boolean ),
            new AttributeBrick( "nivell", "Nivell", false, ContractUsuari.ID_NIVELL, AttributeBrick.allowedFormats.Object )
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
            default:
                throw new MaintenanceException( "This object: " + contractName + " not parsed ina parseObject" );
        }
        
        return id;

    }

    @Override
    public List searchOcurrences(HashMap<String, Object> data) throws SQLException, ClassNotFoundException, IllegalArgumentException {
       
        return new UsuariDAO().select( data );    
    
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        super.initialize(url, rb); // optional custom init
        
    }

    @Override
    public GenericPopUp createPopUpObject(GenericPopUp.TipusAccio tipusAccio) throws IOException {
        
        return FXMLCduController.crear( this.getScene().getWindow(), true, tipusAccio );
        
    }

    @Override
    public GenericPopUp createPopUpAdvSearch(GenericPopUp.TipusAccio tipusAccio) throws IOException {
        
        return FXMLCduController.crear( this.getScene().getWindow(), true, tipusAccio );
        
    }

}
