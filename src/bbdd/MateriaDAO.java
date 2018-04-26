package com.company.DAM2.Bibliorevolució.BBDD.dao;

import com.company.DAM2.Bibliorevolució.BBDD.connector.ConnectionFactory;
import com.company.DAM2.Bibliorevolució.BBDD.contract.ContractMateria;
import com.company.DAM2.Bibliorevolució.objecte.Materia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MateriaDAO implements IObjectDAO<Materia> {
    private Connection conn;
    private ResultSet rs;
    private PreparedStatement ps;

    public MateriaDAO(){
        conn=null;
        rs=null;
        ps=null;
    }

    @Override
    public List<Materia> selectAll() throws ClassNotFoundException, SQLException{
        List<Materia> list = new ArrayList<>();
        String sql;
        Materia selectMateria;
        try {
            conn = ConnectionFactory.getConnection();
            sql = "Select "+ContractMateria.ID+","+ContractMateria.NOM+" from "+ContractMateria.NOM_TAULA;
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while(rs.next()){
                selectMateria = new Materia();
                selectMateria.setId(rs.getInt(1));
                selectMateria.setNom(rs.getString(2));
                list.add(selectMateria);
            }
        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
        } finally {
            this.close();
        }
        return list;
    }
    @Override
    public List<Materia> select(Materia materia) throws ClassNotFoundException, SQLException{
        List<Materia> list = new ArrayList<>();
        String sql;
        try {
            conn = ConnectionFactory.getConnection();
            sql = "Select "+ContractMateria.ID+","+ContractMateria.NOM+ " from "+ContractMateria.NOM_TAULA+
                    " where "+ContractMateria.NOM+" LIKE ? ";
            ps = conn.prepareStatement(sql);
            ps.setString(1,'%'+materia.getNom()+'%');
            rs = ps.executeQuery();
            while(rs.next()){
                materia = new Materia();
                materia.setId(rs.getInt(1));
                materia.setNom(rs.getString(2));
                list.add(materia);
            }
        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
        } finally {
            this.close();
        }
        return list;
    }
    @Override
    public boolean insert(Materia materia) throws ClassNotFoundException, SQLException{
        String insert;
        boolean inserit = false;
        try {
            conn = ConnectionFactory.getConnection();
            insert = "Insert into "+ContractMateria.NOM_TAULA+" values (?,?)";
            ps = conn.prepareStatement(insert);
            ps.setInt(1,nextId());
            ps.setString(2,materia.getNom());
            inserit = true;
        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
        } finally {
            this.close();
        }
        return inserit;
    }
    @Override
    public boolean delete(Materia materia) throws ClassNotFoundException, SQLException{
        String delete;
        boolean borrat = false;
        try {
            conn = ConnectionFactory.getConnection();
            delete = "Delete from "+ContractMateria.NOM_TAULA+" where "+ContractMateria.ID+" = ?";
            ps = conn.prepareStatement(delete);
            ps.setInt(1,materia.getId());
            ps.executeUpdate();
            borrat = true;
        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
        } finally {
            this.close();
        }
        return borrat;
    }
    @Override
    public boolean update(Materia materia) throws ClassNotFoundException, SQLException{
        String update;
        boolean actualitzat = false;
        try {
            conn = ConnectionFactory.getConnection();
            update = "UPDATE from "+ContractMateria.NOM_TAULA+" SET "+
                    ContractMateria.NOM+" = ? where "+ContractMateria.ID+" = ?";
            ps = conn.prepareStatement(update);
            ps.setString(1,materia.getNom());
            ps.setInt(2,materia.getId());
            ps.executeUpdate();
            actualitzat = true;
        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
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
            conn = ConnectionFactory.getConnection();
            sql = "SELECT max("+ContractMateria.ID+") FROM "+ContractMateria.NOM_TAULA;
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            if(rs.next()){
                id = rs.getInt(1)+1;
            }
        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
        } finally {
            this.close();
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
}
