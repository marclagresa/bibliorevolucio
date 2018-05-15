package base;

import java.io.IOException;
import java.util.function.Consumer;
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
public abstract class GenericPopUp extends Stage{
    
    protected Consumer<Object> onAcceptCallBack = null;
    protected Runnable onCancelCallBack = null;
    
    public enum TipusAccio{
        Crear,Modificar,Deshabilitar,Buscar;
    }
    
    protected void setRoot(Parent root){
        this.setScene(new Scene(root));
    }
    
    public void onAccept(Consumer<Object> fn){
        
        onAcceptCallBack = fn;
    }
    
    public void onCancel(Runnable fn){
        
        onCancelCallBack = fn;
    }
    
    protected static <E extends GenericPopUp> E crearPopUp(String fitxerFXML, Class<E> cl, Window owner, boolean isModal, TipusAccio tipus) throws IOException{
      
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
    
    public abstract void emplenarDades(Object obj);
}