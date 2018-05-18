package base.EmplanarComboBox;

import bbdd.NivellDAO;
import contract.ContractNivell;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import objecte.Nivell;

import java.util.HashMap;

/**
 * Created by Marc on 18/05/2018.
 */
public class ClNivell implements ChangeListener<String> {

    ComboBox cb;

    public ClNivell(ComboBox combo){
        this.cb = combo;
    }

    @Override
    public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
        String campOrdre = ContractNivell.ID;
        if(!newValue.equals("")){
            HashMap<String,Object> cercaNivell=new HashMap<>();
            cercaNivell.put(ContractNivell.NOM, newValue);
            try {
                NivellDAO objNivellDAO= new NivellDAO();
                ObservableList<Nivell> opcionsNivell = FXCollections.observableArrayList(objNivellDAO.select(cercaNivell,campOrdre,10,0,false));

                if (cb.getValue() == null) {
                    cb.setItems(opcionsNivell);
                }

            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }else{
            ObservableList<Nivell> opcionsNivell = null;
            cb.setItems(opcionsNivell);
            cb.setValue(null);
        }
    }
}
