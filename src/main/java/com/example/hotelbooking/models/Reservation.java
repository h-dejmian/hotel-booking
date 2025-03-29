package com.example.hotelbooking.models;

import java.time.LocalDate;

public class Reservation {
    private static int idCount;
    private int id;
    private String guest;
    private LocalDate date;
    private int hotel_id;

    public Reservation(String guest, LocalDate date) {
        this.id = ++idCount;
        this.guest = guest;
        this.date = date;
    }

    public Reservation(int id, String guest, LocalDate date) {
        this.id = id;
        this.guest = guest;
        this.date = date;
    }

    public Reservation(int id, String guest, LocalDate date, int hotel_id) {
        this.id = id;
        this.guest = guest;
        this.date = date;
        this.hotel_id = hotel_id;
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
