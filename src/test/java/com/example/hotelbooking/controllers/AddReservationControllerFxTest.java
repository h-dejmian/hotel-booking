package com.example.hotelbooking.controllers;

import com.example.hotelbooking.dao.HotelDao;
import com.example.hotelbooking.dao.ReservationDao;
import com.example.hotelbooking.models.Hotel;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.ApplicationTest;

import java.time.LocalDate;

import static org.mockito.Mockito.*;

@ExtendWith(ApplicationExtension.class)
public class AddReservationControllerFxTest extends ApplicationTest {

    private AddReservationController controller;
    private ReservationDao reservationDao;
    private HotelDao hotelDao;
    private MainController mainController;

    private TextField guestField;
    private DatePicker datePicker;

    @Override
    public void start(Stage stage) {
        controller = new AddReservationController();

        guestField = new TextField();
        datePicker = new DatePicker();

        controller.setGuestNameField(guestField);
        controller.setDatePicker(datePicker);

        mainController = mock(MainController.class);
        reservationDao = mock(ReservationDao.class);
        hotelDao = mock(HotelDao.class);

        controller.setMainController(mainController);
        controller.setReservationDao(reservationDao);
        controller.setHotelDao(hotelDao);
        controller.setHotelId(1);

        Scene scene = new Scene(guestField, 300, 200);
        stage.setScene(scene);
        stage.show();
    }

    @BeforeEach
    void setupMocks() {
        Hotel hotel = new Hotel("Test Hotel");
        when(hotelDao.getHotelById(1)).thenReturn(hotel);
    }

    @Test
    void testSaveReservation_withValidData(FxRobot robot) {
        robot.interact(() -> {
            guestField.setText("Anna Nowak");
            datePicker.setValue(LocalDate.of(2025, 5, 15));
        });

        robot.interact(() -> controller.saveReservation());

        verify(reservationDao).saveReservation(argThat(res ->
                res.getGuest().equals("Anna Nowak")
                        && res.getDate().equals(LocalDate.of(2025, 5, 15))
                        && res.getHotel().getName().equals("Test Hotel")
        ));

        verify(mainController).loadReservationsForHotel(1);
    }
}
