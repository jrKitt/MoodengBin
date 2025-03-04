module com.example.moodengbin {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.moodengbin to javafx.fxml;
    exports com.example.moodengbin;
}