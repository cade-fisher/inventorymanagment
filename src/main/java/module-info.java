module com.example.demo2 {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.demo2 to javafx.fxml;
    exports com.example.demo2;
    opens com.example.demo2.Model to javafx.fxml;
    exports com.example.demo2.Model;
}