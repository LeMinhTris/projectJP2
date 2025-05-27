package com.hkt.app.controller;

import com.hkt.app.model.Product;
import com.hkt.app.model.ProductData;
import com.hkt.app.dao.UnitDAO;
import com.hkt.app.dao.CategoryDAO;
import com.hkt.app.model.Unit;
import com.hkt.app.model.Category;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class AddProductController implements Initializable {

    @FXML
    private Button btnCreate;

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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Load dữ liệu vào cbUnit
        List<Unit> units = UnitDAO.getAllUnits();
        ObservableList<String> unitNames = FXCollections.observableArrayList();
        for (Unit u : units) {
            unitNames.add(u.getName());
        }
        cbUnit.setItems(unitNames);

        // Load dữ liệu vào cbCategory
        List<Category> categories = CategoryDAO.getAllCategories();
        ObservableList<String> categoryNames = FXCollections.observableArrayList();
        for (Category c : categories) {
            categoryNames.add(c.getName());
        }
        cbCategory.setItems(categoryNames);

        // Dữ liệu sẵn cho cbStatus
        cbStatus.setItems(FXCollections.observableArrayList("Còn hàng", "Hết hàng"));
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

    @FXML
    void changToDashboard(ActionEvent event) throws IOException {
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
    void changToProduct(ActionEvent event) throws IOException {
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

    public void setProduct(Product product) {
        tfId.setText(product.getId());
        tfName.setText(product.getName());
        tfPrice.setText(String.valueOf(product.getPrice()));
        cbUnit.setValue(product.getUnitName());
        cbCategory.setValue(product.getCategoryName());
        tfQuantity.setText(String.valueOf(product.getQuantity()));
        cbStatus.setValue(product.getStatus());
    }

    @FXML
    private void handleCreateProduct(ActionEvent event) {
        try {
            String id = tfId.getText().trim();
            String name = tfName.getText().trim();
            double price = Double.parseDouble(tfPrice.getText().trim());
            String unit = cbUnit.getValue();
            int quantity = Integer.parseInt(tfQuantity.getText().trim());
            String category = cbCategory.getValue();
            String status = cbStatus.getValue();

            if (id.isEmpty() || name.isEmpty() || unit == null || category == null || status == null) {
                System.out.println("Vui lòng nhập đầy đủ thông tin.");
                return;
            }

            Product newProduct = new Product(id, name, price, unit, quantity, category, status);
            ProductData.addProduct(newProduct);
            System.out.println("Tạo sản phẩm thành công: " + newProduct);
            resetForm();

        } catch (NumberFormatException e) {
            System.out.println("Giá tiền và số lượng phải là số hợp lệ.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void resetForm() {
        tfId.clear();
        tfName.clear();
        tfPrice.clear();
        tfQuantity.clear();
        cbUnit.setValue(null);
        cbCategory.setValue(null);
        cbStatus.setValue(null);
    }
}
