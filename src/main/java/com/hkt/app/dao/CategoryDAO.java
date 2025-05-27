// CategoryDAO.java
package com.hkt.app.dao;

import com.hkt.app.model.Category;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import com.hkt.app.database.DBConnection;

public class CategoryDAO {
    public static List<Category> getAllCategories() {
        List<Category> categories = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT id, name FROM categories")) {

            while (rs.next()) {
                String id = rs.getString("id");
                String name = rs.getString("name");
                System.out.println("Loaded category: id=" + id + ", name=" + name); // <-- debug
                categories.add(new Category(id, name));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("Total categories loaded: " + categories.size()); // <-- debug
        return categories;
    }

}
