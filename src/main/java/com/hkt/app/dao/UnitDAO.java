// UnitDAO.java
package com.hkt.app.dao;
import com.hkt.app.database.DBConnection;

import com.hkt.app.model.Unit;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UnitDAO {
    public static List<Unit> getAllUnits() {
        List<Unit> units = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT id, name FROM units")) {

            while (rs.next()) {
                units.add(new Unit(rs.getString("id"), rs.getString("name")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return units;
    }
}
