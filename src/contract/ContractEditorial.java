
package contract;

import java.sql.Types;
import java.util.HashMap;

/**
 * @author sergiclotas
 */
public abstract class ContractEditorial {

    //NOM DE LA TAULA: es el nom de la taula que s'utilitza a la base de dades.

    public static final String NOM_TAULA = "editorial";

    //CAMPS DE LA TAULA: es el noms dels camps* que conté la taula.
    //*S'afagueix el nom de la taula i un punt al principi per que els camps no siguin ambigu.

    public static final String ID = NOM_TAULA+".id";
    public static final String NOM = NOM_TAULA+".nom";
    public static final String ACTIVA = NOM_TAULA+".activa";
    public static final HashMap<String,Integer>DEFINICIO= new HashMap<String,Integer>(){{
        put(ID, Types.INTEGER);
        put(NOM, Types.VARCHAR);
        put(ACTIVA, Types.BOOLEAN);
    }};
}
