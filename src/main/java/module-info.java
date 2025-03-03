module com.example.memorycard {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.memorycard to javafx.fxml;
    exports com.example.memorycard;
}