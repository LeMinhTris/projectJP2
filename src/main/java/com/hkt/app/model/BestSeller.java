package com.hkt.app.model;

public class BestSeller {
    // field
    private String idBS;
    private String nameBS;
    private String categoryBS;
    private double priceBS;

    // constructor
    public BestSeller() {};

    public BestSeller(String idBS, String nameBS, String categoryBS, double priceBS) {
        this.idBS = idBS;
        this.nameBS = nameBS;
        this.categoryBS = categoryBS;
        this.priceBS = priceBS;
    }

    // GETTER
    public String getIdBS() {
        return idBS;
    }

    public String getNameBS() {
        return nameBS;
    }

    public String getCategoryBS() {
        return categoryBS;
    }

    public double getPriceBS() {
        return priceBS;
    }

    // SETTER
    public void setIdBS(String idBS) {
        this.idBS = idBS;
    }

    public void setNameBS(String nameBS) {
        this.nameBS = nameBS;
    }

    public void setCategoryBS(String categoryBS) {
        this.categoryBS = categoryBS;
    }

    public void setPriceBS(double priceBS) {
        this.priceBS = priceBS;
    }
}
