package com.hkt.app.database;

import io.github.cdimascio.dotenv.Dotenv;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DBConnection {
//    private static
    private static final Logger logger = Logger.getLogger(DBConnection.class.getName());

    public static Connection getConnection() {
        Connection conn = null;
        try {
            Dotenv dotenv = Dotenv.configure().ignoreIfMissing().load();

            String rawURL = dotenv.get("DB_URL");
            if (rawURL == null || rawURL.isBlank()) {
                throw new RuntimeException("❌ DB_URL not defined in .env file");
            }
            String host = dotenv.get("DB_HOST");
            String port = dotenv.get("DB_PORT");
            String dbName = dotenv.get("DB_NAME");
            String user = dotenv.get("DB_USER");
            String password = dotenv.get("DB_PASSWORD");
            String url = String.format(rawURL, host, port, dbName);
            conn = DriverManager.getConnection(url, user, password);
            logger.info("✅ Connected to Database: " + dbName);
        } catch (SQLException ex) {
            logger.log(Level.SEVERE, "❌ SQL error when connecting to database!", ex);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "❌Unspecified error while connecting to database!",e);
        }
        return conn;
    }
}
