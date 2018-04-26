package com.company.DAM2.Bibliorevolució.BBDD.dao;

import com.company.DAM2.Bibliorevolució.BBDD.connector.ConnectionFactory;
import com.company.DAM2.Bibliorevolució.BBDD.contract.ContractNivell;
import com.company.DAM2.Bibliorevolució.objecte.Nivell;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class NivellDAO implements IObjectDAO<Nivell> {
    private Connection conn;
    private ResultSet rs;
    private PreparedStatement ps;

    public NivellDAO(){
        conn=null;
        rs=null;
        ps=null;
    }

    @Override
    public List<Nivell> selectAll() throws ClassNotFoundException, SQLException{
        List<Nivell> list = new ArrayList<>();
        String sql;
        Nivell selectNivell;
        try {
            conn = ConnectionFactory.getConnection();
            sql = "Select "+ContractNivell.ID+","+ContractNivell.NOM+" from "+ContractNivell.NOM_TAULA;
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while(rs.next()){
                selectNivell = new Nivell();
                selectNivell.setId(rs.getInt(1));
                selectNivell.setNom(rs.getString(2));
                list.add(selectNivell);
            }
        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
        } finally {
            this.close();
        }
        return list;
    }
    @Override
    public List<Nivell> select(Nivell nivell) throws ClassNotFoundException, SQLException{
        List<Nivell> list = new ArrayList<>();
        String sql;
        try {
            conn = ConnectionFactory.getConnection();
            sql = "Select "+ContractNivell.ID+","+ContractNivell.NOM+" from "+ContractNivell.NOM_TAULA+
                    " where "+ContractNivell.NOM+" LIKE ? ";
            ps = conn.prepareStatement(sql);
            ps.setString(1,'%'+nivell.getNom()+'%');
            rs = ps.executeQuery();
            while(rs.next()){
                nivell = new Nivell();
                nivell.setId(rs.getInt(1));
                nivell.setNom(rs.getString(2));
                list.add(nivell);
            }
        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
        } finally {
            this.close();
        }
        return list;
    }
    @Override
    public boolean insert(Nivell nivell) throws ClassNotFoundException, SQLException{
        String insert;
        boolean inserit = false;
        try {
            conn = ConnectionFactory.getConnection();
            insert = "Insert into "+ContractNivell.NOM_TAULA+" values (?,?)";
            ps = conn.prepareStatement(insert);
            ps.setInt(1,nextId());
            ps.setString(2,nivell.getNom());

            inserit = true;
        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
        } finally {
            this.close();
        }
        return inserit;
    }
    @Override
    public boolean delete(Nivell nivell) throws ClassNotFoundException, SQLException{
        String delete;
        boolean borrat = false;
        try {
            conn = ConnectionFactory.getConnection();
            delete = "Delete from "+ContractNivell.NOM_TAULA+" where "+ContractNivell.ID+" = ?";
            ps = conn.prepareStatement(delete);
            ps.setInt(1,nivell.getId());
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
    public boolean update(Nivell nivell) throws ClassNotFoundException, SQLException{
        String update;
        boolean actualitzat = false;
        try {
            conn = ConnectionFactory.getConnection();
            update = "UPDATE from "+ContractNivell.NOM_TAULA+" SET "+
                    ContractNivell.NOM+" = ? where "+ContractNivell.ID+" = ?";
            ps = conn.prepareStatement(update);
            ps.setString(1,nivell.getNom());
            ps.setInt(2,nivell.getId());
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
            sql = "SELECT max("+ContractNivell.ID+") FROM "+ContractNivell.NOM_TAULA;
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
