package finalmission.support;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Component
public class DataInitializer {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void clear() {
        entityManager.createNativeQuery("""
                                SET REFERENTIAL_INTEGRITY FALSE;
                                TRUNCATE TABLE crew;
                                ALTER TABLE crew ALTER COLUMN id RESTART WITH 1;
                                TRUNCATE TABLE coach;
                                ALTER TABLE coach ALTER COLUMN id RESTART WITH 1;
                                TRUNCATE TABLE meeting;
                                ALTER TABLE meeting ALTER COLUMN id RESTART WITH 1;
                                SET REFERENTIAL_INTEGRITY TRUE;
                                """).executeUpdate();
    }
}
