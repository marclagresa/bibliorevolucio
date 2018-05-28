package base;

import base.GenericPopUp.TipusAccio;
import excepcions.MaintenanceException;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;
import javafx.application.Platform;
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
import javafx.scene.control.ComboBox;
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
 * This class need be implemented like example below 
 * 
 * Example in UsuariMaintenanceControlador.java
 * @author Rafel
 */
public abstract class GenericMaintenanceControlador extends GenericControlador implements Initializable, AttributeWall {
    
    private static final String FILEFXML = "/fxml/FXMLMaintenance.fxml";

    private final String _TITLELIST;
    private final int _LIMITXPAGE;
    
    private WidgetList _WIDGETLIST;
    private WidgetSearch _WIDGETSEARCH;
    
    private HashMap< String, Object > _lastSearch;
    
    private Label _currentLabel;
    private int _currentPage;
    private int _maxPage;
    
    @FXML
    private TextField _searchField;
    @FXML
    private CheckBox _searchCheckB;
    @FXML
    private ComboBox _searchComboB;
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
    protected Label _lblAdvancedSearch;
    @FXML
    private Button _btnAdd;
    @FXML
    private Button _btnModify;
    @FXML
    private Button _btnDel;
    @FXML
    private Button _btnDuplicate;
    @FXML
    private HBox _hbPages;
    
    /**
     * Same implementatio at superclass but diferents parameters
     * @param <C extends GenericMaintenanceControlador>
     * @param c
     * @param nomCapcalera
     * @return
     * @throws IOException 
     */
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
     * Exemple in UsuariMaintenanceControlador.java
     * 
     * @param _TITLELIST
     * @param _LIMITXPAGE 
     */
    public GenericMaintenanceControlador(String _TITLELIST, int _LIMITXPAGE) {
        
        this._TITLELIST = _TITLELIST;
        this._LIMITXPAGE = _LIMITXPAGE;
        
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        this._WIDGETLIST = new WidgetList( this, _TITLELIST, _tvTable, _vbCheckers, _spCheckers, () -> {
            
            try {
                openPage( 1 );
            } catch (SQLException | ClassNotFoundException ex) {
                // Set in logger
                // Open a generic alert
            }
            
        } );
        this._WIDGETSEARCH = new WidgetSearch( this, _searchField, _searchCheckB, _searchComboB, _cbAttributes, ( name, combo ) -> {
            
            try {
                parseCombo( name, combo );
            } catch (MaintenanceException ex) {
                // Set in logger
                // Open a generic alert
            }
            
        });
        
        // Disable duplicate option
        _btnDuplicate.setVisible(false);
        _btnDuplicate.setDisable(true);
        
    }
    
    /**
     * @param tipusAccio
     * @return GenericPopUp Object
     * @throws IOException 
     * @throws excepcions.MaintenanceException 
     */
    public abstract GenericPopUp createPopUpObject( TipusAccio tipusAccio ) throws IOException, MaintenanceException;
    
    /**
     * @return GenericPopUp Object
     * @throws IOException 
     * @throws excepcions.MaintenanceException 
     */
    public abstract GenericPopUp createPopUpAdvSearch() throws IOException, MaintenanceException; 
    
