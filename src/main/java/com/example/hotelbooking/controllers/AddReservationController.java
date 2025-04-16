package com.example.hotelbooking.controllers;

import com.example.hotelbooking.dao.HotelDao;
import com.example.hotelbooking.dao.HotelDaoImpl;
import com.example.hotelbooking.dao.ReservationDao;
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
    private ReservationDao reservationDao;
    private HotelDao hotelDao;

    private int hotelId;

    public AddReservationController() {
    }

    @FXML
    public void saveReservation() {
        String guestName = guestNameField.getText();
        LocalDate date = datePicker.getValue();
        Hotel hotel = hotelDao.getHotelById(hotelId);

        if (guestName.isEmpty() || date == null) {
            System.out.println("Wypełnij wszystkie pola!");
            return;
        }

        reservationDao.saveReservation(new Reservation(guestName, date, hotel));
        mainController.loadReservationsForHotel(hotelId);

        System.out.println("Dodano rezerwację dla: " + guestName);
        ((Stage) guestNameField.getScene().getWindow()).close();
    }

    public void setHotelId(int hotelId) {
        this.hotelId = hotelId;
    }

    public void setReservationDao(ReservationDao reservationDao) {
        this.reservationDao = reservationDao;
    }

    public void setHotelDao(HotelDao hotelDao) {
        this.hotelDao = hotelDao;
    }

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    public void setGuestNameField(TextField guestNameField) {
        this.guestNameField = guestNameField;
    }

    public void setDatePicker(DatePicker datePicker) {
        this.datePicker = datePicker;
    }
}
