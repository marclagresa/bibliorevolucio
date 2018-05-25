package base;


import java.io.IOException;
import java.net.URL;
import java.nio.file.FileSystems;
import java.util.HashMap;
import java.util.ResourceBundle;

import base.EmplanarComboBox.*;
import contract.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.fxml.FXML;
import objecte.*;

public class FXMLControllerConsultaAvanzadaProducta extends GenericPopUp implements Initializable {
    public int contador;
    public static ObservableList<String> itemsAutors = FXCollections.observableArrayList();
    public static ObservableList<String> itemsIdioma = FXCollections.observableArrayList();
    public static ObservableList<String> itemsNivell = FXCollections.observableArrayList();
    @FXML
    private TextField titolTF;
    @FXML
    private ComboBox<Persona> autorComB;
    @FXML
    private ComboBox<Editorial> editorialComB;
    @FXML
    private TextField isbnTF;
    @FXML
    private ComboBox<Cdu> cduComB;
    @FXML
    private TextField cLliureTF;
    @FXML
    private ComboBox<Idioma> idiomaComB;
    @FXML
    private ComboBox<Format> formatComB;
    @FXML
    private ComboBox<Nivell> nivellComB;
    @FXML
    private DatePicker dataDP;
    @FXML
    private Button consultaB;
    @FXML
    private ListView autorList;
    @FXML
    private ListView idiomaList;
    @FXML
    private ListView nivellList;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        ClNivell clNivell = new ClNivell(nivellComB);
        nivellComB.getEditor().textProperty().addListener(clNivell);

        ClEditorial clEditorial = new ClEditorial(editorialComB);
        editorialComB.getEditor().textProperty().addListener(clEditorial);

        ClCdu clCdu = new ClCdu(cduComB);
        cduComB.getEditor().textProperty().addListener(clCdu);

        ClIdioma clIdioma = new ClIdioma(idiomaComB);
        idiomaComB.getEditor().textProperty().addListener(clIdioma);

        ClFormat clFormat = new ClFormat(formatComB);
        formatComB.getEditor().textProperty().addListener(clFormat);

        ClPersona clPersona = new ClPersona(autorComB);
        autorComB.getEditor().textProperty().addListener(clPersona);

    }
    @FXML
    public void consultar(ActionEvent actionEvent) {
        Producte prod = new Producte();
        HashMap<String, Object> consulta = new HashMap<>();
        consulta.put(ContractProducte.NOM, titolTF.getText());
        consulta.put(ContractProducte.ISBN, isbnTF.getText());
        consulta.put(ContractProducte.ANY_PUBLICACIO, dataDP.getValue());
        if(autorComB.getSelectionModel().getSelectedIndex()!= -1) {
            consulta.put(ContractPersona.NOM, autorComB.getItems().get( autorComB.getSelectionModel().getSelectedIndex() ));
        }
        if(editorialComB.getSelectionModel().getSelectedIndex()!= -1) {
            consulta.put(ContractProducte.EDITORIAL_ID, editorialComB.getItems().get(editorialComB.getSelectionModel().getSelectedIndex()));
        }
        if(cduComB.getSelectionModel().getSelectedIndex()!= -1) {
            consulta.put(ContractCdu.ID, cduComB.getItems().get( cduComB.getSelectionModel().getSelectedIndex() ));
        }

        if(formatComB.getSelectionModel().getSelectedIndex()!= -1) {
            consulta.put(ContractProducte.FORMAT_ID, formatComB.getItems().get(formatComB.getSelectionModel().getSelectedIndex()));
        }
        if( nivellComB.getSelectionModel().getSelectedIndex()!= -1) {
            consulta.put(ContractNivell.ID, nivellComB.getItems().get( nivellComB.getSelectionModel().getSelectedIndex() ));
        }
        System.out.println(consulta);
        ((Stage) consultaB.getScene().getWindow()).close();
    }
    @FXML
    public void cancelar(){
        ((Stage) consultaB.getScene().getWindow()).close();

    }

    public static FXMLControllerConsultaAvanzadaProducta crear(Window owner, boolean isModal) throws IOException {

        return crearPopUp("/fxml/FXMLConsultaAvanzadaProducta.fxml", FXMLControllerConsultaAvanzadaProducta.class, owner, isModal, null);
    }

    @FXML
    public void afegirAutor(){
        afeguirDadeListView(autorComB, autorList, itemsAutors);
    }
    @FXML
    public void afegirIdioma(){
        afeguirDadeListView(idiomaComB, idiomaList,itemsIdioma);
    }
    @FXML
    public void afegirNivell(){
        afeguirDadeListView(nivellComB, nivellList,itemsNivell);
    }
    @FXML
    public void elimAutor(){
        System.out.println("hola");elimDadeListView(autorList,itemsAutors);}
    @FXML
    public void elimIdioma(){elimDadeListView(idiomaList,itemsIdioma);}
    @FXML
    public void elimNivell(){elimDadeListView(nivellList,itemsNivell);}



    public static void afeguirDadeListView(ComboBox comboBox, ListView lw, ObservableList items){
        items.add(comboBox.getSelectionModel().getSelectedItem().toString());
        System.out.println(items);
        lw.getItems().clear();
        lw.getItems().addAll(items);
    }
    public static void elimDadeListView(ListView lw, ObservableList items){
        System.out.println(lw.getSelectionModel().getSelectedItems().toString());
        for(int j = 0;items.size()>j;j++){
            System.out.println(items.get(j).toString());
            if(lw.getSelectionModel().getSelectedItems().contains(items.get(j).toString())){
                System.out.println("que pasa");
                items.remove(j);
                lw.getItems().clear();
                lw.getItems().addAll(items);
            }
        }

    }
    @Override
    public void emplenarDades(Object obj) {

    }
}
