package contract;

import java.sql.Types;
import java.util.HashMap;

/**
 * @author albertCorominas
 */
public abstract class ContractProcedencia {

    //NOM DE LA TAULA: es el nom de la taula que s'utilitza a la base de dades.

    public static final String NOM_TAULA = "procedencia";

    //CAMPS DE LA TAULA: es el noms dels camps* que conté la taula.
    //*S'afagueix el nom de la taula i un punt al principi per que els camps no siguin ambigu.

    public static final String ID = NOM_TAULA+".id";
    public static final String NOM = NOM_TAULA+".nom";
    public static final String ACTIVA = NOM_TAULA+".activa";
    //DEFINICIO DE LA TAULA: amb aquest HashMap el que aconseguim es relacionar tots els camps de la taula amb un tipus de variable*.
    //*Aixó ens serveix més endevant per poder fer comprovacions d'entrades d'usuari.

    public static HashMap<String,Integer> DEFINICIO = new HashMap<String,Integer>(){{
        put(ID, Types.INTEGER);
        put(NOM, Types.VARCHAR);
        put(ACTIVA, Types.BOOLEAN);
    }};
}
