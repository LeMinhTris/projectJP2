package com.hkt.app.controller;

import com.hkt.app.model.BestSeller;
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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class DashboardController implements Initializable {


    @FXML
    private TableView<BestSeller> tvBestSeller;


    @FXML
    private TableColumn<BestSeller, String> colCategorySeller;


    @FXML
    private TableColumn<BestSeller, String> colIdSeller;


    @FXML
    private TableColumn<BestSeller, String> colNameSeller;


    @FXML
    private TableColumn<BestSeller, Double> colPriceSeller;


    @FXML
    private ImageView closeChat;


    @FXML
    private AnchorPane khungChat;


    @FXML
    private ImageView btnChatBot;


    @FXML
    private VBox chatBox;


    @FXML
    private TextField userInput;


    @FXML
    private ScrollPane chatScroll;


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


    public void onClickChatBot(javafx.scene.input.MouseEvent mouseEvent) {
        khungChat.setVisible(true);
    }


    public void onClickCloseChat(javafx.scene.input.MouseEvent mouseEvent) {
        khungChat.setVisible(false);
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
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

        // Thiết lập dữ liệu cho TableColumn
        colIdSeller.setCellValueFactory(new PropertyValueFactory<>("idBS"));
        colNameSeller.setCellValueFactory(new PropertyValueFactory<>("nameBS"));
        colCategorySeller.setCellValueFactory(new PropertyValueFactory<>("categoryBS"));
        colPriceSeller.setCellValueFactory(new PropertyValueFactory<>("priceBS"));

        // Tạo dữ liệu cứng
        ObservableList<BestSeller> bestSellerList = FXCollections.observableArrayList(
                new BestSeller("BS01", "Sữa tươi", "Thực phẩm", 22000),
                new BestSeller("BS02", "Bánh mì", "Thực phẩm", 15000),
                new BestSeller("BS03", "Nước suối", "Đồ uống", 8000),
                new BestSeller("BS04", "Trứng gà", "Thực phẩm", 30000)
        );

        // Đưa dữ liệu vào TableView
        tvBestSeller.setItems(bestSellerList);
        tvBestSeller.setStyle("-fx-font-size: 16px");

        centerAlignColumn(colIdSeller);
        centerAlignColumn(colNameSeller);
        centerAlignColumn(colCategorySeller);
        centerAlignColumn(colPriceSeller);
        tvBestSeller.setFixedCellSize(40);

        // Scroll chat tự động như cũ
        chatBox.heightProperty().addListener((obs, oldVal, newVal) -> {
            chatScroll.setVvalue(1.0);
        });

        userInput.setOnAction(event -> handleUserInput());

    }


    private void addBotMessage(String message) {
        Label botLabel = new Label(message);
        botLabel.setStyle("-fx-background-color: #4471C4; -fx-padding: 8; -fx-background-radius: 10; -fx-text-fill: white;");

        HBox botHBox = new HBox(botLabel);
        botHBox.setStyle("-fx-alignment: CENTER_LEFT; -fx-padding: 5;");
        botHBox.setPrefWidth(chatScroll.getWidth() - 20);

        chatBox.getChildren().add(botHBox);
    }


    private <T> void centerAlignColumn(TableColumn<BestSeller, T> column) {
        column.setCellFactory(col -> new TableCell<BestSeller, T>() {
            @Override
            protected void updateItem(T item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item.toString());
                    setStyle("-fx-alignment: CENTER;");
                }
            }
        });
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
}
