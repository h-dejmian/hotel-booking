package com.example.hotelbooking.database;

import com.example.hotelbooking.models.Reservation;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DatabaseManager {
    private static final String URL = "jdbc:sqlite:hotels.db";

    static {
        try (Connection conn = DriverManager.getConnection(URL)) {
            conn.createStatement().execute("CREATE TABLE IF NOT EXISTS hotels (id INTEGER PRIMARY KEY, name TEXT)");
            conn.createStatement().execute("CREATE TABLE IF NOT EXISTS reservations (id INTEGER PRIMARY KEY, guest TEXT, date TEXT, hotel_id INTEGER, FOREIGN KEY(hotel_id) REFERENCES hotels(id))");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void addHotel(String name) {
        String sql = "INSERT INTO hotels (name) VALUES (?)";

        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void addReservation(String guest, String date, int hotelId) {
        String sql = "INSERT INTO reservations (guest, date, hotel_id) VALUES (?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, guest);
            pstmt.setString(2, date);
            pstmt.setInt(3, hotelId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static List<String> getAllHotels() {
        List<String> hotels = new ArrayList<>();
        String sql = "SELECT name FROM hotels";

        try (Connection conn = DriverManager.getConnection(URL);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                hotels.add(rs.getString("name"));
            }
            System.out.println("Załadowano hotele: " + hotels);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return hotels;
    }

    public static String getHotelById(int id) {
        String sql = "SELECT name FROM hotels WHERE id = ?";

        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            return rs.getString("name");

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static int getHotelIdByName(String name) {
        String sql = "SELECT id FROM hotels WHERE name = ?";

        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, name);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt("id");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return -1;
    }

    public static List<Reservation> getReservationsByHotelId(int hotelId) {
        List<Reservation> reservations = new ArrayList<>();
        String sql = "SELECT * FROM reservations WHERE hotel_id = ?";

        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, hotelId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                reservations.add(new Reservation(
                        rs.getInt("id"),
                        rs.getString("guest"),
                        LocalDate.parse(rs.getString("date")),
                        rs.getInt("hotel_id")
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return reservations;
    }
}
