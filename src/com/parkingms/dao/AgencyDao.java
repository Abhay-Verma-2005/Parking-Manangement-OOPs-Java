package com.parkingms.dao;

import com.parkingms.models.Agency;
import com.parkingms.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;

public class AgencyDao {
    public AgencyDao() {}

    public List<Agency> loadAll() {
        List<Agency> agencies = new ArrayList<>();
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Agency> query = session.createQuery("from Agency", Agency.class);
            agencies = query.list();
        } catch (Exception e) {
            System.err.println("Error loading agencies from database: " + e.getMessage());
        }
        return agencies;
    }

    public void saveAll(List<Agency> agencies) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            for (Agency a : agencies) {
                session.merge(a);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            System.err.println("Error saving agencies to database: " + e.getMessage());
        }
    }
}