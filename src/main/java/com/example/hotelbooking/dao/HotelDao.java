package com.example.hotelbooking.dao;

import com.example.hotelbooking.models.Hotel;

import java.util.List;

public interface HotelDao {
    public void saveHotel(Hotel hotel);
    public List<Hotel> getAllHotels();
    public Hotel getHotelById(int id);
    public int getHotelIdByName(String name);
    public String getHotelNameById(int id);
}
