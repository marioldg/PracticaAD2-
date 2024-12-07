package org.example.conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class Conexion {
    private String url = "jdbc:mysql://localhost:3306/appdatabase";
    private String user = "root";
    private String password = "root";
    private String driver = "com.mysql.cj.jdbc.Driver";
    private Connection connection;
    public Statement statement;

    public Conexion() {
        try {
            Class.forName(driver);
            connection = DriverManager.getConnection(url, user, password);
            statement = connection.createStatement();
            // Realiza las operaciones SQL aquí, ya sea un Query inicial o cualquier otra operación.
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Statement getStatement() {
        return statement;
    }

    public void cerrar() {
        try {
            if (statement != null) {
                statement.close();
            }
            if (connection != null) {
                connection.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
