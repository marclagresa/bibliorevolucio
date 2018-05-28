/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bbdd;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

/**
 * Interficia que defineix els metodes generals que tindran els daos
 * Els metodes dels objectes DAO llançan excempcions ClassNotFoundException
 * si no s' ha pogut carregar el driver JDBC això es degut a que s' ha corrumput l' arxiu
 * de configuració Es recomana tenir una copia de seguretat dels arxius de configuracio.
 * 
 * @author AlbertCorominias,sergiclotas
 */
public interface IObjectDAO<E> {
    /**
     * Funció per actualitzar les dades d' un registre a la taula a la que fagi
     * referencia la classe implamentada
     * @param e Objecte Base del DAO
     * @return true si s' ha pogut actualitzar el registre altrament return false
     * @throws ClassNotFoundException Si No es pot carregar el driver JDBC
     * @throws SQLException si no s' ha pogut establir la conexió a la BBDD
     */
    public abstract boolean update(E e)throws ClassNotFoundException,SQLException;
    
    /**
     * Funció per inserir un registre nou a la taula que fagi referencia 
     * l'objecte DAO que implementi la interficia
     * @param e objecte base a la que fa referencia el DAO
     * @return true si s'ha pogut inserir el registre correctament
     * altrament retorna fals
     * @throws ClassNotFoundException si no s'ha pogut carregar el driver JDBC
     * @throws SQLException si no s'ha pogut conectar a la BBDD
     */
    public abstract boolean insert(E e)throws ClassNotFoundException,SQLException;
    /**
     * Funció que retorna tots els registres de la taula que fagi referencia 
     * l'objecte que implementi la interficia.
     * @return una Llista d' objecte al que fa referencia el DAO
     * @throws ClassNotFoundException si no s'ha pogut carregar el driver JDBC
     * @throws SQLException  si ha fallat la conexio amb la BBDD
     */
    public abstract List<E> selectAll()throws ClassNotFoundException,SQLException;
    /**
     * Funció per obtenir una llista de registres amb uns criteris de cerca rebuts per parametres
     * de la taula a la que fagi referencia l' objecte DAO que implementa la classe
     * 
     * @param dades 
     *  Un HashMap que conte com a claus les columnes i el valors per la sentencia where
     *  en el cas de taules amb relacions M N en aquest HashMap es podra enviar una sentencia per filtrar
     *  per els possibles valors de les relacions. Per exemple si volem buscar els productes que els seus nivells siguin
     *  ESO o BATXILLER li passariem per aqui. Mirar Contractes
     * @param campOrdre 
     *  una String que ens diu quina és la columna a utilitzar per la sentencia ORDER BY
     *  Se li pot passar null si no volem la query ordenada en una columna en concret
     * 
     * @param totalRegistres 
     *  un Enter que ens diu el total de registres que volem llistar. Se li pot passar null
     *  si els volem llistar tots tot i que no es recomana per tal de no carregar masses registre en memòria.
     *  
     *  tan registreInicial com totalRegistres s' utilitzan per la seqüencia LIMIT de la sentencia 
     *  quedan de la següent manera:
     * 
     *  LIMIT registreInicial,totalRegistres;
     * 
     * @param registreInicial
     *  un Enter que ens diu quin ha de ser el registre inicial de la query.
     *  se li pot enviar null si no volem indicar quin ha de ser el primer registre inicial.
     * 
     *  tan registreInicial com totalRegistres s' utilitzan per la seqüencia LIMIT de la sentencia 
     *  quedan de la següent manera:
     * 
     *  LIMIT registreInicial,totalRegistres;
     * 
     * @param ascendent
     * un Booleà per indicar si l' ordre ha de ser descendent o ascendent. Se li pot passar null 
     * i en aquest cas ens ordenera en ordre ASC (defecte de mysql) si li hem indicat un campOrdre
     * en cas que campOrdre sigui null aquest parametre no te efecte a la sentencia SQL 
     * per tant també hauria de ser null.
     * 
     * @return una llista amb els registres seleccionats de la taula faci referencia l' objecte que implementa
     * la interficia.
     * @throws SQLException si no s' ha pogut establir la conexio a la Base De Dades
     * @throws ClassNotFoundException  si no s'ha pogut carregar el driver JDBC
     * @throws IllegalArgumentException si algun dels valors enviats al hashmap no correspon al sqltype esperat.
     * 
     */
    public abstract List<E> select(HashMap <String,Object> dades,String campOrdre,Integer totalRegistres,Integer registreInicial,Boolean ascendent)throws SQLException,ClassNotFoundException,IllegalArgumentException;
    /**
     * Funció que ens retorna l'ultim ID +1 de la taula que faci referencia l' objecte DAO que implementa la interfica.
     * @return enter ultim id +1 o 1 si no hi han registres.
     * @throws ClassNotFoundException Si no s' ha pogut carregar el driver JDBC (comprovar arxiu de configuracio i llibreries)
     * @throws SQLException Si no s'ha pogut conectar a la bbdd (comprovar arxiu de configuracio i servidor on es troba la bbdd)
     */
    public abstract int nextId() throws ClassNotFoundException,SQLException;
    /**
     * Funció que retorna un objecte base de la taula a la que faci referencia
     * l'objecte DAO que implementa la interfica
     * @param id enter que es el valor de la clau primaria de la taula.
     * @return objcte registre amb id= al parametre rebut o una nova instacia de l'objecte si no existeix cap registre amb aquell id
     * @throws ClassNotFoundException Si no s' ha pogut carregar el driver JDBC
     * @throws SQLException  si no s'ha pogut conectar a la BBDD
     */
    public abstract E select(int id) throws ClassNotFoundException,SQLException;
    /**
     * Funció que retorna el total del registres d' una taula, pot contenir criteris
     * de cerca que s'implementan de la mateixa manera que el metode select(Has...)
     * si el HashMap esta buit retorna el total de registres de la taula
     * @param dades HashMap per definir els criteris de cerca per la query
     * @return enter amb el total de registres de la query
     * @throws ClassNotFoundException si no s' ha pogut carregar el driver JDBC 
     * @throws SQLException  si no s'ha pogut conectar a la BBDD
     */
    public abstract int selectCount(HashMap<String,Object>dades) throws ClassNotFoundException,SQLException;
   // public abstract void close();
}
