package com.hkt.app.database;

import java.sql.Connection;

public class TestConnection {
    public static void main(String[] args) {
        Connection conn = DBConnection.getConnection();
        if (conn != null) {
            System.out.println("✅Connected successfully to the database!");
        } else {
            System.out.println("❌ Failed to connect to the database.");
        }
    }
}