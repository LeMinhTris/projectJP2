package com.hkt.app.model;

import java.util.ArrayList;
import java.util.List;

public class ProductData {
    private static List<Product> productList = new ArrayList<>();

    public static void addProduct(Product product) {
        productList.add(product);
    }

    public static List<Product> getProductList() {
        return productList;
    }
}
