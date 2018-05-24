package maintenance;

import com.sun.javafx.property.PropertyReference;
import excepcions.MaintenanceException;
import java.util.HashMap;
import java.util.List;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import maintenance.AttributeBrick.allowedFormats;

/**
 * This class represents a widget that contains a custom table view for specific object and chooseable columns to display
 * 
 * @author Rafel mascaros
 */
public class WidgetList {
            
    private final TableView<?> _TABLE;
    private final ScrollPane _SCROLLCHECKERS;
    private final VBox _CHECKERS;
    private final HashMap< TableColumn, String > _columnsAttribName = new HashMap<>();

    /**
     * Set the obtained param configs, generate the tableview
     * 
     * @param _wallClass The class to be implemented
     * @param title Title of upper column 
     * @param _TABLE 
     * @param _CHECKERS
     */
    public WidgetList( AttributeWall _wallClass, String title, TableView<?> _TABLE, VBox _CHECKERS, ScrollPane _scrollCheckers ) {
        
        this._TABLE = _TABLE;
        this._SCROLLCHECKERS = _scrollCheckers;
        this._CHECKERS = _CHECKERS;
        
        generate( generateUpperColumn( title ), _wallClass.getAttributeWall() );
        loadDesign();
        
    }
    
    /**
     * Create Upper column to make chooseble section
     * 
     * @return TableColumn< ? , String > The built column 
     */
    private TableColumn< ? , String > generateUpperColumn( String title ){
       
        // Set layout of this column
        Label chooseLbl = new Label( title );
        chooseLbl.setPadding(new Insets(0, 5, 0, 0));

        Button chooseBtn = new Button( "+" );
        
        // This event set the visiblity of section check
        chooseBtn.setOnAction((event) -> {
            _SCROLLCHECKERS.setVisible( !_SCROLLCHECKERS.isVisible() );
        });
        
        BorderPane boxPane = new BorderPane();
        boxPane.setRight( chooseBtn );
        boxPane.setCenter( chooseLbl );
        boxPane.setMinWidth( 110 );
        boxPane.setPadding( new Insets(5) );
        
        // UpperColumn
        TableColumn upperColumn = new TableColumn();
        upperColumn.setGraphic( boxPane );
        
        return upperColumn;
        
    }
    
    /**
     * Build the table creating a column and  checkbox for each attribute
     * Put all checkbox into vbox
     */
    private void generate( TableColumn upperColumn, List<AttributeBrick> attributes ){
        
        // Iterate list of attributes
        attributes.forEach( attrBrick -> {
            
            // Create a new column
            TableColumn< ?, ? > column;
            CheckBox ckBox;

            column = new TableColumn( attrBrick.getNAMECOLUMN() );
            // Custom view for booleans
            if( attrBrick.getFORMAT().equals( allowedFormats.Boolean ) ) {
                
                column.setCellValueFactory( (param) -> {
          
                    PropertyReference propertyRef = new PropertyReference<>( param.getValue().getClass(), attrBrick.getNAME() );

                    ObservableValue property;
                    if (propertyRef.hasProperty()) {
                        property = propertyRef.getProperty( param.getValue() );
                    } else {
                        Object value = propertyRef.get( param.getValue() );
                        property = new ReadOnlyObjectWrapper<>( value );
                    }
                    
                    return property;
                    
                });
                
                column.setCellFactory( tc -> new CheckBoxTableCell<>() );
                
            } else {
                
                column.setCellValueFactory( new PropertyValueFactory<>( attrBrick.getNAME() ) );
                
            }
                        
            // His checkbox
            ckBox = new CheckBox( attrBrick.getNAMECOLUMN() );
            column.setVisible( attrBrick.isDEFAULT() );

            ckBox.selectedProperty().addListener((observable, oldValue, newValue) -> {
                column.setVisible( newValue );
            });
            
            ckBox.setSelected( attrBrick.isDEFAULT() );
            
            // Add to list
            _CHECKERS.getChildren().add( ckBox );
            
            // Add this new column to upper column
            upperColumn.getColumns().addAll( column );
            
            // Add column in list
            _columnsAttribName.put( column, attrBrick.getCONTRACTNAME() );
            
        });
        
        _TABLE.getColumns().add( upperColumn );
        
    }
    
    /**
     * Load design of class
     */
    private void loadDesign() {
        
        // Modify text that shown when table is empty
        _TABLE.setPlaceholder( new Label( "No hi ha cap resultat." ));
        
    }
    
    /**
     * Fill table view with obtained list
     * 
     * @param list 
     */
    public void fillTable( ObservableList list ){
        
        _TABLE.setItems( list );
        
    }
    
    /**
     * Remove all items in the table
     */
    public void clearTable() {
        
        _TABLE.getItems().clear();

    }
    
    /**
     * @return Object The selected item of table view, if are multiple selection return last object
     * @throws excepcions.MaintenanceException When any item are selected
     */
    public Object getSelected() throws MaintenanceException {
        
        Object object = _TABLE.getSelectionModel().getSelectedItem();
        
        if( object == null) {
            throw new MaintenanceException( "Any item selected" );
        }
        
        return object;
        
    }
    
    // <editor-fold desc="getters">

    public TableView<?> getTABLE() {
        return _TABLE;
    }

    public VBox getCHECKERS() {
        return _CHECKERS;
    }
    
    public HashMap<TableColumn, String> getColumnsAttribName() {
        return _columnsAttribName;
    }
    
    // </editor-fold>

}
