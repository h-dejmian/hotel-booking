package com.example.hotelbooking.controllers;

import com.example.hotelbooking.dao.HotelDao;
import com.example.hotelbooking.dao.ReservationDao;
import com.example.hotelbooking.models.Hotel;
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
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

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
    private TableColumn<Reservation, Void> actionColumn;

    private ObservableList<Reservation> reservationData = FXCollections.observableArrayList();

    @FXML
    private void addHotel() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/hotelbooking/add-hotel-view.fxml"));
            Scene scene = new Scene(loader.load(), 300, 200);
            scene.getStylesheets().add(getClass().getResource("/styles/styles.css").toExternalForm());

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
            scene.getStylesheets().add(getClass().getResource("/styles/styles.css").toExternalForm());

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
    private void showReservationDetails(Reservation reservation) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/hotelbooking/reservation-details.fxml"));
            Scene scene = new Scene(loader.load(), 300, 200);
            scene.getStylesheets().add(getClass().getResource("/styles/styles.css").toExternalForm());

            ReservationDetailsController controller = loader.getController();
            controller.setMainController(this);
            controller.setHotelId(selectedHotelId);
            controller.setReservation(reservation);

            Stage stage = new Stage();
            stage.setTitle("Szczegóły Rezerwacji");
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

        configureActionColumn();
    }

    private void configureActionColumn() {
        actionColumn.setCellFactory(param -> new TableCell<>() {
            private final Button deleteButton = new Button();
            private final Button detailsButton = new Button();

            {
                ImageView icon = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/icons/question.png"))));
                icon.setFitWidth(16);
                icon.setFitHeight(16);
                detailsButton.setGraphic(icon);

                icon = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/icons/trash.jpg"))));
                icon.setFitWidth(16);
                icon.setFitHeight(16);
                deleteButton.setGraphic(icon);

                deleteButton.setStyle("-fx-background-color: transparent; -fx-cursor: hand;");
                detailsButton.setStyle("-fx-background-color: transparent; -fx-cursor: hand;");

                deleteButton.setOnAction(event -> {
                    Reservation reservation = getTableView().getItems().get(getIndex());
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Potwierdzenie");
                    alert.setHeaderText("Usuwanie rezerwacji");
                    alert.setContentText("Czy na pewno chcesz usunąć tę rezerwację?");

                    alert.showAndWait().ifPresent(response -> {
                        if (response == ButtonType.OK) {
                            ReservationDao.deleteReservation(reservation.getId());
                            getTableView().getItems().remove(reservation);
                        }
                    });
                });

                detailsButton.setOnAction(event -> {
                    Reservation reservation = getTableView().getItems().get(getIndex());
                    showReservationDetails(reservation);
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    HBox buttonBox = new HBox(5, detailsButton, deleteButton);
                    setGraphic(buttonBox);
                }
            }
        });
    }



    public void loadReservationsForHotel(String hotelName) {
        int hotelId = getHotelIdByName(hotelName);

        if (hotelId != -1) {
            reservationData.setAll(ReservationDao.getReservationsByHotelId(hotelId));
        }
    }

    public void loadReservationsForHotel(int hotelId) {
        if (hotelId != -1) {
            reservationData.setAll(ReservationDao.getReservationsByHotelId(hotelId));
        }
    }

    public void loadHotels() {
        hotelList.getItems().clear();
        hotelList.getItems().addAll(HotelDao.getAllHotels().stream().map(Hotel::getName).toList());
    }

    private int getHotelIdByName(String name) {
        return HotelDao.getHotelIdByName(name);
    }

    private String getHotelNameById(int id) {
        return HotelDao.getHotelNameById(id);
    }

    public ObservableList<Reservation> getReservationData() {
        return reservationData;
    }

    public void setReservationList(TableView<Reservation> reservationList) {
        this.reservationList = reservationList;
    }

    public void setGuestColumn(TableColumn<Reservation, String> guestColumn) {
        this.guestColumn = guestColumn;
    }

    public void setDateColumn(TableColumn<Reservation, String> dateColumn) {
        this.dateColumn = dateColumn;
    }

    public void setReservationData(ObservableList<Reservation> reservationData) {
        this.reservationData = reservationData;
        this.reservationList.setItems(reservationData);
    }
}