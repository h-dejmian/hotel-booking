package com.example.hotelbooking.models;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "reservations")
public class Reservation {
    @Id
    @GeneratedValue
    private int id;
    private String guest;
    private LocalDate date;
    @ManyToOne
    @JoinColumn(name = "hotel_id", nullable = false)
    private Hotel hotel;

    public Reservation(int id, String guest, LocalDate date, int hotel_id) {
        this.id = id;
        this.guest = guest;
        this.date = date;
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
}
