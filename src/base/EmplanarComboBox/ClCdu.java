package base.EmplanarComboBox;

import bbdd.CduDAO;
import contract.ContractCdu;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import objecte.Cdu;

import java.util.HashMap;

/**
 * Created by Marc on 18/05/2018.
 */
public class ClCdu implements ChangeListener<String> {

    ComboBox cb;

    public ClCdu(ComboBox combo){
        this.cb = combo;
    }

    @Override
    public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
        String campOrdre = ContractCdu.ID;
        if(!newValue.equals("")){
            HashMap<String,Object> cercaCdu=new HashMap<>();
            cercaCdu.put(ContractCdu.NOM, newValue);
            try {
                CduDAO objCduDAO= new CduDAO();
                ObservableList<Cdu> opcionsCdu = FXCollections.observableArrayList(objCduDAO.select(cercaCdu,campOrdre,10,0,false));

                if (cb.getValue() == null) {
                    cb.setItems(opcionsCdu);
                }

            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }else{
            ObservableList<Cdu> opcionsCdu = null;
            cb.setItems(opcionsCdu);
            cb.setValue(null);
        }
    }
}