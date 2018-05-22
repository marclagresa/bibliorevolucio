package base.EmplanarComboBox;

import bbdd.EditorialDAO;
import contract.ContractEditorial;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import objecte.Editorial;

import java.util.HashMap;

/**
 * Created by Marc on 18/05/2018.
 */
public class  ClEditorial implements ChangeListener<String> {

    ComboBox cb;
    int cont =0 ;
    boolean press = false;


    public ClEditorial(ComboBox combo){
        this.cb = combo;
    }

    @Override
    public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {

        String campOrdre = ContractEditorial.ID;
        System.out.println(newValue.toString());
        System.out.println(oldValue.toString());
        if(!newValue.equals("")){
            HashMap<String,Object> cercaEditorial=new HashMap<>();
            cercaEditorial.put(ContractEditorial.NOM, newValue);
            try {
                EditorialDAO objEditorialDAO= new EditorialDAO();
                ObservableList<Editorial> opcionsEditorial = FXCollections.observableArrayList(objEditorialDAO.select(cercaEditorial,campOrdre,10,0,false));

                if (cb.getValue() == null) {
                    cb.setItems(opcionsEditorial);
                }

                System.out.println(opcionsEditorial.size()+opcionsEditorial.toString());
                cont ++;
                System.out.println(cont);

            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }else{
            ObservableList<Editorial> opcionsEditorial = null;
            cb.setItems(opcionsEditorial);

            cb.setValue(null);
        }
        System.out.println("hola");
    }
}