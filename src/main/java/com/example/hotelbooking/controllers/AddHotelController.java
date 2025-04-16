package com.example.hotelbooking.controllers;

import com.example.hotelbooking.dao.HotelDao;
import com.example.hotelbooking.dao.HotelDaoImpl;
import com.example.hotelbooking.models.Hotel;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AddHotelController {
    @FXML
    private TextField hotelNameField;
    @FXML
    private MainController mainController;
    private HotelDao hotelDao;

    public AddHotelController() {
    }

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    @FXML
    private void saveHotel() {
        String hotelName = hotelNameField.getText();
        if (!hotelName.isEmpty()) {
            hotelDao.saveHotel(new Hotel(hotelName));
            System.out.println("Dodano hotel: " + hotelName);
            mainController.loadHotels();
            ((Stage) hotelNameField.getScene().getWindow()).close();
        }
    }

    public void setHotelDao(HotelDao hotelDao) {
        this.hotelDao = hotelDao;
    }
}