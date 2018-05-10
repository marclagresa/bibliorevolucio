/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package base;

import bbdd.EditorialDAO;
import bbdd.FormatDAO;
import bbdd.IdiomaDAO;
import com.csvreader.CsvReader;
import com.csvreader.CsvWriter;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.FileSystems;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import objecte.Editorial;
import objecte.Format;
import objecte.Idioma;
import objecte.Producte;


/**
 * FXML Controller class
 *
 * @author sergiclotas
 */
public class FXMLTraspasController implements Initializable {
    CsvReader reader;
    CsvWriter writer;
    @FXML
    private Button btnCsvOrigen;
    @FXML
    private Button btnCsvDesti;
    @FXML
    private TextField txfCsvOrigen;
    @FXML
    private TextField txfCsvDesti;
    @FXML
    private TextField txfFilesLlegides;
    @FXML
    private TextField txfFilesTraspasades;
    @FXML
    private TextField txfFilesNoTraspasades;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       reader=null;
       writer=null;
    }
    public void traspasar()throws Exception{
        if(txfCsvDesti.getText().isEmpty()){
            System.out.println("Error no heu escollit el csv d' origen.");  
        }
        else{
            writer=new CsvWriter(txfCsvDesti.getText(), ';', Charset.defaultCharset());
        }
        if(txfCsvOrigen.getText().isEmpty()){
            System.out.println("Error no heu escollit el csv on guardar els registres que fallin.");
        }
        else{
            reader=new CsvReader(txfCsvOrigen.getText(),';',Charset.defaultCharset());
        }
        if(reader!=null && writer !=null){
            traspasarDades();
        }
        
    }
    private void traspasarDades()throws Exception{
        Thread t= new Thread(new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                Producte p;
                ConnectionFactory.getInstance().configure( FileSystems.getDefault().getPath("src/base", "configBibliotecari"));
                int registresLlegits=0;
                int registreFallits=0;
                int registresGuardats=0;
                try{
                    if(reader.readHeaders()){

                        if(!reader.getHeaders()[0].equals("EXCEPCIO")){
                            writer.write("EXCEPCIO");
                        }
                        writer.writeRecord(reader.getHeaders());
                    }
                    while(reader.readRecord()){
                        try {
                           p=new Producte();
                           registresLlegits++;
                         //  p.setFormat(getFormat(reader.get("FORMAT")));
                         //  p.setIdioma(getIdioma(reader.get("LLENGUA")));
                           p.setEditorial(getEditorial(reader.get("EDITORIAL"),reader.get("PAÍS"), reader.get("LLOC")));
                           registresGuardats++;
                        } catch (Exception e) {
                            registreFallits++;
                            System.err.println(e.getMessage());
                        }finally{
                           // txfFilesLlegides.setText(String.format("%d",registresLlegits));
                           // txfFilesNoTraspasades.setText(String.format("%d",registreFallits));
                           // txfFilesTraspasades.setText(String.format("%d", registresGuardats));
                        }
                    }
                } catch(IOException e){
                    System.err.println(e.getMessage());
                } finally{
                    writer.flush();
                    writer.close();
                    reader.close();
                }
                return null;
            }
        });
        t.setDaemon(true);
        t.start();
        
    }
    private Idioma getIdioma(String nomIdioma)throws SQLException,ClassNotFoundException{
        Idioma idiomaObj = new Idioma();
        IdiomaDAO idiomaDAOObj = new IdiomaDAO();
        String [] idiomes;   //En un sol registre i poden haver-hi diferents idiomes. es necessitara fer un split.
        nomIdioma=nomIdioma.replace(" ", "");
        if(!nomIdioma.isEmpty()){
            nomIdioma=nomIdioma.toLowerCase(); 
            idiomes=nomIdioma.split(";");
            if(idiomes.length==1){
                idiomes=nomIdioma.split("-");
            }
            if(idiomes.length==1){
                idiomes=nomIdioma.split("/");
            }
            for(String idioma:idiomes){
                switch(idioma){
                    case "català.":
                        idioma="català";
                        break;
                    case "catala.":
                        idioma="català";
                        break;
                    case "calalà":
                        idioma="català";
                        break;
                    case "catalana":
                        idioma="català";
                        break;
                    case "espanyol":
                        idioma="castellà";
                        break;
                    case "castellana":
                        idioma="castellà";
                        break;
                    case "castellano":
                        idioma="castellà";
                        break;
                }
                idiomaObj=idiomaDAOObj.select(idioma);
                if(idiomaObj.getId()==-1){
                    idiomaObj.setId(idiomaDAOObj.nextId());
                    idiomaObj.setNom(idioma);
                    idiomaDAOObj.insert(idiomaObj);
                }
            }
        }
        return idiomaObj;
    }
    private Editorial getEditorial(String nomEditorial,String pais,String adreca)throws SQLException,ClassNotFoundException{
        Editorial editorialObj;
        EditorialDAO editorialDAOObj=new EditorialDAO();
        if(!nomEditorial.matches("\\s*")){
            
        }
        return new Editorial();
                
            
    }
    /*
    private Cdu getCdu(String nomCdu) throws SQLException,ClassNotFoundException{
        Cdu cduObj;
        CduDAO cduDAOObj = new CduDAO();
        if(!nomCdu.isEmpty()){
            
        }
    }*/
    private Format getFormat(String nomFormat) throws SQLException,ClassNotFoundException {
        Format formatObj;
        FormatDAO formatDAOObj = new FormatDAO();
        if(nomFormat.isEmpty()){
            nomFormat="LLIBRE";
        }
        nomFormat=nomFormat.toUpperCase();
        formatObj=formatDAOObj.select(nomFormat);
        if(formatObj.getId()==-1){
            formatObj.setNom(nomFormat);
            formatObj.setId(formatDAOObj.nextId());
            formatDAOObj.insert(formatObj);
        }
        return formatObj;
        
    }
    @FXML
    public void seleccionarCsv(ActionEvent event)throws IOException{
        FileChooser fc = new FileChooser();
        File f=fc.showOpenDialog(new Stage());
        String pathFile ="";
        if(f!=null){
            pathFile=f.getAbsolutePath();
        }
        if(!pathFile.isEmpty()){
            switch(((Button)event.getSource()).getId()){
                case "btnCsvDesti":
                    txfCsvDesti.setText(pathFile);
                    break;
                case "btnCsvOrigen":
                    txfCsvOrigen.setText(pathFile);
                    break;
            }
        }
    }
}
