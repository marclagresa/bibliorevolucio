package contract;

import java.sql.Types;
import java.util.HashMap;

/**
 * @author albertCorominas
 */

public abstract class ContractProducte {
    public static final String NOM_TAULA = ".producte";
    public static final String ID = NOM_TAULA+".id_producte";
    public static final String ISBN = NOM_TAULA+".ISBN";
    public static final String NOM = NOM_TAULA+".nom";
    public static final String NUM_PAG = NOM_TAULA+".num_pag";
    public static final String DIMENSIONS = NOM_TAULA+".dimensions(cm)";
    public static final String ANY_PUBLICACIO = NOM_TAULA+".any_publicacio";
    public static final String RESUM = NOM_TAULA+".resum";
    public static final String CARACTERISTIQUES = NOM_TAULA+".caracteristiques";
    public static final String URL_PORTADA = NOM_TAULA+".url_portada";
    public static final String ADRECA_WEB = NOM_TAULA+".adreca_web";
    public static final String ESTAT = NOM_TAULA+".estat";
    public static final String IDIOMA_ID = NOM_TAULA+".idioma_id";
    public static final String EDITORIAL_ID = NOM_TAULA+".editorial_id";
    public static final String FORMAT_ID = NOM_TAULA+".format_id";
    public static final String PROCEDENCIA_ID = NOM_TAULA+".procedencia_id";
    public static final String NIVELL_ID = NOM_TAULA+".nivell_id";
    public static final String COLECCIO_ID = NOM_TAULA+".coleccio_id";
    public static final String CDU_ID = NOM_TAULA+".cdu_id";
    public static HashMap<String,Integer> DEFINICIO = new HashMap<String,Integer>(){{
        put(ID, Types.INTEGER);
        put(ISBN, Types.VARCHAR);
        put(NOM, Types.VARCHAR);
        put(NUM_PAG, Types.INTEGER);
        put(DIMENSIONS,Types.VARCHAR);
        put(ANY_PUBLICACIO, Types.DATE);
        put(RESUM, Types.VARCHAR);
        put(CARACTERISTIQUES, Types.VARCHAR);
        put(URL_PORTADA, Types.VARCHAR);
        put(ADRECA_WEB, Types.VARCHAR);
        put(ESTAT, Types.BOOLEAN);
        put(IDIOMA_ID,Types.INTEGER);
        put(EDITORIAL_ID,Types.INTEGER);
        put(FORMAT_ID, Types.INTEGER);
        put(PROCEDENCIA_ID, Types.INTEGER);
        put(NIVELL_ID, Types.INTEGER);
        put(COLECCIO_ID, Types.INTEGER);
        put(CDU_ID, Types.INTEGER);
    }};
}
