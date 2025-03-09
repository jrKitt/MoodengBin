package com.example.moodengbin;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.Optional;

public class HomepageController {

    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML private ImageView coffeeImage;
    @FXML private ImageView recommendImage;
    @FXML private ImageView milkImage;
    @FXML private ImageView pepsiImage;
    @FXML private ImageView healthImage;

    @FXML
    public void initialize() {
        coffeeImage.setOnMouseClicked(event -> {
            try {
                switchToCoffee(event);
            } catch (IOException e) {
                System.err.println("Error: " + e.getMessage());
            }
        });

        recommendImage.setOnMouseClicked(event -> {
            try {
                switchToRecommend(event);
            } catch (IOException e) {
                System.err.println("Error: " + e.getMessage());
            }
        });

        milkImage.setOnMouseClicked(event -> {
            try {
                switchToMilk(event);
            } catch (IOException e) {
                System.err.println("Error: " + e.getMessage());
            }
        });

        pepsiImage.setOnMouseClicked(event -> {
            try {
                switchToPepsi(event);
            } catch (IOException e) {
                System.err.println("Error: " + e.getMessage());
            }
        });

        healthImage.setOnMouseClicked(event -> {
            try {
                switchToHealth(event);
            } catch (IOException e) {
                System.err.println("Error: " + e.getMessage());
            }
        });
    }

    @FXML
    public void switchToCoffee(javafx.scene.input.MouseEvent event) throws IOException {
        changeScene(event, "/com/example/moodengbin/Coffee.fxml");
    }

    @FXML
    public void switchToRecommend(javafx.scene.input.MouseEvent event) throws IOException {
        changeScene(event, "/com/example/moodengbin/Recommend.fxml");
    }

    @FXML
    public void switchToMilk(javafx.scene.input.MouseEvent event) throws IOException {
        changeScene(event, "/com/example/moodengbin/Milk.fxml");
    }

    @FXML
    public void switchToPepsi(javafx.scene.input.MouseEvent event) throws IOException {
        changeScene(event, "/com/example/moodengbin/Pepsi.fxml");
    }

    @FXML
    public void switchToHealth(javafx.scene.input.MouseEvent event) throws IOException {
        changeScene(event, "/com/example/moodengbin/Health.fxml");
    }

    private void changeScene(javafx.scene.input.MouseEvent event, String fxmlFile) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource(fxmlFile));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    public void handleAdminLogin() {
        Stage loginStage = new Stage();
        loginStage.initModality(Modality.APPLICATION_MODAL);
        loginStage.setTitle("Admin Login");

        TextField usernameField = new TextField();
        usernameField.setPromptText("Username");
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.add(usernameField, 0, 0);
        grid.add(passwordField, 0, 1);

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Admin Login");
        alert.setHeaderText("กรุณาเข้าสู่ระบบ");
        alert.getDialogPane().setContent(grid);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            String username = usernameField.getText();
            String password = passwordField.getText();

            if (username.equals("admin") && password.equals("admin123")) {
                System.out.println("Login สำเร็จ!");
                loginStage.close();
                try {
                    switchToUserPanel();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                showAlert("Login ผิดพลาด!", "กรุณาลองใหม่อีกครั้ง");
            }
        }
    }

    private void switchToUserPanel() throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("UserPanel.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("User Panel");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
