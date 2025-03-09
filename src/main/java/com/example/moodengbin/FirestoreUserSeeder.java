package com.example.moodengbin;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.WriteResult;
import java.util.concurrent.ExecutionException;
import java.util.HashMap;
import java.util.Map;

import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.DocumentReference;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class FirestoreUserSeeder {
    public static void main(String[] args) {
        Firestore db = FirebaseConnection.connect();
        String collectionName = "users";

        String userId = "user_001";
        Map<String, Object> user = new HashMap<>();
        user.put("name", "John Doe");
        user.put("phone", "0812345678");
        user.put("cash", 200.0);

        DocumentReference docRef = db.collection(collectionName).document(userId);
        try {
            WriteResult result = docRef.set(user).get();
            System.out.println("เพิ่มผู้ใช้สำเร็จ! เวลาอัปเดต: " + result.getUpdateTime());
        } catch (InterruptedException | ExecutionException e) {
            System.err.println("ไม่สามารถเพิ่มผู้ใช้ได้: " + e.getMessage());
        }
    }
}
