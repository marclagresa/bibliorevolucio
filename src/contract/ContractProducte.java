package contract;

import java.sql.Types;
import java.util.HashMap;
/**
 * @author albertCorominas
 */

public abstract class ContractProducte {
    public static final String NOM_TAULA = "producte";
    public static final String ID = NOM_TAULA+".id_producte";
    public static final String ISBN = NOM_TAULA+".ISBN";
    public static final String NOM = NOM_TAULA+".nom";
    public static final String NUM_PAG = NOM_TAULA+".num_pag";
    public static final String DIMENSIONS = NOM_TAULA+".dimensions";
    public static final String ANY_PUBLICACIO = NOM_TAULA+".any_publicacio";
    public static final String RESUM = NOM_TAULA+".resum";
    public static final String CARACTERISTIQUES = NOM_TAULA+".caracteristiques";
    public static final String URL_PORTADA = NOM_TAULA+".url_portada";
    public static final String ADRECA_WEB = NOM_TAULA+".adreca_web";
    public static final String ESTAT = NOM_TAULA+".estat";

    public static final String EDITORIAL_ID = NOM_TAULA+".editorial_id";
    public static final String FORMAT_ID = NOM_TAULA+".format_id";
    public static final String PROCEDENCIA_ID = NOM_TAULA+".procedencia_id";
    public static final String COLECCIO_ID = NOM_TAULA+".coleccio_id";
    public static final String CDU = NOM_TAULA+".cdu";
    public static final String LLOC = NOM_TAULA+".lloc";

    public static final String PAIS = NOM_TAULA+".pais";
    public static final String IDIOMA_ID = ID+" in ( SELECT " + ContractProducteIdioma.ID_PRODUCTE + " FROM " + ContractProducteIdioma.NOM_TAULA;
    public static final String AUTORS = ID + " in ( SELECT " + ContractProductePersona.ID_PRODUCTE + " FROM " + ContractProductePersona.NOM_TAULA;
    public static final String NIVELL = ID + " IN (SELECT " + ContractProducteNivell.ID_PRODUCTE+ " FROM " + ContractProducteNivell.NOM_TAULA;
    public static final String MATERIA = ID + " IN (SELECT " + ContractMateriaProducte.ID_PRODUCTE+ " FROM " + ContractMateriaProducte.NOM_TAULA;

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
        put(EDITORIAL_ID,Types.INTEGER);
        put(FORMAT_ID, Types.INTEGER);
        put(PROCEDENCIA_ID, Types.INTEGER);
        put(COLECCIO_ID, Types.INTEGER);
        put(CDU, Types.VARCHAR);
        put(LLOC, Types.VARCHAR);
        put(PAIS, Types.VARCHAR);
        put(NIVELL,Types.ARRAY);
        put(IDIOMA_ID,Types.ARRAY);
        put(AUTORS,Types.ARRAY);
        put(MATERIA,Types.ARRAY );
    }};
}
