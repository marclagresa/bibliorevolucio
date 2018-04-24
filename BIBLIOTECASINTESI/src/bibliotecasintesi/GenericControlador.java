/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bibliotecasintesi;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

/**
 *
 * @author fcase
 */
public class GenericControlador extends VBox{
    protected PantallaPrincipalControlador pare=null;
    protected String nomCapcelera;
    
    protected void setfill(Parent p){
      getChildren().add(p);
    }
    
    public void setPare(PantallaPrincipalControlador pare){
        this.pare=pare;
        
    }
    
    public String getCapcalera(){
        return nomCapcelera;
    }
    
    protected static <E extends GenericControlador> E crearFinestre(String fitxerXFML, Class<E> cl,String nomCapcalera) throws IOException{
       // Class<E> clasz=(Class<E>)((ParameterizedType)getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        
        FXMLLoader loder = new FXMLLoader(cl.getResource(fitxerXFML));
        Parent p = loder.load();
        E c= loder.getController();
        c.setfill(p);
        c.nomCapcelera=nomCapcalera;
        return c;
        
    }
    
}
