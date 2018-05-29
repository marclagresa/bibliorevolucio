package bbdd;

import base.ConnectionFactory;
import contract.ContractUsuari;
import objecte.Usuari;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Classe encarregada de llegir/escriure a la taula usuari de la BBDD
 *
 * @author sergiclotas
 */
public class UsuariDAO extends BDObject implements IObjectDAO<Usuari> {


    public UsuariDAO() {
       super();
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
    public Usuari select(String emailUser) throws SQLException, ClassNotFoundException {
        Usuari usr = new Usuari();
        String query;
        try {
            conn = ConnectionFactory.getInstance().getConnection();
            query = "SELECT * "
                    + "FROM " + ContractUsuari.NOM_TAULA + " "
                    + "WHERE " + ContractUsuari.EMAIL + " LIKE ?";
            ps = conn.prepareStatement(query);
            ps.setString(1, emailUser);
            rs = ps.executeQuery();
            
            if (rs.next()) {
               usr=read();
            }
        } catch (SQLException e) {
            throw new SQLException(e.getMessage(), e.getSQLState(), e.getErrorCode(), e.getCause());
        } catch (ClassNotFoundException e) {
            throw new ClassNotFoundException(e.getMessage(), e.getCause());

        } finally {
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
            conn = ConnectionFactory.getInstance().getConnection();
            query = "SELECT * FROM " + ContractUsuari.NOM_TAULA
                    + " WHERE " + ContractUsuari.ACTIVA + " = 1";
            ps = conn.prepareStatement(query);
            rs = ps.executeQuery();
            while (rs.next()) {
                list.add(read());
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

    public List<Usuari> select(HashMap<String, Object> dades) throws SQLException, ClassNotFoundException {
        List<Usuari> list = new ArrayList<>();
        Object[] valors;
        String query;
        int i;
        boolean dadesCorrectes = comprovarDadesConsulta(dades, ContractUsuari.DEFINICIO);
        if(dadesCorrectes){
            try {
                conn = ConnectionFactory.getInstance().getConnection();
                query = "SELECT * FROM " + ContractUsuari.NOM_TAULA;
                valors = new Object[dades.size()];
                i = 0;
                for (String camp : dades.keySet()) {

                    if (i == 0) {
                        query += " WHERE ";
                    } else {
                        query += " AND ";
                    }
                    query += camp;
                    if (dades.get(camp).getClass().equals(String.class)) {
                        query += " LIKE ?";
                        valors[i] = "%" + dades.get(camp) + "%";
                    } else {
                        query += " = ?";
                        valors[i] = dades.get(camp);
                    }
                    i++;
                }
                ps = conn.prepareStatement(query);
                for (i = 0; i < valors.length; i++) {
                    ps.setObject(i + 1, valors[i]);
                }
                rs = ps.executeQuery();
                while (rs.next()) {
                    list.add(read());
                }

            } catch (SQLException ex) {
                throw new SQLException(ex.getMessage(), ex.getSQLState(), ex.getErrorCode(), ex.getCause());
            } catch (ClassNotFoundException ex) {
                throw new ClassNotFoundException(ex.getMessage(), ex.getCause());
            } finally {
                this.close();
            }
        }else{
            throw new IllegalArgumentException("Els tipus de dades de la consulta no són correctes");
        }
        
        return list;

    }
    
    @Override
    public List<Usuari> select(HashMap <String,Object> dades,String campOrdre,Integer totalRegistres,Integer registreInicial,Boolean ascendent)throws SQLException,ClassNotFoundException{
        List<Usuari> usuaris =new ArrayList<>();
        ArrayList<Object>valors;
        String query;
        int i;
        boolean dadesCorrectes=comprovarDadesConsulta(dades, ContractUsuari.DEFINICIO);
        if(dadesCorrectes){
            try {
                conn=ConnectionFactory.getInstance().getConnection();
                valors=new ArrayList<>();
                query = "SELECT * FROM "+ContractUsuari.NOM_TAULA;
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
                    i++;

                }
                if(campOrdre!=null){
                    query+=" ORDER BY " + campOrdre;
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
                rs=ps.executeQuery();
                while(rs.next()){
                    usuaris.add(read());
                }
            } catch (SQLException ex) {
                throw new SQLException(ex.getMessage(), ex.getSQLState() , ex.getErrorCode(), ex.getCause());
            } catch(ClassNotFoundException ex){
                throw new ClassNotFoundException(ex.getMessage(), ex.getCause());
            }finally{
                this.close();
            }
        }
        else{
            throw new IllegalArgumentException("Els tipus de dades de la consulta no són correctes");
        }
        return usuaris;
    }
    @Override
    public int selectCount(HashMap <String,Object> dades)throws SQLException,ClassNotFoundException,IllegalArgumentException{
        int count=0;
        ArrayList<Object> valors;
        int i;
        String query;
        boolean dadesCorrectes=comprovarDadesConsulta(dades, ContractUsuari.DEFINICIO);
        if(dadesCorrectes){
            try {
              conn=ConnectionFactory.getInstance().getConnection();
              valors=new ArrayList<>();
              query = "SELECT COUNT(*) FROM "+ContractUsuari.NOM_TAULA;
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
        }
        else{
            throw new IllegalArgumentException("Els tipus de dades de la consulta no són correctes");
        }
        
        return count;
    }
    @Override
    public boolean insert(Usuari usuari) throws SQLException, ClassNotFoundException {
        boolean inserit = false;
        String query;
        try {
            conn = ConnectionFactory.getInstance().getConnection();
            query = "INSERT INTO " + ContractUsuari.NOM_TAULA + " ( "
                    + ContractUsuari.ID + ", "
                    + ContractUsuari.NOM + ", "
                    + ContractUsuari.PRIMER_COGNOM + ", "
                    + ContractUsuari.SEGON_COGNOM + ", "
                    + ContractUsuari.TELEFON_FIX + ", "
                    + ContractUsuari.TELEFON_MOBIL + ", "
                    + ContractUsuari.EMAIL + ", "
                    + ContractUsuari.ID_NIVELL + ", "
                    + ContractUsuari.ADMIN + ", "
                    + ContractUsuari.ACTIVA + ", "
                    + ContractUsuari.CONTRASENYA + ", "
                    + ContractUsuari.SALT + " ) "
                    + "VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";
            ps = conn.prepareStatement(query);
            ps.setInt(1, usuari.getId());
            ps.setString(2, usuari.getNom());
            ps.setString(3, usuari.getPcognom());
            ps.setString(4, usuari.getScognom());
            ps.setString(5, usuari.getTelefonFixe());
            ps.setString(6, usuari.getTelefonMobil());
            ps.setString(7, usuari.getEmail());
            if(usuari.getNivell().getId()==-1){
                ps.setNull(8, Types.INTEGER);
            }
            else{
                ps.setInt(8, usuari.getNivell().getId());
            }
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
    @Override
    public boolean update(Usuari usuari) throws SQLException, ClassNotFoundException {
        boolean updated = false;
        String query;
        try {
            conn = ConnectionFactory.getInstance().getConnection();
            query = "UPDATE " + ContractUsuari.NOM_TAULA + " "
                    + "SET " + ContractUsuari.NOM + "= ?, "
                    + ContractUsuari.PRIMER_COGNOM + " = ?, "
                    + ContractUsuari.SEGON_COGNOM + " = ?, "
                    + ContractUsuari.TELEFON_MOBIL + " = ?, "
                    + ContractUsuari.TELEFON_FIX + " = ?, "
                    + ContractUsuari.EMAIL + " = ?, "
                    + ContractUsuari.ID_NIVELL + " = ?, "
                    + ContractUsuari.ACTIVA + " = ?, "
                    + ContractUsuari.ADMIN + " = ?, "
                    + ContractUsuari.SALT + " = ?, "
                    + ContractUsuari.CONTRASENYA + " = ? "
                    + "WHERE " + ContractUsuari.ID + " = ?";
            ps = conn.prepareStatement(query);
            ps.setString(1, usuari.getNom());
            ps.setString(2, usuari.getPcognom());
            ps.setString(3, usuari.getScognom());
            ps.setString(4, usuari.getTelefonMobil());
            ps.setString(5, usuari.getTelefonFixe());
            ps.setString(6, usuari.getEmail());
            if(usuari.getNivell().getId()==-1){
                ps.setNull(7, Types.INTEGER);
            }
            else{
                ps.setInt(7, usuari.getNivell().getId());
            }
            ps.setBoolean(8, usuari.isActiu());
            ps.setBoolean(9, usuari.isAdmin());
            ps.setString(10, usuari.getSalt());
            ps.setString(11, usuari.getContrasenya());
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

    @Override
    public int nextId() throws SQLException, ClassNotFoundException {

        int id = -1;
        try {
            conn = ConnectionFactory.getInstance().getConnection();
            String sql = "SELECT max(" + ContractUsuari.ID + ") MAXID FROM " + ContractUsuari.NOM_TAULA;
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            if (!rs.next()) {
                id = 1;
            } else {
                id = rs.getInt("MAXID") + 1;
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
    @Override
    public Usuari select(int id) throws ClassNotFoundException, SQLException {
        Usuari usr = new Usuari();
        String query;
        try {
            conn = ConnectionFactory.getInstance().getConnection();
            query = "SELECT * "
                    + "FROM " + ContractUsuari.NOM_TAULA + " "
                    + "WHERE " + ContractUsuari.ID + " = ?";
            ps = conn.prepareStatement(query);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            if (rs.next()) {
                usr = read();
            }
        } catch (SQLException ex) {
            throw new SQLException(ex.getMessage(), ex.getSQLState(), ex.getErrorCode(), ex.getCause());
        } catch (ClassNotFoundException ex) {
            throw new ClassNotFoundException(ex.getMessage(), ex.getCause());
        } finally {
            this.close();
        }
        return usr;
    }

    @Override
    protected Usuari read() throws SQLException, ClassNotFoundException {
        NivellDAO nivellDao = new NivellDAO();
        return new Usuari(
                rs.getInt(ContractUsuari.ID),
                rs.getString(ContractUsuari.NOM),
                rs.getString(ContractUsuari.PRIMER_COGNOM),
                rs.getString(ContractUsuari.SEGON_COGNOM),
                rs.getString(ContractUsuari.TELEFON_MOBIL),
                rs.getString(ContractUsuari.TELEFON_FIX),
                rs.getString(ContractUsuari.EMAIL),
                rs.getString(ContractUsuari.CONTRASENYA),
                rs.getBoolean(ContractUsuari.ACTIVA),
                rs.getString(ContractUsuari.SALT),
                rs.getBoolean(ContractUsuari.ADMIN),
                nivellDao.select(rs.getInt(ContractUsuari.ID_NIVELL))
        );
    }
}
