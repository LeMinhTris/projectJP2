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

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

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
    private ImageView btnChatBot;

    @FXML
    private TextField userInput;

    @FXML
    private VBox chatBox;

    @FXML
    private ScrollPane chatScroll;

    @FXML
    private ImageView closeChat;

    @FXML
    private AnchorPane khungChat;

    @FXML
    private Button btnReset;
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
    private Product originalProduct;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("=-------------Selected value in cbUnit: " + cbUnit.getValue());

        List<Unit> units = UnitDAO.getAllUnits();
        ObservableList<String> unitNames = FXCollections.observableArrayList();
        for (Unit u : units) {
            unitNames.add(u.getName());
        }
        cbUnit.setItems(unitNames);

        List<Category> categories = CategoryDAO.getAllCategories();
        ObservableList<String> categoryNames = FXCollections.observableArrayList();
        for (Category c : categories) {
            categoryNames.add(c.getName());
        }
        cbCategory.setItems(categoryNames);


        // Dữ liệu sẵn cho cbStatus
        cbStatus.setItems(FXCollections.observableArrayList("Còn hàng", "Hết hàng"));

        // chatbot
        // Auto scroll xuống cuối khi chatBox có thay đổi chiều cao
        chatBox.heightProperty().addListener((obs, oldVal, newVal) -> {
            chatScroll.setVvalue(1.0);
        });

        // Cho phép nhấn Enter trong TextField để gửi
        userInput.setOnAction(event -> handleUserInput());

        // Gợi ý tin nhắn ban đầu từ chatbot
        Platform.runLater(() -> {
            addBotMessage("Chào bạn! Tôi là trợ lý ảo của HKT Studio.");
            addBotMessage("Bạn muốn biết thông tin gì? Ví dụ: 'Giờ mở cửa', 'Sản phẩm bán chạy'...");
            addBotMessage("Ví dụ: 'Giờ mở cửa', 'Sản phẩm bán chạy'...");
        });
    }

    public void onClickChatBot(javafx.scene.input.MouseEvent mouseEvent) {
        khungChat.setVisible(true);
    }

    public void onClickCloseChat(javafx.scene.input.MouseEvent mouseEvent) {
        khungChat.setVisible(false);
    }

    @FXML
    private void handleUserInput() {
        String input = userInput.getText().trim();
        if (input.isEmpty()) return;

        // ====== TIN NHẮN NGƯỜI DÙNG ======
        Label userLabel = new Label(input);
        userLabel.setWrapText(true);
        userLabel.setStyle("-fx-background-color: lightgray; -fx-padding: 8; -fx-background-radius: 10; -fx-text-fill: black;");

        HBox userHBox = new HBox(userLabel);
        userHBox.setStyle("-fx-alignment: CENTER_RIGHT; -fx-padding: 5;");
        userHBox.setPrefWidth(chatScroll.getWidth() - 20); // Đảm bảo tin nhắn đẩy về phải
        chatBox.getChildren().add(userHBox);

        // ====== PHẢN HỒI CHATBOT ======
        String response = getBotResponse(input);

        Label botLabel = new Label(response);
        botLabel.setWrapText(true); // Tự động xuống dòng nếu quá dài
        botLabel.setStyle("-fx-background-color: #4471C4; -fx-padding: 8; -fx-background-radius: 10; -fx-text-fill: white;");

        HBox botHBox = new HBox(botLabel);
        botHBox.setStyle("-fx-alignment: CENTER_LEFT; -fx-padding: 5;");
        botHBox.setPrefWidth(chatScroll.getWidth() - 20);
        chatBox.getChildren().add(botHBox);

        userInput.clear();
    }


    private String getBotResponse(String input) {
        input = input.toLowerCase();
        if (input.contains("hello") || input.contains("xin chào")) {
            return "Chào bạn! Tôi có thể giúp gì?";
        } else if (input.contains("giờ mở cửa")) {
            return "Cửa hàng mở từ 8h sáng đến 10h tối mỗi ngày.";
        } else if (input.contains("cảm ơn")) {
            return "Không có chi!";
        }
        return "Tôi chưa hiểu. Bạn có thể nói lại rõ hơn không?";

        cbStatus.setItems(FXCollections.observableArrayList("Available", "Out of stock"));

    }

    @FXML
    void changToAdd(ActionEvent event) throws IOException {
        changeScene(event, "/view/addProduct.fxml");
    }

    @FXML
    void changToDashboard(ActionEvent event) throws IOException {
        changeScene(event, "/view/dashboard.fxml");
    }

    @FXML
    void changToProduct(ActionEvent event) throws IOException {
        changeScene(event, "/view/product.fxml");
    }

    private void changeScene(ActionEvent event, String fxmlPath) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxmlPath));
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
    void changToLogin(ActionEvent event) throws IOException {
        Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmAlert.setTitle("Confirm Log Out");
        confirmAlert.setHeaderText("Are you certain want to log out?");
        confirmAlert.setContentText("Press OK to log out, Cancel to stay");

        // Hiển thị hộp thoại và đợi phản hồi
        ButtonType result = confirmAlert.showAndWait().orElse(ButtonType.CANCEL);

        if (result == ButtonType.OK) {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/login.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setWidth(1000);
            stage.setHeight(600);
            stage.setResizable(false);
            stage.setTitle("Convenient Store Management");
            stage.setScene(scene);
            stage.show();
        }
    }


    public void setProduct(Product product) {
        tfId.setText(product.getId());
        tfName.setText(product.getName());
        tfPrice.setText(String.valueOf(product.getPrice()));
        tfQuantity.setText(String.valueOf(product.getQuantity()));
        tfId.setDisable(true);

        List<Unit> units = UnitDAO.getAllUnits();
        ObservableList<String> unitNames = FXCollections.observableArrayList();
        units.forEach(u -> unitNames.add(u.getName()));
        cbUnit.setItems(unitNames);

        List<Category> categories = CategoryDAO.getAllCategories();
        ObservableList<String> categoryNames = FXCollections.observableArrayList();
        categories.forEach(c -> categoryNames.add(c.getName()));
        cbCategory.setItems(categoryNames);

        String unitName = UnitDAO.getUnitNameById(product.getUnitId());
        String categoryName = CategoryDAO.getCategoryNameById(product.getCategoryId());

        if (unitName != null) {
            cbUnit.getSelectionModel().select(unitName);
        }
        if (categoryName != null) {
            cbCategory.getSelectionModel().select(categoryName);
        }

        cbStatus.setValue("1".equals(product.getStatus()) ? "Available" : "Out of stock");

        this.originalProduct = new Product(
                product.getId(),
                product.getName(),
                product.getPrice(),
                product.getUnitId(),
                unitName,
                product.getQuantity(),
                product.getCategoryId(),
                categoryName,
                product.getStatus()
        );
    }

    @FXML
    private void handleCreateProduct(ActionEvent event) {
        try {
            String id = tfId.getText().trim();
            String name = tfName.getText().trim();
            String priceText = tfPrice.getText().trim();
            String quantityText = tfQuantity.getText().trim();
            String unitName = cbUnit.getValue();
            String categoryName = cbCategory.getValue();
            String statusText = cbStatus.getValue();

            if (id.isEmpty() || name.isEmpty() || priceText.isEmpty() || quantityText.isEmpty()
                    || unitName == null || categoryName == null || statusText == null) {
                showAlert(Alert.AlertType.WARNING, "Input Error", null, "Please fill in all the fields.");
                return;
            }

            double price;
            try {
                price = Double.parseDouble(priceText);
                if (price < 0) {
                    showAlert(Alert.AlertType.WARNING, "Input Error", null, "Price must be a positive number.");
                    return;
                }
            } catch (NumberFormatException e) {
                showAlert(Alert.AlertType.WARNING, "Input Error", null, "Price must be a valid number.");
                return;
            }

            int quantity;
            try {
                quantity = Integer.parseInt(quantityText);
                if (quantity < 0) {
                    showAlert(Alert.AlertType.WARNING, "Input Error", null, "Quantity must be a positive integer.");
                    return;
                }
            } catch (NumberFormatException e) {
                showAlert(Alert.AlertType.WARNING, "Input Error", null, "Quantity must be a valid integer.");
                return;
            }

            if (ProductDAO.isProductIdExists(id)) {
                showAlert(Alert.AlertType.WARNING, "Input Error", null, "Product ID already exists. Please use a different ID.");
                return;
            }

            String status = "Out of stock".equals(statusText) ? "0" : "1";

            int unitId = UnitDAO.getUnitIdByName(unitName);
            int categoryId = CategoryDAO.getCategoryIdByName(categoryName);

            if (unitId == -1) {
                showAlert(Alert.AlertType.WARNING, "Input Error", null, "Invalid unit.");
                return;
            }
            if (categoryId == -1) {
                showAlert(Alert.AlertType.WARNING, "Input Error", null, "Invalid category.");
                return;
            }

            Product newProduct = new Product(id, name, price, unitId, unitName, quantity, categoryId, categoryName, status);
            boolean success = ProductDAO.insertProduct(newProduct);

            if (success) {
                showAlert(Alert.AlertType.INFORMATION, "Success", null, "Product added successfully!");
                changToProduct(event);
            } else {
                showAlert(Alert.AlertType.ERROR, "Failure", null, "Failed to add product. Please try again.");
            }

        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "System Error", null, "A system error occurred. Please try again.");
        }
    }

    private void showAlert(Alert.AlertType type, String title, String header, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    @FXML
    private void handleUpdateProduct(ActionEvent event) {
        try {
            String id = tfId.getText().trim();
            String name = tfName.getText().trim();
            double price = Double.parseDouble(tfPrice.getText().trim());
            String unitName = cbUnit.getValue();
            int quantity = Integer.parseInt(tfQuantity.getText().trim());
            String categoryName = cbCategory.getValue();
            String statusText = cbStatus.getValue();

            if (id.isEmpty() || name.isEmpty() || unitName == null || categoryName == null || statusText == null) {
                System.out.println("Please fill in all the fields.");
                return;
            }

            String status = "Out of stock".equals(statusText) ? "0" : "1";

            int unitId = UnitDAO.getUnitIdByName(unitName);
            if (unitId == -1) {
                System.out.println("Invalid unit.");
                return;
            }

            int categoryId = CategoryDAO.getCategoryIdByName(categoryName);
            if (categoryId == -1) {
                System.out.println("Invalid category.");
                return;
            }

            Product updatedProduct = new Product(id, name, price, unitId, unitName, quantity, categoryId, categoryName, status);
            boolean success = ProductDAO.updateProduct(updatedProduct);

            Alert alert;
            if (success) {
                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setContentText("Product updated successfully!");
            } else {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Failure");
                alert.setContentText("Failed to update product. Please try again.");
            }
            alert.setHeaderText(null);
            alert.showAndWait();
            changToProduct(event);

        } catch (NumberFormatException e) {
            System.out.println("Price and quantity must be valid numbers.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleResetForm() {
        if (originalProduct != null) {
            tfId.setText(originalProduct.getId());
            tfName.setText(originalProduct.getName());
            tfPrice.setText(String.valueOf(originalProduct.getPrice()));
            tfQuantity.setText(String.valueOf(originalProduct.getQuantity()));
            cbUnit.getSelectionModel().select(originalProduct.getUnitName());
            cbCategory.getSelectionModel().select(originalProduct.getCategoryName());
            cbStatus.setValue("1".equals(originalProduct.getStatus()) ? "Available" : "Out of stock");
        }
    }

    @FXML
    private void handleResetFormClear() {
        tfId.clear();
        tfName.clear();
        tfPrice.clear();
        tfQuantity.clear();

        ObservableList<String> unitItems = cbUnit.getItems();
        cbUnit.setItems(null);
        cbUnit.setItems(unitItems);
        cbUnit.getSelectionModel().clearSelection();
        cbUnit.setValue(null);

        ObservableList<String> categoryItems = cbCategory.getItems();
        cbCategory.setItems(null);
        cbCategory.setItems(categoryItems);
        cbCategory.getSelectionModel().clearSelection();
        cbCategory.setValue(null);

        ObservableList<String> statusItems = cbStatus.getItems();
        cbStatus.setItems(null);
        cbStatus.setItems(statusItems);
        cbStatus.getSelectionModel().clearSelection();
        cbStatus.setValue(null);
    }

    private void addBotMessage(String message) {
        Label botLabel = new Label(message);
        botLabel.setStyle("-fx-background-color: #4471C4; -fx-padding: 8; -fx-background-radius: 10; -fx-text-fill: white;");

        HBox botHBox = new HBox(botLabel);
        botHBox.setStyle("-fx-alignment: CENTER_LEFT; -fx-padding: 5;");
        botHBox.setPrefWidth(chatScroll.getWidth() - 20);

        chatBox.getChildren().add(botHBox);
    }
}
