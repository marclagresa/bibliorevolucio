



/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import base.FXMLProducteController;
import static base.GenericPopUp.TipusAccio.Crear;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author fcase
 */
public class BibliotecaSintesi extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        //FileInputStream file = new FileInputStream(new File("bibliorevolucio/fxml/FXMLPantallaPrincipal.fxml"));
        //Parent root = FXMLLoader.load(file);
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/FXMLPantallaPrincipal.fxml"));
        
        
        
        
        Scene scene = new Scene(root);
        scene.getStylesheets().add
                (BibliotecaSintesi.class.getResource("/media/css/estils.css").toExternalForm());
        
        stage.setTitle("Bibliorevolució");
        stage.setScene(scene);
        stage.show();        
        
        FXMLProducteController c = FXMLProducteController.crear(stage, true, Crear);
        c.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
  
    
}
