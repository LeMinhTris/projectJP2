package com.hkt.app.database;

import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

public class DBConnection {
    private static Connection conn = null;

    public static Connection getConnection() {
        if (conn != null) return conn;

        try {
            // we assume db.properties is in the resources folder
            Properties prop = new Properties();
            InputStream input = DBConnection.class.getClassLoader().getResourceAsStream("db.properties");
            if (input == null) {
                throw new RuntimeException("not found db.properties file in resources");
            }
            prop.load(input);

            String host = prop.getProperty("db.host");
            String port = prop.getProperty("db.port");
            String dbName = prop.getProperty("db.name");
            String user = prop.getProperty("db.user");
            String password = prop.getProperty("db.password");

            String url = String.format("jdbc:sqlserver://%s:%s;databaseName=%s;encrypt=true;trustServerCertificate=true", host, port, dbName);

            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            conn = DriverManager.getConnection(url, user, password);
            System.out.println("✅ Successful connection to database: " + dbName);

        } catch (Exception e) {
            System.err.println("❌ Unable to connect to Database:");
            e.printStackTrace();
        }

        return conn;
    }
}
