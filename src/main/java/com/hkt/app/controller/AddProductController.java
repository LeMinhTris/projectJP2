package com.hkt.app.controller;

import com.hkt.app.model.Product;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;

public class AddProductController {

    @FXML
    private ImageView avatarImage1;

    @FXML
    private ComboBox<String> cbCategory;

    @FXML
    private ComboBox<String> cbStatus;

    @FXML
    private ComboBox<String> cbUnit;

    @FXML
    private TextField tfId;

    @FXML
    private TextField tfName;

    @FXML
    private TextField tfPrice;

    @FXML
    private TextField tfQuantity;

    @FXML
    void changToAdd(ActionEvent event) throws IOException {
        // Load giao diện từ FXMLs
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/addProduct.fxml"));
        Parent root = fxmlLoader.load();

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        // Tạo Scene từ root
        Scene scene = new Scene(root);

        stage.setWidth(1220);
        stage.setHeight(660);
        stage.setResizable(false);

        // Cài đặt title và scene
        stage.setTitle("Convenient Store Management");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void changToDashboard(ActionEvent event) throws IOException {
        // Load giao diện từ FXMLs
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/dashboard.fxml"));
        Parent root = fxmlLoader.load();

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        // Tạo Scene từ root
        Scene scene = new Scene(root);

        stage.setWidth(1220);
        stage.setHeight(660);
        stage.setResizable(false);

        // Cài đặt title và scene
        stage.setTitle("Convenient Store Management");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void changToProduct(ActionEvent event) throws IOException {
        // Load giao diện từ FXMLs
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/product.fxml"));
        Parent root = fxmlLoader.load();

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        // Tạo Scene từ root
        Scene scene = new Scene(root);

        stage.setWidth(1220);
        stage.setHeight(660);
        stage.setResizable(false);

        // Cài đặt title và scene
        stage.setTitle("Convenient Store Management");
        stage.setScene(scene);
        stage.show();
    }

    public void setProduct(Product product) {
        // Gán dữ liệu vào các TextField, ComboBox,...
        tfId.setText(product.getId());
        tfName.setText(product.getName());
        tfPrice.setText(String.valueOf(product.getPrice()));
        cbUnit.setValue(product.getUnitName());
        cbCategory.setValue(product.getCategoryName());
        tfQuantity.setText(String.valueOf(product.getQuantity()));
        cbStatus.setValue(product.getStatus());
    }

}
