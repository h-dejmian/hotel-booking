module com.example.hotelbooking {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.hotelbooking to javafx.fxml;
    exports com.example.hotelbooking;
}