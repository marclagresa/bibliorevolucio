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
public abstract class ContractMateriaProducte {

    //NOM DE LA TAULA: es el nom de la taula que s'utilitza a la base de dades.

    public static final String NOM_TAULA ="producte_materia";

    //CAMPS DE LA TAULA: es el noms dels camps* que conté la taula.
    //*S'afagueix el nom de la taula i un punt al principi per que els camps no siguin ambigu.

    public static final String ID_PRODUCTE=NOM_TAULA+".producte_id";
    public static final String ID_MATERIA=NOM_TAULA+".materia_id";
}
