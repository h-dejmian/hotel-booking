package com.example.hotelbooking.controllers;

import com.example.hotelbooking.dao.HotelDao;
import com.example.hotelbooking.dao.ReservationDao;
import com.example.hotelbooking.database.DatabaseManager;
import com.example.hotelbooking.models.Hotel;
import com.example.hotelbooking.models.Reservation;
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
        Hotel hotel = HotelDao.getHotelById(hotelId);

        if (guestName.isEmpty() || date == null) {
            System.out.println("Wypełnij wszystkie pola!");
            return;
        }

        ReservationDao.saveReservation(new Reservation(guestName, date, hotel));
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
