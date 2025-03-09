package com.example.moodengbin;

import com.google.cloud.firestore.*;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

public class PaymentController {
    @FXML private Button CashButton;
    @FXML private Button PromptPayButton;
    @FXML private Button CancelButton;

    private String menuName;
    private double menuPrice;
    private Stage stage;
    private Scene scene;

    public void setPaymentDetails(String menuName, double menuPrice) {
        this.menuName = menuName;
        this.menuPrice = menuPrice;
        System.out.println("เมนูที่เลือก: " + menuName);
        System.out.println("ราคา: " + menuPrice + " ฿");
    }
    @FXML
    private void handleTopUp(String phoneNumber, int amount) {
        Platform.runLater(() -> {
            Stage qrState2 = new Stage();
            qrState2.setTitle("สแกน QR เพื่อเติมเงิน");



            ImageView qrImageView = new ImageView();
            String qrPath = "/com/example/moodengbin/Picture/Qrcode.JPG";
            if (getClass().getResource(qrPath) != null) {
                Image qrImage = new Image(getClass().getResource(qrPath).toExternalForm());
                qrImageView.setImage(qrImage);
                qrImageView.setFitWidth(200);
                qrImageView.setFitHeight(200);
            } else {
                System.err.println("ไม่พบไฟล์ QR Code: " + qrPath);
            }


            Button confirmButton = new Button("ยืนยันการเติมเงิน");
            confirmButton.setDisable(false);
            confirmButton.setStyle("-fx-font-size: 14px; -fx-background-color: #4CAF50; -fx-text-fill: white;");
            confirmButton.setOnAction(event -> {
                qrState2.close();
                updateUserBalance(phoneNumber, amount);
            });



            VBox vbox = new VBox(10, qrImageView, confirmButton);
            vbox.setAlignment(Pos.CENTER);
            vbox.setStyle("-fx-padding: 20px;");

            Scene popupScene = new Scene(vbox, 250, 320);
            qrState2.setScene(popupScene);
            qrState2.showAndWait();
        });

    }


    private void showTopUpDialog(String phoneNumber) {
        Stage popupStage = new Stage();
        popupStage.initModality(Modality.APPLICATION_MODAL);
        popupStage.setTitle("เติมเงิน");

        Label messageLabel = new Label("ยอดเงินของคุณไม่เพียงพอ\nกรุณาเลือกจำนวนเงินเพื่อเติมเงิน:");
        messageLabel.setStyle("-fx-font-size: 14px;");

        Button btn50 = createTopUpButton(50, phoneNumber, popupStage);
        Button btn100 = createTopUpButton(100, phoneNumber, popupStage);
        Button btn150 = createTopUpButton(150, phoneNumber, popupStage);
        Button btn250 = createTopUpButton(250, phoneNumber, popupStage);
        Button btn500 = createTopUpButton(500, phoneNumber, popupStage);
        Button btn1000 = createTopUpButton(1000, phoneNumber, popupStage);

        Button btnCancel = new Button("ยกเลิกรายการ");
        btnCancel.setStyle("-fx-background-color: #FF5733; -fx-text-fill: white; -fx-font-size: 14px;");
        btnCancel.setOnAction(e -> popupStage.close());

        VBox vbox = new VBox(10, messageLabel, btn50, btn100, btn150, btn250, btn500, btn1000, btnCancel);
        vbox.setAlignment(Pos.CENTER);
        vbox.setStyle("-fx-padding: 20px;");

        Scene popupScene = new Scene(vbox, 300, 400);
        popupStage.setScene(popupScene);
        popupStage.showAndWait();
    }

    private Button createTopUpButton(int amount, String phoneNumber, Stage popupStage) {
        Button button = new Button(amount + " ฿");
        button.setStyle("-fx-font-size: 14px; -fx-background-color: #3F1C1C; -fx-text-fill: white;");
        button.setOnAction(event -> {
            System.out.println("กดปุ่มเติมเงิน: " + amount);
            popupStage.close();
            handleTopUp(phoneNumber, amount);
        });
        return button;
    }


