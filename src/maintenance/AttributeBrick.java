package maintenance;

/**
 * This object contains basic data for work with attributes
 * 
 * @author Rafel mascaros
 */
public class AttributeBrick {
    
    private final String _NAME;
    private final String _NAMECOLUMN;
    private final boolean _DEFAULT;
    private final String _CONTRACTNAME;
    private final allowedFormats _FORMAT;
    
    // When add new format implementit in GenericMaintenanceControlador.java - searchAction() method
    public enum allowedFormats {
        
        Boolean,
        String,
        Integer,
        Date,
        List,
        Object;

    }
    
    public AttributeBrick(String _name, String _nomColumns, boolean _default, String _contractName, allowedFormats _format ) {
        
        this._NAME = _name;
        this._NAMECOLUMN = _nomColumns;
        this._DEFAULT = _default;
        this._CONTRACTNAME = _contractName;
        this._FORMAT = _format;
        
    }
    
    public String getNAME() {
        return _NAME;
    }

    public String getNAMECOLUMN() {
        return _NAMECOLUMN;
    }

    public boolean isDEFAULT() {
        return _DEFAULT;
    }

    public String getCONTRACTNAME() {
        return _CONTRACTNAME;
    }

    public allowedFormats getFORMAT() {
        return _FORMAT;
    }
    
    @Override
    public String toString() {
        return _NAMECOLUMN;
    }
}
