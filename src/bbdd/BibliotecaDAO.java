package bbdd;

import base.ConnectionFactory;
import contract.ContractBiblioteca;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import objecte.Biblioteca;


/**
 * Classe encarregade de la lectura i escritura de la taula
 * bibliorevolucio.biblioteca
 * 
 * @author albertCorominas,sergiclotas
 */

public class BibliotecaDAO extends BDObject implements IObjectDAO<Biblioteca> {
    
    public BibliotecaDAO(){
        super();
    }

    @Override
    public List<Biblioteca> selectAll() throws ClassNotFoundException, SQLException {
        List<Biblioteca> list = new ArrayList<>();
        String sql;
        try {
            conn = ConnectionFactory.getInstance().getConnection();
            sql = "SELECT * FROM "
                + ContractBiblioteca.NOM_TAULA;
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while(rs.next()){
                list.add(this.read());
            }
        } catch (SQLException ex){
            throw new SQLException (ex.getMessage(),ex.getSQLState(),ex.getErrorCode(),ex.getCause());
        }catch( ClassNotFoundException ex) {
            throw new ClassNotFoundException(ex.getMessage(),ex.getCause());
        } finally {
            super.close();
        }
        return list;
    }
    @Override
    public List<Biblioteca> select(HashMap <String,Object> dades,String campOrdre,Integer totalRegistres,Integer registreInicial,Boolean ascendent)throws SQLException,ClassNotFoundException{
        List<Biblioteca> bilioteques=new ArrayList<>();
        ArrayList<Object>valors;
        String query;
        int i;
        boolean dadesCorrectes=super.comprovarDadesConsulta(dades, ContractBiblioteca.DEFINICIO);
        if(dadesCorrectes){
            try {
                conn=ConnectionFactory.getInstance().getConnection();
                valors=new ArrayList<>();
                query = "SELECT * FROM "+ContractBiblioteca.NOM_TAULA;
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
                    query+=" ORDER BY "+campOrdre;
                    
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
                    bilioteques.add(this.read());
                }
            } catch (SQLException ex) {
                throw new SQLException(ex.getMessage(), ex.getSQLState() , ex.getErrorCode(), ex.getCause());
            } catch(ClassNotFoundException ex){
                throw new ClassNotFoundException(ex.getMessage(), ex.getCause());
            }finally{
                super.close();
            }
        }
        else{
            throw new IllegalArgumentException("el tipus de dades de la consulta no són correctes.");
        }

        return bilioteques;
    }
    @Override
    public int selectCount(HashMap <String,Object> dades)throws SQLException,ClassNotFoundException{
        int count=0;
        ArrayList<Object> valors;
        int i;
        String query;
        boolean dadesCorrectes=super.comprovarDadesConsulta(dades, ContractBiblioteca.DEFINICIO);
        if(dadesCorrectes){
            try {
                conn=ConnectionFactory.getInstance().getConnection();
                valors=new ArrayList<>();
                query = "SELECT COUNT(*) FROM "+ContractBiblioteca.NOM_TAULA;
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
                super.close();
            }
        }
        else{
            throw new IllegalArgumentException("El tipus de dades de la consulta no són correctes.");
        }
        return count;
    }
    @Override
    public Biblioteca select(int id) throws ClassNotFoundException, SQLException{
        Biblioteca biblioteca = new Biblioteca();
        String sql;
        try {
            conn = ConnectionFactory.getInstance().getConnection();
            sql = "SELECT * FROM "
                + ContractBiblioteca.NOM_TAULA + " "
                + "WHERE " + ContractBiblioteca.ID + " = ? ";
            ps = conn.prepareStatement(sql);
            ps.setInt(1,id);
            rs = ps.executeQuery();
            if(rs.next()){
                biblioteca=this.read();
            }
        } catch (SQLException ex) {
            throw new SQLException(ex.getMessage(),ex.getSQLState(),ex.getErrorCode(),ex.getCause());

        } catch(ClassNotFoundException ex){
            throw new ClassNotFoundException(ex.getMessage(), ex.getCause());
        } finally {
            super.close();
        }
        return biblioteca;
    }
    @Override
    public boolean insert(Biblioteca biblioteca) throws ClassNotFoundException, SQLException{
        String insert;
        boolean inserit;
        try {
            conn = ConnectionFactory.getInstance().getConnection();
            insert = "Insert into "+ContractBiblioteca.NOM_TAULA+" values (?,?)";
            ps = conn.prepareStatement(insert);
            ps.setInt(1,biblioteca.getId());
            ps.setString(2,biblioteca.getNom());
            inserit=ps.executeUpdate()==1;
        } catch (SQLException ex) {
            throw new SQLException (ex.getMessage(),ex.getSQLState(),ex.getErrorCode(),ex.getCause());
        } catch( ClassNotFoundException ex){
            throw new ClassNotFoundException(ex.getMessage(), ex.getCause());
        }finally {
            super.close();
        }
        return inserit;
    }
    @Override
    public boolean update(Biblioteca biblioteca) throws ClassNotFoundException, SQLException{
        String update;
        boolean actualitzat;
        try {
            conn = ConnectionFactory.getInstance().getConnection();
            update = "UPDATE "+ContractBiblioteca.NOM_TAULA+" SET "
                    +ContractBiblioteca.NOM+" = ? where "
                    +ContractBiblioteca.ID+" = ? ";
            ps = conn.prepareStatement(update);
            ps.setString(1,biblioteca.getNom());
            ps.setInt(2,biblioteca.getId());
            actualitzat=ps.executeUpdate()==1;
        } catch (SQLException ex){
            throw new SQLException(ex.getMessage(),ex.getSQLState(),ex.getErrorCode(),ex.getCause());
        }catch( ClassNotFoundException ex) {
            throw new ClassNotFoundException(ex.getMessage(),ex.getCause());
        } finally {
            super.close();
        }
        return actualitzat;
    }
    @Override
    public int nextId() throws ClassNotFoundException, SQLException{
        int id = 1;
        String sql;
        try {
            conn = ConnectionFactory.getInstance().getConnection();
            sql = "SELECT max("+ContractBiblioteca.ID+") FROM "+ContractBiblioteca.NOM_TAULA;
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            if(rs.next()){
                id = rs.getInt(1)+1;
            }
        } catch (SQLException ex){
            throw new SQLException(ex.getMessage(),ex.getSQLState(),ex.getErrorCode(),ex.getCause());
        }catch( ClassNotFoundException ex) {
            throw new ClassNotFoundException (ex.getMessage(),ex.getCause());
        } finally {
            super.close();
        }
        return id;
    } 
    @Override
    protected Biblioteca read() throws SQLException{
        Biblioteca objBiblioteca = new Biblioteca();
        objBiblioteca.setId(rs.getInt(ContractBiblioteca.ID));
        objBiblioteca.setNom(rs.getString(ContractBiblioteca.NOM));
        return objBiblioteca;
    }
}
