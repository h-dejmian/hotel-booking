package com.example.hotelbooking.controllers;

import com.example.hotelbooking.dao.ReservationDao;
import com.example.hotelbooking.models.Reservation;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ReservationDetailsController {

    @FXML
    private VBox viewBox;
    @FXML
    private VBox editBox;
    @FXML
    private Label guestNameLabel;
    @FXML
    private Label hotelNameLabel;
    @FXML
    private Label dateLabel;
    @FXML
    private DatePicker reservationDate;
    @FXML
    private TextField guestNameField;

    @FXML
    private Button editButton;
    @FXML
    private Button saveButton;
    @FXML
    private MainController mainController;

    private int hotelId;
    private Reservation reservation;

    private void updateDetails() {
        guestNameLabel.setText("Gość: " + reservation.getGuest());
        hotelNameLabel.setText("Hotel: " + reservation.getHotel().getName());
        dateLabel.setText("Data: " + reservation.getDate().toString());

        guestNameField.setText(reservation.getGuest());
        reservationDate.setValue(reservation.getDate());
    }

    @FXML
    private void closeWindow() {
        Stage stage = (Stage) guestNameLabel.getScene().getWindow();
        stage.close();
    }

    public void setReservation(Reservation reservation) {
        this.reservation = reservation;

        guestNameLabel.setText("Gość: " + reservation.getGuest());
        hotelNameLabel.setText("Hotel: " + reservation.getHotel().getName());
        dateLabel.setText("Data: " + reservation.getDate().toString());
    }

    @FXML
    public void onEditClick() {
        viewBox.setVisible(false);
        editBox.setVisible(true);
        saveButton.setVisible(true);
        editButton.setDisable(true);
    }

    @FXML
    public void onSaveClick() {
        reservation.setGuest(guestNameField.getText());
        reservation.setDate(reservationDate.getValue());

        ReservationDao.saveReservation(reservation);

        updateDetails();
        mainController.loadReservationsForHotel(hotelId);

        editBox.setVisible(false);
        viewBox.setVisible(true);
        saveButton.setVisible(false);
        editButton.setDisable(false);
    }

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }
    public void setHotelId(int hotelId) {
        this.hotelId = hotelId;
    }
}
