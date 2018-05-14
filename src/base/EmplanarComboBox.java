package base;

import bbdd.*;
import contract.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import objecte.*;

import java.util.HashMap;
//Emplanar CDU
class ClCdu implements ChangeListener<String>{

    ComboBox cb;

    public ClCdu(ComboBox combo){
        this.cb = combo;
    }

    @Override
    public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
        if(!newValue.equals("")){
            HashMap<String,Object> cercaCdu=new HashMap<>();
            cercaCdu.put(ContractCdu.NOM, newValue);
            try {
                CduDAO objCduDAO= new CduDAO();
                ObservableList<Cdu> opcionsCdu = FXCollections.observableArrayList(objCduDAO.select(cercaCdu));
                cb.setItems(opcionsCdu);

            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }else{
            ObservableList<Cdu> opcionsCdu = null;
            cb.setItems(opcionsCdu);
        }
    }
}
//Emplanar Editorial
class ClEditorial implements ChangeListener<String>{

    ComboBox cb;

    public ClEditorial(ComboBox combo){
        this.cb = combo;
    }

    @Override
    public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
        if(!newValue.equals("")){
            HashMap<String,Object> cercaEditorial=new HashMap<>();
            cercaEditorial.put(ContractEditorial.NOM, newValue);
            try {
                EditorialDAO objEditorialDAO= new EditorialDAO();
                ObservableList<Editorial> opcionsEditorial = FXCollections.observableArrayList(objEditorialDAO.select(cercaEditorial));
                cb.setItems(opcionsEditorial);

            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }else{
            ObservableList<Editorial> opcionsEditorial = null;
            cb.setItems(opcionsEditorial);
        }
    }
}
//Emplanar Coleccio
class ClColeccio implements ChangeListener<String>{

    ComboBox cb;

    public ClColeccio(ComboBox combo){
        this.cb = combo;
    }

    @Override
    public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
        if(!newValue.equals("")){
            HashMap<String,Object> cercaColeccio=new HashMap<>();
            cercaColeccio.put(ContractColeccio.NOM, newValue);
            try {
                ColeccioDAO objColeccioDAO= new ColeccioDAO();
                ObservableList<Coleccio> opcionsColeccio = FXCollections.observableArrayList(objColeccioDAO.select(cercaColeccio));
                cb.setItems(opcionsColeccio);

            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }else{
            ObservableList<Coleccio> opcionsColeccio = null;
            cb.setItems(opcionsColeccio);
        }
    }
}
//Emplanar Persona
class ClPersona implements ChangeListener<String>{

    ComboBox cb;

    public ClPersona(ComboBox combo){
        this.cb = combo;
    }

    @Override
    public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
        if(!newValue.equals("")){
            HashMap<String,Object> cercaPersona=new HashMap<>();
            cercaPersona.put(ContractPersona.NOM, newValue);
            try {
                PersonaDAO objPersonaDAO= new PersonaDAO();
                ObservableList<Persona> opcionsPersona = FXCollections.observableArrayList(objPersonaDAO.select(cercaPersona));
                cb.setItems(opcionsPersona);

            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }else{
            ObservableList<Persona> opcionsPersona = null;
            cb.setItems(opcionsPersona);
        }
    }
}
//Emplanar Idioma
class ClIdioma implements ChangeListener<String>{

    ComboBox cb;

    public ClIdioma(ComboBox combo){
        this.cb = combo;
    }

    @Override
    public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
        if(!newValue.equals("")){
            HashMap<String,Object> cercaIdioma=new HashMap<>();
            cercaIdioma.put(ContractIdioma.NOM, newValue);
            try {
                IdiomaDAO objIdiomaDAO= new IdiomaDAO();
                ObservableList<Idioma> opcionsIdioma = FXCollections.observableArrayList(objIdiomaDAO.select(cercaIdioma));
                cb.setItems(opcionsIdioma);

            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }else{
            ObservableList<Idioma> opcionsIdioma = null;
            cb.setItems(opcionsIdioma);
        }
    }
}

class ClFormat implements ChangeListener<String>{

    ComboBox cb;

    public ClFormat(ComboBox combo){
        this.cb = combo;
    }

    @Override
    public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
        if(!newValue.equals("")){
            HashMap<String,Object> cercaFormat=new HashMap<>();
            cercaFormat.put(ContractFormat.NOM, newValue);
            try {
                FormatDAO objFormatDAO= new FormatDAO();
                ObservableList<Format> opcionsFormat = FXCollections.observableArrayList(objFormatDAO.select(cercaFormat));
                cb.setItems(opcionsFormat);

            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }else{
            ObservableList<Format> opcionsFormat = null;
            cb.setItems(opcionsFormat);
        }
    }
}

class ClNivell implements ChangeListener<String>{

    ComboBox cb;

    public ClNivell(ComboBox combo){
        this.cb = combo;
    }

    @Override
    public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
        if(!newValue.equals("")){
            HashMap<String,Object> cercaNivell=new HashMap<>();
            cercaNivell.put(ContractNivell.NOM, newValue);
            try {
                NivellDAO objNivellDAO= new NivellDAO();
                ObservableList<Nivell> opcionsNivell = FXCollections.observableArrayList(objNivellDAO.select(cercaNivell));
                cb.setItems(opcionsNivell);

            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }else{
            ObservableList<Nivell> opcionsNivell = null;
            cb.setItems(opcionsNivell);
        }
    }
}


