package vn.week3.util;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class JpaUtil {
    private static final EntityManagerFactory factory;

    static {
        try {
            factory = Persistence.createEntityManagerFactory("JPA_WebApp");
        } catch (Throwable ex) {
            System.err.println("Lỗi nghiêm trọng: Không thể khởi tạo EntityManagerFactory." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static EntityManager getEntityManager() {
        return factory.createEntityManager();
    }

    public static void shutdown() {
        if (factory != null && factory.isOpen()) {
            factory.close();
            System.out.println("EntityManagerFactory da duoc dong.");
        }
    }
}