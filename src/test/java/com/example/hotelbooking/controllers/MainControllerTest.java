package com.example.hotelbooking;

import com.example.hotelbooking.controllers.MainController;
import com.example.hotelbooking.dao.ReservationDao;
import com.example.hotelbooking.models.Hotel;
import com.example.hotelbooking.models.Reservation;
import javafx.collections.FXCollections;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class MainControllerTest {

    private MainController controller;

    @BeforeAll
    static void initJfx() {
        JfxTestHelper.initJavaFx();
    }

    @BeforeEach
    void setup() {
        controller = new MainController();

        TableView<Reservation> tableView = new TableView<>();
        TableColumn<Reservation, String> guestCol = new TableColumn<>("Gość");
        TableColumn<Reservation, String> dateCol = new TableColumn<>("Data");

        controller.setReservationList(tableView);
        controller.setGuestColumn(guestCol);
        controller.setDateColumn(dateCol);

        controller.setReservationData(FXCollections.observableArrayList());
    }

    @Test
    void testLoadReservationsForHotel_addsReservations() {
        // given
        List<Reservation> reservations = List.of(
                new Reservation("Jan Kowalski", LocalDate.now(), new Hotel("Test Hotel"))
        );

        try (MockedStatic<ReservationDao> mockedDao = Mockito.mockStatic(ReservationDao.class)) {
            //when
            mockedDao.when(() -> ReservationDao.getReservationsByHotelId(1))
                    .thenReturn(reservations);

            controller.loadReservationsForHotel(1);

            //then
            assertEquals(1, controller.getReservationData().size());
            assertEquals("Jan Kowalski", controller.getReservationData().get(0).getGuest());
        }
    }
}