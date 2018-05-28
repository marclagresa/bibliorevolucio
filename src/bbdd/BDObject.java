/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bbdd;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 *
 * @author sergiclotas
 */
public abstract class BDObject{
    protected Connection conn;
    protected PreparedStatement ps;
    protected ResultSet rs;
    
    protected BDObject() {
        conn=null;
        ps=null;
        rs=null;
    }
    protected void close()throws SQLException{
        if(conn!=null){
            conn.close();
            conn=null;
        }
        if(ps!=null){
            ps.close();
            ps=null;
        }
        if(rs!=null){
            rs.close();
            rs=null;
        }
    }
    protected abstract Object read()throws SQLException,ClassNotFoundException;
    protected boolean comprovarDadesConsulta(HashMap<String,Object> dades,HashMap<String,Integer>definicio){
       Iterator<Map.Entry<String, Object>> iterador = dades.entrySet().iterator();
       Map.Entry<String, Object> entry;
       Object valor;
       String clau;
       boolean comprovat = true;
        while (iterador.hasNext() && comprovat){
            entry = iterador.next();
            clau=entry.getKey();
            valor=entry.getValue();
            switch(definicio.get(clau)){
                case Types.INTEGER:
                    comprovat = valor instanceof Integer;
                    break;
                case Types.VARCHAR:
                    comprovat = valor instanceof String;
                    break;
                case Types.BOOLEAN:
                    comprovat = valor instanceof Boolean;
                    break;
                case Types.ARRAY: 
                    comprovat = valor instanceof String[] || valor instanceof Integer[];
                    break;
            }
        }
        return comprovat;
    }
}
