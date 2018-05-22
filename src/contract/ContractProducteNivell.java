/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package contract;

import java.sql.Types;
import java.util.HashMap;

/**
 *
 * @author sergiclotas
 */
public class ContractProducteNivell {
    public static final String NOM_TAULA="producte_nivell";
    public static final String ID_PRODUCTE=NOM_TAULA+".id_producte";
    public static final String ID_NIVELL= NOM_TAULA+".id_nivell";
    public static HashMap<String,Integer> DEFINICIO = new HashMap<String,Integer>(){{
        put(ID_NIVELL, Types.INTEGER);
        put(ID_PRODUCTE, Types.INTEGER);
    }};
}
