package base;

import java.io.IOException;
import java.util.function.Consumer;
import java.util.function.Supplier;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;

/**
 *
 * @author AdriaLlop
 */
public class GenericPopUp extends Stage{
    
    protected Consumer<Object> onAcceptCallBack = null;
    protected Supplier onCancelCallBack = null;
    
    protected void setRoot(Parent root){
        this.setScene(new Scene(root));
    }
    
    protected void onAccept(Consumer<Object> fn){
        
        onAcceptCallBack = fn;
    }
    
    protected void onCancel(Supplier fn){
        
        onCancelCallBack = fn;
    }
    
    protected static <E extends GenericPopUp> E crearPopUp(String fitxerFXML, Class<E> cl, Window owner, boolean isModal) throws IOException{
      
        FXMLLoader loder = new FXMLLoader(cl.getResource(fitxerFXML));
        Parent p = loder.load();
        E c= loder.getController();
        
        c.setRoot(p);
        c.initOwner(owner);
        
        if(isModal){
            c.initModality(Modality.WINDOW_MODAL);
        }
        
        return c;
    }
}