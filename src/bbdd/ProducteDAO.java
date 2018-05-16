package bbdd;

import base.ConnectionFactory;
import contract.ContractExemplar;
import contract.ContractProducte;


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
    public List<Producte> select(HashMap <String,Object> dades,String campOrdre,Integer totalRegistres,Integer registreInicial,Boolean ascendent)throws SQLException,ClassNotFoundException{
        List<Producte> productes =new ArrayList<>();
        ArrayList<Object>valors;
        String query;
        int i;
        try {
            conn=ConnectionFactory.getInstance().getConnection();
            valors=new ArrayList<>();
            query = "SELECT * FROM "+ContractProducte.NOM_TAULA;
            i=0;
            for(String camp:dades.keySet()){
                if(i ==0){
                    query += " WHERE ";
                }
                else{
                    query += " AND ";
                }
                if(dades.get(camp).getClass().equals(String.class)){
                    query += camp+" LIKE ?";
                    valors.add("%"+dades.get(camp)+"%");
                }
                else{
                    query += camp+ " = ?";
                    valors.add(dades.get(camp));
                }

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
            for(Object valor:valors){
                ps.setObject(valors.indexOf(valor)+1, valor);
            }
            rs=ps.executeQuery();
            while(rs.next()){
                productes.add(this.read());
            }
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
        int i;
        String query;
        try {
            conn=ConnectionFactory.getInstance().getConnection();
            valors=new ArrayList<>();
            query = "SELECT COUNT(*) FROM "+ContractProducte.NOM_TAULA;
            i=0;
            for(String camp:dades.keySet()){
                if(i ==0){
                    query += " WHERE ";
                }
                else{
                    query += " AND ";
                }
                if(dades.get(camp).getClass().equals(String.class)){
                    query += camp+" LIKE ?";
                    valors.add("%"+dades.get(camp)+"%");
                }
                else{
                    query += camp+ " = ?";
                    valors.add(dades.get(camp));
                }

            }
            ps=conn.prepareStatement(query);
            for(Object valor:valors){
                ps.setObject(valors.indexOf(valor)+1, valor);
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
                    +ContractProducte.NIVELL_ID+","+ContractProducte.COLECCIO_ID+","+ContractProducte.CDU_ID+
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
                    + ContractProducte.IDIOMA_ID + ", "
                    + ContractProducte.EDITORIAL_ID + ", "
                    + ContractProducte.FORMAT_ID + ", "
                    + ContractProducte.PROCEDENCIA_ID + ", "
                    + ContractProducte.NIVELL_ID + ", "
                    + ContractProducte.COLECCIO_ID + ", "
                    + ContractProducte.CDU_ID + ", "
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
                    +ContractProducte.NIVELL_ID+" = ?,"+ContractProducte.COLECCIO_ID+" = ?,"+ContractProducte.CDU_ID+" = ?,"
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
            ps.setInt(10,producte.getIdioma().getId());
            ps.setInt(11,producte.getEditorial().getId());
            ps.setInt(12,producte.getFormat().getId());
            ps.setInt(13,producte.getProcedencia().getId());
            ps.setInt(14,producte.getNivell().getId());
            ps.setInt(15,producte.getColeccio().getId());
            ps.setInt(16,producte.getCDU().getId());
            ps.setBoolean(17, producte.getEstat());
            ps.setInt(18,producte.getId());
            
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
        //objProducte.setNivell(new NivellDAO().select(rs.getInt(ContractProducte.NIVELL_ID))); es una relacio n m s' ha de canviar
        objProducte.setColeccio(new ColeccioDAO().select(rs.getInt(ContractProducte.COLECCIO_ID)));
        objProducte.setCDU(new CduDAO().select(rs.getInt(ContractProducte.CDU_ID)));
        objProducte.setExemplars(new HashSet<Exemplar>(0));
        objProducte.setLloc(rs.getString(ContractProducte.LLOC));
        return objProducte;
    }
    
    public static void main(String[] args) {
        
    }
}
