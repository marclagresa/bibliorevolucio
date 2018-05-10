package contract;


import java.sql.Types;
import java.util.HashMap;

/**
 * @author albertCorominas
 */
public abstract class ContractPersona {
    public static final String NOM_TAULA = "persona";
    public static final String ID = NOM_TAULA+".id";
    public static final String NOM = NOM_TAULA+".nom";
    public static HashMap<String,Integer> DEFINICIO = new HashMap<String,Integer>(){{
        put(ID, Types.INTEGER);
        put(NOM, Types.VARCHAR);
    }};
}