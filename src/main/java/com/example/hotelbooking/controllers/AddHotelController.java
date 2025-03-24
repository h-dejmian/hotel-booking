package com.example.hotelbooking.controllers;

import com.example.hotelbooking.database.DatabaseManager;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AddHotelController {
    @FXML
    private TextField hotelNameField;
    @FXML
    private MainController mainController;

    public AddHotelController() {
    }

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    @FXML
    private void saveHotel() {
        String hotelName = hotelNameField.getText();
        if (!hotelName.isEmpty()) {
            DatabaseManager.addHotel(hotelName);
            System.out.println("Dodano hotel: " + hotelName);
            mainController.loadHotels();
            ((Stage) hotelNameField.getScene().getWindow()).close();
        }
    }
}