package com.hkt.app.controller;

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
import javafx.scene.control.TableRow;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class ProductController implements Initializable {

    @FXML
    private ImageView avatarImage1;

    @FXML
    private TableColumn<?, ?> colActions;

    @FXML
    private TableColumn<Product, String> colCategory;

    @FXML
    private TableColumn<Product, String> colID;

    @FXML
    private TableColumn<Product, String> colImage;

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

    // field
    Statement st;
    ResultSet rs;
    PreparedStatement ps;

    DBConnection connection = new DBConnection();

    @FXML
    public ObservableList<Product> getProducts() {
        ObservableList<Product> productList = null;
        Connection conn = null;

        // create list
        productList = FXCollections.observableArrayList();

        // 1. open connect
        conn = connection.getConnection();

        // 2. write query statement
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

        // 3. execute
        try {
            st = conn.createStatement();
            rs = st.executeQuery(query);

            // 4. read value
            while (rs.next()) {
                String id = rs.getString("id");
                String name = rs.getString("name");
                double price = rs.getDouble("price");
                String unitName = rs.getString("unit_name");
                System.out.printf("MTH 1: %s", unitName);
                int quantity = rs.getInt("quantity");
                String categoryName = rs.getString("category_name");
                String status = rs.getString("status");
                String imageUrl = rs.getString("image_url");

                // create new Product object from above information
                Product newPro = new Product(id, name, price, unitName, quantity, categoryName, status, imageUrl);

                // add into productList
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
        colImage.setCellValueFactory(new PropertyValueFactory<>("imageUrl"));

        ObservableList<Product> list = getProducts();

        tvProducts.setItems(list);

        tvProducts.setStyle("-fx-font-size: 16px; -fx-alignment: CENTER;");
        tvProducts.setFixedCellSize(40);
    }

    private void goToUpdatePage(Product product, MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/UpdateProduct.fxml"));
        Parent root = loader.load();

        // Lấy controller của trang update và truyền dữ liệu
        AddProductController controller = loader.getController();
        controller.setProduct(product);

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
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
                        // Gọi hàm chuyển trang và truyền đối tượng product
                        goToUpdatePage(selectedProduct, event);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
            return row;
        });

    }
}
