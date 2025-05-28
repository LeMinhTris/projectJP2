package com.hkt.app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        // Load giao diện từ FXMLs
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/dashboard.fxml"));
        Parent root = fxmlLoader.load();

        // Tạo Scene từ root
        Scene scene = new Scene(root);

        stage.setWidth(1200);
        stage.setHeight(650);
        stage.setResizable(false);

        // Cài đặt title và scene
        stage.setTitle("Convenient Store Management");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}