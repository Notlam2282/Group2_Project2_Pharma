package com.example.pharmacy;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;

public class LoginController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private TextField passwordTextField;

    @FXML
    private Button loginButton;

    @FXML
    private Button togglePasswordButton;

    private boolean isPasswordVisible = false;

    @FXML
    public void initialize() {
        // Đảm bảo chỉ 1 ô hiển thị tại 1 thời điểm
        passwordTextField.setVisible(false);
        System.out.println("Controller initialized");
    }

    @FXML
    private void handleLogin(ActionEvent event) {
        String username = usernameField.getText();
        String password = isPasswordVisible ? passwordTextField.getText() : passwordField.getText();

        if (username.isEmpty() || password.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Thiếu thông tin", "Vui lòng nhập đầy đủ tên đăng nhập và mật khẩu!");
            return;
        }

        // 🔹 Tài khoản test giả lập (chưa dùng database)
        if ((username.equals("admin") && password.equals("12345")) ||
                (username.equals("staff") && password.equals("1111"))) {

            showAlert(Alert.AlertType.INFORMATION, "Đăng nhập thành công", "Chào mừng " + username + "!");
            System.out.println("✅ Login success: " + username);
        } else {
            showAlert(Alert.AlertType.ERROR, "Đăng nhập thất bại", "Sai tên đăng nhập hoặc mật khẩu!");
            System.out.println("❌ Login failed");
        }
    }

    @FXML
    private void togglePasswordVisibility(ActionEvent event) {
        isPasswordVisible = !isPasswordVisible;

        if (isPasswordVisible) {
            passwordTextField.setText(passwordField.getText());
            passwordTextField.setVisible(true);
            passwordField.setVisible(false);
            togglePasswordButton.setText("👁");
        } else {
            passwordField.setText(passwordTextField.getText());
            passwordField.setVisible(true);
            passwordTextField.setVisible(false);
            togglePasswordButton.setText("👁");
        }
    }

    // Hàm tiện ích hiển thị thông báo
    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
