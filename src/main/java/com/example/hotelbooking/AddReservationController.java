package com.example.hotelbooking;

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
    private void saveReservation() {
        String guestName = guestNameField.getText();
        LocalDate date = datePicker.getValue();

        if (guestName.isEmpty() || date == null) {
            System.out.println("Wypełnij wszystkie pola!");
            return;
        }

        DatabaseManager.addReservation(guestName, date.toString());
        System.out.println("Dodano rezerwację dla: " + guestName);
        ((Stage) guestNameField.getScene().getWindow()).close();
    }
}
