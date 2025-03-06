package com.example.moodengbin;

import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.DocumentReference;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class FireStoreSeeder {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        Firestore db = FirebaseConnection.connect();

        Object[][] menuItems = {
                {50, "Americano", "Recommended", "อเมริกาโน่หอมเข้ม", 70.00, "Picture/3_20250225181438-removebg-preview.png"},
                {51, "Thai Milk Tea", "Recommended", "ชานมไทยเข้มข้น", 60.00, "Picture/5_20250225181205-removebg-preview.png"},
                {52, "Strawberry Soda", "Recommended", "สตรอเบอรี่โซดาสดชื่น", 65.00, "Picture/6_20250225182341-removebg-preview.png"},
                {53, "Lemon Soda", "Recommended", "มะนาวโซดาสดชื่น", 55.00, "Picture/6_20250225182139-removebg-preview.png"},
                {54, "Coconut", "Recommended", "มะพร้าวน้ำหอมสด", 75.00, "Picture/6_20250225181947-removebg-preview.png"},
                {55, "Matcha Latte", "Recommended", "มัจฉะลาเต้หอมละมุน", 85.00, "Picture/5_20250225180958-removebg-preview.png"}
        };


        for (Object[] item : menuItems) {
            Map<String, Object> data = new HashMap<>();
            data.put("id", item[0]);
            data.put("name", item[1]);
            data.put("category", item[2]);
            data.put("description", item[3]);
            data.put("price", item[4]);
            data.put("image_path", item[5]);


            DocumentReference docRef = db.collection("menu_items").document("item" + item[0]);
            docRef.set(data).get();
            System.out.println("✅ เพิ่มเมนู: " + item[1]);
        }

        System.out.println("เพิ่มเมนูทั้งหมดลง Firestore เรียบร้อยแล้ว!");
    }
}
