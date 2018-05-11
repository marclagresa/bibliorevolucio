package base;

import base.GenericPopUp.TipusAccio;
import excepcions.MaintenanceException;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import maintenance.AttributeBrick;
import maintenance.AttributeWall;
import maintenance.WidgetList;
import maintenance.WidgetSearch;
import maintenance.WidgetSearch.SearchData;

    
/**
 * This class need be implemented like below example 
 * If _WIDGETLIST or _WIDGETSEARCH are null trhow MaintenanceException in method loadElemets() called in constructor
 * 
 * Example in UsuariMaintenanceControlador.java:
 * -------------------------------------------------------------------------
 *  public UsuariMaintenanceControlador() throws MaintenanceException {
 *  
 *   GenericWall wall = new UsuariWall();
 *   this._WIDGETLIST = new WidgetList( wall, "Usuaris" );
 *   this._WIDGETSEARCH = new WidgetSearch( wall );
 *  
 *  }
 * -------------------------------------------------------------------------
 *
 * @author Rafel
 */
public abstract class GenericMaintenanceControlador extends GenericControlador implements Initializable, AttributeWall {
    
    private static final String FILEFXML = "/fxml/FXMLMaintenance.fxml";

    private WidgetList _WIDGETLIST;
    private WidgetSearch _WIDGETSEARCH;
    
    @FXML
    private TextField _searchField;
    @FXML
    private ChoiceBox< AttributeBrick > _cbAttributes;
    @FXML
    private ScrollPane _spCheckers;
    @FXML
    private VBox _vbCheckers;
    @FXML
    private TableView<?> _tvTable;
    @FXML
    private Button _btnSearch;
    @FXML
    private Label _lblAdvancedSearch;
    @FXML
    private Button _btnAdd;
    @FXML
    private Button _btnModify;
    @FXML
    private Button _btnDel;
    @FXML
    private Button _btnDuplicate;
    @FXML
    private CheckBox _searchCb;
    
    public static < C extends GenericMaintenanceControlador> C crearFinestre( C c, String nomCapcalera ) throws IOException {
        
        FXMLLoader loader = new FXMLLoader( GenericMaintenanceControlador.class.getResource( FILEFXML ) );
        loader.setController( c );
        Parent p = loader.load();
        c.setfill(p);
        c.nomCapcelera = nomCapcalera;
        return c;
        
    }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        this._WIDGETLIST = new WidgetList( this, titleList(), _tvTable, _vbCheckers, _spCheckers );
        this._WIDGETSEARCH = new WidgetSearch( this, _searchField, _searchCb, _cbAttributes );
        
    }
    
    /**
     *  Example:
     *  @Override
     *  public GenericPopUp createPopUpObject() throws IOException {
     *      return FXMLUserController.crear( this.getScene().getWindow(), true );
     *  }
     * 
     * @return GenericPopUp Object
     * @throws IOException 
     */
    public abstract GenericPopUp createPopUpObject( TipusAccio tipusAccio ) throws IOException;
    
    /**
     *  Example:
     *  @Override
     *  public GenericPopUp createPopUpAdvSearch() throws IOException {
     *      return FXMLUserController.crear( this.getScene().getWindow(), true );
     *  }  
     * @return GenericPopUp Object
     * @throws IOException 
     */
    public abstract GenericPopUp createPopUpAdvSearch( TipusAccio tipusAccio) throws IOException; 
        
    /**
     * Do the search and fill the table
     * @param event
     * @throws SQLException
     * @throws ClassNotFoundException 
     */
    @FXML   
    private void searchAction(ActionEvent event) throws SQLException, ClassNotFoundException, MaintenanceException {

        List list = null;
        SearchData sd = _WIDGETSEARCH.getSearchData();
        
        try {
            
            HashMap< String, Object > data = new HashMap<>();
            String contractName = sd.getBrick().getCONTRACTNAME();
        
            switch( sd.getBrick().getFORMAT() ) {
                case Boolean:
                    data.put( contractName, sd.getCbOption() );
                    break;
                case Object:
                    data.put( contractName, parseObject( contractName, sd.getObject() ) );
                    break;
                case Date:
                case Integer:
                case String:
                    data.put( contractName, sd.getFieldText() );
                    break;
            }
            
            list = searchOcurrences( data );
            
        } catch ( IllegalArgumentException e ) {
            
            throw new MaintenanceException( getClass().getName() + ": Wrong data - searchAction()" );
            
        }
        
        _WIDGETLIST.fillTable( FXCollections.observableList( list ) );
        
    }
    
    @FXML   // Create new window and open it
    private void advSearchAction(ActionEvent event) {
        
        try {
               
            GenericPopUp w = createPopUpAdvSearch( TipusAccio.Buscar );

        } catch ( IOException ex ) {

            System.out.println( getClass().getName() + ": Error when try to create AdvancedSearchPopUp window in loadFunctionalies()" );

        }
        
    }

    @FXML   // Create new window and open it
    private void addAction(ActionEvent event) {
       
        try {

            GenericPopUp w = createPopUpObject( TipusAccio.Crear );
            w.show();

        } catch ( IOException ex ) {

            System.out.println( getClass().getName() + ": Error when try to create ObjectPopUp window in loadFunctionalies() - Add" );

        }
        
    }

    @FXML   // Get object to modify then load new window with the object and open it
    private void modifyAction(ActionEvent event) {
        
        try {
                
            Object object = _WIDGETLIST.getSelected();
            GenericPopUp window = createPopUpObject(TipusAccio.Modificar );
            window.emplenarDades( object );

        } catch ( IOException ex ) {

            System.out.println( getClass().getName() + ": Error when try to create ObjectPopUp window in loadFunctionalies() - Modify" );

        } catch ( MaintenanceException ex ) {
            // When any item selected
            // Implement label
            System.out.println( ex.getMessage() );

        } 
        
    }

    @FXML   // Get object to delete then load new window with the object and open it
    private void deleteAction(ActionEvent event) {
        
        try {
                
            Object object = _WIDGETLIST.getSelected();
            GenericPopUp window = createPopUpObject( TipusAccio.Deshabilitar);
            window.emplenarDades( object );

        } catch ( IOException ex ) {

            System.out.println( getClass().getName() + ": Error when try to create ObjectPopUp window in loadFunctionalies() - Delete" );

        } catch ( MaintenanceException ex ) {
            // When any item selected
            // Implement label
            System.out.println( ex.getMessage() );

        }
        
    }

    @FXML   // Get object to Duplicate then load new window with the object and open it
    private void duplicateAction(ActionEvent event) {
       
        try {
                
            Object object = _WIDGETLIST.getSelected();
            GenericPopUp window = createPopUpObject(TipusAccio.Crear);
            window.emplenarDades( object );

        } catch ( IOException ex ) {

            System.out.println( getClass().getName() + ": Error when try to create ObjectPopUp window in loadFunctionalies() - Duplicate" );

        } catch ( MaintenanceException ex ) {
            // When any item selected
            // Implement label
            System.out.println( ex.getMessage() );

        }
        
    }
}
