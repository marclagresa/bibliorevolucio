package base.EmplanarComboBox;

import bbdd.ColeccioDAO;
import contract.ContractColeccio;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import objecte.Coleccio;

import java.sql.SQLException;
import java.util.HashMap;

/**
 * Created by Marc on 18/05/2018.
 */
public class ClColeccio implements ChangeListener<String> {

    ComboBox cb;

    public ClColeccio(ComboBox combo){
        this.cb = combo;
    }
    /*Fas un listener per cade cop que es far un canvi en el combobox texecuti el codi
    * L'hi passes un comboox on li emplenes les dades i et fa una serca a la base de dades
    * depenen del valor del combobox, i el va actualitzan cade comp que canvia el text*/

    @Override
    public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
        try {
            if (cb.getSelectionModel().isEmpty()) {
                cb.getEditor().setStyle("-fx-background-color: white; -fx-opacity: 1.0;");

                String campOrdre = ContractColeccio.ID;

                HashMap<String, Object> cercaColeccio = new HashMap<>();
                cercaColeccio.put(ContractColeccio.NOM, newValue);

                ColeccioDAO objColeccioDAO = new ColeccioDAO();
                ObservableList<Coleccio> opcionsColeccio = null;

                opcionsColeccio = FXCollections.observableArrayList(objColeccioDAO.select(cercaColeccio, campOrdre, 10, 0, false));


                cb.setItems(opcionsColeccio);

            } else {
                cb.getEditor().setStyle("-fx-background-color: greenyellow; -fx-opacity: 0.5;");
                boolean reset;

                if (cb.getSelectionModel().getSelectedItem() instanceof String) {

                    // When you do lose the focus
                    reset = !cb.getSelectionModel().getSelectedItem().equals(newValue);

                } else {

                    // When you don't lose the focus
                    reset = !((Coleccio) cb.getSelectionModel().getSelectedItem()).getNom().equals(newValue);

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