package com.example.moodengbin;

import com.google.cloud.firestore.*;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import java.util.concurrent.ExecutionException;

public class UserPanelController {

    @FXML private TextField usernameField;
    @FXML private TextField menuField;
    @FXML private TextField priceField;
    @FXML private Button loadUserButton;
    @FXML private Button updateButton;

    private Firestore db;

    @FXML
    public void initialize() {
        db = FirebaseConnection.connect();
    }

    @FXML
    public void loadUserData() {
        String username = usernameField.getText().trim();
        if (username.isEmpty()) {
            showAlert("Error", "กรุณากรอกหมายเลขโทรศัพท์ของผู้ใช้");
            return;
        }

        try {
            DocumentSnapshot userDoc = db.collection("users").document(username).get().get();
            if (userDoc.exists()) {
                menuField.setText(userDoc.getString("lastMenu"));
                priceField.setText(String.valueOf(userDoc.getDouble("lastPrice")));
            } else {
                showAlert("Error", "ไม่พบข้อมูลผู้ใช้");
            }
        } catch (InterruptedException | ExecutionException e) {
            showAlert("Error", "เกิดข้อผิดพลาดขณะโหลดข้อมูลผู้ใช้");
        }
    }

    @FXML
    public void updateUserData() {
        String username = usernameField.getText().trim();
        String menu = menuField.getText();
        String priceText = priceField.getText();

        if (username.isEmpty() || menu.isEmpty() || priceText.isEmpty()) {
            showAlert("Error", "กรุณากรอกข้อมูลให้ครบ");
            return;
        }

        try {
            double price = Double.parseDouble(priceText);

            db.collection("users").document(username).update(
                    "lastMenu", menu,
                    "lastPrice", price
            );

            showAlert("Success", "อัปเดตข้อมูลสำเร็จ!\nเมนู: " + menu + "\nราคา: " + price + "฿");
        } catch (NumberFormatException e) {
            showAlert("Error", "กรุณากรอกราคาเป็นตัวเลข");
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
