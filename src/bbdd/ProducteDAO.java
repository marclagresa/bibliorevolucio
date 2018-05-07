package bbdd;

import base.ConnectionFactory;
import contract.ContractAutoria;
import contract.ContractExemplar;
import contract.ContractProducte;
import objecte.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
            sql = "Select "+ ContractProducte.ID+","+ContractProducte.ISBN+","+ContractProducte.NOM+","
                    +ContractProducte.NUM_PAG+","+ContractProducte.DIMENSIONS+","+ContractProducte.ANY_PUBLICACIO+","
                    +ContractProducte.RESUM+","+ContractProducte.CARACTERISTIQUES+","
                    +ContractProducte.URL_PORTADA+","+ContractProducte.ADRECA_WEB+","+ContractProducte.ESTAT+","
                    +ContractProducte.IDIOMA_ID+","+ContractProducte.EDITORIAL_ID+","
                    +ContractProducte.FORMAT_ID+","+ContractProducte.PROCEDENCIA_ID+","
                    +ContractProducte.NIVELL_ID+","+ContractProducte.COLECCIO_ID+","+ContractProducte.CDU_ID+
                    "from "+ ContractProducte.NOM_TAULA;
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            list.clear();
            while(rs.next()){
                selectProducte = this.read();
                list.add(selectProducte);
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
    public List<Producte> select(HashMap <String,Object> dades) throws ClassNotFoundException, SQLException{
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
                    throw new SQLException("Error tipus de dades incorrectes!!");
                }
            }
            ps = conn.prepareStatement(sql);
            for(i=0;i<valors.length;i++){
                ps.setObject(i+1, valors[i]);
            }
            rs = ps.executeQuery();
      
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
    public Producte select(int id) throws ClassNotFoundException, SQLException{
        Producte producte = new Producte();
        IdiomaDAO idioma = new IdiomaDAO();
        EditorialDAO editorial = new EditorialDAO();
        FormatDAO format = new FormatDAO();
        String sql;
        try {
            conn = ConnectionFactory.getInstance().getConnection();
            sql = "Select "+ContractProducte.ID+","+ContractProducte.ISBN+","+ContractProducte.NOM+","
                    +ContractProducte.NUM_PAG+","+ContractProducte.DIMENSIONS+","+ContractProducte.ANY_PUBLICACIO+","
                    +ContractProducte.RESUM+","+ContractProducte.CARACTERISTIQUES+","
                    +ContractProducte.URL_PORTADA+","+ContractProducte.ADRECA_WEB+","+ContractProducte.ESTAT+","
                    +ContractProducte.IDIOMA_ID+","+ContractProducte.EDITORIAL_ID+","
                    +ContractProducte.FORMAT_ID+","+ContractProducte.PROCEDENCIA_ID+","
                    +ContractProducte.NIVELL_ID+","+ContractProducte.COLECCIO_ID+","+ContractProducte.CDU_ID+
                    " from "+ ContractProducte.NOM_TAULA + " where " + ContractProducte.ID + " = ? ";
            ps = conn.prepareStatement(sql);
            ps.setInt(1,id);
            rs = ps.executeQuery();
            producte.setId(rs.getInt(1));
            producte.setISBN(rs.getString(2));
            producte.setNom(rs.getString(3));
            producte.setNumPag(rs.getInt(4));
            producte.setDimensions(rs.getString(5));
            producte.setAnyPublicacio(rs.getString(6));
            producte.setResum(rs.getString(7));
            producte.setCaracteristiques(rs.getString(8));
            producte.setUrlPortada(rs.getString(9));
            producte.setAdreçaWeb(rs.getString(10));
            producte.setEstat(rs.getBoolean(11));
            producte.setIdioma(idioma.select(rs.getInt(12)));

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
            id = nextId();
            insert = "Insert into "+ContractProducte.NOM_TAULA+
                    " values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
            ps = conn.prepareStatement(insert);
            ps.setInt(1,id);
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
            ps.setInt(12,producte.getIdioma().getId());
            ps.setInt(13,producte.getEditorial().getId());
            ps.setInt(14,producte.getFormat().getId());
            ps.setInt(15,producte.getProcedencia().getId());
            ps.setInt(16,producte.getNivell().getId());
            ps.setInt(17,producte.getColeccio().getId());
            ps.setInt(18,producte.getCDU().getId());
            ps.executeUpdate();
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
                    +ContractProducte.URL_PORTADA+" = ?,"+ContractProducte.ADRECA_WEB+" = ?,"+ContractProducte.IDIOMA_ID+" = ?,"
                    +ContractProducte.EDITORIAL_ID+" = ?,"+ContractProducte.FORMAT_ID+" = ?,"+ContractProducte.PROCEDENCIA_ID+" = ?,"
                    +ContractProducte.NIVELL_ID+" = ?,"+ContractProducte.COLECCIO_ID+" = ?,"+ContractProducte.CDU_ID+" = ?" +
                    " where "+ContractProducte.ID+" = ?";
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
            ps.setInt(10,producte.getIdioma().getId());
            ps.setInt(11,producte.getEditorial().getId());
            ps.setInt(12,producte.getFormat().getId());
            ps.setInt(13,producte.getProcedencia().getId());
            ps.setInt(14,producte.getNivell().getId());
            ps.setInt(15,producte.getColeccio().getId());
            ps.setInt(16,producte.getCDU().getId());
            ps.setInt(17,producte.getId());
            
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
        Producte objProducte = new Producte();
        HashMap<String,Object> hashConsulta;
        
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
        objProducte.setEditorial(new EditorialDAO().select(rs.getInt(ContractProducte.EDITORIAL_ID)));
        objProducte.setIdioma(new IdiomaDAO().select(rs.getInt(ContractProducte.IDIOMA_ID)));
        objProducte.setFormat(new FormatDAO().select(rs.getInt(ContractProducte.FORMAT_ID)));
        objProducte.setProcedencia(new ProcedenciaDAO().select(rs.getInt(ContractProducte.PROCEDENCIA_ID)));
        objProducte.setNivell(new NivellDAO().select(rs.getInt(ContractProducte.NIVELL_ID)));
        objProducte.setColeccio(new ColeccioDAO().select(rs.getInt(ContractProducte.COLECCIO_ID)));
        objProducte.setCDU(new CduDAO().select(rs.getInt(ContractProducte.CDU_ID)));
        hashConsulta=new HashMap<>();
        hashConsulta.put(ContractExemplar.ID_PRODUCTE, objProducte.getId());
        objProducte.setExemplars(new HashSet<>(new ExemplarDAO().select(hashConsulta)));
        
        return objProducte;
    }
    
    public static void main(String[] args) {
        
    }
}