    private void updateUserBalance(String phoneNumber, int amount) {
        Firestore db = FirebaseConnection.connect();
        try {
            DocumentReference userRef = db.collection("users").document(phoneNumber);
            DocumentSnapshot userDoc = userRef.get().get();

            if (userDoc.exists()) {
                double currentBalance = userDoc.getDouble("cash");
                double newBalance = currentBalance + amount;

                userRef.update("cash", newBalance).get();

                Platform.runLater(() -> showAlert("เติมเงินสำเร็จ", "ยอดเงินปัจจุบันของคุณคือ: " + newBalance + " ฿"));

            } else {
                Platform.runLater(() -> showAlert("ผิดพลาด", "ไม่พบบัญชีผู้ใช้สำหรับเบอร์นี้"));
            }
        } catch (InterruptedException | ExecutionException e) {
            System.err.println("เกิดข้อผิดพลาดขณะอัปเดตยอดเงิน: " + e.getMessage());
            Platform.runLater(() -> showAlert("เกิดข้อผิดพลาด", "ไม่สามารถเติมเงินได้"));
        }
    }



    @FXML
    private void handlePromptPayPayment() {
        Stage popupStage = new Stage();
        popupStage.initModality(Modality.APPLICATION_MODAL);
        popupStage.setTitle("สแกน QR เพื่อชำระเงิน");

        ImageView qrImageView = new ImageView();
        String qrPath = "/com/example/moodengbin/Picture/Qrcode.JPG";
        if (getClass().getResource(qrPath) != null) {
            Image qrImage = new Image(getClass().getResource(qrPath).toExternalForm());
            qrImageView.setImage(qrImage);
            qrImageView.setFitWidth(200);
            qrImageView.setFitHeight(200);
        } else {
            System.err.println("ไม่พบไฟล์ QR Code: " + qrPath);
        }

        CheckBox confirmCheckBox = new CheckBox("ฉันได้โอนเงินแล้ว");
        confirmCheckBox.setStyle("-fx-font-size: 14px;");

        Button confirmButton = new Button("ยืนยันการโอน");
        confirmButton.setDisable(true);
        confirmButton.setStyle("-fx-font-size: 14px; -fx-background-color: #4CAF50; -fx-text-fill: white;");
        confirmButton.setOnAction(event -> {
            popupStage.close();
            processPayment();
        });

        confirmCheckBox.setOnAction(event -> confirmButton.setDisable(!confirmCheckBox.isSelected()));

        VBox vbox = new VBox(10, qrImageView, confirmCheckBox, confirmButton);
        vbox.setAlignment(Pos.CENTER);
        vbox.setStyle("-fx-padding: 20px;");

        Scene popupScene = new Scene(vbox, 250, 320);
        popupStage.setScene(popupScene);
        popupStage.showAndWait();
    }

    private void processPayment() {
        System.out.println("กำลังบันทึกข้อมูลการชำระเงิน...");

        Firestore db = FirebaseConnection.connect();
        Map<String, Object> paymentData = new HashMap<>();
        paymentData.put("menuName", menuName);
        paymentData.put("price", menuPrice);
        paymentData.put("paymentMethod", "PromptPay");
        paymentData.put("timestamp", System.currentTimeMillis());

        try {
            db.collection("payments").add(paymentData).get();
            System.out.println("บันทึกการชำระเงินสำเร็จ!");
            try {
                switchToFinalPage();
            } catch (IOException e) {
                System.err.println("เกิดข้อผิดพลาดขณะเปลี่ยนหน้า: " + e.getMessage());
            }
        } catch (InterruptedException | ExecutionException e) {
            System.err.println("เกิดข้อผิดพลาดขณะบันทึกการชำระเงิน: " + e.getMessage());
        }
    }

