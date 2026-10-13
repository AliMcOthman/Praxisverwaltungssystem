package de.praxis.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static java.sql.DriverManager.getConnection;

public class DatenBankConnection {
    public static  Connection getConnention() throws SQLException {
        String url= "jdbc:mysql://localhost:3306/praxis_db?createDatabaseIfNotExist=true";
        String user="root";
        String passwort="";

        return DriverManager.getConnection(url,user,passwort);
    }
}
