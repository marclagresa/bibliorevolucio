package contract;

import java.sql.Types;
import java.util.HashMap;

/**
 * @author albertCorominas
 */
public abstract class ContractNivell {
    public static final String NOM_TAULA = "nivell";
    public static final String ID = "id";
    public static final String NOM = "nom";
    public static HashMap<String,Integer> DEFINICIO = new HashMap<String,Integer>(){{
        put(ID, Types.INTEGER);
        put(NOM, Types.VARCHAR);
    }};
}
