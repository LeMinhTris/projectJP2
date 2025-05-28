package com.hkt.app.dao;

import com.hkt.app.database.DBConnection;
import com.hkt.app.model.Unit;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UnitDAO {

    public static List<Unit> getAllUnits() {
        List<Unit> units = new ArrayList<>();
        System.out.println("DEBUG: Bắt đầu lấy tất cả units từ DB");
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT id, name FROM units")) {

            while (rs.next()) {
                String id = rs.getString("id");
                String name = rs.getString("name");
                System.out.println("DEBUG: Lấy unit - ID: " + id + ", Name: " + name);
                units.add(new Unit(id, name));
            }

        } catch (SQLException e) {
            System.out.println("DEBUG: Lỗi khi lấy danh sách units");
            e.printStackTrace();
        }
        System.out.println("DEBUG: Tổng số unit lấy được: " + units.size());
        return units;
    }

    public static String getUnitNameById(int id) {
        String name = null;
        String sql = "SELECT name FROM units WHERE id = ?";
        System.out.println("DEBUG: Truy vấn tên unit với ID = " + id);
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);  // đúng rồi, id là int
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    name = rs.getString("name");
                    System.out.println("DEBUG: Tên unit tìm được: " + name);
                } else {
                    System.out.println("DEBUG: Không tìm thấy unit với ID = " + id);
                }
            }
        } catch (SQLException e) {
            System.out.println("DEBUG: Lỗi SQL khi truy vấn unit name theo ID");
            e.printStackTrace();
        }
        return name;
    }


    public static int getUnitIdByName(String unitName) {
        int id = -1;
        String sql = "SELECT id FROM units WHERE name = ?";
        System.out.println("DEBUG: Đang truy vấn unitName = '" + unitName + "'");
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, unitName.trim()); // trim bỏ khoảng trắng thừa
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    id = rs.getInt("id");
                    System.out.println("DEBUG: Tìm thấy ID = " + id);
                } else {
                    System.out.println("DEBUG: Không tìm thấy unitName trong DB");
                }
            }
        } catch (SQLException e) {
            System.out.println("DEBUG: Lỗi SQL khi truy vấn unit name");
            e.printStackTrace();
        }
        return id;
    }
    public static ObservableList<String> getUnitNamesFromDB() {
        ObservableList<String> unitNames = FXCollections.observableArrayList();
        String sql = "SELECT name FROM units";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                unitNames.add(rs.getString("name"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return unitNames;
    }

}
