package com.hkt.app.controller;

import com.sun.javafx.stage.StageHelper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

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
        validEmails.add("fwhuynhnhutkhoii@gmail.com");
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
}
