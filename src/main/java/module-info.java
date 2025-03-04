module com.example.moodengbin {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.moodengbin to javafx.fxml;
    exports com.example.moodengbin;
}