package org.example.shop.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnection {
    private static final String URL = "jdbc:postgresql://localhost:5432/shop";
    private static final String USER = "postgres";
    private static final String PASSWORD = "postgres";
    private static Connection connection;

    public static Connection getConnection() {
        if (connection == null) {
            try {
                Class.forName("org.postgresql.Driver");  //  драйвер
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
            } catch (SQLException e) {
                System.out.println("Connection failed: " + e.getMessage());
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                System.out.println("Driver class not found: " + e.getMessage());
                e.printStackTrace();
            }
        }
        return connection;
    }
}
