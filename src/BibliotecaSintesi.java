



/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
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
        
        stage.setScene(scene);
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
  
    
}