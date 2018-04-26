package com.company.DAM2.Bibliorevolució.BBDD.dao;

import java.sql.SQLException;
import java.util.List;

public interface IObjectDAO<E> {
    /**
     * Funcio per obtenir una llista de totes els E.
     * @return List<E> (Llista E)
     */
    public List<E> selectAll() throws ClassNotFoundException, SQLException;

    /**
     * Funcio per obtenir una llista de totes de E que encaixin amb una e donada.
     * @param e (e donada)
     * @return List<E> (Llista E)
     */
    public List<E> select(E e) throws SQLException, ClassNotFoundException;

    /**
     * Funcio per insertar una E nova.
     * @param e (E per inserir)
     * @return boolean (S'ha inserit o no)
     */
    public boolean insert(E e) throws ClassNotFoundException, SQLException;

    /**
     * Funcio per borrar una E.
     * @param e (E per borrar)
     * @return boolean (S'ha borrat o no)
     */
    public boolean delete(E e) throws SQLException, ClassNotFoundException;

    /**
     * Funcio per actualitzar una E.
     * @param e (E per actualitzar)
     * @return boolean (S'ha actualitzat o no)
     */
    public boolean update(E e) throws SQLException, ClassNotFoundException;

    /**
     * Funcio per obtenir un nou id per la seguent E.
     * @return int (Seguent id)
     */
    public int nextId() throws SQLException, ClassNotFoundException;

    /**
     * Funcio per tancar les conexions a BBDD
     */
    public void close();
}
