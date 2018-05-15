package base;

import base.GenericPopUp.TipusAccio;
import excepcions.MaintenanceException;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.SortType;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
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

    private final String _TITLELIST;
    private final int _LIMITXPAGE;
    
    private WidgetList _WIDGETLIST;
    private WidgetSearch _WIDGETSEARCH;
    
    private HashMap< String, Object > _lastSearch;
    
    private int _currentPage;
    private int _maxPage;
    
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
    @FXML
    private HBox _hbPages;
    
    public static < C extends GenericMaintenanceControlador> C crearFinestre( C c, String nomCapcalera ) throws IOException {
        
        FXMLLoader loader = new FXMLLoader( GenericMaintenanceControlador.class.getResource( FILEFXML ) );
        loader.setController( c );
        Parent p = loader.load();
        c.setfill(p);
        c.nomCapcelera = nomCapcalera;
        return c;
        
    }

    /**
     * Confgis
     * @param _TITLELIST
     * @param _LIMITXPAGE 
     */
    public GenericMaintenanceControlador(String _TITLELIST, int _LIMITXPAGE) {
        this._TITLELIST = _TITLELIST;
        this._LIMITXPAGE = _LIMITXPAGE;
    }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        this._WIDGETLIST = new WidgetList( this, _TITLELIST, _tvTable, _vbCheckers, _spCheckers );
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
    private void searchAction( ActionEvent event ) throws SQLException, ClassNotFoundException, MaintenanceException {

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
                case Integer:
                    data.put( contractName, Integer.valueOf( sd.getFieldText() ) );
                    break;
                case Date:
                case String:
                    data.put( contractName, sd.getFieldText() );
                    break;
            }
            
            _WIDGETLIST.clearTable();
            List list = searchOcurrences( data, 0, _LIMITXPAGE, null, null);
            _WIDGETLIST.fillTable( FXCollections.observableList( list ) );

            generatePagination( getTotalItems( data ) );
            _lastSearch = data;
            _currentPage = 1;

        } catch ( IllegalArgumentException e ) {
            // Do alert
            //throw new MaintenanceException( getClass().getName() + ": Wrong data - searchAction()" );
            
        }
        
    }
    
    /**
     * Create pagination for search
     * 
     * @param totalItems 
     */
    private void generatePagination( Integer totalItems ) {
        
        EventHandler< MouseEvent > labelClickEvent = (MouseEvent event) -> {
            try {
                
                openPage( Integer.valueOf( ( (Label) event.getSource()).getText()) );
                
            } catch ( Exception ex) {
                // Dont know what can do
            }
        };
        
        int pages = (int) Math.ceil( totalItems/_LIMITXPAGE );
        for( int i = 1; i <= pages; i++ ) {

            Label label = new Label( String.valueOf( i ) );
            label.setOnMouseClicked( labelClickEvent );
            _hbPages.getChildren().add( label );

        }
        
        _maxPage = pages;
        
    }
    
    /**
     * Open page in table view respecting the sort order
     * 
     * @param page
     * @throws SQLException
     * @throws ClassNotFoundException 
     */
    private void openPage( int page ) throws SQLException, ClassNotFoundException {
        
        TableColumn sc = null;
        SortType st = null;
        Boolean ascending = null; // null = custom sort 

        if ( !_tvTable.getSortOrder().isEmpty() ) {
            sc = (TableColumn) _tvTable.getSortOrder().get( 0 );
            st = sc.getSortType();
        }

        try {
            
            if( st.equals( SortType.ASCENDING ) ) {
                ascending = true;
            } else if ( st.equals( SortType.DESCENDING ) ) {
                ascending = false;
            }
            
        } catch (NullPointerException e) {
            // is null ignore this
        }
        
        List list = searchOcurrences( _lastSearch, 
                page*_LIMITXPAGE,
                _LIMITXPAGE,
                _WIDGETLIST.getColumnsAttribName().get( sc ),
                ascending );
        _WIDGETLIST.clearTable();
        _WIDGETLIST.fillTable( FXCollections.observableList( list ) );
        _currentPage = page;

        if ( sc != null ) {
            sc.setSortType( st );
        }
        
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

            GenericPopUp window = createPopUpObject( TipusAccio.Crear );
            window.show();

        } catch ( IOException ex ) {

            System.out.println( getClass().getName() + ": Error when try to create ObjectPopUp window in loadFunctionalies() - Add" );

        }
        
    }

    @FXML   // Get object to modify then load new window with the object and open it
    private void modifyAction(ActionEvent event) {
        
        try {
                
            Object object = _WIDGETLIST.getSelected();
            GenericPopUp window = createPopUpObject( TipusAccio.Modificar );
            window.emplenarDades( object );
            window.show();
            
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
            GenericPopUp window = createPopUpObject( TipusAccio.Deshabilitar );
            window.emplenarDades( object );
            window.show();
            
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
            GenericPopUp window = createPopUpObject( TipusAccio.Crear );
            window.emplenarDades( object );
            window.show();
            
        } catch ( IOException ex ) {

            System.out.println( getClass().getName() + ": Error when try to create ObjectPopUp window in loadFunctionalies() - Duplicate" );

        } catch ( MaintenanceException ex ) {
            // When any item selected
            // Implement label
            System.out.println( ex.getMessage() );

        }
        
    }
    
    @FXML
    private void previousPage(MouseEvent event) throws SQLException, ClassNotFoundException {
        
        if( _currentPage > 1) {
            openPage( _currentPage-- );
        }
        
    }

    @FXML
    private void nextPage(MouseEvent event) throws SQLException, ClassNotFoundException {
        
        if( _currentPage < _maxPage ) {
            openPage( _currentPage++ );
        }
        
    }
    
}
