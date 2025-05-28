package com.hkt.app.controller;

import com.hkt.app.model.Product;
import com.hkt.app.model.ProductData;
import com.hkt.app.dao.UnitDAO;
import com.hkt.app.dao.CategoryDAO;
import com.hkt.app.model.Unit;
import com.hkt.app.model.Category;

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

    private void addBotMessage(String message) {
        Label botLabel = new Label(message);
        botLabel.setStyle("-fx-background-color: #4471C4; -fx-padding: 8; -fx-background-radius: 10; -fx-text-fill: white;");

        HBox botHBox = new HBox(botLabel);
        botHBox.setStyle("-fx-alignment: CENTER_LEFT; -fx-padding: 5;");
        botHBox.setPrefWidth(chatScroll.getWidth() - 20);

        chatBox.getChildren().add(botHBox);
    }
}
