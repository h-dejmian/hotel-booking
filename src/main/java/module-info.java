module com.example.hotelbooking {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires org.hibernate.orm.core;
    requires jakarta.persistence;
    requires java.naming;

    opens com.example.hotelbooking to javafx.fxml;
    exports com.example.hotelbooking;
    exports com.example.hotelbooking.controllers;
    opens com.example.hotelbooking.controllers to javafx.fxml;
    exports com.example.hotelbooking.database;
    opens com.example.hotelbooking.database to javafx.fxml;
    opens com.example.hotelbooking.models to org.hibernate.orm.core,javafx.base;
}