package com.hkt.app.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class DashboardController {

    @FXML
    private ImageView avatarImage1;

    @FXML
    private TableColumn<?, ?> colFullname;

    @FXML
    private TableColumn<?, ?> colNO;

    @FXML
    private TableColumn<?, ?> colPhone;

    @FXML
    private AnchorPane tvCustomer;

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

}
