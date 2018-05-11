/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package excepcions;

/**
 *
 * @author Rafel
 */
public class MaintenanceException extends Exception {

    public MaintenanceException() {
        super();
    }

    public MaintenanceException(String string) {
        super(string);
    }

    public MaintenanceException(String string, Throwable thrwbl) {
        super(string, thrwbl);
    }

    public MaintenanceException(Throwable thrwbl) {
        super(thrwbl);
    }
     
}
