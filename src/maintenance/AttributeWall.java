package maintenance;

import excepcions.MaintenanceException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import javafx.scene.control.TableColumn.SortType;

/**
 * Super class to work with widgets maintenance
 * 
 * Exemple in UsuariMaintenanceControlador.java
 * 
 * @author Rafel
 */
public interface AttributeWall {
    
    /**
     * Return the number of occurences of specific search
     * 
     * @param data
     * @return 
     */
    public Integer getTotalItems( HashMap< String, Object > data ) throws ClassNotFoundException, SQLException;
    
    /**
     * This methode be need implemented with clases of his representing class
     * Call DAO method to get list
     * 
     * @param data
     * @param startItem
     * @param limitXPage
     * @param attribToOrder
     * @param sortType
     * @return The list of found elements
     * @throws java.sql.SQLException
     * @throws java.lang.ClassNotFoundException
     */
    public List searchOcurrences( HashMap< String, Object> data, Integer startItem, Integer limitXPage, String attribToOrder, Boolean sortType ) throws SQLException, ClassNotFoundException, IllegalArgumentException ;
    
    /**
     * This method need read the contractName and return id of the object
     * 
     * @param contractName
     * @param object
     * @return 
     * @throws excepcions.MaintenanceException 
     */
    public Integer parseObject( String contractName, Object object ) throws MaintenanceException ;
    
    /**
     * This methode be need implemented with attributs of his representing class
     * Works with: AttributeBrick.class:
     * 
     * @return The list of atributes of implemented object
     */
    public List<AttributeBrick> getAttributeWall();
    
}
