package base.EmplanarComboBox;

import bbdd.PersonaDAO;
import contract.ContractPersona;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import objecte.Persona;

import java.sql.SQLException;
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
        try {
            if (cb.getSelectionModel().isEmpty()) {
                cb.getEditor().setStyle("-fx-background-color: white; -fx-opacity: 1.0;");
                String campOrdre = ContractPersona.ID;

                HashMap<String, Object> cercaPersona = new HashMap<>();
                cercaPersona.put(ContractPersona.NOM, newValue);

                PersonaDAO objPersonaDAO = new PersonaDAO();
                ObservableList<Persona> opcionsPersona = null;

                opcionsPersona = FXCollections.observableArrayList(objPersonaDAO.select(cercaPersona, campOrdre, 10, 0, false));


                cb.setItems(opcionsPersona);

            } else {
                cb.getEditor().setStyle("-fx-background-color: greenyellow; -fx-opacity: 0.5;");
                boolean reset;

                if (cb.getSelectionModel().getSelectedItem() instanceof String) {

                    // When you do lose the focus
                    reset = !cb.getSelectionModel().getSelectedItem().equals(newValue);

                } else {

                    // When you don't lose the focus
                    reset = !((Persona) cb.getSelectionModel().getSelectedItem()).getNom().equals(newValue);

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