package com.hkt.app.model;

public class Product {
    // Fields
    private String id;
    private String name;
    private double price;
    private int unitId;             // ID của đơn vị (unit)
    private String unitName;        // Tên đơn vị
    private int quantity;
    private int categoryId;         // ID của danh mục (category)
    private String categoryName;    // Tên danh mục
    private String status;
    private String imageUrl;

    // Constructor mặc định
    public Product() {}

    // ✅ Constructor đầy đủ: dùng khi load từ DB (có cả ID & Name của unit/category)
    public Product(String id, String name, double price, int unitId, String unitName, int quantity, int categoryId, String categoryName, String status) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.unitId = unitId;
        this.unitName = unitName;
        this.quantity = quantity;
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.status = status;
    }

    // ✅ Constructor cũ để tương thích nếu cần
    public Product(String id, String name, double price, String unitName, int quantity, String categoryName, String status) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.unitName = unitName;
        this.quantity = quantity;
        this.categoryName = categoryName;
        this.status = status;
    }

    // GETTER
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int getUnitId() {
        return unitId;
    }

    public String getUnitName() {
        return unitName;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public String getStatus() {
        return status;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getStatusDescription() {
        return "1".equals(this.status) ? "còn hàng" : "hết hàng";
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

    public void setUnitName(String unitName) {
        this.unitName = unitName;
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

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
