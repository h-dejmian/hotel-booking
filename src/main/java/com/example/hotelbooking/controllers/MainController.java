package com.example.hotelbooking.controllers;

import com.example.hotelbooking.database.DatabaseManager;
import com.example.hotelbooking.models.Reservation;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;

public class MainController {
    @FXML
    private ListView<String> hotelList;
    @FXML
    private TableView<Reservation> reservationList;
    private int selectedHotelId = -1;
    private int selectedReservationId = -1;

    @FXML
    private TableColumn<Reservation, String> guestColumn;
    @FXML
    private TableColumn<Reservation, String> dateColumn;
    @FXML
    private TableColumn<Reservation, Void> deleteColumn;

    private ObservableList<Reservation> reservationData = FXCollections.observableArrayList();

    @FXML
    private void addHotel() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/hotelbooking/add-hotel-view.fxml"));
            Scene scene = new Scene(loader.load(), 300, 200);

            AddHotelController addHotelController = loader.getController();
            addHotelController.setMainController(this);

            Stage stage = new Stage();
            stage.setTitle("Nowy Hotel");
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void addReservation() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/hotelbooking/add-reservation-view.fxml"));
            Scene scene = new Scene(loader.load(), 300, 200);

            AddReservationController controller = loader.getController();
            controller.setHotelId(selectedHotelId);
            controller.setMainController(this);

            Stage stage = new Stage();
            stage.setTitle("Nowa Rezerwacja");
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void initialize() {
        loadHotels();

        guestColumn.setCellValueFactory(new PropertyValueFactory<>("guest"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        reservationList.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        reservationList.setItems(reservationData);

        hotelList.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                selectedHotelId = getHotelIdByName(newValue);
                loadReservationsForHotel(newValue);
            }
        });

        addDeleteButtonToTable();
    }

    private void addDeleteButtonToTable() {
        deleteColumn.setCellFactory( param -> new TableCell<>() {
            private final Button deleteButton = new Button("Usuń");

            {
                ImageView icon = new ImageView(new Image(getClass().getResourceAsStream("/icons/trash.jpg")));
                icon.setFitWidth(16);
                icon.setFitHeight(16);
                deleteButton.setGraphic(icon);

                deleteButton.setStyle("-fx-background-color: transparent; -fx-cursor: hand;");
                deleteButton.setOnMouseEntered(e -> deleteButton.setStyle("-fx-background-color: #ffdddd; -fx-cursor: hand;"));
                deleteButton.setOnMouseExited(e -> deleteButton.setStyle("-fx-background-color: transparent; -fx-cursor: hand;"));

                deleteButton.setOnAction(event -> {
                    Reservation reservation = getTableView().getItems().get(getIndex());
                    DatabaseManager.deleteReservationById(reservation.getId());
                    loadReservationsForHotel(getHotelNameById(selectedHotelId));
                });

                deleteButton.setOnAction(event -> {
                    Reservation reservation = getTableView().getItems().get(getIndex());
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Potwierdzenie");
                    alert.setHeaderText("Usuwanie rezerwacji");
                    alert.setContentText("Czy na pewno chcesz usunąć tę rezerwację?");

                    alert.showAndWait().ifPresent(response -> {
                        if (response == ButtonType.OK) {
                            DatabaseManager.deleteReservationById(reservation.getId());
                            getTableView().getItems().remove(reservation);
                        }
                    });
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                }
                else {
                    setGraphic(deleteButton);
                }
            }
        });
    }

    public void loadReservationsForHotel(String hotelName) {
        int hotelId = getHotelIdByName(hotelName);

        if (hotelId != -1) {
            reservationData.setAll(DatabaseManager.getReservationsByHotelId(hotelId));
        }
    }

    public void loadReservationsForHotel(int hotelId) {
        if (hotelId != -1) {
            reservationData.setAll(DatabaseManager.getReservationsByHotelId(hotelId));
        }
    }

    public void loadHotels() {
        hotelList.getItems().clear();
        hotelList.getItems().addAll(DatabaseManager.getAllHotels());
    }

    private int getHotelIdByName(String name) {
        return DatabaseManager.getHotelIdByName(name);
    }

    private String getHotelNameById(int id) {
        return DatabaseManager.getHotelNameById(id);
    }
}