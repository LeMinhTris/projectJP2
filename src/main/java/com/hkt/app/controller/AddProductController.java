package com.hkt.app.controller;
import javafx.application.Platform;
import com.hkt.app.model.Product;
import com.hkt.app.dao.UnitDAO;
import com.hkt.app.dao.CategoryDAO;
import com.hkt.app.dao.ProductDAO;
import com.hkt.app.model.Unit;
import com.hkt.app.model.Category;
import javafx.scene.control.Alert;
import java.util.stream.Collectors;

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
        // Load dữ liệu đơn vị
        List<Unit> units = UnitDAO.getAllUnits();
        ObservableList<String> unitNames = FXCollections.observableArrayList();
        for (Unit u : units) {
            unitNames.add(u.getName());
        }
        cbUnit.setItems(unitNames);

        // Load dữ liệu danh mục
        List<Category> categories = CategoryDAO.getAllCategories();
        ObservableList<String> categoryNames = FXCollections.observableArrayList();
        for (Category c : categories) {
            categoryNames.add(c.getName());
        }
        cbCategory.setItems(categoryNames);

        // Load trạng thái
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
        tfQuantity.setText(String.valueOf(product.getQuantity()));
        tfId.setDisable(true);

        // Load danh sách tên đơn vị
        List<Unit> units = UnitDAO.getAllUnits();
        ObservableList<String> unitNames = FXCollections.observableArrayList();
        for (Unit u : units) {
            unitNames.add(u.getName());
        }
        cbUnit.setItems(unitNames);

        // Load danh sách tên danh mục
        List<Category> categories = CategoryDAO.getAllCategories();
        ObservableList<String> categoryNames = FXCollections.observableArrayList();
        for (Category c : categories) {
            categoryNames.add(c.getName());
        }
        cbCategory.setItems(categoryNames);

        // Lấy tên đơn vị và danh mục từ id
        String unitName = UnitDAO.getUnitNameById(product.getUnitId());
        String categoryName = CategoryDAO.getCategoryNameById(product.getCategoryId());

        // Set giá trị tương ứng trong dropdown
        if (unitName != null && unitNames.contains(unitName)) {
            cbUnit.setValue(unitName);
        }

        if (categoryName != null && categoryNames.contains(categoryName)) {
            cbCategory.setValue(categoryName);
        }

        // Set trạng thái
        cbStatus.setValue("1".equals(product.getStatus()) ? "Còn hàng" : "Hết hàng");
    }







    @FXML
    private void handleCreateProduct(ActionEvent event) {
        try {
            String id = tfId.getText().trim();
            String name = tfName.getText().trim();
            double price = Double.parseDouble(tfPrice.getText().trim());
            String unitName = cbUnit.getValue();
            int quantity = Integer.parseInt(tfQuantity.getText().trim());
            String categoryName = cbCategory.getValue();
            String statusText = cbStatus.getValue();

            if (id.isEmpty() || name.isEmpty() || unitName == null || categoryName == null || statusText == null) {
                System.out.println("Vui lòng nhập đầy đủ thông tin.");
                return;
            }

            String status = "Hết hàng".equals(statusText) ? "0" : "1";
            int unitId = UnitDAO.getUnitIdByName(unitName);
            int categoryId = CategoryDAO.getCategoryIdByName(categoryName);

            // Tạo đối tượng Product với đầy đủ trường
            Product newProduct = new Product(id, name, price, unitId, quantity, categoryId, categoryName, status);

            // Gọi DAO để lưu
            boolean success = ProductDAO.insertProduct(newProduct);

            if (success) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Thành công");
                alert.setHeaderText(null);
                alert.setContentText("Sản phẩm đã được thêm thành công!");
                alert.showAndWait();

                changToProduct(event);
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Thất bại");
                alert.setHeaderText(null);
                alert.setContentText("Không thể thêm sản phẩm. Vui lòng thử lại!");
                alert.showAndWait();
            }


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
    @FXML
    private void handleUpdateProduct(ActionEvent event) {
        try {
            String id = tfId.getText().trim();
            String name = tfName.getText().trim();
            double price = Double.parseDouble(tfPrice.getText().trim());
            String unitName = cbUnit.getValue();  // Lấy tên đơn vị
            int quantity = Integer.parseInt(tfQuantity.getText().trim());
            String categoryName = cbCategory.getValue();
            String statusText = cbStatus.getValue();
            if (id.isEmpty() || name.isEmpty() || unitName == null || categoryName == null || statusText == null) {
                System.out.println("Vui lòng nhập đầy đủ thông tin.");
                return;
            }

            // Chuyển trạng thái sang giá trị trong DB (0 hoặc 1)
            String status = "Hết hàng".equals(statusText) ? "0" : "1";

            // Lấy id của đơn vị dựa trên tên
            int unitId = UnitDAO.getUnitIdByName(unitName);
            if (unitId == -1) {
                System.out.println("Đơn vị không hợp lệ.");
                return;
            }

            // Lấy id của category dựa trên tên
            int categoryId = CategoryDAO.getCategoryIdByName(categoryName);
            if (categoryId == -1) {
                System.out.println("Danh mục không hợp lệ.");
                return;
            }

            // Tạo đối tượng Product với unitId, categoryId
            Product updatedProduct = new Product(id, name, price, unitId, quantity, categoryId, categoryName, status);

            // Gọi hàm cập nhật
            boolean success = ProductDAO.updateProduct(updatedProduct);

            if (success) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Thành công");
                alert.setHeaderText(null);
                alert.setContentText("Sản phẩm đã được thêm thành công!");
                alert.showAndWait();

                changToProduct(event);
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Thất bại");
                alert.setHeaderText(null);
                alert.setContentText("Không thể thêm sản phẩm. Vui lòng thử lại!");
                alert.showAndWait();
            }

        } catch (NumberFormatException e) {
            System.out.println("Giá tiền và số lượng phải là số hợp lệ.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
