package base.EmplanarComboBox;

import bbdd.ColeccioDAO;
import contract.ContractColeccio;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import objecte.Coleccio;

import java.util.HashMap;

/**
 * Created by Marc on 18/05/2018.
 */
public class ClColeccio implements ChangeListener<String> {

    ComboBox cb;

    public ClColeccio(ComboBox combo){
        this.cb = combo;
    }

    @Override
    public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
        String campOrdre = ContractColeccio.ID;
        if(!newValue.equals("")){
            HashMap<String,Object> cercaColeccio=new HashMap<>();
            cercaColeccio.put(ContractColeccio.NOM, newValue);
            try {
                ColeccioDAO objColeccioDAO= new ColeccioDAO();
                ObservableList<Coleccio> opcionsColeccio = FXCollections.observableArrayList(objColeccioDAO.select(cercaColeccio,campOrdre,10,0,false));

                if (cb.getValue() == null) {
                    cb.setItems(opcionsColeccio);
                }
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }else{
            ObservableList<Coleccio> opcionsColeccio = null;
            cb.setItems(opcionsColeccio);
            cb.setValue(null);
        }
    }
}