package com.hkt.app.dao;

import com.hkt.app.model.Product;
import com.hkt.app.database.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class ProductDAO {

    public static boolean insertProduct(Product product) {
        String sql = "INSERT INTO products (id, name, price, quantity, unit_id, category_id, status) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {


            pstmt.setString(1, product.getId());
            pstmt.setString(2, product.getName());
            pstmt.setDouble(3, product.getPrice());
            pstmt.setInt(4, product.getQuantity());
            pstmt.setInt(5, product.getUnitId());
            pstmt.setInt(6, product.getCategoryId());
            pstmt.setString(7, product.getStatus());

            int affectedRows = pstmt.executeUpdate();

            System.out.println("DEBUG: Số dòng bị ảnh hưởng khi insert: " + affectedRows);

            return affectedRows > 0;

        } catch (SQLException e) {
            System.out.println("DEBUG: Lỗi SQL khi insert sản phẩm:");
            e.printStackTrace();
            return false;
        }
    }

    public static boolean updateProduct(Product product) {
        String sql = "UPDATE products SET name = ?, price = ?, quantity = ?, unit_id = ?, category_id = ?, status = ? " +
                "WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {


            pstmt.setString(1, product.getName());
            pstmt.setDouble(2, product.getPrice());
            pstmt.setInt(3, product.getQuantity());
            pstmt.setInt(4, product.getUnitId());
            pstmt.setInt(5, product.getCategoryId());
            pstmt.setString(6, product.getStatus());
            pstmt.setString(7, product.getId());

            int affectedRows = pstmt.executeUpdate();


            return affectedRows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean isProductIdExists(String id) {
        // Ví dụ giả định bạn dùng JDBC
        String sql = "SELECT COUNT(*) FROM products WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                int count = rs.getInt(1);
                return count > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
