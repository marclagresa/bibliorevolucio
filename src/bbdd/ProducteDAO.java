package bbdd;

import base.ConnectionFactory;
import contract.ContractMateriaProducte;
import contract.ContractProducte;
import contract.ContractProducteIdioma;
import contract.ContractProducteNivell;
import java.io.IOException;
import java.nio.file.FileSystems;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import objecte.Cdu;
import objecte.Coleccio;
import objecte.Editorial;
import objecte.Exemplar;
import objecte.Idioma;
import objecte.Nivell;
import objecte.Procedencia;
import objecte.Producte;

public class ProducteDAO implements IObjectDAO<Producte> {
    private Connection conn;
    private ResultSet rs;
    private PreparedStatement ps;

    public ProducteDAO(){
        conn=null;
        rs=null;
        ps=null;
    }
    @Override
    public List<Producte> selectAll() throws ClassNotFoundException, SQLException{
        List<Producte> list = new ArrayList<>();
        String sql;
        Producte selectProducte;
        Idioma objIdioma;
        Editorial objEditorial;
        Procedencia objProcedencia;
        Nivell objNivell;
        Coleccio objColeccio;
        Cdu objCdu;
        Set <Exemplar> exemplars;
        try {
            conn = ConnectionFactory.getInstance().getConnection();
            sql = "Select * from "+ ContractProducte.NOM_TAULA;
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            list.clear();
            while(rs.next()){
                list.add(this.read());
            }
        } catch (SQLException ex){
            throw new SQLException (ex.getMessage(),ex.getSQLState(),ex.getErrorCode(),ex.getCause());
        }catch( ClassNotFoundException ex) {
            throw new ClassNotFoundException(ex.getMessage(),ex.getCause());
        } finally {
            this.close();
        }
        return list;
    }
    @Override
    public List<Producte> select(HashMap <String,Object> dades,
        String campOrdre,Integer totalRegistres,
        Integer registreInicial,Boolean ascendent)throws SQLException,ClassNotFoundException{
        List<Producte> productes =new ArrayList<>();
        ArrayList<Object>valors;
        String query;
        Integer[]ids;
        int i;
        long ini,fin;
        try {
        
            conn=ConnectionFactory.getInstance().getConnection();
         
            valors=new ArrayList<>();
            query = "SELECT * FROM "+ContractProducte.NOM_TAULA;
            i=0;
            for (Map.Entry<String, Object> entry : dades.entrySet()) {
                String camp = entry.getKey();
                Object valor = entry.getValue();
                if(i ==0){
                    query += " WHERE ";
                }
                else{
                    query += " AND ";
                }
                if(valor.getClass().equals(String.class)){
                    query += camp+" LIKE ? ";
                    valors.add("%"+valor+"%");
                }
                else if(valor.getClass().equals(Integer.class)){
                    query += camp+ " = ? ";
                    valors.add(dades.get(camp));     
                }
                else if(valor.getClass().equals(Boolean.class)){
                    query += camp+ " = ? ";
                    valors.add(dades.get(camp));
                }
                else if(valor.getClass().equals(Integer[].class)){
                    ids=(Integer[])valor;
                    query +=camp;
                    for (int j = 0; j < ids.length; j++) {
                        if(j==0){
                            query += " WHERE ";
                        }
                        else{
                            query += " OR ";
                        }
                        switch(camp){
                            case ContractProducte.IDIOMA_ID:
                                query+=ContractProducteIdioma.ID_IDIOMA + " = ? ";
                                valors.add(ids[j]);
                                break;
                            case ContractProducte.AUTORS:
                                query+=ContractProducteIdioma.ID_IDIOMA + " = ?";
                                valors.add(ids[j]);
                                break;
                            case ContractProducte.MATERIA:
                                query+=ContractMateriaProducte.ID_MATERIA+ " = ?";
                                valors.add(ids[j]);
                                break;
                            case ContractProducte.NIVELL:
                                query+=ContractProducteNivell.ID_NIVELL + " = ?";
                                valors.add(ids[j]);
                                break;
                        }
                    }
                    query+=")";
                }
                i++;
            }
            if(campOrdre!=null){
                query+=" ORDER BY ? ";
                valors.add(campOrdre);
                if(ascendent){
                    query+=" ASC ";
                }
                else{
                    query+= " DESC ";
                }
            }
            if(registreInicial!=null || totalRegistres!=null){
                query += " LIMIT ";
                if(registreInicial!=null){
                    query += " ?, ";
                    valors.add(registreInicial);
                }
                if(totalRegistres==null){
                    query +=" 18446744073709551615";
                }
                else{
                    query +=" ?";
                    valors.add(totalRegistres);
                }

            }
            ps=conn.prepareStatement(query);
            
            for(i=0;i<valors.size();i++){
                ps.setObject(i+1, valors.get(i));
            }
            ini=System.currentTimeMillis();
            rs=ps.executeQuery();
            fin=System.currentTimeMillis();
            System.out.println("Fi query:"+ (ini-fin));
            ini=System.currentTimeMillis();
            while(rs.next()){
                productes.add(this.read());
            }
            fin=System.currentTimeMillis();
            System.out.println("Fi carrega: " + (fin-ini));
        } catch (SQLException ex) {
            throw new SQLException(ex.getMessage(), ex.getSQLState() , ex.getErrorCode(), ex.getCause());
        } catch(ClassNotFoundException ex){
            throw new ClassNotFoundException(ex.getMessage(), ex.getCause());
        }finally{
            this.close();
        }

        return productes;
    }

