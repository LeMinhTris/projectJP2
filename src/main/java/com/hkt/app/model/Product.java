package com.hkt.app.model;

public class Product {
    // field
    private String id;
    private String name;
    private double price;
    private String unitName;
    private int quantity;
    private String categoryName;
    private String status;
    private String imageUrl;

    // constructor
    public Product() {};

    // Constructor 7 tham số (bỏ qua imageUrl)
    public Product(
            String id,
            String name,
            double price,
            String unitName,
            int quantity,
            String categoryName,
            String status) {
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
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public double getPrice() {
        return this.price;
    }

    public String getUnitName() {
        return this.unitName;
    }

    public int getQuantity() {
        return this.quantity;
    }

    public String getCategoryName() {
        return this.categoryName;
    }

    public String getStatus() {
        return this.status;
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

    public void setUnit(String unitName) {
        this.unitName = unitName;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
