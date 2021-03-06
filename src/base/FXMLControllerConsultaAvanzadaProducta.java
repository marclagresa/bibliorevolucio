package base;


import java.io.IOException;
import java.net.URL;
import java.nio.file.FileSystems;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
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
    public  ObservableList<Persona> itemsAutors = FXCollections.observableArrayList();
    public  ObservableList<Idioma> itemsIdioma = FXCollections.observableArrayList();
    public  ObservableList<Nivell> itemsNivell = FXCollections.observableArrayList();

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
    @FXML
    private CheckBox actiu;
    @FXML
    private Button tornarEnreraB;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //inicialitces la funcio dels combobox
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
        //Passes les dades per consultar
        ArrayList<Integer> arrayAutor = new ArrayList<>();
        ArrayList<Integer> arrayIdioma = new ArrayList<>();
        ArrayList<Integer> arrayNivell = new ArrayList<>();
        Producte prod = new Producte();
        HashMap<String, Object> consulta = new HashMap<>();
        //comprobes si hi ha alguna dada
        if(actiu.isSelected()) {
            consulta.put(ContractProducte.ACTIVA, actiu.isSelected());
        }
        if (!titolTF.getText().isEmpty()) {
            consulta.put(ContractProducte.NOM, titolTF.getText());
        }
        if (!isbnTF.getText().isEmpty()) {
            consulta.put(ContractProducte.ISBN, isbnTF.getText());
        }
        if (!cLliureTF.getText().isEmpty()){
            consulta.put(ContractProducte.RESUM, cLliureTF.getText());
        }
        if (dataDP.getValue() != null){
            consulta.put(ContractProducte.ANY_PUBLICACIO, dataDP.getValue());
        }
        //combobox agafes la id de l'objecte
        if(editorialComB.getSelectionModel().getSelectedIndex()!= -1) {

            consulta.put(ContractProducte.EDITORIAL_ID, editorialComB.getItems().get(editorialComB.getSelectionModel().getSelectedIndex()).getId());
        }

        if(cduComB.getSelectionModel().getSelectedIndex()!= -1) {
            consulta.put(ContractProducte.CDU, cduComB.getItems().get( cduComB.getSelectionModel().getSelectedIndex() ).getId());
        }

        if(formatComB.getSelectionModel().getSelectedIndex()!= -1) {
            consulta.put(ContractProducte.FORMAT_ID, formatComB.getItems().get(formatComB.getSelectionModel().getSelectedIndex()).getId());
        }

        // combobox agafes tots els que hi hagin dintre la observable list
        if(autorComB.getSelectionModel().getSelectedIndex()!= -1) {
            for(int j = 0;itemsAutors.size()>j;j++) {
                arrayAutor.add(itemsAutors.get(j).getId());
            }

            System.out.println(arrayAutor);
            consulta.put(ContractProducte.AUTORS, arrayAutor.toArray(new Integer[arrayAutor.size()]));
        }
        if( nivellComB.getSelectionModel().getSelectedIndex()!= -1) {
            for(int j = 0;itemsNivell.size()>j;j++) {
                arrayNivell.add(itemsNivell.get(j).getId());
            }
            consulta.put(ContractProducte.NIVELL, arrayNivell.toArray(new Integer[arrayNivell.size()]));
        }
        if( idiomaComB.getSelectionModel().getSelectedIndex()!= -1) {
            for(int j = 0;itemsIdioma.size()>j;j++) {
                arrayIdioma.add(itemsIdioma.get(j).getId());
            }
            consulta.put(ContractProducte.IDIOMA_ID, arrayIdioma.toArray(new Integer[arrayIdioma.size()]));
        }
        if(onAcceptCallBack != null) {
            onAcceptCallBack.accept(consulta);
        }
            ((Stage) consultaB.getScene().getWindow()).close();

    }
    @FXML
    public void cancelar(){
        if(onCancelCallBack != null) {
            onCancelCallBack.run();
        }
        ((Stage) tornarEnreraB.getScene().getWindow()).close();

    }

    public static FXMLControllerConsultaAvanzadaProducta crear(Window owner, boolean isModal) throws IOException {

        return crearPopUp("/fxml/FXMLConsultaAvanzadaProducta.fxml", FXMLControllerConsultaAvanzadaProducta.class, owner, isModal, null);
    }

    @FXML
    public void afegirAutor(){
        OpcionsListView.afeguirDadeListView(autorComB, autorList, itemsAutors);
    }
    @FXML
    public void afegirIdioma(){
        OpcionsListView.afeguirDadeListView(idiomaComB, idiomaList,itemsIdioma);
    }
    @FXML
    public void afegirNivell(){
        OpcionsListView.afeguirDadeListView(nivellComB, nivellList,itemsNivell);
    }
    @FXML
    public void elimAutor(){OpcionsListView.elimDadeListView(autorList,itemsAutors);}
    @FXML
    public void elimIdioma(){OpcionsListView.elimDadeListView(idiomaList,itemsIdioma);}
    @FXML
    public void elimNivell(){OpcionsListView.elimDadeListView(nivellList,itemsNivell);}




    @Override
    public void emplenarDades(Object obj) {

    }
}
