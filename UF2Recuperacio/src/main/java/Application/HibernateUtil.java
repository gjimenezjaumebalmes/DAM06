package Application;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {
    private static SessionFactory sessionFactory;

    public static synchronized SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                sessionFactory = new Configuration().configure().buildSessionFactory();
            } catch (Throwable ex) {
                System.err.println("Error en la inicialización: " + ex);
                throw new ExceptionInInitializerError(ex);
            }
        }
        return sessionFactory;
    }
}