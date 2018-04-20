package com.company.DAM2.Bibliorevolució.Connector;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectorBD {
    public static Connection connectar() {
        Connection c = null;
        try {
            c = DriverManager.getConnection("jdbc:mysql://172.16.201.11:3306/bibliorevolucio","root", "admin");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return c;
    }
}
