package base.EmplanarComboBox;

import bbdd.MateriaDAO;
import contract.ContractMateria;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import objecte.Materia;

import java.sql.SQLException;
import java.util.HashMap;

/**
 * Created by Marc on 18/05/2018.
 */
public class ClMateria implements ChangeListener<String> {
    /*Fas un listener per cade cop que es far un canvi en el combobox texecuti el codi
    * L'hi passes un comboox on li emplenes les dades i et fa una serca a la base de dades
    * depenen del valor del combobox, i el va actualitzan cade comp que canvia el text*/

    ComboBox cb;

    public ClMateria(ComboBox combo){
        this.cb = combo;
    }

    @Override
    public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
        try {
            cb.getEditor().setStyle("-fx-background-color: white; -fx-opacity: 1.0;");
            if (cb.getSelectionModel().isEmpty()) {

                String campOrdre = ContractMateria.ID;

                HashMap<String, Object> cercaMateria = new HashMap<>();
                cercaMateria.put(ContractMateria.NOM, newValue);

                MateriaDAO objMateriaDAO = new MateriaDAO();
                ObservableList<Materia> opcionsMateria = null;

                opcionsMateria = FXCollections.observableArrayList(objMateriaDAO.select(cercaMateria, campOrdre, 10, 0, false));


                cb.setItems(opcionsMateria);

            } else {
                cb.getEditor().setStyle("-fx-background-color: greenyellow; -fx-opacity: 0.5;");
                boolean reset;

                if (cb.getSelectionModel().getSelectedItem() instanceof String) {

                    // When you do lose the focus
                    reset = !cb.getSelectionModel().getSelectedItem().equals(newValue);

                } else {

                    // When you don't lose the focus
                    reset = !((Materia) cb.getSelectionModel().getSelectedItem()).getNom().equals(newValue);

                }

                if (reset) {
                    cb.getSelectionModel().clearSelection();
                    cb.setValue(newValue);
                }

            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
