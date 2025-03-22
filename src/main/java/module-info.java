module com.example.hotelbooking {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.hotelbooking to javafx.fxml;
    exports com.example.hotelbooking;
    exports com.example.hotelbooking.controllers;
    opens com.example.hotelbooking.controllers to javafx.fxml;
    exports com.example.hotelbooking.database;
    opens com.example.hotelbooking.database to javafx.fxml;
}