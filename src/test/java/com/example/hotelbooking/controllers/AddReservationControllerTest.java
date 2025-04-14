package com.example.hotelbooking.controllers;

import com.example.hotelbooking.JfxTestHelper;
import com.example.hotelbooking.dao.HotelDao;
import com.example.hotelbooking.dao.ReservationDao;
import com.example.hotelbooking.models.Hotel;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.time.LocalDate;

public class AddReservationControllerTest {
    private AddReservationController addReservationController = new AddReservationController();

    @BeforeAll
    static void initJfx() {
        JfxTestHelper.initJavaFx();
    }

    @Test
    void testSaveReservation() {
        //Given
        TextField guestField = Mockito.mock(TextField.class);
        Mockito.when(guestField.getText()).thenReturn("Jan Kowalski");

        javafx.scene.Scene mockScene = Mockito.mock(javafx.scene.Scene.class);
        javafx.stage.Stage mockStage = Mockito.mock(javafx.stage.Stage.class);

        Mockito.when(guestField.getScene()).thenReturn(mockScene);
        Mockito.when(mockScene.getWindow()).thenReturn(mockStage);
        Mockito.when(guestField.getScene()).thenReturn(mockScene);

        DatePicker datePicker = new DatePicker(LocalDate.of(2025, 4, 10));
        MainController mainController = Mockito.mock(MainController.class);
        addReservationController.setMainController(mainController);

        Hotel hotel = new Hotel("Test Hotel");

        addReservationController.setGuestNameField(guestField);
        addReservationController.setDatePicker(datePicker);
        addReservationController.setHotelId(1);

        try (
                MockedStatic<HotelDao> hotelDaoMock = Mockito.mockStatic(HotelDao.class);
                MockedStatic<ReservationDao> reservationDaoMock = Mockito.mockStatic(ReservationDao.class)
        ) {
            hotelDaoMock.when(() -> HotelDao.getHotelById(1)).thenReturn(hotel);

            //When
            addReservationController.saveReservation();

            //Then
            reservationDaoMock.verify(() ->
                            ReservationDao.saveReservation(Mockito.argThat(res ->
                                    res.getGuest().equals("Jan Kowalski")
                                            && res.getDate().equals(LocalDate.of(2025, 4, 10))
                                            && res.getHotel().getName().equals("Test Hotel")
                            )),
                    Mockito.times(1)
            );

            Mockito.verify(mainController).loadReservationsForHotel(1);
            Mockito.verify(mockStage).close();
        }
    }
}
