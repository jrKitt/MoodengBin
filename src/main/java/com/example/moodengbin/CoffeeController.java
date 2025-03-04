package com.example.moodengbin;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class CoffeeController {

    @FXML private GridPane coffeeGrid;
    @FXML private Button CoolEspresso, Espresso, BlackCoffee, Mocha, Cappuccino, Latte, CaramelLatte, MatchaCoffeeLatte, ThaiCoffee, LycheeKano, YuzuKano, MintCoffeeLatte;
    @FXML private ImageView CoolEspressoImg, EspressoImg, BlackCoffeeImg, MochaImg, CappuccinoImg, LatteImg, CaramelLatteImg, MatchaCoffeeLatteImg, ThaiCoffeeImg, LycheeKanoImg, YuzuKanoImg, MintCoffeeLatteImg;
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
        buttonMap.put("Cappuccino", Cappuccino);
        buttonMap.put("Latte", Latte);
        buttonMap.put("Mocha", Mocha);
        buttonMap.put("Caramel Latte", CaramelLatte);
        buttonMap.put("Matcha Coffee Latte", MatchaCoffeeLatte);
        buttonMap.put("Thai Coffee", ThaiCoffee);
        buttonMap.put("Lychee Kano", LycheeKano);
        buttonMap.put("Yuzu Kano", YuzuKano);
        buttonMap.put("Mint Coffee Latte", MintCoffeeLatte);

        imageMap.put("Espresso", EspressoImg);
        imageMap.put("Black Coffee", BlackCoffeeImg);
        imageMap.put("Cool Espresso", CoolEspressoImg);
        imageMap.put("Cappuccino", CappuccinoImg);
        imageMap.put("Latte", LatteImg);
        imageMap.put("Mocha", MochaImg);
        imageMap.put("Caramel Latte", CaramelLatteImg);
        imageMap.put("Matcha Coffee Latte", MatchaCoffeeLatteImg);
        imageMap.put("Thai Coffee", ThaiCoffeeImg);
        imageMap.put("Lychee Kano", LycheeKanoImg);
        imageMap.put("Yuzu Kano", YuzuKanoImg);
        imageMap.put("Mint Coffee Latte", MintCoffeeLatteImg);
    }

    private void loadCoffeeMenu() {
        List<Menu> coffeeMenu = getMenu.getMenuByCategory("Coffee");

        for (Menu menu : coffeeMenu) {
            Button button = buttonMap.get(menu.getName());
            ImageView imageView = imageMap.get(menu.getName());

            if (button != null) {
                button.setText(menu.getName() + "\n" + menu.getPrice() + "฿");
            }

            if (imageView != null) {
                File file = new File(menu.getImagePath());
                if (file.exists()) {
                    imageView.setImage(new Image(file.toURI().toString()));
                }
            }
        }
    }
}
