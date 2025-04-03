package com.example.hotelbooking.dao;

import com.example.hotelbooking.HibernateUtil;
import com.example.hotelbooking.models.Reservation;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class ReservationDao {
    public static void saveReservation(Reservation reservation) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.persist(reservation);
            transaction.commit();
        }
        catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public static List<Reservation> getReservationsByHotelId(int hotelId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from Reservation where hotel.id = :hotelId", Reservation.class)
                    .setParameter("hotelId", hotelId)
                    .list();
        }
    }

    public static void deleteReservation(int id) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            Reservation reservation = session.find(Reservation.class, id);
            if (reservation != null) {
                session.remove(reservation);
            }

            transaction.commit();
        }
        catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }
}
