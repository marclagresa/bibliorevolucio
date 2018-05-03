package bbdd;

import base.ConnectionFactory;
import contract.ContractEditorial;
import contract.ContractUsuari;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.sql.Connection;
import objecte.Usuari;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLType;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 * Classe encarregada de llegir/escriure a la taula usuari de la BBDD
 * @author sergiclotas
 */
public class UsuariDAO implements IObjectDAO<Usuari> {

    Connection c;
    PreparedStatement ps;
    ResultSet rs;
    String query;
    public UsuariDAO() {
        this.c=null;
        this.ps=null;
        this.rs=null;
    }
    /**
     * Funció que rep l' email d' un usuari i retorna un Objecte Usuari
     * 
     * @param emailUser email de l' usuari que estem buscant
     * @return Usuari que te l email rebut per parametre o un nou Usuari si no 
     * existeix cap usuari amb aquest email.
     * @throws SQLException si no es pot conectar a la bbdd
     * @throws ClassNotFoundException si el driver jdbc no esta carregat
     */
    public Usuari select(String emailUser)throws SQLException,ClassNotFoundException{
        Usuari usr=new Usuari();
        String query;
        try{
            c=ConnectionFactory.getInstance().getConnection();
            query = "SELECT * " 
                  + "FROM " + ContractUsuari.NOM_TAULA +" "
                  + "WHERE " +ContractUsuari.EMAIL + " = ?";
            ps=c.prepareStatement(query);
            ps.setString(1, emailUser);
            rs=ps.executeQuery();
            if(rs.next()){
                usr=fillUsuari();
            }
        } catch(SQLException e){
            throw new SQLException(e.getMessage(), e.getSQLState(), e.getErrorCode(),e.getCause());
        } catch(ClassNotFoundException e){
            throw new ClassNotFoundException(e.getMessage(), e.getCause());
            
        } finally{
            this.close();
        }
        return usr;
    }
    /**
     * Funció que ens retorna tots els usuaris de la base de dades
     *
     * @return List de tots els usuaris
     * @throws SQLException si no es pot conectar a a la base de dades
     * @throws ClassNotFoundException Si el driver jdbc no es pot carregar a la
     * bbdd
     */
    @Override
    public List<Usuari> selectAll() throws SQLException, ClassNotFoundException {
        List<Usuari> list = new ArrayList<>();
        Usuari usr;
        String query;
        try {
            c = ConnectionFactory.getInstance().getConnection();
            query  = "SELECT * FROM "+ ContractUsuari.NOM_TAULA 
                  + " WHERE "+ ContractUsuari.ACTIU + " = 1";
            ps = c.prepareStatement(query);
            rs = ps.executeQuery();
            while (rs.next()) {
                list.add(fillUsuari());
            }

        } catch (SQLException ex) {
            throw new SQLException(ex.getMessage(), ex.getSQLState(), ex.getErrorCode(), ex.getCause());
        } catch (ClassNotFoundException ex) {
            throw new ClassNotFoundException(ex.getMessage(), ex.getCause());
        } finally {
            this.close();
        }
        return list;
    }

    
    /**
     * Funció que ens retorna una llista d' usuaris amb les condicion que
     * ens marca l' usuari rebut per parametre
     * @param dades
     * @return List de usuaris que coincideixen amb les dades rebudes de l' objecte usuari
     * @throws SQLException si no s'ha pogut conectar a la bbdd
     * @throws ClassNotFoundException  si no s'ha pogut carregar el driver jdbc
     */
    @Override
    public List<Usuari> select(HashMap <String,Object> dades) throws SQLException, ClassNotFoundException {
        List<Usuari> list = new ArrayList<>();
        Usuari usr;
        Object[]valors;
        int i;
        boolean dadesCorrectes=false;
        try {
            
            c = ConnectionFactory.getInstance().getConnection();
            query = "SELECT * FROM "+ ContractUsuari.NOM_TAULA;
            valors=new Object[dades.size()];
            i=0;
            for(String camp:dades.keySet()){
                switch(ContractUsuari.DEFINICIO.get(camp)){
                    case Types.BOOLEAN:
                        dadesCorrectes=dades.get(camp).getClass().equals(Boolean.class);
                        break;
                    case Types.CHAR:
                    case Types.VARCHAR:
                        dadesCorrectes=dades.get(camp).getClass().equals(String.class);
                        break;
                    case Types.INTEGER:
                        dadesCorrectes=dades.get(camp).getClass().equals(Integer.class);
                        break;
                }
                if(dadesCorrectes){
                    if(i == 0){
                        query+=" WHERE ";
                    }
                    else{
                        query+=" AND ";
                    }
                     query+= camp;
                    if(dades.get(camp).getClass().equals(String.class)){
                       query+= " LIKE ?";
                       valors[i]="%" + dades.get(camp) + "%";
                    }
                    else{
                        query+=" = ?";
                        valors[i]=dades.get(camp);
                    }
                }
                else{
                    throw new SQLException("El tipus de dades introduïdes no són correctes.");
                }
                i++;
            }
            ps = c.prepareStatement(query);
            for(i=0;i<valors.length;i++){
                ps.setObject(i+1, valors[i]);
            }
            rs = ps.executeQuery();
            while (rs.next()) {
                list.add(fillUsuari());
            }

            
        } catch (SQLException ex) {
            throw new SQLException(ex.getMessage(), ex.getSQLState(), ex.getErrorCode(), ex.getCause());
        } catch (ClassNotFoundException ex) {
            throw new ClassNotFoundException(ex.getMessage(), ex.getCause());
        } finally {
            this.close();
        }
        return list;

    }

