package com.example.hotelbooking.dao;

import com.example.hotelbooking.HibernateUtil;
import com.example.hotelbooking.models.Reservation;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class ReservationDaoImpl implements ReservationDao {
    @Override
    public void saveReservation(Reservation reservation) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.merge(reservation);
            transaction.commit();
        }
        catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }
    @Override
    public List<Reservation> getReservationsByHotelId(int hotelId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from Reservation where hotel.id = :hotelId", Reservation.class)
                    .setParameter("hotelId", hotelId)
                    .list();
        }
    }

    @Override
    public void deleteReservation(int id) {
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
