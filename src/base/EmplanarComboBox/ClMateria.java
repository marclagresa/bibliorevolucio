package base.EmplanarComboBox;

import bbdd.MateriaDAO;
import contract.ContractMateria;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import objecte.Materia;

import java.util.HashMap;

/**
 * Created by Marc on 18/05/2018.
 */
public class ClMateria implements ChangeListener<String> {

    ComboBox cb;

    public ClMateria(ComboBox combo){
        this.cb = combo;
    }

    @Override
    public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
        String campOrdre = ContractMateria.ID;
        if(!newValue.equals("")){
            HashMap<String,Object> cercaMateria=new HashMap<>();
            cercaMateria.put(ContractMateria.NOM, newValue);
            try {
                MateriaDAO objMateriaDAO= new MateriaDAO();
                ObservableList<Materia> opcionsMateria = FXCollections.observableArrayList(objMateriaDAO.select(cercaMateria,campOrdre,10,0,false));

                if (cb.getValue() == null) {
                    cb.setItems(opcionsMateria);
                }
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }else{
            ObservableList<Materia> opcionsMateria = null;
            cb.setItems(opcionsMateria);
            cb.setValue(null);
        }
    }
}
