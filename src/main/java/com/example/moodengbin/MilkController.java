package com.example.moodengbin;

import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

public class MilkController {
    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private GridPane milkGridPane;

    @FXML
    public void switchToHome(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("Homepage.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    public void switchToCoffee(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("Coffee.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }    @FXML
    public void switchToHealth(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("Health.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }    @FXML
    public void switchToMilk(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("Milk.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }    @FXML
    public void swtichToPepsi(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("Pepsi.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    public void switchToRecommend(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("Recommend.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    public void initialize() {
        milkGridPane.setAlignment(Pos.CENTER);
        loadMilkMenu();
    }

    private void loadMilkMenu() {
        Firestore db = FirebaseConnection.connect();

        try {
            QuerySnapshot querySnapshot = db.collection("menu_items")
                    .whereEqualTo("category", "Milk")
                    .get()
                    .get();

            int row = 0, col = 0;
            for (QueryDocumentSnapshot document : querySnapshot.getDocuments()) {
                String name = document.getString("name");
                double price = document.getDouble("price");
                String imagePath = document.getString("image_path");

                ImageView imageView = new ImageView();
                imageView.setFitWidth(50);
                imageView.setFitHeight(50);

                String fullPath = "/com/example/moodengbin/" + imagePath;
                if (getClass().getResource(fullPath) != null) {
                    Image image = new Image(getClass().getResource(fullPath).toExternalForm());
                    imageView.setImage(image);
                } else {
                    System.err.println("ไม่พบรูปภาพ: " + fullPath);
                }

                Label menuLabel = new Label(name + "\n" + price + "฿");
                menuLabel.setStyle("-fx-font-size: 12px; -fx-text-alignment: center; -fx-margin-bottom: 10px;");

                VBox vbox = new VBox();
                vbox.setAlignment(Pos.TOP_CENTER);
                vbox.setSpacing(5);
                vbox.getChildren().addAll(imageView, menuLabel);

                Button menuButton = new Button();
                menuButton.setPrefSize(120, 140);
                menuButton.setStyle("-fx-background-color: white; " +
                        "-fx-font-size: 12px; " +
                        "-fx-padding: 10 0 10 0; " +
                        "-fx-text-alignment: center; " +
                        "-fx-alignment: center; " +
                        "-fx-background-radius: 10; " +
                        "-fx-border-color: #ddd; " +
                        "-fx-border-radius: 10; ");

                menuButton.setGraphic(vbox);
                menuButton.setContentDisplay(javafx.scene.control.ContentDisplay.TOP);

                menuButton.setUserData(new String[]{name, String.valueOf(price), fullPath});
                menuButton.setOnAction(this::handleMenuSelection);

                GridPane.setMargin(menuButton, new Insets(5));
                milkGridPane.add(menuButton, col, row);
                col++;
                if (col == 3) {
                    col = 0;
                    row++;
                }
            }

        } catch (InterruptedException | ExecutionException e) {
            System.err.println("โหลดข้อมูลล้มเหลว: " + e.getMessage());
        }
    }

    private void handleMenuSelection(ActionEvent event) {
        Button clickedButton = (Button) event.getSource();
        String[] menuData = (String[]) clickedButton.getUserData();

        if (menuData == null || menuData.length < 3) {
            System.err.println("ข้อมูลเมนูไม่ถูกต้อง");
            return;
        }

        String menuName = menuData[0];
        double menuPrice = Double.parseDouble(menuData[1]);
        String imagePath = menuData[2];

        System.out.println("กำลังส่งข้อมูลไป OrderPage: " + menuName + ", " + menuPrice + "฿, รูปภาพ: " + imagePath);

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("OrderPage.fxml"));
            Parent root = loader.load();

            OrderPageController orderPageController = loader.getController();
            if (orderPageController != null) {
                orderPageController.setMenuDetails(menuName, menuPrice, imagePath);
            } else {
                System.err.println("OrderPageController เป็น null!");
            }

            stage = (Stage) milkGridPane.getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            System.err.println("ไม่สามารถเปลี่ยนหน้า: " + e.getMessage());
        }
    }
}
