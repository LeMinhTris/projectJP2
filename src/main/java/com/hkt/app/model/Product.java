package com.hkt.app.model;

public class Product {
    private String unitName;  // phải có biến này

    public String getUnitName() {  // phải có getter này
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    // field
    private String id;
    private String name;
    private double price;
    private int unitId;             // dùng ID để liên kết với bảng Unit
    private int quantity;
    private int categoryId;         // Sửa: đổi từ String sang int
    private String categoryName;
    private String status;
    private String imageUrl;

    // Constructor mặc định
    public Product() {}

    // Constructor có 7 tham số (như bạn có trước đây)
    public Product(String id, String name, double price, String unitName, int quantity, String categoryName, String status) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.unitName = unitName;
        this.quantity = quantity;
        this.categoryName = categoryName;
        this.status = status;
    }

    // Constructor mới có 8 tham số (đúng với chỗ bạn gọi)
    public Product(String id, String name, double price, int unitId, int quantity, int categoryId, String categoryName, String status) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.unitId = unitId;
        this.quantity = quantity;
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.status = status;
    }

    // GETTER
    public String getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public double getPrice() {
        return this.price;
    }

    public int getUnitId() {
        return this.unitId;
    }

    public int getQuantity() {
        return this.quantity;
    }

    public int getCategoryId() {
        return this.categoryId;
    }

    public String getCategoryName() {
        return this.categoryName;
    }

    public String getStatus() {
        return this.status;
    }

    // Method mới để trả về mô tả trạng thái
    public String getStatusDescription() {
        if ("1".equals(this.status)) {
            return "còn hàng";
        } else {
            return "hết hàng";
        }
    }

    // SETTER
    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setUnitId(int unitId) {
        this.unitId = unitId;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
