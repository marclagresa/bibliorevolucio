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

    //NOM DE LA TAULA: es el nom de la taula que s'utilitza a la base de dades.

    public static final String NOM_TAULA="producte_autor";

    //CAMPS DE LA TAULA: es el noms dels camps* que conté la taula.
    //*S'afagueix el nom de la taula i un punt al principi per que els camps no siguin ambigu.

    public static final String ID = NOM_TAULA + ".id";
    public static final String ID_PRODUCTE = NOM_TAULA + ".id_producte";
    public static final String ID_PERSONA = NOM_TAULA + ".id_persona";
    public static final String DESCRIPCIO = NOM_TAULA + ".descripcio";

    //DEFINICIO DE LA TAULA: amb aquest HashMap el que aconseguim es relacionar tots els camps de la taula amb un tipus de variable*.
    //*Aixó ens serveix més endevant per poder fer comprovacions d'entrades d'usuari.

    public static final HashMap<String,Integer> DEFINICIO = new HashMap<String,Integer>(){{
        put(ID, Types.INTEGER);
        put(ID_PERSONA, Types.INTEGER);
        put(ID_PRODUCTE, Types.INTEGER);
        put(DESCRIPCIO, Types.VARCHAR);
    }};
}