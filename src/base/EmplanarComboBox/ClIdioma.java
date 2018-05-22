package base.EmplanarComboBox;

import bbdd.IdiomaDAO;
import contract.ContractIdioma;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import objecte.Idioma;

import java.util.HashMap;

/**
 * Created by Marc on 18/05/2018.
 */
public class ClIdioma implements ChangeListener<String> {

    ComboBox cb;

    public ClIdioma(ComboBox combo){
        this.cb = combo;
    }

    @Override
    public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
        String campOrdre = ContractIdioma.ID;
        if(!newValue.equals("")){
            HashMap<String,Object> cercaIdioma=new HashMap<>();
            cercaIdioma.put(ContractIdioma.NOM, newValue);
            try {
                IdiomaDAO objIdiomaDAO= new IdiomaDAO();
                ObservableList<Idioma> opcionsIdioma = FXCollections.observableArrayList(objIdiomaDAO.select(cercaIdioma,campOrdre,10,0,false));

                if (cb.getValue() == null) {
                    cb.setItems(opcionsIdioma);
                }
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }else{
            ObservableList<Idioma> opcionsIdioma = null;
            cb.setItems(opcionsIdioma);
            cb.setValue(null);
        }
    }
}

