/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package contract;

/**
 *
 * @author sergiclotas
 */
public abstract class ContractProducteIdioma {

    //NOM DE LA TAULA: es el nom de la taula que s'utilitza a la base de dades.

    public static final String NOM_TAULA="producte_idioma";

    //CAMPS DE LA TAULA: es el noms dels camps* que conté la taula.
    //*S'afagueix el nom de la taula i un punt al principi per que els camps no siguin ambigu.

    public static final String ID_PRODUCTE=NOM_TAULA+".id_producte";
    public static final String ID_IDIOMA=NOM_TAULA+".id_idioma";
}
