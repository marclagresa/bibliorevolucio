package base.EmplanarComboBox;

import bbdd.FormatDAO;
import contract.ContractFormat;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import objecte.Format;

import java.util.HashMap;

/**
 * Created by Marc on 18/05/2018.
 */
public class ClFormat implements ChangeListener<String> {

    ComboBox cb;

    public ClFormat(ComboBox combo){
        this.cb = combo;
    }

    @Override
    public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
        String campOrdre = ContractFormat.ID;
        if(!newValue.equals("")){
            HashMap<String,Object> cercaFormat=new HashMap<>();
            cercaFormat.put(ContractFormat.NOM, newValue);
            try {
                FormatDAO objFormatDAO= new FormatDAO();
                ObservableList<Format> opcionsFormat = FXCollections.observableArrayList(objFormatDAO.select(cercaFormat,campOrdre,10,0,false));

                if (cb.getValue() == null) {
                    cb.setItems(opcionsFormat);
                }

            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }else{
            ObservableList<Format> opcionsFormat = null;
            cb.setItems(opcionsFormat);
            cb.setValue(null);
        }
    }
}