    @FXML
    private void handleCashPayment() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("ยืนยันเบอร์มือถือ");
        dialog.setHeaderText("กรอกเบอร์มือถือของคุณ");
        Optional<String> result = dialog.showAndWait();
        result.ifPresent(this::checkPhoneNumberInFirebase);
    }

    private void checkPhoneNumberInFirebase(String phoneNumber) {
        Firestore db = FirebaseConnection.connect();

        try {
            QuerySnapshot querySnapshot = db.collection("users")
                    .whereEqualTo("phone", phoneNumber)
                    .get()
                    .get();

            if (!querySnapshot.isEmpty()) {
                DocumentSnapshot userDoc = querySnapshot.getDocuments().get(0);
                double currentCash = userDoc.getDouble("cash");

                if (currentCash >= menuPrice) {
                    deductCash(phoneNumber, currentCash);
                } else {
                    showTopUpDialog(phoneNumber);
                }
            } else {
                showRegisterDialog(phoneNumber);
            }
        } catch (InterruptedException | ExecutionException e) {
            System.err.println("เกิดข้อผิดพลาดขณะตรวจสอบเบอร์: " + e.getMessage());
        }
    }

    private void deductCash(String phoneNumber, double currentCash) {
        Firestore db = FirebaseConnection.connect();
        double newBalance = currentCash - menuPrice;

        try {
            db.collection("users").document(phoneNumber)
                    .update("cash", newBalance)
                    .get();

            System.out.println("ตัดเงินสำเร็จ! คงเหลือ: " + newBalance);
            showAlert("ชำระเงินสำเร็จ", "ยอดเงินคงเหลือ: " + newBalance + " ฿");

            saveOrderHistory(phoneNumber);
            switchToFinalPage2(phoneNumber);

        } catch (InterruptedException | ExecutionException | IOException e) {
            System.err.println("เกิดข้อผิดพลาดขณะตัดเงิน: " + e.getMessage());
        }
    }

    private void saveOrderHistory(String phoneNumber) {
        Firestore db = FirebaseConnection.connect();

        Map<String, Object> orderData = new HashMap<>();
        orderData.put("menuName", menuName);
        orderData.put("price", menuPrice);
        orderData.put("paymentMethod", "Cash");
        orderData.put("timestamp", System.currentTimeMillis());

        try {
            db.collection("users").document(phoneNumber)
                    .collection("history")
                    .add(orderData)
                    .get();

            System.out.println("บันทึกประวัติการสั่งซื้อสำเร็จ!");

        } catch (InterruptedException | ExecutionException e) {
            System.err.println("เกิดข้อผิดพลาดขณะบันทึกประวัติ: " + e.getMessage());
        }
    }

    private void switchToFinalPage2(String phoneNumber) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("FinalPage.fxml"));
        Parent root = loader.load();

        FinalPageController finalPageController = loader.getController();
        if (finalPageController != null) {
            finalPageController.setUserPhone(phoneNumber);
        }

        stage = (Stage) CashButton.getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    private void showRegisterDialog(String phoneNumber) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("สมัครสมาชิกหมูเด้งบิน");
        alert.setHeaderText("ไม่พบเบอร์นี้ในระบบ");
        alert.setContentText("คุณต้องการสมัครบัญชีใหม่หรือไม่?");

        Optional<javafx.scene.control.ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == javafx.scene.control.ButtonType.OK) {
            registerNewUser(phoneNumber);
        }
    }

    private void registerNewUser(String phoneNumber) {
        Firestore db = FirebaseConnection.connect();
        Map<String, Object> newUser = new HashMap<>();
        newUser.put("phone", phoneNumber);
        newUser.put("cash", 100.0);

        try {
            db.collection("users").document(phoneNumber)
                    .set(newUser)
                    .get();

            showAlert("สมัครสมาชิกสำเร็จ!", "คุณได้รับเครดิต 100 บาท");
        } catch (InterruptedException | ExecutionException e) {
            System.err.println("เกิดข้อผิดพลาดในการสมัคร: " + e.getMessage());
        }
    }
    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    @FXML
    public void cancelPayment(javafx.event.ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Homepage.fxml"));
        Parent root = loader.load();
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    private void switchToFinalPage() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("FinalPage.fxml"));
        Parent root = loader.load();
        stage = (Stage) CashButton.getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

}

