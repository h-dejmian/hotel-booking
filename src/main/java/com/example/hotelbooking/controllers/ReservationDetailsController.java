package com.example.hotelbooking.controllers;

import com.example.hotelbooking.models.Reservation;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class ReservationDetailsController {
    @FXML
    private Label guestNameLabel;
    @FXML
    private Label hotelNameLabel;
    @FXML
    private Label reservationDateLabel;

    private Reservation reservation;

    public void setReservation(Reservation reservation) {
        this.reservation = reservation;

        guestNameLabel.setText("Gość: " + reservation.getGuest());
        hotelNameLabel.setText("Hotel: " + reservation.getHotel().getName());
        reservationDateLabel.setText("Data: " + reservation.getDate());
    }

    @FXML
    private void closeWindow() {
        Stage stage = (Stage) guestNameLabel.getScene().getWindow();
        stage.close();
    }
}
