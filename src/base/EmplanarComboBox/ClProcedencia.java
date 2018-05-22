package base.EmplanarComboBox;

import bbdd.ProcedenciaDAO;
import contract.ContractProcedencia;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import objecte.Procedencia;

import java.util.HashMap;

/**
 * Created by Marc on 18/05/2018.
 */
public class ClProcedencia implements ChangeListener<String> {

    ComboBox cb;

    public ClProcedencia(ComboBox combo){
        this.cb = combo;
    }

    @Override
    public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
        String campOrdre = ContractProcedencia.ID;
        if(!newValue.equals("")){
            HashMap<String,Object> cercaProcedencia=new HashMap<>();
            cercaProcedencia.put(ContractProcedencia.NOM, newValue);
            try {
                ProcedenciaDAO objProcedenciaDAO= new ProcedenciaDAO();
                ObservableList<Procedencia> opcionsProcedencia = FXCollections.observableArrayList(objProcedenciaDAO.select(cercaProcedencia,campOrdre,10,0,false));

                if (cb.getValue() == null) {
                    cb.setItems(opcionsProcedencia);
                }

            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }else{
            ObservableList<Procedencia> opcionsProcedencia = null;
            cb.setItems(opcionsProcedencia);
            cb.setValue(null);
        }
    }
}
