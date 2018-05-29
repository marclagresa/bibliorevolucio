package base;

import base.EmplanarComboBox.ClNivell;
import base.EmplanarComboBox.OpcionsListView;
import contract.ContractNivell;
import contract.ContractUsuari;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.Window;
import objecte.*;


import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Marc on 19/04/2018.
 */
public class FXMLControllerConsultaAvanzadaUsuari extends GenericPopUp implements Initializable {
    public ObservableList<Nivell> itemsNivell = FXCollections.observableArrayList();
    @FXML private TextField nomTF;
    @FXML private ComboBox<Nivell> nivellComB;
    @FXML private TextField cognom_1TF;
    @FXML private TextField cognom_2TF;
    @FXML private TextField emailTF;
    @FXML private CheckBox adminCheckB;
    @FXML private CheckBox actiuCheckB;
    @FXML private ListView nivellList;
    @FXML private Button consultar;
    @FXML private Button cancelar;
    @FXML
    public void consultar(ActionEvent actionEvent) {
        ArrayList<Integer> arrayNivell = new ArrayList<>();
        Usuari user = new Usuari();
        HashMap<String, Object> consulta = new HashMap<>();
        if (comEmail()) {
            if(!nomTF.getText().isEmpty()) {
                consulta.put(ContractUsuari.NOM, nomTF.getText());
            }
            if(!cognom_1TF.getText().isEmpty()) {
                consulta.put(ContractUsuari.PRIMER_COGNOM, cognom_1TF.getText());
            }
            if(!cognom_2TF.getText().isEmpty()) {
                consulta.put(ContractUsuari.SEGON_COGNOM, cognom_2TF.getText());
            }
            if(!emailTF.getText().isEmpty()) {
                consulta.put(ContractUsuari.EMAIL, emailTF.getText());
            }
            if(adminCheckB.isSelected()) {
                consulta.put(ContractUsuari.ADMIN, adminCheckB.isSelected());
            }
            if(adminCheckB.isSelected()) {
                consulta.put(ContractUsuari.ACTIU, actiuCheckB.isSelected());
            }
            if( nivellComB.getSelectionModel().getSelectedIndex()!= -1) {
                for(int j = 0;itemsNivell.size()>j;j++) {
                    arrayNivell.add(itemsNivell.get(j).getId());
                }
                consulta.put(ContractNivell.ID, arrayNivell.toArray(new Integer[arrayNivell.size()]));
            }
            System.out.println(user.getNivell());
        }else{
            emailTF.setStyle("-fx-border-color: red;"+
                    " -fx-background-color:#FFCDD2");
            Alert alerta = new Alert(Alert.AlertType.WARNING);
            alerta.setTitle("Alerta");
            alerta.setHeaderText("Tens algun camp incorrecta");
            alerta.showAndWait().ifPresent(rs -> {
                if (rs == ButtonType.OK) {
                }
            });}
        if(onAcceptCallBack != null) {
            onAcceptCallBack.accept(consulta);
        }
        ((Stage) consultar.getScene().getWindow()).close();



    }
    @FXML
    public void cancelar(){
        if(onCancelCallBack != null) {
            onCancelCallBack.run();
        }
        ((Stage) cancelar.getScene().getWindow()).close();

    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ClNivell clNivell = new ClNivell(nivellComB);
        nivellComB.getEditor().textProperty().addListener(clNivell);


    }
    public static FXMLControllerConsultaAvanzadaUsuari crear(Window owner, boolean isModal) throws IOException {

        return crearPopUp("/fxml/FXMLusuariConsultaAvanzada.fxml", FXMLControllerConsultaAvanzadaUsuari.class, owner, isModal,null);
    }
    public Boolean comEmail(){
        Pattern pattern = Pattern
                .compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                        + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
        String email = emailTF.getText();

        Matcher mather = pattern.matcher(email);
        if (mather.find() == true) {
            return true;
        } else {
            return false;
        }
    }

    @FXML
    public void afegirNivell(){
        OpcionsListView.afeguirDadeListView(nivellComB, nivellList,itemsNivell);
    }
    @FXML
    public void elimNivell(){OpcionsListView.elimDadeListView(nivellList,itemsNivell);}


    @Override
    public void emplenarDades(Object obj) {

    }
}