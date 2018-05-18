package base.EmplanarComboBox;

import bbdd.PersonaDAO;
import contract.ContractPersona;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import objecte.Persona;

import java.util.HashMap;

/**
 * Created by Marc on 18/05/2018.
 */
public class ClPersona implements ChangeListener<String> {

    ComboBox cb;

    public ClPersona(ComboBox combo){
        this.cb = combo;
    }

    @Override
    public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
        String campOrdre = ContractPersona.ID;
        if(!newValue.equals("")){
            HashMap<String,Object> cercaPersona=new HashMap<>();
            cercaPersona.put(ContractPersona.NOM, newValue);
            try {
                PersonaDAO objPersonaDAO= new PersonaDAO();
                ObservableList<Persona> opcionsPersona = FXCollections.observableArrayList(objPersonaDAO.select(cercaPersona,campOrdre,10,0,false));

                if (cb.getValue() == null) {
                    cb.setItems(opcionsPersona);
                }

            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }else{
            ObservableList<Persona> opcionsPersona = null;
            cb.setItems(opcionsPersona);
            cb.setValue(null);
        }
    }
}