    /**
     * Funció que intenta insertar un nou registre a la taula usuari
     * 
     * @param usuari que volem inserir
     * @return true si s' ha pogut inserir altrament retorna fals.
     * @throws SQLException si no s' ha pogut conectar a la BBDD.
     * @throws ClassNotFoundException  si no s' ha pogut carregar el driver.
     */
    @Override
    public boolean insert(Usuari usuari) throws SQLException, ClassNotFoundException {
        boolean inserit = false;
        String query;
        try {
            c = ConnectionFactory.getInstance().getConnection();
            query = "INSERT INTO "+ ContractUsuari.NOM_TAULA +" ( "
                    + ContractUsuari.ID + ", "
                    + ContractUsuari.NOM + ", "
                    + ContractUsuari.PRIMER_COGNOM + ", "
                    + ContractUsuari.SEGON_COGNOM + ", "
                    + ContractUsuari.TELEFON_FIX + ", "
                    + ContractUsuari.TELEFON_MOBIL + ", "
                    + ContractUsuari.EMAIL + ", "
                    + ContractUsuari.NIVELL + ", "
                    + ContractUsuari.ADMIN + ", "
                    + ContractUsuari.ACTIU + ", "
                    + ContractUsuari.CONTRASENYA + ", "
                    + ContractUsuari.SALT + " "
                +   "VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";
            ps = c.prepareStatement(query);
            ps.setInt(1, nextId());
            ps.setString(2, usuari.getNom());
            ps.setString(3, usuari.getPcognom());
            ps.setString(4, usuari.getScognom());
            ps.setString(5, usuari.getTelefonFixe());
            ps.setString(6, usuari.getTelefonMobil());
            ps.setString(7, usuari.getEmail());
            ps.setString(8, usuari.getNivell());
            ps.setBoolean(9, usuari.isAdmin());
            ps.setBoolean(10, usuari.isActiu());
            ps.setString(11, usuari.getContrasenya());
            ps.setString(12, usuari.getSalt());
            inserit = ps.executeUpdate() == 1;

        } catch (SQLException ex) {
            throw new SQLException(ex.getMessage(), ex.getSQLState(), ex.getErrorCode(), ex.getCause());
        } catch (ClassNotFoundException ex) {
            throw new ClassNotFoundException(ex.getMessage(), ex.getCause());
        } finally {
            this.close();
        }
        return inserit;
    }
    
    /**
     * Funció que intenta borrar l' usari rebut per paràmetre de la base de dades.
     * @param usuari que volem borrar
     * @return true si s' ha pogut fer el delete altrament retorna false
     * @throws SQLException si no s'ha pogut conectar a la bbdd
     * @throws ClassNotFoundException si no s' ha pogut carregar el driver jdbc
     */
    
    /**
     * Funció que intenta fer l'update de l'usuari rebut per paràmetre
     * @param usuari del que volem fer l' update
     * @return true si s'ha pogut executar l' update correctament altrament retorna fals
     * @throws SQLException si no s'ha pogut conectar a la bbdd
     * @throws ClassNotFoundException  si no s' ha pogut carregar el driver jdbc
     */
    @Override
    public boolean update(Usuari usuari) throws SQLException, ClassNotFoundException {
        boolean updated = false;
        String query;
        try {
            c = ConnectionFactory.getInstance().getConnection();
            query = "UPDATE FROM " + ContractUsuari.NOM_TAULA + " "
                    +"SET "+ContractUsuari.NOM+"= ?, "
                    + ContractUsuari.PRIMER_COGNOM +" = ?, "
                    + ContractUsuari.SEGON_COGNOM + " = ?, "
                    + ContractUsuari.TELEFON_MOBIL + " = ?, "
                    + ContractUsuari.TELEFON_FIX + " = ?, "
                    + ContractUsuari.EMAIL + " = ?, "
                    + ContractUsuari.NIVELL + " = ?, "
                    + ContractUsuari.ACTIU + " = ?, "
                    + ContractUsuari.ADMIN + " = ?, "
                    + ContractUsuari.SALT + " = ?, "
                    + ContractUsuari.CONTRASENYA + " = ? "
                    + "WHERE " +ContractUsuari.ID + " = ?" ;
            ps = c.prepareStatement(query);
            ps.setString(1, usuari.getNom());
            ps.setString(2, usuari.getPcognom());
            ps.setString(3, usuari.getScognom());
            ps.setString(4, usuari.getTelefonMobil());
            ps.setString(5, usuari.getTelefonFixe());
            ps.setString(6, usuari.getEmail());
            ps.setString(7, usuari.getNivell());
            ps.setBoolean(8, usuari.isActiu());
            ps.setBoolean(9, usuari.isAdmin());
            ps.setString(10, usuari.getSalt());
            ps.setString (11,usuari.getContrasenya());
            ps.setInt(12, usuari.getId());
            updated = ps.executeUpdate() == 1;

        } catch (SQLException ex) {
            throw new SQLException(ex.getMessage(), ex.getSQLState(), ex.getErrorCode(), ex.getCause());

        } catch (ClassNotFoundException ex) {
            throw new ClassNotFoundException(ex.getMessage(), ex.getCause());
        } finally {
            this.close();
        }
        return updated;
    }
   
    
    /**
     * Funcio que ens retorna el primer id disponible per assignar a un nou usuari
     * @return int nou id per un nou usuari
     * @throws SQLException si no s'ha pogut conectar a la bbdd
     * @throws ClassNotFoundException si no s'ha pogut carregar el driver jdbc
     */
    @Override
    public int nextId() throws SQLException, ClassNotFoundException {

        int id = -1;
        try {
            c = ConnectionFactory.getInstance().getConnection();
            String sql = "SELECT max("+ContractUsuari.ID+") MAXID FROM "+ContractUsuari.NOM_TAULA;
            ps = c.prepareStatement(sql);
            rs = ps.executeQuery();
            if (!rs.next()) {
                id = 1;
            } else {
                id = rs.getInt("MAXID") +1;
            }

        } catch (SQLException ex) {
            throw new SQLException(ex.getMessage(), ex.getSQLState(), ex.getErrorCode(), ex.getCause());
        } catch (ClassNotFoundException ex) {
            throw new ClassNotFoundException(ex.getMessage(), ex.getCause());
        } finally {
            this.close();
        }
        return id;
    }
    
