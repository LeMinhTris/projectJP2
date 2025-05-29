package com.hkt.app.controller;

import com.sun.javafx.stage.StageHelper;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class LoginController implements Initializable {

    @FXML
    private Button btnGG;

    @FXML
    private Button btnSignIn;

    @FXML
    private Label forgot;

    @FXML
    private PasswordField pfPassword;

    @FXML
    private TextField tfEmail;

    @FXML
    void onSignIn(ActionEvent event) throws IOException {
        String email = tfEmail.getText().trim();
        String password = pfPassword.getText().trim();

        /* Validate */
        StringBuilder errors = new StringBuilder();

        // Danh sách email hợp lệ
        ArrayList<String> validEmails = new ArrayList<>();
        validEmails.add("leminhtri260903@gmail.com");
        validEmails.add("fwhuynhnhutkhoi@gmail.com");
        validEmails.add("chunhau.py@gmail.com");

        // Regex kiểm tra định dạng email
        String emailRegex = "^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$";

        // Kiểm tra email
        if (email.isBlank()) {
            errors.append("Email is required!\n");
        } else if (!email.matches(emailRegex)) {
            errors.append("Invalid email format!\n");
        } else if (!validEmails.contains(email)) {
            errors.append("Email is not registered!\n");
        }

        // Kiểm tra password
        if (password.isBlank()) {
            errors.append("Password is required!\n");
        } else if (!password.equals("P@ssword")) {
            errors.append("Incorrect password!\n");
        }

        if (errors.length() > 0) {
            Alert errAlert = new Alert(Alert.AlertType.ERROR);
            errAlert.setTitle("Validation errors");
            errAlert.setHeaderText("Please fix the following errors: ");
            errAlert.setContentText(errors.toString());
            errAlert.showAndWait();
        } else {
            Alert succAlert = new Alert(Alert.AlertType.INFORMATION);
            succAlert.setTitle("Login Successful");
            succAlert.setHeaderText("You have logged in successfully ✔");
            succAlert.showAndWait();

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/dashboard.fxml"));
            Parent root = fxmlLoader.load();

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);

            stage.setWidth(1200);
            stage.setHeight(650);
            stage.setResizable(false);
            stage.setTitle("Convenient Store Management");
            stage.setScene(scene);
            stage.show();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        btnSignIn.setDefaultButton(true);
    }


    private static final String CLIENT_ID = "122758262415-luslqmedk0u97vm4hlkti7au4op1jkui.apps.googleusercontent.com";
    private static final String REDIRECT_URI = "http://localhost:8888";
    private static final String SCOPE = "https://www.googleapis.com/auth/userinfo.email";
    private static final String RESPONSE_TYPE = "code";
    private static final int PORT = 8888;

    public static void loginWithGoogle(Runnable onSuccess) throws Exception {
        String authUrl = "https://accounts.google.com/o/oauth2/v2/auth?"
                + "client_id=" + URLEncoder.encode(CLIENT_ID, "UTF-8")
                + "&redirect_uri=" + URLEncoder.encode(REDIRECT_URI, "UTF-8")
                + "&response_type=" + RESPONSE_TYPE
                + "&scope=" + URLEncoder.encode(SCOPE, "UTF-8");

        Desktop.getDesktop().browse(new URI(authUrl));
        startLocalServer(onSuccess);
    }

    private static void startLocalServer(Runnable onSuccess) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(PORT), 0);
        ExecutorService executor = Executors.newFixedThreadPool(1);
        server.setExecutor(executor);

        server.createContext("/", new HttpHandler() {
            @Override
            public void handle(HttpExchange exchange) throws IOException {
                String query = exchange.getRequestURI().getQuery();
                String response;
                if (query != null && query.contains("code=")) {
                    String code = query.split("code=")[1].split("&")[0];
                    response = "Login successful Bro!!!👍👍 ";
                    System.out.println("Received code: " + code);

                    // Gọi callback sau khi thành công
                    if (onSuccess != null) {
                        new Thread(onSuccess).start();
                    }

                } else {
                    response = "No login code received.";
                }

                byte[] bytes = response.getBytes(StandardCharsets.UTF_8);
                exchange.getResponseHeaders().add("Content-Type", "text/plain; charset=utf-8");
                exchange.sendResponseHeaders(200, bytes.length);

                OutputStream os = exchange.getResponseBody();
                os.write(bytes);
                os.close();

                new Thread(() -> {
                    try {
                        Thread.sleep(1000);
                        server.stop(0);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }).start();
            }
        });

        server.start();
//        System.out.println("Waiting for Google auth response at: " + REDIRECT_URI);
    }
    @FXML
    private void loginWithGoogle(ActionEvent event) {
        try {
            LoginController.loginWithGoogle(() -> {
                Platform.runLater(() -> {
                    try {
                        Parent root = FXMLLoader.load(getClass().getResource("/view/dashboard.fxml"));
                        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                        stage.setScene(new Scene(root));
                        stage.setWidth(1200);
                        stage.setHeight(650);
                        stage.setResizable(false);
                        stage.setTitle("Convenient Store Management");
                        stage.show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
