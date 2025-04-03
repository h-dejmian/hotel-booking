package com.example.hotelbooking.models;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "reservations")
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String guest;
    private LocalDate date;
    @ManyToOne
    @JoinColumn(name = "hotel_id", nullable = false)
    private Hotel hotel;

    public Reservation() {
    }

    public Reservation(String guest, LocalDate date, Hotel hotel) {
        this.guest = guest;
        this.date = date;
        this.hotel = hotel;
    }

    public String getGuest() {
        return guest;
    }

    public LocalDate getDate() {
        return date;
    }

    public int getId() {
        return id;
    }

    public Hotel getHotel() {
        return hotel;
    }

    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }
}
