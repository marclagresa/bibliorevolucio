package maintenance;

import com.sun.javafx.property.PropertyReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
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
     * @param _checkers
     * @param _scrollCheckers
     */
    public WidgetList( AttributeWall _wallClass, String title, TableView<?> _TABLE, VBox _checkers, ScrollPane _scrollCheckers, Runnable onColumnAction ) {
        
        this._TABLE = _TABLE;
        this._SCROLLCHECKERS = _scrollCheckers;
        this._CHECKERS = _checkers;
        
        generate( generateUpperColumn( title ), _wallClass.getAttributeWall(), onColumnAction );
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
    private void generate( TableColumn upperColumn, List<AttributeBrick> attributes, Runnable onColumnAction ){
        
        // Iterate list of attributes
        attributes.forEach( attrBrick -> {
            
            // Create a new column
            TableColumn< ?, ? > column;
            CheckBox ckBox;

            column = new TableColumn( attrBrick.getNAMECOLUMN() );
            // Custom view for booleans
            switch ( attrBrick.getFORMAT() ) {
                case Boolean:
                    
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
                    
                    break;
                default:
                    
                    column.setCellValueFactory( new PropertyValueFactory<>( attrBrick.getNAME() ) );
                    
            }
            
            // Listener when property change
            column.sortTypeProperty().addListener((observable) -> {
                onColumnAction.run();
            });
            
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
     * We save each column order of the list, for later of filled the return it
     * @param list 
     */
    public void fillTable( ObservableList list ){

        // save
        ObservableList<TableColumn> columnList = FXCollections.observableArrayList();
        _TABLE.getSortOrder().forEach( ( column ) -> {
            columnList.add( column );
        });
        
        // fill
        _TABLE.setItems( list );
        
        // return it
        columnList.forEach( ( column ) -> {
            _TABLE.getSortOrder().add( column );
        });        
        
    }
    
    /**
     * Remove all items in the table
     */
    public void clearTable() {
        
        _TABLE.getItems().clear();

    }
    
    /**
     * @return Object The selected item of table view, if are multiple selection return last object
     */
    public Object getSelected() throws IllegalArgumentException {
        
        Object object = _TABLE.getSelectionModel().getSelectedItem();
        
        if( object == null) {
            throw new IllegalArgumentException( "Any item selected" );
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
