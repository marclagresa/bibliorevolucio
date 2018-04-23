/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package base;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javax.xml.bind.DatatypeConverter;
/**
 * Objecte que te un metode static per aconseguir el hash d' un string
 * @author sergiclotas
 */
public class HashGenerator {
    /**
     * Funció que ens retorna un has sha-256 de una string rebuda per parametre
     * @param s de la que volem fer el hash
     * @return hash amb l' algoritme SHA-256
     * @throws NoSuchAlgorithmException 
     */
    public static String generateHash(String s) throws NoSuchAlgorithmException{
        String hashString;
        
        MessageDigest md=MessageDigest.getInstance("SHA-256");
        md.update(s.getBytes());
        hashString=DatatypeConverter.printHexBinary(md.digest()).toUpperCase();
        
        return hashString;
    }
}
