package com.example.moodengbin;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.FirestoreOptions;
import java.io.FileInputStream;
import java.io.IOException;

public class FirebaseConnection {
    private static Firestore firestore;

    public static Firestore connect() {
        if (firestore == null) {
            try {
                FileInputStream serviceAccount = new FileInputStream("src/main/resources/serviceAccountKey.json");

                FirestoreOptions options = FirestoreOptions.newBuilder()
                        .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                        .build();

                firestore = options.getService();
                System.out.println("Connected to Firebase Firestore!");
            } catch (IOException e) {
                System.err.println("Connection failed: " + e.getMessage());
            }
        }
        return firestore;
    }
}
