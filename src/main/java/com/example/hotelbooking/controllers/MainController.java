package com.example.hotelbooking.controllers;

import com.example.hotelbooking.database.DatabaseManager;
import com.example.hotelbooking.models.Reservation;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

import java.io.IOException;

public class MainController {
    @FXML
    private ListView<String> hotelList;
    @FXML
    private TableView<Reservation> reservationList;

    @FXML
    private void addHotel() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/hotelbooking/add-hotel-view.fxml"));
            Scene scene = new Scene(loader.load(), 300, 200);
            Stage stage = new Stage();
            stage.setTitle("Nowy Hotel");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void addReservation() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/hotelbooking/add-reservation-view.fxml"));
            Scene scene = new Scene(loader.load(), 300, 200);
            Stage stage = new Stage();
            stage.setTitle("Nowa Rezerwacja");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void initialize() {
        hotelList.getItems().addAll(DatabaseManager.getAllHotels());
    }
}