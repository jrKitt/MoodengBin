package com.example.moodengbin;

import com.google.cloud.firestore.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Dialog;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

public class FinalPageController {
    @FXML private Button viewHistoryButton;
    private Stage stage;
    private Scene scene;
    private Parent root;
    @FXML

    public void switchToHome(javafx.event.ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("Homepage.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    private String userPhone;

    public void setUserPhone(String phone) {
        this.userPhone = phone;
        System.out.println("เบอร์โทรที่ใช้จ่าย: " + userPhone);
    }

    @FXML
    private void showOrderHistoryPopup() {
        if (userPhone == null || userPhone.isEmpty()) {
            showAlert("ไม่พบเบอร์โทร", "กรุณาล็อกอินหรือซื้อสินค้าก่อน");
            return;
        }

        ObservableList<String> historyList = FXCollections.observableArrayList();
        Firestore db = FirebaseConnection.connect();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");

        try {
            List<QueryDocumentSnapshot> historyDocs = db.collection("users")
                    .document(userPhone)
                    .collection("history")
                    .orderBy("timestamp", Query.Direction.DESCENDING)
                    .get()
                    .get()
                    .getDocuments();

            for (QueryDocumentSnapshot doc : historyDocs) {
                String menuName = doc.getString("menuName");
                double price = doc.getDouble("price");
                long timestamp = doc.getLong("timestamp");

                String dateStr = dateFormat.format(timestamp);
                String orderInfo = menuName + " : " + price + " ฿ (" + dateStr + ")";
                historyList.add(orderInfo);
            }

        } catch (InterruptedException | ExecutionException e) {
            System.err.println("โหลดประวัติการสั่งซื้อผิดพลาด: " + e.getMessage());
        }

        if (historyList.isEmpty()) {
            showAlert("ไม่มีประวัติการสั่งซื้อ", "คุณยังไม่มีการสั่งซื้อ");
            return;
        }

        ListView<String> listView = new ListView<>(historyList);
        Dialog<Void> dialog = new Dialog<>();
        dialog.setTitle("ประวัติการสั่งซื้อ");
        dialog.getDialogPane().setContent(listView);
        dialog.getDialogPane().getButtonTypes().add(javafx.scene.control.ButtonType.OK);
        dialog.showAndWait();
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
