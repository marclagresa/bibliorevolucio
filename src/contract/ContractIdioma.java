package contract;

import java.sql.Types;
import java.util.HashMap;

/**
 * @author albertCorominas
 */
public abstract class ContractIdioma {
    public static final String NOM_TAULA = "idioma";
    public static final String ID = "id";
    public static final String NOM = "nom";
    public static final HashMap<String,Integer> DEFINICIO = new HashMap<String,Integer>(){{
        put(ID, Types.INTEGER);
        put(NOM, Types.VARCHAR);
    }};
}
