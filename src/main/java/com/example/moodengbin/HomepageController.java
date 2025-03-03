package com.example.moodengbin;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HomepageController {

    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    public void switchToCoffee(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("Coffee.fxml"));
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
    public void switchToMilk(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("Milk.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void switchToPepsi(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("Pepsi.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void switchToHealth(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("Health.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
