package com.parkingms.dao;

import com.parkingms.models.ParkRecord;
import com.parkingms.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;

public class ParkingDao {
    
    public ParkingDao() {}

    public List<ParkRecord> loadAll() {
        List<ParkRecord> parkings = new ArrayList<>();
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<ParkRecord> query = session.createQuery("from com.parkingms.models.ParkRecord", ParkRecord.class);
            parkings = query.list();
        } catch (Exception e) {
            System.err.println("Error loading parkings from database: " + e.getMessage());
        }
        return parkings;
    }

    public void save(ParkRecord record) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.persist(record);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            System.err.println("Error saving parking record to database: " + e.getMessage());
        }
    }

    public void delete(ParkRecord record) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.remove(record);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            System.err.println("Error deleting parking record from database: " + e.getMessage());
        }
    }
}
