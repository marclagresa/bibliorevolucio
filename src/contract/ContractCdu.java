
package contract;

import java.sql.Types;
import java.util.HashMap;

/**
 * @author albertCorominas
 */
public abstract class ContractCdu {
    public static final String NOM_TAULA = "cdu";
    public static final String ID = NOM_TAULA+".id";
    public static final String NOM = NOM_TAULA+".nom";
    public static final String IDPARE = NOM_TAULA+".idPare";
    public static final HashMap<String,Integer>  DEFINICIO = new HashMap <String,Integer> (){{
       put(ID,Types.VARCHAR);
       put(IDPARE,Types.VARCHAR);
       put(NOM,Types.VARCHAR);
    }};
}
