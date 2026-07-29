package com.parkingms.utils;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import com.parkingms.models.Agency;
import com.parkingms.models.User;

import java.util.logging.Level;
import java.util.logging.Logger;

public class HibernateUtil {
    private static SessionFactory sessionFactory;

    static {
        Logger rootLogger = Logger.getLogger("");
        rootLogger.setLevel(Level.SEVERE);
        for (java.util.logging.Handler handler : rootLogger.getHandlers()) {
            handler.setLevel(Level.SEVERE);
        }
    }

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                Configuration config = new Configuration();
                
                // Database connection settings directly in code (Simple, like before)
                config.setProperty("hibernate.connection.driver_class", "org.postgresql.Driver");
                config.setProperty("hibernate.connection.url", "jdbc:postgresql://localhost:5432/parkingms");
                config.setProperty("hibernate.connection.username", "abhayverma");
                config.setProperty("hibernate.connection.password", "");
                
                // Hibernate settings
                config.setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
                config.setProperty("hibernate.hbm2ddl.auto", "update"); // Auto-creates tables
                config.setProperty("hibernate.show_sql", "false"); // Turn off raw SQL printing

                // Add annotated models
                config.addAnnotatedClass(Agency.class);
                config.addAnnotatedClass(User.class);
                config.addAnnotatedClass(com.parkingms.models.ParkRecord.class);
                
                sessionFactory = config.buildSessionFactory();
            } catch (Exception e) {
                System.err.println("SessionFactory creation failed: " + e);
            }
        }
        return sessionFactory;
    }
}
