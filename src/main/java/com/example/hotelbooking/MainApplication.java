package com.example.hotelbooking;

import jakarta.persistence.EntityManager;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("main-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 600);
        stage.setTitle("Hotel Booking");
        stage.setScene(scene);
        stage.show();

        EntityManager em = HibernateUtil.getEntityManager();
        System.out.println("✅ Połączenie z bazą działa!");
        em.close();
        HibernateUtil.close();
    }


    public static void main(String[] args) {
        launch();
    }
}