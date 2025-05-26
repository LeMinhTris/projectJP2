package com.hkt.app.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class DashboardController implements Initializable {

    @FXML
    private ImageView avatarImage1;

    @FXML
    private Button btnAdd;

    @FXML
    private Button btnProducts;

    @FXML
    private TableColumn<?, ?> colFullname;

    @FXML
    private TableColumn<?, ?> colNO;

    @FXML
    private TableColumn<?, ?> colPhone;

    @FXML
    private AnchorPane tvCustomer;

    @FXML
    public void changToProduct(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/product.fxml"));
        Parent root = fxmlLoader.load();

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        Scene scene = new Scene(root);
        stage.setWidth(1220);
        stage.setHeight(660);
        stage.setResizable(false);

        stage.setTitle("Convenient Store Management");
        stage.setScene(scene);
        stage.show();
    }

    public void changToDashboard(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/dashboard.fxml"));
        Parent root = fxmlLoader.load();

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        Scene scene = new Scene(root);
        stage.setWidth(1220);
        stage.setHeight(660);
        stage.setResizable(false);

        stage.setTitle("Convenient Store Management");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void changToAdd(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/addProduct.fxml"));
        Parent root = fxmlLoader.load();

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        Scene scene = new Scene(root);
        stage.setWidth(1220);
        stage.setHeight(660);
        stage.setResizable(false);

        stage.setTitle("Convenient Store Management");
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }
}
