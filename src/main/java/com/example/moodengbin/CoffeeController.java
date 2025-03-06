package com.example.moodengbin;

import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class CoffeeController {
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
    @FXML private Button Espresso, BlackCoffee, CoolEspresso, Mocha, Cappuccino, Latte,
            CaramelLatte, MatchaCoffeeLatte, ThaiCoffee, LycheeKano, YuzuKano, MintCoffeeLatte;

    @FXML private ImageView EspressoImg, BlackCoffeeImg, CoolEspressoImg, MochaImg, CappuccinoImg, LatteImg,
            CaramelLatteImg, MatchaCoffeeLatteImg, ThaiCoffeeImg, LycheeKanoImg, YuzuKanoImg, MintCoffeeLatteImg;

    private final Map<String, Button> buttonMap = new HashMap<>();
    private final Map<String, ImageView> imageMap = new HashMap<>();

    @FXML
    public void initialize() {
        setupUIComponents();
        loadCoffeeMenu();
    }

    private void setupUIComponents() {
        buttonMap.put("Espresso", Espresso);
        buttonMap.put("Black Coffee", BlackCoffee);
        buttonMap.put("Cool Espresso", CoolEspresso);
        buttonMap.put("Mocha", Mocha);
        buttonMap.put("Cappuccino", Cappuccino);
        buttonMap.put("Latte", Latte);
        buttonMap.put("Caramel Latte", CaramelLatte);
        buttonMap.put("Matcha Coffee Latte", MatchaCoffeeLatte);
        buttonMap.put("Thai Coffee", ThaiCoffee);
        buttonMap.put("Lychee Kano", LycheeKano);
        buttonMap.put("Yuzu Kano", YuzuKano);
        buttonMap.put("Mint Coffee Latte", MintCoffeeLatte);

        imageMap.put("Espresso", EspressoImg);
        imageMap.put("Black Coffee", BlackCoffeeImg);
        imageMap.put("Cool Espresso", CoolEspressoImg);
        imageMap.put("Mocha", MochaImg);
        imageMap.put("Cappuccino", CappuccinoImg);
        imageMap.put("Latte", LatteImg);
        imageMap.put("Caramel Latte", CaramelLatteImg);
        imageMap.put("Matcha Coffee Latte", MatchaCoffeeLatteImg);
        imageMap.put("Thai Coffee", ThaiCoffeeImg);
        imageMap.put("Lychee Kano", LycheeKanoImg);
        imageMap.put("Yuzu Kano", YuzuKanoImg);
        imageMap.put("Mint Coffee Latte", MintCoffeeLatteImg);
    }

    private void loadCoffeeMenu() {
        Firestore db = FirebaseConnection.connect();

        try {
            QuerySnapshot querySnapshot = db.collection("menu_items")
                    .whereEqualTo("category", "Coffee")
                    .get()
                    .get();

            for (QueryDocumentSnapshot document : querySnapshot.getDocuments()) {
                String name = document.getString("name");
                double price = document.getDouble("price");
                String imagePath = document.getString("image_path");
                Button button = buttonMap.get(name);
                if (button != null) {
                    button.setText(name + "\n" + price + "฿");
                }

                ImageView imageView = imageMap.get(name);
                if (imageView != null) {
                    File file = new File(imagePath);
                    if (file.exists()) {
                        imageView.setImage(new Image(file.toURI().toString()));
                    }
                }
            }

        } catch (InterruptedException | ExecutionException e) {
            System.err.println(e.getMessage());
        }
    }

}
