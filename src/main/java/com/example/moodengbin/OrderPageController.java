package com.example.moodengbin;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class OrderPageController {
    @FXML private ImageView selectedMenuImage;
    @FXML private Button HotButton, CoolButton, BlendButton;
    @FXML private Button NotSweetButton, LittleSweetButton, SweetButton;

    private Stage stage;
    private Scene scene;
    private Parent root;

    private String menuName;
    private double menuPrice;
    private String selectedTemperature = "";
    private String selectedSweetness = "";

    @FXML
    public void cancelOrder(javafx.event.ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("Homepage.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void setMenuDetails(String menuName, double price, String imagePath) {
        this.menuName = menuName;
        this.menuPrice = price;

        System.out.println("เมนูที่เลือก: " + this.menuName);
        System.out.println("ราคา: " + this.menuPrice + " ฿");

        if (imagePath != null && !imagePath.isEmpty()) {
            System.out.println("กำลังโหลดรูปภาพ: " + imagePath);
            try {
                Image image = new Image(imagePath);
                selectedMenuImage.setImage(image);
            } catch (Exception e) {
                System.err.println("ไม่สามารถโหลดรูปภาพ: " + e.getMessage());
            }
        } else {
            System.err.println("imagePath เป็นค่าว่างหรือ null!");
        }
    }

    public String getMenuName() {
        return menuName;
    }

    public double getMenuPrice() {
        return menuPrice;
    }

    @FXML
    private void proceedToPayment(javafx.event.ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Payment.fxml"));
        Parent root = loader.load();

        PaymentController paymentController = loader.getController();
        if (paymentController != null) {
            paymentController.setPaymentDetails(menuName, menuPrice);
        } else {
            System.err.println("PaymentController เป็น null!");
        }

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void selectTemperature(javafx.event.ActionEvent event) {
        Button clickedButton = (Button) event.getSource();
        selectedTemperature = clickedButton.getText();

        resetButtonStyles(List.of(HotButton, CoolButton, BlendButton));
        highlightSelectedButton(clickedButton);
    }

    @FXML
    private void selectSweetness(javafx.event.ActionEvent event) {
        Button clickedButton = (Button) event.getSource();
        selectedSweetness = clickedButton.getText();

        resetButtonStyles(List.of(NotSweetButton, LittleSweetButton, SweetButton));
        highlightSelectedButton(clickedButton);
    }

    private void resetButtonStyles(List<Button> buttons) {
        for (Button button : buttons) {
            button.setStyle("-fx-background-color: white; -fx-background-radius: 10; -fx-border-color: black;");
        }
    }

    private void highlightSelectedButton(Button button) {
        button.setStyle("-fx-background-color: #3F1C1C; -fx-text-fill: white; -fx-background-radius: 10; -fx-border-color: black;");
    }
}
