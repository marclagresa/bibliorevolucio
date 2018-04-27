/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bbdd;

import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author sergiclotas
 */
public interface IObjectDAO<E> {
    public abstract boolean delete(E e)throws ClassNotFoundException,SQLException;
    public abstract boolean update(E e)throws ClassNotFoundException,SQLException;
    public abstract boolean insert(E e)throws ClassNotFoundException,SQLException;
    public abstract List<E> selectAll()throws ClassNotFoundException,SQLException;
    public abstract List<E> select(E e)throws ClassNotFoundException,SQLException;
    public abstract int nextId() throws ClassNotFoundException,SQLException;

    public abstract E select(int id) throws ClassNotFoundException,SQLException;
    public abstract void close();
}
