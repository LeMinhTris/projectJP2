package com.hkt.app.controller;

import com.hkt.app.database.DBConnection;
import com.hkt.app.model.Product;
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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
        import java.util.ResourceBundle;

public class ProductController implements Initializable {
    @FXML
    private TextField userInput;

    @FXML
    private AnchorPane khungChat;

    @FXML
    private VBox chatBox;

    @FXML
    private ScrollPane chatScroll;

    @FXML
    private ImageView closeChat;

    @FXML
    private ImageView btnChatBot;

    @FXML
    private ImageView avatarImage1;

    @FXML
    private TableColumn<Product, Void> colActions;

    @FXML
    private TableColumn<Product, String> colCategory;

    @FXML
    private TableColumn<Product, String> colID;

    @FXML
    private TableColumn<Product, String> colName;

    @FXML
    private TableColumn<Product, Double> colPrice;

    @FXML
    private TableColumn<Product, String> colStatus;

    @FXML
    private TableColumn<Product, String> colUnit;

    @FXML
    private TableColumn<Product, Integer> colQuantity;

    @FXML
    private TableView<Product> tvProducts;

    // DB fields
    Statement st;
    ResultSet rs;
    PreparedStatement ps;

    DBConnection connection = new DBConnection();

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

    @FXML
    public ObservableList<Product> getProducts() {
        ObservableList<Product> productList = FXCollections.observableArrayList();
        Connection conn = connection.getConnection();

        String query = """
                SELECT
                    p.id,
                    p.name,
                    p.price,
                    p.quantity,
                    u.name AS unit_name,
                    c.name AS category_name,
                    p.status,
                    p.image_url
                FROM products p
                JOIN units u ON p.unit_id = u.id
                JOIN categories c ON p.category_id = c.id;
                """;

        try {
            st = conn.createStatement();
            rs = st.executeQuery(query);

            while (rs.next()) {
                String id = rs.getString("id");
                String name = rs.getString("name");
                double price = rs.getDouble("price");
                String unitName = rs.getString("unit_name");
                int quantity = rs.getInt("quantity");
                String categoryName = rs.getString("category_name");
                String status = rs.getString("status");
                String imageUrl = rs.getString("image_url");

                Product newPro = new Product(id, name, price, unitName, quantity, categoryName, status);
                productList.add(newPro);
            }
        } catch (SQLException e) {
            System.out.println("Something went wrong!\n");
            e.printStackTrace();
            System.out.println();
        }

        return productList;
    }

    public void showProduct() {
        colID.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        colUnit.setCellValueFactory(new PropertyValueFactory<>("unitName"));
        colQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        colCategory.setCellValueFactory(new PropertyValueFactory<>("categoryName"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));

        addButtonToTable();

        ObservableList<Product> list = getProducts();
        tvProducts.setItems(list);

        tvProducts.setStyle("-fx-font-size: 16px; -fx-alignment: CENTER;");
        tvProducts.setFixedCellSize(40);
    }

    private void addButtonToTable() {
        Callback<TableColumn<Product, Void>, TableCell<Product, Void>> cellFactory = new Callback<>() {
            @Override
            public TableCell<Product, Void> call(final TableColumn<Product, Void> param) {
                return new TableCell<>() {
                    private final Button btnEdit = new Button("Update");
                    private final Button btnDelete = new Button("Delete");

                    {
                        // Style cho nút Update (màu xanh, bo góc, hover nhẹ)
                        btnEdit.setStyle(
                                "-fx-background-color: #4CAF50;" +   // màu xanh lá
                                        "-fx-text-fill: white;" +             // chữ màu trắng
                                        "-fx-font-weight: bold;" +
                                        "-fx-background-radius: 8;" +        // bo góc
                                        "-fx-cursor: hand;"
                        );
                        btnEdit.setOnMouseEntered(e -> btnEdit.setStyle(
                                "-fx-background-color: #45a049;" +
                                        "-fx-text-fill: white;" +
                                        "-fx-font-weight: bold;" +
                                        "-fx-background-radius: 8;" +
                                        "-fx-cursor: hand;"
                        ));
                        btnEdit.setOnMouseExited(e -> btnEdit.setStyle(
                                "-fx-background-color: #4CAF50;" +
                                        "-fx-text-fill: white;" +
                                        "-fx-font-weight: bold;" +
                                        "-fx-background-radius: 8;" +
                                        "-fx-cursor: hand;"
                        ));

                        // Style cho nút Delete (màu đỏ, bo góc, hover nhẹ)
                        btnDelete.setStyle(
                                "-fx-background-color: #f44336;" +   // màu đỏ
                                        "-fx-text-fill: white;" +
                                        "-fx-font-weight: bold;" +
                                        "-fx-background-radius: 8;" +
                                        "-fx-cursor: hand;"
                        );
                        btnDelete.setOnMouseEntered(e -> btnDelete.setStyle(
                                "-fx-background-color: #da190b;" +
                                        "-fx-text-fill: white;" +
                                        "-fx-font-weight: bold;" +
                                        "-fx-background-radius: 8;" +
                                        "-fx-cursor: hand;"
                        ));
                        btnDelete.setOnMouseExited(e -> btnDelete.setStyle(
                                "-fx-background-color: #f44336;" +
                                        "-fx-text-fill: white;" +
                                        "-fx-font-weight: bold;" +
                                        "-fx-background-radius: 8;" +
                                        "-fx-cursor: hand;"
                        ));

                        btnEdit.setOnAction(event -> {
                            Product product = getTableView().getItems().get(getIndex());
                            try {
                                goToUpdatePage(product, null);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        });

                        btnDelete.setOnAction(event -> {
                            Product product = getTableView().getItems().get(getIndex());
                            deleteProduct(product);
                        });
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(new javafx.scene.layout.HBox(10, btnEdit, btnDelete));
                        }
                    }
                };
            }
        };
        colActions.setCellFactory(cellFactory);
    }

    private void deleteProduct(Product product) {
        Connection conn = connection.getConnection();
        String sql = "DELETE FROM products WHERE id = ?";
        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, product.getId());
            int result = ps.executeUpdate();
            if (result > 0) {
                System.out.println("Xóa sản phẩm thành công: " + product.getId());
                showProduct(); // refresh lại danh sách sau khi xóa
            } else {
                System.out.println("Xóa sản phẩm thất bại: " + product.getId());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void goToUpdatePage(Product product, MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/UpdateProduct.fxml"));
        Parent root = loader.load();

        AddProductController controller = loader.getController();
        controller.setProduct(product);

        Stage stage;
        if (event != null) {
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        } else {
            // Nếu event null (ví dụ gọi từ nút sửa), lấy stage từ tableView
            stage = (Stage) tvProducts.getScene().getWindow();
        }

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        showProduct();

        tvProducts.setRowFactory(tv -> {
            TableRow<Product> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    Product selectedProduct = row.getItem();
                    try {
                        goToUpdatePage(selectedProduct, event);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
            return row;
        });

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

    private void addBotMessage(String message) {
        Label botLabel = new Label(message);
        botLabel.setStyle("-fx-background-color: #4471C4; -fx-padding: 8; -fx-background-radius: 10; -fx-text-fill: white;");

        HBox botHBox = new HBox(botLabel);
        botHBox.setStyle("-fx-alignment: CENTER_LEFT; -fx-padding: 5;");
        botHBox.setPrefWidth(chatScroll.getWidth() - 20);

        chatBox.getChildren().add(botHBox);
    }
}