    public List<Producte> select(HashMap <String,Object> dades) throws ClassNotFoundException, SQLException{
        if(dades!=null){
            List<Producte> list = new ArrayList<>();
            String sql;
            Object [] valors;
  
            int i;
            boolean dadaCorrecte=false;

            try {
                conn = ConnectionFactory.getInstance().getConnection();
                sql = "SELECT * FROM "+ContractProducte.NOM_TAULA;
                i=0;
                valors=new Object[dades.size()];
                for(String camp:dades.keySet()){
                    valors=new Object[dades.size()];
                    switch(ContractProducte.DEFINICIO.get(camp)){
                        case Types.INTEGER:
                            dadaCorrecte=dades.get(camp).getClass().equals(Integer.class);
                            break;
                        case Types.CHAR:
                        case Types.VARCHAR:
                            dadaCorrecte=dades.get(camp).getClass().equals(String.class);
                            break;
                        case Types.BOOLEAN:
                            dadaCorrecte=dades.get(camp).getClass().equals(Boolean.class);
                            break;
                    }
                    if(i ==0){
                        sql += " WHERE " ;
                    }
                    else{
                        sql += " AND ";
                    }
                    sql+=camp;
                    if(dades.get(camp).getClass().equals(String.class)){
                        sql +=" LIKE ? ";
                    }
                    else{
                        sql+=" = ?";
                    }
                    if(dadaCorrecte){
                        valors[i]=dades.get(camp);
                        i++;
                    }
                    else{
                        throw new IllegalArgumentException("Dades incorrectes.");
                    }
                }
                ps = conn.prepareStatement(sql);
                for(i=0;i<valors.length;i++){
                    ps.setObject(i+1, valors[i]);
                }
                
                rs = ps.executeQuery();
                
                
                while(rs.next()){
                    list.add(this.read());
                }
                

            } catch (SQLException ex){
                throw new SQLException (ex.getMessage(),ex.getSQLState(),ex.getErrorCode(),ex.getCause());
            }catch( ClassNotFoundException ex) {
                throw new ClassNotFoundException(ex.getMessage(),ex.getCause());
            }catch(IllegalArgumentException ex){
                throw new IllegalArgumentException(ex.getMessage());
            } finally {
                this.close();
            }
            return list;
        }
        else{
            throw new NullPointerException();
        }
    }

