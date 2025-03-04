package com.example.moodengbin;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class RecommendController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
}
