package com.hkt.app.database;

import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

public class DBConnection {

    public static Connection getConnection() {
        Connection conn = null;

        try {
            // Load file cấu hình db.properties trong resources
            Properties prop = new Properties();
            InputStream input = DBConnection.class.getClassLoader().getResourceAsStream("db.properties");
            if (input == null) {
                throw new RuntimeException("❌ Không tìm thấy file db.properties trong resources!");
            }
            prop.load(input);

            String host = prop.getProperty("db.host");
            String port = prop.getProperty("db.port");
            String dbName = prop.getProperty("db.name");
            String user = prop.getProperty("db.user");
            String password = prop.getProperty("db.password");

            String url = String.format(
                    "jdbc:sqlserver://%s:%s;databaseName=%s;encrypt=true;trustServerCertificate=true",
                    host, port, dbName
            );

            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            conn = DriverManager.getConnection(url, user, password);
            System.out.println("✅ Đã kết nối thành công đến cơ sở dữ liệu: " + dbName);

        } catch (Exception e) {
            System.err.println("❌ Lỗi khi kết nối CSDL:");
            e.printStackTrace();
        }

        return conn;
    }
}
