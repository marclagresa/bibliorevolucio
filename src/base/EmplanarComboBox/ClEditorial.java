package base.EmplanarComboBox;

import bbdd.EditorialDAO;
import contract.ContractEditorial;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import objecte.Editorial;

import java.sql.SQLException;
import java.util.HashMap;

/**
 * Created by Marc on 18/05/2018.
 */
public class  ClEditorial implements ChangeListener<String> {

    ComboBox cb;
    int cont =0 ;
    boolean press = false;


    public ClEditorial(ComboBox combo){
        this.cb = combo;
    }

    @Override
    public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
        try {
            cb.getEditor().setStyle("-fx-background-color: white; -fx-opacity: 1.0;");

            if (cb.getSelectionModel().isEmpty()) {

                String campOrdre = ContractEditorial.ID;

                HashMap<String, Object> cercaEditorial = new HashMap<>();
                cercaEditorial.put(ContractEditorial.NOM, newValue);
                cercaEditorial.put(ContractEditorial.ACTIVA, true);

                EditorialDAO objEditorialDAO = new EditorialDAO();
                ObservableList<Editorial> opcionsEditorial = null;

                opcionsEditorial = FXCollections.observableArrayList(objEditorialDAO.select(cercaEditorial, campOrdre, 10, 0, false));


                cb.setItems(opcionsEditorial);

            } else {
                cb.getEditor().setStyle("-fx-background-color: greenyellow; -fx-opacity: 0.5;");
                boolean reset;

                if (cb.getSelectionModel().getSelectedItem() instanceof String) {

                    // When you do lose the focus
                    reset = !cb.getSelectionModel().getSelectedItem().equals(newValue);

                } else {

                    // When you don't lose the focus
                    reset = !((Editorial) cb.getSelectionModel().getSelectedItem()).getNom().equals(newValue);

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