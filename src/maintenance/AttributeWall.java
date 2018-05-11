package maintenance;

import excepcions.MaintenanceException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

/**
 * Super class to work with widgets maintenance
 * @author Rafel
 */
public interface AttributeWall {
    
    /**
     * This methode be need implemented with clases of his representing class
     * Call DAO method to get list
     * 
     * Exemple in UsuariMaintenanceControlador.java:
     * 
     * @param data
     * @return The list of found elements
     * @throws java.sql.SQLException
     * @throws java.lang.ClassNotFoundException
     */
    public List searchOcurrences( HashMap< String, Object> data ) throws SQLException, ClassNotFoundException, IllegalArgumentException ;
    
    /**
     * This method need read the contractName and return id of the object
     * 
     * Exemple in UsuariMaintenanceControlador.java:
     * 
     * @param contractName
     * @param object
     * @return 
     * @throws excepcions.MaintenanceException 
     */
    public Integer parseObject( String contractName, Object object ) throws MaintenanceException ;
    
    /**
     * This methode be need implemented with attributs of his representing class
     * Works with: AttributeBrick.class
     * 
     * Exemple in UsuariMaintenanceControlador.java:
     * 
     * @return The list of atributes of implemented object
     */
    public List<AttributeBrick> getAttributeWall();
    
    /**
     * Set the title of listview
     * @return 
     */
    public String titleList();
    
    //    @Override
    //    public void initialize(URL url, ResourceBundle rb) {
    //        super.initialize(url, rb); // optional custom init
    //    }
    
}
