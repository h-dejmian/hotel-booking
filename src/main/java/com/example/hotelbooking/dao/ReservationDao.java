package com.example.hotelbooking.dao;

import com.example.hotelbooking.models.Reservation;

import java.util.List;

public interface ReservationDao {
     void saveReservation(Reservation reservation);
     List<Reservation> getReservationsByHotelId(int hotelId);
     void deleteReservation(int id);

}
