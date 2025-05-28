package com.hkt.app.controller;

import javafx.beans.property.ReadOnlyStringWrapper;
import com.hkt.app.database.DBConnection;
import com.hkt.app.model.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import java.util.Optional;

public class ProductController implements Initializable {

    @FXML
    private javafx.scene.control.TextField txtSearch;


    @FXML
    private ImageView avatarImage1;
    @FXML
    private TableColumn<Product, Void> colActions;

    @FXML
    private TableColumn<Product, String> colCategory;

    @FXML
    private TableColumn<Product, String> colID;

//    @FXML
//    private TableColumn<Product, String> colImage;

    @FXML
    private TableColumn<Product, String> colName;

    @FXML
    private TableColumn<Product, Double> colPrice;

    @FXML
    TableColumn<Product, String> colStatus = new TableColumn<>("Trạng thái");
//    private TableColumn<Product, String> colStatus;

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
        colStatus.setCellValueFactory(cellData -> {
            String status = cellData.getValue().getStatus(); // bây giờ là String
            String display = "Hết hàng";
            if ("1".equals(status)) {
                display = "Còn hàng";
            }
            return new ReadOnlyStringWrapper(display);
        });

//        colImage.setCellValueFactory(new PropertyValueFactory<>("imageUrl"));

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
                            confirmAndDeleteProduct(product);
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
        controller.setProduct(product); // <-- Gọi sau khi đã load xong FXML

        Stage stage;
        if (event != null) {
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        } else {
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
    }
    private void confirmAndDeleteProduct(Product product) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Xác nhận xóa");
        alert.setHeaderText("Bạn có chắc muốn xóa sản phẩm này không?");
        alert.setContentText("Sản phẩm: " + product.getName());

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            deleteProduct(product);
        }
        // Nếu người dùng chọn Cancel hoặc đóng hộp thoại thì không làm gì
    }
    @FXML
    private void handleSearch(ActionEvent event) {
        String keyword = txtSearch.getText().toLowerCase().trim();
        if (keyword.isEmpty()) {
            tvProducts.setItems(getProducts()); // reset nếu không nhập gì
            return;
        }

        ObservableList<Product> filteredList = FXCollections.observableArrayList();
        for (Product p : getProducts()) {
            if (p.getId().toLowerCase().contains(keyword) || p.getName().toLowerCase().contains(keyword)) {
                filteredList.add(p);
            }
        }

        tvProducts.setItems(filteredList);
    }



}