    /**
     * Create pagination for search
     * 
     * @param totalItems 
     */
    private void generatePagination( Integer totalItems ) {
        
        _hbPages.getChildren().clear();
        
        EventHandler< MouseEvent > labelClickEvent = (MouseEvent event) -> {
            
            try {
                
                // Check is there last label remove style
                if( _currentLabel != null ){
                    _currentLabel.getStyleClass().remove( "numerosSeleccionats" );
                }
                
                Label newLabel = ((Label) event.getSource());
                // set style at new label
                newLabel.getStyleClass().add( "numerosSeleccionats");
                // change _currentLabel
                _currentLabel = newLabel;
                openPage( Integer.valueOf( newLabel.getText() ) );

            } catch (SQLException | ClassNotFoundException ex) {
                // Set in logger
                // Open a generic alert
            } 
            
        };
        
        int pages =  (int) Math.ceil( totalItems/(_LIMITXPAGE*1.0 ));
        
        // alwayas add first page 
        Label label = new Label( String.valueOf( 1 ) );
        label.setOnMouseClicked( labelClickEvent );
        label.getStyleClass().add( "numerosSeleccionats");
        _currentLabel = label;
        _currentPage = 1;
        _hbPages.getChildren().add( label );
        
        for( int i = 2; i <= pages; i++ ) {

            label = new Label( String.valueOf( i ) );
            label.setOnMouseClicked( labelClickEvent );
            _hbPages.getChildren().add( label );
            
            if( i%2 == 0) {
                label.getStyleClass().add( "numerosParells" );
            }

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
        
        // Check if there is a previous search
        if( _lastSearch != null ) {
        
            TableColumn sc = null;
            SortType st = null;
            Boolean ascending = true;

            if ( !_tvTable.getSortOrder().isEmpty() ) {
                sc = (TableColumn) _tvTable.getSortOrder().get( 0 );
                st = sc.getSortType();
            }

            if( sc!=null ){
               ascending = st.equals(SortType.ASCENDING);
            }

            List list = searchOcurrences( _lastSearch, 
                    _LIMITXPAGE*(page-1),
                    _LIMITXPAGE,
                    _WIDGETLIST.getColumnsAttribName().get( sc ),
                    ascending );
            _WIDGETLIST.fillTable( FXCollections.observableList( list ) );
            _currentPage = page;
        
        }
        
    }
    
    /**
     * Make and fill the search
     * 
     * @param data
     * @throws SQLException
     * @throws ClassNotFoundException
     * @throws IllegalArgumentException 
     */
    private void doSearch( HashMap< String, Object > data ) throws SQLException, ClassNotFoundException, IllegalArgumentException {
        
        TableColumn sc = null;
        SortType st = null;
        Boolean ascending = true;

        if ( !_tvTable.getSortOrder().isEmpty() ) {
            sc = (TableColumn) _tvTable.getSortOrder().get( 0 );
            st = sc.getSortType();
        }
       
        if( sc!=null ){
           ascending = st.equals(SortType.ASCENDING);
        }
        
        generatePagination( getTotalItems( data ) );
        
        List list = searchOcurrences( data, 0, _LIMITXPAGE, _WIDGETLIST.getColumnsAttribName().get( sc ), ascending );
        _WIDGETLIST.fillTable( FXCollections.observableList( list ) );

        _lastSearch = data;
        
        
    }
    
    @FXML   // Its called when button is pressed, that gets the data to parse into HashMap then do the search
    private void searchAction(ActionEvent event) {

        SearchData sd = _WIDGETSEARCH.getSearchData();
        // Falta afegir el format "list" en el switch
        try {
            
            HashMap< String, Object > data = new HashMap<>();
            String contractName = sd.getBrick().getCONTRACTNAME();
        
            switch( sd.getBrick().getFORMAT() ) {
                case Boolean:
                    data.put( contractName, sd.getCbOption() );
                    break;
                case Object:
                    data.put( contractName, parseObject( sd.getBrick().getNAME(), sd.getObject() ) );
                    break;
                case Integer:
                    data.put( contractName, Integer.valueOf( sd.getFieldText() ) );
                    break;
                case List:
                    data.put( contractName, new Integer[]{ (Integer) parseObject( sd.getBrick().getNAME(), sd.getObject() ) });
                    break;
                case Date:
                case String:
                    data.put( contractName, sd.getFieldText() );
                    break;
            }
            
            Platform.runLater(() -> {
                        
                // Obrir pop up loading
                try {

                    doSearch( data );

                } catch ( IllegalArgumentException e ) {
                    // Only open an alert
                    // If not tested can maybe an error in code            
                } catch (SQLException | ClassNotFoundException ex) {
                    // Set in logger
                    // Open a generic alert
                } finally {
                    // Tancar pop up loading
                }
                
            });
            
        } catch ( MaintenanceException ex ) {
            // Set in logger
            // Open a generic alert
        }
        
    }
    
    @FXML   // Create new window and open it
    private void advSearchAction(MouseEvent event) {
        
        try {
               
            GenericPopUp w = createPopUpAdvSearch( );
            
            w.onAccept( ( map ) -> {
                
                Platform.runLater(() -> {
                        
                    // Obrir pop up loading
                    try {

                        doSearch( (map instanceof HashMap) ? (HashMap) map : null );

                    } catch ( IllegalArgumentException e ) {
                        // Only open an alert
                        // If not tested can maybe an error in code            
                    } catch (SQLException | ClassNotFoundException ex) {
                        // Set in logger
                        // Open a generic alert
                    } finally {
                        // Tancar pop up loading
                    }
                });
                    
            });
            
            w.show();

        } catch ( IOException | MaintenanceException ex ) {
            // Set in logger
            // Open a generic alert
        }
        
    }

    @FXML   // Create new window and open it
    private void addAction(ActionEvent event) {
       
        try {

            GenericPopUp window = createPopUpObject( TipusAccio.Crear );
            window.show();

        } catch ( IOException | MaintenanceException ex ) {
            // Set in logger
            // Open a generic alert
        }
        
    }

    @FXML   // Get object to modify then load new window with the object and open it
    private void modifyAction(ActionEvent event) {
        
        try {
                
            Object object = _WIDGETLIST.getSelected();
            GenericPopUp window = createPopUpObject( TipusAccio.Modificar );
            window.emplenarDades( object );
            
            window.onAccept( ( nothing ) -> {
                
                Platform.runLater(() -> {
                        
                    try {

                        openPage( _currentPage );
                        
                    } catch ( IllegalArgumentException e ) {
                        // Only open an alert
                        // If not tested can maybe an error in code            
                    } catch (SQLException | ClassNotFoundException ex) {
                        // Set in logger
                        // Open a generic alert
                    } 
                    
                });
                    
            });
            
            window.show();
            
        } catch ( IOException | MaintenanceException ex ) {
            // Set in logger
            // Open a generic alert
        } catch ( IllegalArgumentException ex ) {
            // When any item selected
                // Open an alert
        } 
        
    }

    @FXML   // Get object to delete then load new window with the object and open it
    private void deleteAction(ActionEvent event) {
        
        try {
                
            Object object = _WIDGETLIST.getSelected();
            GenericPopUp window = createPopUpObject( TipusAccio.Deshabilitar );
            window.emplenarDades( object );
            
            window.onAccept( ( nothing ) -> {
                
                Platform.runLater(() -> {
                        
                    try {

                        openPage( _currentPage );
                        
                    } catch ( IllegalArgumentException e ) {
                        // Only open an alert
                        // If not tested can maybe an error in code            
                    } catch (SQLException | ClassNotFoundException ex) {
                        // Set in logger
                        // Open a generic alert
                    } 
                    
                });
                    
            });
            
            window.show();
            
        } catch ( IOException | MaintenanceException ex ) {
            // Set in logger
            // Open a generic alert
        } catch ( IllegalArgumentException ex ) {
            // When any item selected
                // Open an alert
        } 
        
    }

    @FXML   // Get object to Duplicate then load new window with the object and open it
    private void duplicateAction(ActionEvent event) {
       
        try {
                
            Object object = _WIDGETLIST.getSelected();
            GenericPopUp window = createPopUpObject( TipusAccio.Crear );
            window.emplenarDades( object );
            
            window.onAccept( ( nothing ) -> {
                
                Platform.runLater(() -> {
                        
                    try {

                        openPage( _currentPage );
                        
                    } catch ( IllegalArgumentException e ) {
                        // Only open an alert
                        // If not tested can maybe an error in code            
                    } catch (SQLException | ClassNotFoundException ex) {
                        // Set in logger
                        // Open a generic alert
                    } 
                    
                });
                    
            });
            
            window.show();
            
        } catch ( IOException | MaintenanceException ex ) {
            // Set in logger
            // Open a generic alert
        } catch ( IllegalArgumentException ex ) {
            // When any item selected
                // Open an alert
        } 
        
    }
    
    @FXML
    private void previousPage(MouseEvent event) {
        
        try {
            
            if( _currentPage > 1) {
                openPage( --_currentPage );
            }
            
        } catch ( SQLException | ClassNotFoundException ex) {
            // Set in logger
            // Open a generic alert
        }
        
    }

    @FXML
    private void nextPage(MouseEvent event) {
        
        try {
            
            if( _currentPage < _maxPage ) {
                openPage( ++_currentPage );
            }
            
        } catch ( SQLException | ClassNotFoundException ex) {
            // Set in logger
            // Open a generic alert
        }
        
    }
    
}