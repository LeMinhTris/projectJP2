package com.hkt.app.dao;

import com.hkt.app.model.Product;

public class TestProductDAO {
    public static void main(String[] args) {
        Product p = new Product();
        p.setId("p102");  // ID khác để tránh trùng
        p.setName("Sản phẩm test mới");
        p.setPrice(100.5);
        p.setQuantity(10);
        p.setUnitId(3);
        p.setCategoryId(2);
        p.setStatus("1");

        System.out.println("Thử insert sản phẩm mới:");
        boolean insertResult = ProductDAO.insertProduct(p);
        System.out.println("Insert thành công? " + insertResult);

        System.out.println("Thử cập nhật sản phẩm:");
        p.setPrice(120.0);
        boolean updateResult = ProductDAO.updateProduct(p);
        System.out.println("Update thành công? " + updateResult);
    }
}

