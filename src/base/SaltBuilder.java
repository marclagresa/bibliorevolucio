package base;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * Classe amb metode static que ens retorna una String aleatoria de 15 caracters
 * @author sergiclotas
 */

import org.apache.commons.text.RandomStringGenerator;
import static org.apache.commons.text.CharacterPredicates.LETTERS;
import static org.apache.commons.text.CharacterPredicates.DIGITS;
public class SaltBuilder{
    /**
     * Funcio per generar un salt 
     * @return una string amb minus,mayus i numeros de 15 digits de longitud.
     */
    public static String generateSalt(){
        RandomStringGenerator rsg=new RandomStringGenerator.Builder()
                .withinRange('0', 'z')
                .filteredBy(LETTERS,DIGITS)
                .build();
        return rsg.generate(15);
    }
}
