package base.EmplanarComboBox;

import bbdd.ProcedenciaDAO;
import contract.ContractProcedencia;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import objecte.Procedencia;

import java.sql.SQLException;
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
        try {
            if (cb.getSelectionModel().isEmpty()) {
                cb.getEditor().setStyle("-fx-background-color: white; -fx-opacity: 1.0;");

                String campOrdre = ContractProcedencia.ID;

                HashMap<String, Object> cercaProcedencia = new HashMap<>();
                cercaProcedencia.put(ContractProcedencia.NOM, newValue);

                ProcedenciaDAO objProcedenciaDAO = new ProcedenciaDAO();
                ObservableList<Procedencia> opcionsProcedencia = null;

                opcionsProcedencia = FXCollections.observableArrayList(objProcedenciaDAO.select(cercaProcedencia, campOrdre, 10, 0, false));


                cb.setItems(opcionsProcedencia);

            } else {
                cb.getEditor().setStyle("-fx-background-color: greenyellow; -fx-opacity: 0.5;");
                boolean reset;

                if (cb.getSelectionModel().getSelectedItem() instanceof String) {

                    // When you do lose the focus
                    reset = !cb.getSelectionModel().getSelectedItem().equals(newValue);

                } else {

                    // When you don't lose the focus
                    reset = !((Procedencia) cb.getSelectionModel().getSelectedItem()).getNom().equals(newValue);

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
