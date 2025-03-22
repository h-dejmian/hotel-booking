package com.example.hotelbooking.database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseManager {
    private static final String URL = "jdbc:sqlite:hotels.db";

    static {
        try (Connection conn = DriverManager.getConnection(URL)) {
            conn.createStatement().execute("CREATE TABLE IF NOT EXISTS hotels (id INTEGER PRIMARY KEY, name TEXT)");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try (Connection conn = DriverManager.getConnection(URL)) {
            conn.createStatement().execute("CREATE TABLE IF NOT EXISTS reservations (id INTEGER PRIMARY KEY, guest TEXT, date TEXT)");
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

    public static void addReservation(String guest, String date) {
        String sql = "INSERT INTO reservations (guest, date) VALUES (?, ?)";

        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, guest);
            pstmt.setString(2, date);
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

    public static List<String> getAllReservations() {
        List<String> reservations = new ArrayList<>();
        String sql = "SELECT guest FROM reservations";

        try (Connection conn = DriverManager.getConnection(URL);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)){
            while(rs.next()) {
                reservations.add(rs.getString("guest"));
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

        return reservations;
    }
}
