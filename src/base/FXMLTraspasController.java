/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package base;

import bbdd.CduDAO;
import bbdd.ColeccioDAO;
import bbdd.EditorialDAO;
import bbdd.ExemplarDAO;
import bbdd.FormatDAO;
import bbdd.IdiomaDAO;
import bbdd.MateriaDAO;
import bbdd.NivellDAO;
import bbdd.PersonaDAO;
import bbdd.ProcedenciaDAO;
import bbdd.ProducteDAO;
import com.csvreader.CsvReader;
import com.csvreader.CsvWriter;
import contract.ContractEditorial;
import contract.ContractProducte;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.FileSystems;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import objecte.Biblioteca;
import objecte.Cdu;
import objecte.Coleccio;
import objecte.Editorial;
import objecte.Format;
import objecte.Idioma;
import objecte.Materia;
import objecte.Nivell;
import objecte.Persona;
import objecte.Procedencia;
import objecte.Producte;
import objecte.Exemplar;


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
                ProducteDAO pDAO = new ProducteDAO();
                ExemplarDAO eDAO = new ExemplarDAO();
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
                    HashMap<String,Object> consultaExemplar=new HashMap<>();
                    List<Producte> resultatConsulta;
                    while(reader.readRecord()){
                        p=new Producte();
                        try {
                            
                            registresLlegits++;
                            p.setISBN(reader.get("ISBN"));
                            p.setNom(reader.get("TITOL"));
                            p.setCdu(reader.get("CDU"));
                            consultaExemplar.clear();
                            consultaExemplar.put(ContractProducte.ISBN, p.getCdu());
                            consultaExemplar.put(ContractProducte.NOM, p.getNom());
                            consultaExemplar.put(ContractProducte.CDU,p.getCdu());
                            resultatConsulta=new ProducteDAO().select(consultaExemplar);
                            if(resultatConsulta.isEmpty()){
                                p.setId(Integer.valueOf(reader.get("NUMERO")));
                                p.setFormat(getFormat(reader.get("FORMAT")));
                                p.setAutors(getAutors(reader.get("AUTOR")));
                                p.setLloc(reader.get("LLOC"));
                                p.setColeccio(getColeccio(reader.get("COL·LECCIÓ")));
                                p.setAnyPublicacio(reader.get("DATA"));
                                p.setIdiomes(getIdioma(reader.get("LLENGUA")));
                                p.setMateries(getMateries(reader.get("MATERIA")));
                                p.setNivells(getNivells(reader.get("NIVELL")));
                                p.setResum(reader.get("RESUM"));
                                p.setUrlPortada(reader.get("URL"));
                                p.setAdreçaWeb(reader.get("ADREÇA"));
                                p.setDimensions(reader.get("DIMENSIÓ"));
                                try {
                                    p.setNumPag(Integer.valueOf(reader.get("PÀGINES")));
                                } catch (Exception e) {
                                    p.setNumPag(0);
                                }
                                
                                p.setProcedencia(getProcedencia(reader.get("PROC")));
                                p.setCaracteristiques(reader.get("CARC"));
                                p.setEditorial(getEditorial(reader.get("EDITORIAL")));
                                p.setLloc(reader.get("LLOC")+","+reader.get("PAIS"));
                                pDAO.insert(p);
                                eDAO.insert(new Exemplar(new ExemplarDAO().nextId(),true,p,new Biblioteca(1,"inspladelestany")));
                            }
                            else{
                                p=resultatConsulta.get(0);
                                eDAO.insert(new Exemplar(new ExemplarDAO().nextId(),true,p,new Biblioteca(1,"inspladelestany")));
                            }
                            
                            
                            
                            //inserirCdus(reader.get("ID"), reader.get("NOM"), reader.get("IDRETOL"));
                            
                            
                            registresGuardats++;
                        } catch (Exception e) {
                            registreFallits++;
                            System.err.println(p.getId()+" " +e.getMessage());
                            writer.write(e.getMessage());
                            writer.writeRecord(reader.getValues());
                        }finally{
                            
                           // txfFilesLlegides.setText(String.format("%d",registresLlegits));
                           // txfFilesNoTraspasades.setText(String.format("%d",registreFallits));
                           // txfFilesTraspasades.setText(String.format("%d", registresGuardats));
                        }
                    }
                } catch(IOException e){
                    System.err.println(e.getMessage());
                } finally{
                    System.out.println("Process FINALITZAT!!!!");
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
    private Set<Persona> getAutors(String liniaAutors)throws SQLException,ClassNotFoundException{
        Set <Persona> autors= new HashSet<>(0);
        PersonaDAO personaDAOObj;
        Persona personaObj;
        String [] personesLinia;
        if(!liniaAutors.isEmpty()){
            personaDAOObj=new PersonaDAO();
            personesLinia=liniaAutors.split(";");
            for (String personaNom:personesLinia){
                personaObj=personaDAOObj.select(personaNom);
                if(personaObj.getId()==-1){
                    personaObj.setId(personaDAOObj.nextId());
                    personaObj.setNom(personaNom);
                    personaDAOObj.insert(personaObj);
                }
                autors.add(personaObj);
            }
        }
        return autors;
    }
    private void inserirCdus(String id,String nom,String idPare)throws SQLException,ClassNotFoundException{
        CduDAO cduDAOObj = new CduDAO();
        Cdu cduObj=new Cdu(id, idPare, nom);
        cduDAOObj.insert(cduObj);
    }
    private Procedencia getProcedencia(String procedenciaNom) throws SQLException,ClassNotFoundException{
        Procedencia procedenciaObj = new Procedencia();
        ProcedenciaDAO procedenciaDAOObj;
        if(!procedenciaNom.isEmpty()){
            procedenciaDAOObj=new ProcedenciaDAO();
            procedenciaObj=procedenciaDAOObj.select(procedenciaNom);
            if(procedenciaObj.getId()==-1){
                procedenciaObj.setId(procedenciaDAOObj.nextId());
                procedenciaObj.setNom(procedenciaNom);
                procedenciaDAOObj.insert(procedenciaObj);
            }
        }
        return procedenciaObj;
    }
    private Set <Nivell> getNivells(String liniaNivells) throws SQLException,ClassNotFoundException{
        Set<Nivell> nivellsSet=new HashSet<>();
        String [] nivellsLst;
        NivellDAO nivellDAOObj;
        Nivell nivellObj;
        if(!liniaNivells.isEmpty()){
            nivellDAOObj=new NivellDAO();
            nivellsLst=liniaNivells.split("-");
            for(String nomNivell:nivellsLst){
                nivellObj=nivellDAOObj.select(nomNivell);
                if(nivellObj.getId()==-1){
                    nivellObj.setNom(nomNivell);
                    nivellObj.setId(nivellDAOObj.nextId());
                    nivellDAOObj.insert(nivellObj);
                }
                nivellsSet.add(nivellObj);
            }
        }
        return nivellsSet;
    }
    private Set<Materia> getMateries(String materiesReg) throws SQLException,ClassNotFoundException{
        Set<Materia> materiesSet = new HashSet<>();
        String [] materiesLst;
        MateriaDAO materiaDAOObj;
        Materia materiaObj;
        if(!materiesReg.isEmpty()){
            materiaDAOObj=new MateriaDAO();
            materiesLst=materiesReg.split("-");
            for(String nomMateria:materiesLst){
                materiaObj=materiaDAOObj.select(nomMateria);
                if(materiaObj.getId()==-1){
                    materiaObj.setId(materiaDAOObj.nextId());
                    materiaObj.setNom(nomMateria);
                    materiaDAOObj.insert(materiaObj);
                }
                materiesSet.add(materiaObj);
            }
        }
        return materiesSet;
    }
    private Coleccio getColeccio(String coleccioNom) throws SQLException,ClassNotFoundException{
        Coleccio coleccioObj = new Coleccio();
        ColeccioDAO coleccioDAOObj;
        if(!coleccioNom.isEmpty()){
            try{
                coleccioDAOObj=new ColeccioDAO();
                coleccioObj=coleccioDAOObj.select(coleccioNom);
                if(coleccioObj.getId()==-1){
                    coleccioObj.setId(coleccioDAOObj.nextId());
                    coleccioObj.setNom(coleccioNom);
                    coleccioDAOObj.insert(coleccioObj);
                }
            } catch(SQLException | ClassNotFoundException e){
                System.err.println(coleccioObj.getNom() +" "+e.getMessage());
            }
        }
        return coleccioObj;
    }
    private Set<Idioma> getIdioma(String nomIdioma)throws SQLException,ClassNotFoundException{
        Set<Idioma>idiomes=new HashSet<>();
        Idioma idiomaObj;
        IdiomaDAO idiomaDAOObj;
        String [] idomesStrg;   //En un sol registre i poden haver-hi diferents idomesStrg. es necessitara fer un split.
        nomIdioma=nomIdioma.replace(" ", "");
        if(!nomIdioma.isEmpty()){
            try {
                idiomaDAOObj=new IdiomaDAO();
                nomIdioma=nomIdioma.toLowerCase(); 
                idomesStrg=nomIdioma.split(";");
                if(idomesStrg.length==1){
                    idomesStrg=nomIdioma.split("-");
                }
                if(idomesStrg.length==1){
                    idomesStrg=nomIdioma.split("/");
                }
                for(String idioma:idomesStrg){
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
                    idiomes.add(idiomaObj);
                }  
            } catch (SQLException e) {
                System.err.println(e.getMessage());
            }
            
        }
        return idiomes;
    }
    private Editorial getEditorial(String nomEditorial)throws SQLException,ClassNotFoundException{
        Editorial editorialObj = new Editorial();
        EditorialDAO editorialDAOObj=new EditorialDAO();
        HashMap <String,Object> consulta;
        List<Editorial>editorials=new ArrayList<>();
        if(!nomEditorial.matches("\\s*")){
            try{
                consulta=new HashMap<>();
                consulta.put(ContractEditorial.NOM, nomEditorial);
                editorials=editorialDAOObj.select(consulta,null,null,null,null);
                if(editorials.size() >0){
                    editorialObj=editorials.get(0);
                }
                else{
                    editorialObj.setNom(nomEditorial);
                    editorialObj.setId(editorialDAOObj.nextId());
                    editorialDAOObj.insert(editorialObj);
                }
            }catch(SQLException  e){
                System.err.println(e.getMessage());
            }
            
        }
        return editorialObj;
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
        try{
            if(formatObj.getId()==-1){
                formatObj.setNom(nomFormat);
                formatObj.setId(formatDAOObj.nextId());
                formatDAOObj.insert(formatObj);
            }   
        }catch(SQLException e){
            System.err.println(e.getMessage());
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
