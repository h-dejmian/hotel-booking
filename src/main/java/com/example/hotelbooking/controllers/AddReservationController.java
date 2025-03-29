package com.example.hotelbooking.controllers;

import com.example.hotelbooking.database.DatabaseManager;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.time.LocalDate;

public class AddReservationController {
    @FXML
    private TextField guestNameField;
    @FXML
    private DatePicker datePicker;
    @FXML
    private MainController mainController;

    private int hotelId;

    public AddReservationController() {
    }

    @FXML
    private void saveReservation() {
        String guestName = guestNameField.getText();
        LocalDate date = datePicker.getValue();

        if (guestName.isEmpty() || date == null) {
            System.out.println("Wypełnij wszystkie pola!");
            return;
        }

        DatabaseManager.addReservation(guestName, date.toString(), hotelId);
        mainController.loadReservationsForHotel(hotelId);

        System.out.println("Dodano rezerwację dla: " + guestName);
        ((Stage) guestNameField.getScene().getWindow()).close();
    }

    public void setHotelId(int hotelId) {
        this.hotelId = hotelId;
    }

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }
}
