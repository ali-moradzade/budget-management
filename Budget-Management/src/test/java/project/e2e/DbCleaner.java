package project.e2e;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class DbCleaner {

    @PersistenceContext
    private EntityManager em;

    @Transactional
    public void clean() {

        // IMPORTANT: order matters because of FK constraints
        em.createNativeQuery("TRUNCATE TABLE expense_participants CASCADE").executeUpdate();
        em.createNativeQuery("TRUNCATE TABLE expenses CASCADE").executeUpdate();
        em.createNativeQuery("TRUNCATE TABLE friends CASCADE").executeUpdate();
        em.createNativeQuery("TRUNCATE TABLE categories CASCADE").executeUpdate();
        em.createNativeQuery("TRUNCATE TABLE users CASCADE").executeUpdate();
    }
}