/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package contract;

import java.sql.Types;
import java.util.HashMap;

/**
 * @author sergiclotas
 */
public abstract class ContractProductePersona {
    public static final String NOM_TAULA="`producte_autor`";
    public static final String ID = NOM_TAULA + ".`id`";
    public static final String ID_PRODUCTE = NOM_TAULA + ".`id_producte`";
    public static final String ID_PERSONA = NOM_TAULA + ".`id_persona`";
    public static final String DESCRIPCIO = NOM_TAULA + ".`descripcio`";
    public static final HashMap<String,Integer> DEFINICIO = new HashMap<String,Integer>(){{
        put(ID, Types.INTEGER);
        put(ID_PERSONA, Types.INTEGER);
        put(ID_PRODUCTE, Types.INTEGER);
        put(DESCRIPCIO, Types.VARCHAR);
    }};
}