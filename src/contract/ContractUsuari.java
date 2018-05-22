/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package contract;


import java.util.HashMap;
import java.sql.Types;
/**
 *
 * @author sergiclotas
 */
public abstract class ContractUsuari {
    public static final String NOM_TAULA = "usuari";
    public static final String ID = NOM_TAULA+".id";
    public static final String NOM = NOM_TAULA+".nom";
    public static final String PRIMER_COGNOM = NOM_TAULA+".p_cognom";
    public static final String SEGON_COGNOM = NOM_TAULA+".s_cognom";
    public static final String TELEFON_MOBIL = NOM_TAULA+".telefon_mobil";
    public static final String TELEFON_FIX = NOM_TAULA+".telefon_fixe";
    public static final String EMAIL = NOM_TAULA+".email";
    public static final String CONTRASENYA = NOM_TAULA+".contrasenya";
    public static final String ACTIU = NOM_TAULA+".estat";
    public static final String SALT = NOM_TAULA+".saltContr";
    public static final String ADMIN = NOM_TAULA+".esAdmin";
    public static final String ID_NIVELL = NOM_TAULA+".nivell";
    public static final HashMap<String,Integer> DEFINICIO  = new HashMap<String,Integer>(){{
        //Aquest HashMap s' utilitza per comprovar el tipus de dades rebudes en una query
        //Ens defineix cada columna de quin tipus es.
        put(ID, Types.INTEGER);
        put(NOM, Types.VARCHAR);
        put(PRIMER_COGNOM, Types.VARCHAR);
        put(SEGON_COGNOM, Types.VARCHAR);
        put(TELEFON_MOBIL, Types.VARCHAR);
        put(TELEFON_FIX, Types.VARCHAR);
        put(EMAIL, Types.VARCHAR);
        put(CONTRASENYA, Types.CHAR);
        put(ACTIU, Types.BOOLEAN);
        put(ADMIN,Types.BOOLEAN);
        put(SALT, Types.CHAR);
        put(ID_NIVELL, Types.INTEGER);
    }};   
}
