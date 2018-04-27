package bbdd;

import base.ConnectionFactory;
import java.sql.Connection;
import objecte.Usuari;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UsuariDAO implements IObjectDAO<Usuari> {

    Connection c;
    PreparedStatement ps;
    ResultSet rs;

    public UsuariDAO() {
        this.c=null;
        this.ps=null;
        this.rs=null;
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
            query  = "SELECT * FROM "+ ContractUsuari.NOM_TAULA;
            ps = c.prepareStatement(query);
            rs = ps.executeQuery();
            while (rs.next()) {
                usr = new Usuari();
                usr.setId(rs.getInt(ContractUsuari.ID));
                usr.setNom(rs.getString(ContractUsuari.NOM));
                usr.setPcognom(rs.getString(ContractUsuari.PRIMER_COGNOM));
                usr.setScognom(ContractUsuari.SEGON_COGNOM);
                usr.setEmail(ContractUsuari.EMAIL);
                usr.setTelefonFixe(rs.getString(ContractUsuari.TELEFON_FIX));
                usr.setTelefonMobil(rs.getString(ContractUsuari.TELEFON_MOBIL));
                usr.setContrasenya(rs.getString(ContractUsuari.CONTRASENYA));
                usr.setSalt(rs.getString(ContractUsuari.SALT));
                usr.setActiu(rs.getBoolean(ContractUsuari.ACTIU));
                usr.setAdmin(rs.getBoolean(ContractUsuari.ADMIN ));
                usr.setNivell(rs.getString(ContractUsuari.NIVELL));
                list.add(usr);
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
     * @param usuari
     * @return List de usuaris que coincideixen amb les dades rebudes de l' objecte usuari
     * @throws SQLException si no s'ha pogut conectar a la bbdd
     * @throws ClassNotFoundException  si no s'ha pogut carregar el driver jdbc
     */
    @Override
    public List<Usuari> select(Usuari usuari) throws SQLException, ClassNotFoundException {
        List<Usuari> list = new ArrayList<>();
        Usuari usr;
        String query;
        try {
            c = ConnectionFactory.getInstance().getConnection();
            query = "SELECT * FROM "+ ContractUsuari.NOM_TAULA + " "
                +   "WHERE " + ContractUsuari.NOM + " LIKE ? "
                +   "AND " + ContractUsuari.PRIMER_COGNOM + " LIKE ? "
                +   "AND " + ContractUsuari.SEGON_COGNOM + " LIKE ? "
                +   "AND " + ContractUsuari.EMAIL + " LIKE ? "
                +   "AND " + ContractUsuari.ACTIU + " = ? "
                +   "AND " + ContractUsuari.NIVELL + " LIKE ?";
            ps = c.prepareStatement(query);
            ps.setString(1, "%" + usuari.getNom() + "%");
            ps.setString(2, "%" + usuari.getPcognom() + "%");
            ps.setString(3, "%" + usuari.getScognom() + "%");
            ps.setString(4, "%" + usuari.getEmail() + "%");
            ps.setBoolean(5, usuari.isActiu());
            ps.setString(6, "%" + usuari.getNivell() + "%");
            rs = ps.executeQuery();
            while (rs.next()) {
                usr = new Usuari();
                usr.setId(rs.getInt(ContractUsuari.ID));
                usr.setNom(rs.getString(ContractUsuari.NOM));
                usr.setPcognom(rs.getString(ContractUsuari.PRIMER_COGNOM));
                usr.setScognom(ContractUsuari.SEGON_COGNOM);
                usr.setEmail(ContractUsuari.EMAIL);
                usr.setTelefonFixe(rs.getString(ContractUsuari.TELEFON_FIX));
                usr.setTelefonMobil(rs.getString(ContractUsuari.TELEFON_MOBIL));
                usr.setContrasenya(rs.getString(ContractUsuari.CONTRASENYA));
                usr.setSalt(rs.getString(ContractUsuari.SALT));
                usr.setActiu(rs.getBoolean(ContractUsuari.ACTIU));
                usr.setAdmin(rs.getBoolean(ContractUsuari.ADMIN ));
                usr.setNivell(rs.getString(ContractUsuari.NIVELL));
                list.add(usr);
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
    @Override
    public boolean delete(Usuari usuari) throws SQLException, ClassNotFoundException {
        boolean deleted = false;
        String query;
        try {
            c = ConnectionFactory.getInstance().getConnection();
            query = "DELETE FROM "+ContractUsuari.NOM_TAULA+" "
                  + "WHERE " + ContractUsuari.ID + " = ?";
            ps = ConnectionFactory.getInstance().getConnection().prepareStatement(query);
            ps.setInt(1, usuari.getId());
            deleted = ps.executeUpdate() == 1;
        } catch (SQLException ex) {
            throw new SQLException(ex.getMessage(), ex.getSQLState(), ex.getErrorCode(), ex.getCause());
        } catch (ClassNotFoundException ex) {
            throw new ClassNotFoundException(ex.getMessage(), ex.getCause());
        } finally {
            this.close();
        }
        return deleted;
    }
    
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
                usr.setId(rs.getInt(ContractUsuari.ID));
                usr.setNom(rs.getString(ContractUsuari.NOM));
                usr.setPcognom(rs.getString(ContractUsuari.PRIMER_COGNOM));
                usr.setScognom(ContractUsuari.SEGON_COGNOM);
                usr.setEmail(ContractUsuari.EMAIL);
                usr.setTelefonFixe(rs.getString(ContractUsuari.TELEFON_FIX));
                usr.setTelefonMobil(rs.getString(ContractUsuari.TELEFON_MOBIL));
                usr.setContrasenya(rs.getString(ContractUsuari.CONTRASENYA));
                usr.setSalt(rs.getString(ContractUsuari.SALT));
                usr.setActiu(rs.getBoolean(ContractUsuari.ACTIU));
                usr.setAdmin(rs.getBoolean(ContractUsuari.ADMIN ));
                usr.setNivell(rs.getString(ContractUsuari.NIVELL));
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
}
