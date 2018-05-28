
package contract;

import java.sql.Types;
import java.util.HashMap;

/**
 * @author albertCorominas
 */
public abstract class ContractExemplar {
    public static final String NOM_TAULA = "exemplar";
    public static final String ID = NOM_TAULA+".id";
    public static final String ID_PRODUCTE = NOM_TAULA+".id_producte";
    public static final String ID_BIBLIOTECA = NOM_TAULA+".select d_biblioteca";
    public static final String ESTAT = NOM_TAULA+".estat";
    public static HashMap<String,Integer> DEFINICIO = new HashMap<String,Integer>(){{
        put(ID, Types.INTEGER);
        put(ID_PRODUCTE, Types.INTEGER);
        put(ID_BIBLIOTECA, Types.INTEGER);
        put(ESTAT, Types.BOOLEAN);
    }};
}
