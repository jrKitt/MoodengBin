package com.example.moodengbin;

import com.example.moodengbin.FirebaseConnection;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class getMenu {
    public static List<Menu> getMenuByCategory(String category) {
        List<Menu> menuList = new ArrayList<>();
        Firestore db = FirebaseConnection.connect();

        try {
            QuerySnapshot querySnapshot = db.collection("menu_items")
                    .whereEqualTo("category", category)
                    .get()
                    .get();

            for (QueryDocumentSnapshot document : querySnapshot.getDocuments()) {
                Menu menu = new Menu(
                        document.getLong("id").intValue(),
                        document.getString("name"),
                        document.getString("description"),
                        document.getDouble("price"),
                        document.getString("image_path")
                );
                menuList.add(menu);
            }
        } catch (InterruptedException | ExecutionException e) {
            System.err.println("Error getting menu: " + e.getMessage());
        }

        return menuList;
    }
}
