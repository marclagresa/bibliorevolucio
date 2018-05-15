package maintenance;

import java.util.List;
import javafx.collections.FXCollections;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
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
    private final CheckBox _SEARCHCB;
    private final ChoiceBox< AttributeBrick > _CBATTRIBUTES;
    private final Object _OBJECTE = null;

    public WidgetSearch( AttributeWall _wallClass, TextField _SEARCHFIELD, CheckBox _SEARCHCB, ChoiceBox<AttributeBrick> _CBATTRIBUTES ) {
        
        this._SEARCHFIELD = _SEARCHFIELD;
        this._SEARCHCB = _SEARCHCB;
        this._CBATTRIBUTES = _CBATTRIBUTES;
               
        generate( _wallClass.getAttributeWall() );

    }
    
    /**
     * Load the items of choicebox and others customizations
     */
    private void generate( List<AttributeBrick> attributes ) {
        
        _CBATTRIBUTES.setItems( FXCollections.observableList( attributes ) );
        _CBATTRIBUTES.setTooltip( new Tooltip( "Selecciona el camp que buscar" ));
        
        // Change the visibilty of fields depends of selected item
        _CBATTRIBUTES.getSelectionModel().selectedItemProperty().addListener( (observable, oldValue, newValue) -> {
            
            if( newValue.getFORMAT().equals( AttributeBrick.allowedFormats.Boolean )) {
                _SEARCHCB.setVisible( true );
                _SEARCHFIELD.setVisible( false );
            } else {
                _SEARCHCB.setVisible( false );
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

        public SearchData( String fieldText, Boolean cbOption, AttributeBrick brick, Object object) {
            
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
        
        return new SearchData( _SEARCHFIELD.getText(), _SEARCHCB.isSelected(), _CBATTRIBUTES.getSelectionModel().getSelectedItem(), _OBJECTE);

    }
    
    // <editor-fold desc="getters">
    
    public TextField getSEARCHFIELD() {
        return _SEARCHFIELD;
    }

    public CheckBox getSEARCHCB() {
        return _SEARCHCB;
    }
    
    public ChoiceBox<AttributeBrick> getCBATTRIBUTES() {
        return _CBATTRIBUTES;
    }
    
    // </editor-fold>


}