    public int selectCount(HashMap <String,Object> dades)throws SQLException,ClassNotFoundException{
        int count=0;
        ArrayList<Object> valors;
        Integer []ids;
        int i;
        
        String query;
        try {
            conn=ConnectionFactory.getInstance().getConnection();
            valors=new ArrayList<>();
            query = "SELECT COUNT(*) FROM "+ContractProducte.NOM_TAULA;
            i=0;
            for(Map.Entry<String, Object> entry : dades.entrySet()){
                String camp = entry.getKey();
                Object valor = entry.getValue();
                if(i ==0){
                    query += " WHERE ";
                }
                else{
                    query += " AND ";
                }
                if(valor.getClass().equals(String.class)){
                    query += camp+" LIKE ? ";
                    valors.add("%"+valor+"%");
                }
                else if(valor.getClass().equals(Integer.class)){
                    query += camp+ " = ? ";
                    valors.add(dades.get(camp));     
                }
                else if(valor.getClass().equals(Boolean.class)){
                    query += camp+ " = ? ";
                    valors.add(dades.get(camp));
                }
                else if(valor.getClass().equals(Integer[].class)){
                    ids=(Integer[])valor;
                    query +=camp;
                    for (int j = 0; j < ids.length; j++) {
                        if(j==0){
                            query += " WHERE ";
                        }
                        else{
                            query += " OR ";
                        }
                        switch(camp){
                            case ContractProducte.IDIOMA_ID:
                                query+=ContractProducteIdioma.ID_IDIOMA + " = ? ";
                                valors.add(ids[j]);
                                break;
                            case ContractProducte.AUTORS:
                                query+=ContractProducteIdioma.ID_IDIOMA + " = ?";
                                valors.add(ids[j]);
                                break;
                            case ContractProducte.MATERIA:
                                query+=ContractMateriaProducte.ID_MATERIA+ " = ?";
                                valors.add(ids[j]);
                                break;
                            case ContractProducte.NIVELL:
                                query+=ContractProducteNivell.ID_NIVELL + " = ?";
                                valors.add(ids[j]);
                                break;
                        }
                    }
                    query+=")";
                }
                i++;
            }
            ps=conn.prepareStatement(query);
            for(i=0;i<valors.size();i++){
                ps.setObject(i+1, valors.get(i));
            }
            rs=ps.executeQuery();
            if(rs.next()){
                count=rs.getInt(1);
            }
        } catch (SQLException ex) {
            throw new SQLException(ex.getMessage(), ex.getSQLState() , ex.getErrorCode(), ex.getCause());
        } catch(ClassNotFoundException ex){
            throw new ClassNotFoundException(ex.getMessage(), ex.getCause());
        }finally{
            this.close();
        }
        return count;
    }
    @Override
    public Producte select(int id) throws ClassNotFoundException, SQLException{
        Producte producte = new Producte();
        String sql;
        try {
            conn = ConnectionFactory.getInstance().getConnection();
            sql = "Select "+ContractProducte.ID+","+ContractProducte.ISBN+","+ContractProducte.NOM+","
                    +ContractProducte.NUM_PAG+","+ContractProducte.DIMENSIONS+","+ContractProducte.ANY_PUBLICACIO+","
                    +ContractProducte.RESUM+","+ContractProducte.CARACTERISTIQUES+","
                    +ContractProducte.URL_PORTADA+","+ContractProducte.ADRECA_WEB+","+ContractProducte.ESTAT+","
                    +ContractProducte.IDIOMA_ID+","+ContractProducte.EDITORIAL_ID+","
                    +ContractProducte.FORMAT_ID+","+ContractProducte.PROCEDENCIA_ID+","
                    +ContractProducte.COLECCIO_ID+","+ContractProducte.CDU+
                    " from "+ ContractProducte.NOM_TAULA + " where " + ContractProducte.ID + " = ? ";
            ps = conn.prepareStatement(sql);
            ps.setInt(1,id);
            rs = ps.executeQuery();
            if(rs.next()){
                producte=this.read();
            }
        } catch (SQLException ex) {
            throw new SQLException(ex.getMessage(),ex.getSQLState(),ex.getErrorCode(),ex.getCause());
            
        } catch(ClassNotFoundException ex){
            throw new ClassNotFoundException(ex.getMessage(), ex.getCause());
        } finally {
            this.close();
        }
        return producte;
    }
    @Override
    public boolean insert(Producte producte) throws ClassNotFoundException, SQLException{
        String insert;
        boolean inserit = false;
        int id;
        try {
            conn = ConnectionFactory.getInstance().getConnection();
            
            insert = "Insert into "+ContractProducte.NOM_TAULA+ " ("
                    + ContractProducte.ID  + ", "
                    + ContractProducte.ISBN + ", "
                    + ContractProducte.NOM +", "
                    + ContractProducte.NUM_PAG + ", "
                    + ContractProducte.DIMENSIONS+ ", "
                    + ContractProducte.ANY_PUBLICACIO + ", "
                    + ContractProducte.RESUM + ", "
                    + ContractProducte.CARACTERISTIQUES + ", "
                    + ContractProducte.URL_PORTADA + ", "
                    + ContractProducte.ADRECA_WEB + ", "
                    + ContractProducte.ESTAT + ", "
                    + ContractProducte.EDITORIAL_ID + ", "
                    + ContractProducte.FORMAT_ID + ", "
                    + ContractProducte.PROCEDENCIA_ID + ", "
                    + ContractProducte.COLECCIO_ID + ", "
                    + ContractProducte.CDU + ", "
                    + ContractProducte.PAIS + ","
                    + ContractProducte.LLOC+" ) "
                    + " values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
            ps = conn.prepareStatement(insert);
            ps.setInt(1,producte.getId());
            ps.setString(2,producte.getISBN());
            ps.setString(3,producte.getNom());
            ps.setInt(4,producte.getNumPag());
            ps.setString(5,producte.getDimensions());
            ps.setString(6,producte.getAnyPublicacio());
            ps.setString(7,producte.getResum());
            ps.setString(8,producte.getCaracteristiques());
            ps.setString(9,producte.getUrlPortada());
            ps.setString(10,producte.getAdreçaWeb());
            ps.setBoolean(11,producte.getEstat());
            ps.setInt(12,producte.getEditorial().getId());
            ps.setInt(13,producte.getFormat().getId());
            ps.setInt(14,producte.getProcedencia().getId());
            ps.setInt(15,producte.getColeccio().getId());
            ps.setString(16,producte.getCdu());
            ps.setString(17, producte.getPais());
            ps.setString(18, producte.getLloc());
            ps.executeUpdate();
            producte.getMateries().forEach(action->{
                try {
                    ProducteMateriaDAO pmDAO=new ProducteMateriaDAO();
                    pmDAO.insert(producte.getId(),action.getId());
                } catch (ClassNotFoundException | SQLException e) {
                    System.err.println(e.getMessage());
                }
            });
            producte.getIdiomes().forEach(action->{
                try{
                    ProducteIdiomaDAO piDAO=new ProducteIdiomaDAO();
                    piDAO.insert(producte.getId(), action.getId());
                }catch(ClassNotFoundException | SQLException e){
                    System.err.println(e.getMessage());
                }
            });
            producte.getNivells().forEach(action->{
                try{
                    ProducteNivellDAO pnDAO = new ProducteNivellDAO();
                    pnDAO.insert(producte.getId(), action.getId());
                }catch(SQLException|ClassNotFoundException e){
                    System.err.println(e.getMessage());
                }
            });
            producte.getAutors().forEach(action->{
                String descripcio="autor";
                ProducteAutorDAO paDAO = new ProducteAutorDAO();
                HashMap<String,String> descripcions = new HashMap<>();
                descripcions.put("[tr]", "traductor/a");
                descripcions.put("[trad.]", "traductor/a");
                descripcions.put("[traduct.]", "traductor/a");
                descripcions.put("[Traductor]", "traductor/a");
                descripcions.put("[Tra]", "traductor/a");
                descripcions.put("[adap. i il·lustr.]","adaptació i ilustració" );
                descripcions.put("[adap.]","adaptació" );
                descripcions.put("[adapt.]", "adaptació");
                descripcions.put("[adapta.]", "adaptació");
                descripcions.put("[adaptació]", "adaptació");
                descripcions.put("[adap]", "adaptació");
                descripcions.put("[apèndix]", "apèndix");
                descripcions.put("[aut.]", "autor/a");
                descripcions.put("[autor]", "autor/a");
                descripcions.put("[aut]", "autor/a");
                descripcions.put("[coord.]", "coordinador/a");
                descripcions.put("[coordinadors]", "coordinador/a");
                descripcions.put("[dib.]", "dibuixant/a");
                descripcions.put("[ed.]", "edició");
                descripcions.put("[epíleg]", "epíleg");
                descripcions.put("[fot.]", "fotògraf/a");
                descripcions.put("[fotògr.]", "fotògraf/a");
                descripcions.put("[gràf.]", "gràfics");
                descripcions.put("[guió]", "guióniste/a");
                descripcions.put("[il.lustr.]", "il.lustrador/a");
                descripcions.put("[il.lustrador]", "il.lustrador/a");
                descripcions.put("[il.]", "il.lustrador/a");
                descripcions.put("[il·lustr.]", "il.lustrador/a");
                descripcions.put("[il·lustrador]", "il.lustrador/a");
                descripcions.put("[il·lu]", "il.lustrador/a");
                descripcions.put("[intr.]", "introducció");
                descripcions.put("[introducció]", "introducció");
                descripcions.put("[pròl.]", "pròleg");
                descripcions.put("[pròleg]", "pròleg");
                descripcions.put("[Text]", "Text");
                descripcions.put("[versió]","versió");
                descripcions.put("[[autora]]", "autor/a");
                descripcions.put("[[autor]]", "autor/a");
                
                
                for(String possibleDescripcio : descripcions.keySet()){
                    if(action.getNom().contains(possibleDescripcio)){
                        descripcio=descripcions.get(possibleDescripcio);
                    }
                }
                paDAO.insert(producte.getId(), action.getId(), descripcio,1);
            });
            inserit = true;
        } catch (SQLException ex) {
            throw new SQLException (ex.getMessage(),ex.getSQLState(),ex.getErrorCode(),ex.getCause());
        } catch( ClassNotFoundException ex){
            throw new ClassNotFoundException(ex.getMessage(), ex.getCause());
        }finally {
            this.close();
        }
        return inserit;
    }
    @Override
    public boolean update(Producte producte) throws ClassNotFoundException, SQLException{
        String update;
        boolean actualitzat = false;
        try {
            conn = ConnectionFactory.getInstance().getConnection();
            update = "UPDATE "+ContractProducte.NOM_TAULA+" SET "+ContractProducte.ISBN+" = ?,"
                    +ContractProducte.NOM+" = ?,"+ContractProducte.NUM_PAG+" = ?,"+ContractProducte.DIMENSIONS+" = ?,"
                    +ContractProducte.ANY_PUBLICACIO+" = ?,"+ContractProducte.RESUM+" = ?,"+ContractProducte.CARACTERISTIQUES+" = ?,"
                    +ContractProducte.URL_PORTADA+" = ?,"+ContractProducte.ADRECA_WEB+" = ?,"
                    +ContractProducte.EDITORIAL_ID+" = ?,"+ContractProducte.FORMAT_ID+" = ?,"+ContractProducte.PROCEDENCIA_ID+" = ?,"
                    +ContractProducte.COLECCIO_ID+" = ?,"+ContractProducte.CDU+" = ?,"
                    +ContractProducte.ESTAT + "= ?  where "+ContractProducte.ID+" = ?";
            ps = conn.prepareStatement(update);
            ps.setString(1,producte.getISBN());
            ps.setString(2,producte.getNom());
            ps.setInt(3,producte.getNumPag());
            ps.setString(4,producte.getDimensions());
            ps.setString(5,producte.getAnyPublicacio());
            ps.setString(6,producte.getResum());
            ps.setString(7,producte.getCaracteristiques());
            ps.setString(8,producte.getUrlPortada());
            ps.setString(9,producte.getAdreçaWeb());
            ps.setInt(10,producte.getEditorial().getId());
            ps.setInt(11,producte.getFormat().getId());
            ps.setInt(12,producte.getProcedencia().getId());
            ps.setInt(13,producte.getColeccio().getId());
            ps.setString(14,producte.getCdu());
            ps.setBoolean(15, producte.getEstat());
            ps.setInt(16,producte.getId());
            actualitzat=ps.executeUpdate()==1;
          
        } catch (SQLException ex){
            throw new SQLException(ex.getMessage(),ex.getSQLState(),ex.getErrorCode(),ex.getCause());
        }catch( ClassNotFoundException ex) {
            throw new ClassNotFoundException(ex.getMessage(),ex.getCause());
        } finally {
            this.close();
        }
        return actualitzat;
    }
    @Override
    public int nextId() throws ClassNotFoundException, SQLException{
        int id = 1;
        String sql;
        try {
            conn = ConnectionFactory.getInstance().getConnection();
            sql = "SELECT max("+ContractProducte.ID+") FROM "+ContractProducte.NOM_TAULA;
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            if(rs.next()){
                id = rs.getInt(1)+1;
            }
        } catch (SQLException ex){
            throw new SQLException(ex.getMessage(),ex.getSQLState(),ex.getErrorCode(),ex.getCause());
        }catch( ClassNotFoundException ex) {
            throw new ClassNotFoundException (ex.getMessage(),ex.getCause());
        }
        return id;
    }
    @Override
    public void close(){
        if(this.conn!=null){
            try {
                this.conn.close();
                this.conn=null;
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        if(this.ps!=null){
            try {
                this.ps.close();
                this.ps=null;
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        if(this.rs!=null){
            try{
                this.rs.close();
                this.rs=null;
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    private Producte read()throws SQLException,ClassNotFoundException{
        long ini,fin;
        System.out.println("\n\nPRODUCTE\n\n");
        Producte objProducte = new Producte();
        ini=System.currentTimeMillis();
        objProducte.setNom(rs.getString(ContractProducte.NOM));
        objProducte.setId(rs.getInt(ContractProducte.ID));
        objProducte.setISBN(rs.getString(ContractProducte.ISBN));
        objProducte.setNumPag(rs.getInt(ContractProducte.NUM_PAG));
        objProducte.setDimensions(rs.getString(ContractProducte.DIMENSIONS));
        objProducte.setAnyPublicacio(rs.getString(ContractProducte.ANY_PUBLICACIO));
        objProducte.setResum(rs.getString(ContractProducte.RESUM));
        objProducte.setCaracteristiques(rs.getString(ContractProducte.CARACTERISTIQUES));
        objProducte.setUrlPortada(rs.getString(ContractProducte.URL_PORTADA));
        objProducte.setAdreçaWeb(rs.getString(ContractProducte.ADRECA_WEB));
        objProducte.setEstat(rs.getBoolean(ContractProducte.ESTAT));
        objProducte.setPais(rs.getString(ContractProducte.PAIS));
        objProducte.setLloc(rs.getString(ContractProducte.LLOC));
        objProducte.setCdu(rs.getString(ContractProducte.CDU));
        fin = System.currentTimeMillis();
        System.out.println("Fi carrega atributs:"+(fin-ini));
        ini=System.currentTimeMillis();
        objProducte.setEditorial(new EditorialDAO().select(rs.getInt(ContractProducte.EDITORIAL_ID)));
        fin=System.currentTimeMillis();
        
        System.out.println("Fi carrega editoria:"+(fin-ini));
         ini=System.currentTimeMillis();
        objProducte.setFormat(new FormatDAO().select(rs.getInt(ContractProducte.FORMAT_ID)));
        fin=System.currentTimeMillis();
        System.out.println("Fi carrega format:"+(fin-ini));
         ini=System.currentTimeMillis();
        objProducte.setProcedencia(new ProcedenciaDAO().select(rs.getInt(ContractProducte.PROCEDENCIA_ID)));
        fin=System.currentTimeMillis();
        System.out.println("Fi carrega procedencia:"+(fin-ini));
         ini=System.currentTimeMillis();
        objProducte.setColeccio(new ColeccioDAO().select(rs.getInt(ContractProducte.COLECCIO_ID)));
        fin=System.currentTimeMillis();
        System.out.println("Fi carrega coleccio:"+(fin-ini));
        ini = System.currentTimeMillis();
        objProducte.setExemplars(new HashSet<>(0));
        objProducte.setNivells(new HashSet<>(0));
        fin=System.currentTimeMillis();
        System.out.println("Fi carrega hashes:"+(fin-ini));
        return objProducte;
    }
    
    public static void main(String[] args) throws SQLException,ClassNotFoundException,IOException{
        ConnectionFactory.getInstance().configure( FileSystems.getDefault().getPath("src/base", "configLector.txt"));
        HashMap<String,Object>consulta = new HashMap<>();
        consulta.put(ContractProducte.NOM, "a");
        consulta.put(ContractProducte.IDIOMA_ID, new Integer[]{1,2});
        consulta.put(ContractProducte.NIVELL, new Integer[]{1});
        System.out.println(new ProducteDAO().select(consulta,null,null,null,null).size());
        //new ProducteDAO().select(consulta,null,null,null,null).forEach(action->System.out.println(action.toString()));
    }
}
