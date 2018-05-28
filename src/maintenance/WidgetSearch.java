package maintenance;

import java.util.List;
import javafx.collections.FXCollections;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;

/**
 * This class implments the functionality of a search engine giving the opcion 
 * to search of any attribute, of wich are impleted in AttributeWall.java.
 * This class offers to objects ComboBox, CheckBox and TextField to serach and ChoiceBox to coiche the attribute
 * 
 * @author Rafel
 */
public class WidgetSearch {
        
    private final TextField _SEARCHFIELD;
    private final CheckBox _SEARCHCHECKB;
    private final ComboBox _SEARCHCOMBOB;
    private final ChoiceBox< AttributeBrick > _CBATTRIBUTES;

    public WidgetSearch( AttributeWall _wallClass, TextField _SEARCHFIELD, CheckBox _SEARCHCHECKB, ComboBox _SEARCHCOMBOB, ChoiceBox<AttributeBrick> _CBATTRIBUTES, OnChange changeCombo ) {
        
        this._SEARCHFIELD = _SEARCHFIELD;
        this._SEARCHCHECKB = _SEARCHCHECKB;
        this._SEARCHCOMBOB = _SEARCHCOMBOB;
        this._CBATTRIBUTES = _CBATTRIBUTES;
               
        generate( _wallClass.getAttributeWall() , changeCombo );

    }
    
    @FunctionalInterface
    public interface OnChange {
        public void change( String name, ComboBox combo);
    }
    
    /**
     * Load the items of choicebox and others customizations
     */
    private void generate( List<AttributeBrick> attributes, OnChange changeCombo ) {
        
        _CBATTRIBUTES.setItems( FXCollections.observableList( attributes ) );
        _CBATTRIBUTES.setTooltip( new Tooltip( "Selecciona el camp que buscar" ));
        
        // Change the visibilty of fields depends of selected item and modify combobox
        _CBATTRIBUTES.getSelectionModel().selectedItemProperty().addListener((observable, oldBrick, newBrick) -> {
        
            _SEARCHFIELD.setVisible( false );
            _SEARCHCHECKB.setVisible( false );
            _SEARCHCOMBOB.setVisible( false );

            switch ( newBrick.getFORMAT() ){
                
                case Boolean:
                    _SEARCHCHECKB.setVisible( true );
                    break;
                case Object:
                case List:
                    changeCombo.change( newBrick.getNAME(), _SEARCHCOMBOB );
                    _SEARCHCOMBOB.setVisible( true );
                    break;
                default:
                    _SEARCHFIELD.setVisible( true );

            }

        });
        
        _CBATTRIBUTES.getSelectionModel().selectFirst();

    }
    
    /**
     * Class that works as an event
     */
    public class SearchData {
            
        String fieldText;
        Boolean cbOption;
        Object object;
        AttributeBrick brick;

        public SearchData( String fieldText, Boolean cbOption, AttributeBrick brick, Object object ) {
            
            this.fieldText = fieldText;
            this.cbOption = cbOption;
            this.brick = brick;
            this.object = object;
            
        }
        
        public String getFieldText() {
            return fieldText;
        }

        public Boolean getCbOption() {
            return cbOption;
        }

        public Object getObject() {
            return object;
        }
        
        public AttributeBrick getBrick() {
            return brick;
        }

    }
    
    /**
     * Collect the data of the widget and send it like a event
     * 
     * @return SearchData
     */
    public SearchData getSearchData() {
        
        Object obj = null;
        if(_SEARCHCOMBOB.getSelectionModel().getSelectedIndex() != -1 ) {
            obj = _SEARCHCOMBOB.getItems().get( _SEARCHCOMBOB.getSelectionModel().getSelectedIndex() );
        }
        
        return new SearchData(  _SEARCHFIELD.getText(), 
                                _SEARCHCHECKB.isSelected(),  
                                _CBATTRIBUTES.getSelectionModel().getSelectedItem(),
                                obj );

    }
    
    // <editor-fold desc="getters">
    
    public TextField getSEARCHFIELD() {
        return _SEARCHFIELD;
    }

    public CheckBox getSEARCHCB() {
        return _SEARCHCHECKB;
    }
    
    public ChoiceBox<AttributeBrick> getCBATTRIBUTES() {
        return _CBATTRIBUTES;
    }
    
    // </editor-fold>


}
