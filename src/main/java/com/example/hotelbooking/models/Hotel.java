package com.example.hotelbooking.models;

import java.util.ArrayList;
import java.util.List;

public class Hotel {
    private static int idCount;
    private int id;
    private String name;
    private List<Reservation> reservations = new ArrayList<>();

    public Hotel(String name) {
        this.id = ++idCount;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public List<Reservation> getReservations() {
        return reservations;
    }
}
