package base.EmplanarComboBox;

import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;

import java.util.ArrayList;

/**
 * Created by Marc on 25/05/2018.
 */
public class OpcionsListView {
    /*Li passes un combo un listView i una observablelist agafes el cobobox agafes el seu valor i l'afageixes
    * dintre de una observavbleList despres passes aquesta observablelist i la poses dintre el listview
    * i comprobes que el mateix valor no a estat repatit, tambe pots eliminar un registre */
    public static<E> void afeguirDadeListView(ComboBox<E> comboBox, ListView lw, ObservableList items){
        boolean repatit = false;

        for(int j = 0;items.size()>j;j++) {

            if (items.get(j).toString().equals(comboBox.getSelectionModel().getSelectedItem().toString())) {
                repatit = true;
                break;
            }
        }
        if (!repatit){
            items.add(comboBox.getItems().get(comboBox.getSelectionModel().getSelectedIndex()));
            lw.getItems().clear();
            lw.getItems().addAll(items);
        }else {

        }

    }
    public static void elimDadeListView(ListView lw, ObservableList items){
        for(int j = 0;items.size()>j;j++){
            System.out.println(items.get(j).toString());

            if(lw.getSelectionModel().getSelectedItems().contains(items.get(j))){
                items.remove(j);
                lw.getItems().clear();
                lw.getItems().addAll(items);
            }
        }

    }
}