    /**
     * Métode que ens tanca totes les conexión actives que tinguem.
     */
    @Override
    public void close() {
        
        if (this.c != null) {
            try {
                this.c.close();
                this.c = null;
            } catch (SQLException ex) {

            }
        }
        if (this.ps != null) {
            try {
                this.ps.close();
                this.ps = null;
            } catch (SQLException ex) {

            }

        }
        if (this.rs != null) {
            try {
                this.rs.close();
                this.rs = null;
            } catch (SQLException ex) {

            }
        }
    }
    
    /**
     * Funció que ens retorna l'usuari al que correspont la id rebuda per parametre
     * @param id 
     * @return un Usuari nou si no troba cap usuari amb aquesta id altrament retorna l'usari que correspongi
     * @throws ClassNotFoundException si no s'ha pogut carregar el driver jdbc
     * @throws SQLException si no es pot conectar a la BBDD
     */
    @Override
    public Usuari select(int id) throws ClassNotFoundException, SQLException {
        Usuari usr = new Usuari();
        String query;
        try{
            c=ConnectionFactory.getInstance().getConnection();
            query="SELECT * "
            + "FROM " + ContractUsuari.NOM_TAULA + " "
            + "WHERE " + ContractUsuari.ID + " = ?";
            ps=c.prepareStatement(query);
            ps.setInt(1, id);
            rs=ps.executeQuery();
            if(rs.next()){
                usr=fillUsuari();
            }
        }catch (SQLException ex){
            throw new SQLException(ex.getMessage(), ex.getSQLState(), ex.getErrorCode(), ex.getCause());
        }catch (ClassNotFoundException ex){
            throw new ClassNotFoundException(ex.getMessage(), ex.getCause());
        }finally{
            this.close();
        }
        return usr;
    }
    /**
     * Retorna un nou usuari amb les dades que conte el ResultSet
     * @throws SQLException 
     */
    private Usuari fillUsuari()throws SQLException{
        return new Usuari(
                rs.getInt(ContractUsuari.ID),
                rs.getString(ContractUsuari.NOM), 
                rs.getString(ContractUsuari.PRIMER_COGNOM), 
                rs.getString(ContractUsuari.SEGON_COGNOM), 
                rs.getString(ContractUsuari.TELEFON_MOBIL),
                rs.getString(ContractUsuari.TELEFON_FIX),
                rs.getString(ContractUsuari.EMAIL),
                rs.getString(ContractUsuari.CONTRASENYA),
                rs.getBoolean(ContractUsuari.ACTIU),
                rs.getString(ContractUsuari.SALT),
                rs.getBoolean(ContractUsuari.ADMIN),
                rs.getString(ContractUsuari.NIVELL)
        );
    }
    public static void main(String[] args) {
        
        HashMap <String,Object> prova= new HashMap();
        prova.put(ContractUsuari.ADMIN, true);
        prova.put(ContractUsuari.ID, 1);
        prova.put(ContractUsuari.NOM, "blablab");
        try{
            ConnectionFactory.getInstance().configure(FileSystems.getDefault().getPath("src/base", "configLector.txt"));
            List<Usuari> lstUsr= new UsuariDAO().select(prova);    
            System.out.println(lstUsr.size());
        }catch(SQLException|ClassNotFoundException |IOException e){
            System.err.println(e.getMessage());
        }
            
        
        
    
    }
    
}