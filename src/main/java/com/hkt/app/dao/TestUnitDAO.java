package com.hkt.app.dao;

import com.hkt.app.model.Unit;
import java.util.List;

public class TestUnitDAO {
    public static void main(String[] args) {
        // Test getAllUnits()
        List<Unit> units = UnitDAO.getAllUnits();
        System.out.println("Danh sách tất cả đơn vị:");
        for (Unit u : units) {
            System.out.println("ID: " + u.getId() + ", Name: " + u.getName());
        }

        // Test getUnitNameById(int id)
        int testId = 1; // Thay id này bằng id bạn muốn test
        String unitName = UnitDAO.getUnitNameById(testId);
        System.out.println("Tên đơn vị với ID = " + testId + " là: " + unitName);

        // Test getUnitIdByName(String name)
        String testName = "kg"; // Thay tên đơn vị bạn muốn test
        int unitId = UnitDAO.getUnitIdByName(testName);
        System.out.println("ID của đơn vị '" + testName + "' là: " + unitId);
    }
}
