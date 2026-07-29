package com.parkingms.dao;

import com.parkingms.models.User;
import com.parkingms.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;

public class UserDao {
    public UserDao() {}

    public List<User> loadAll() {
        List<User> users = new ArrayList<>();
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<User> query = session.createQuery("from User", User.class);
            users = query.list();
        } catch (Exception e) {
            System.err.println("Error loading users from database: " + e.getMessage());
        }
        return users;
    }

    public void saveAll(List<User> users) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            for (User u : users) {
                session.merge(u); // Using merge instead of saveOrUpdate as it handles detached entities well in newer Hibernate
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            System.err.println("Error saving users to database: " + e.getMessage());
        }
    }
}
