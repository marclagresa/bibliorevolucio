
package contract;

import java.sql.Types;
import java.util.HashMap;

/**
 * @author sergiclotas
 */
public abstract class ContractEditorial {
    public static final String NOM_TAULA = "editorial";
    public static final String ID = NOM_TAULA+".id";
    public static final String NOM = NOM_TAULA+".nom";
    public static final HashMap<String,Integer>DEFINICIO= new HashMap<String,Integer>(){{
        put(ID, Types.INTEGER);
        put(NOM, Types.VARCHAR);
    }};
}
