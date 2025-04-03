package com.example.hotelbooking.dao;

import com.example.hotelbooking.HibernateUtil;
import com.example.hotelbooking.models.Hotel;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class HotelDao {

    public static void saveHotel(Hotel hotel) {
        Transaction transaction = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.persist(hotel);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    public static List<Hotel> getAllHotels() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from Hotel", Hotel.class).list();
        }
    }

    public static Hotel getHotelById(int id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.find(Hotel.class, id);
        }
    }

    public static int getHotelIdByName(String name) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("select h.id from Hotel h where h.name = :name", Integer.class)
                    .setParameter("name", name)
                    .uniqueResult();
        }
    }

    public static String getHotelNameById(int id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("select h.name from Hotel h where h.id = :id", String.class)
                    .setParameter("id", id)
                    .uniqueResult();
        }
    }
}
