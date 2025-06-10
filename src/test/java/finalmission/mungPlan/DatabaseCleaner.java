package finalmission.mungPlan;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class DatabaseCleaner {
    private final JdbcTemplate jdbcTemplate;

    public DatabaseCleaner(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void clean() {
        jdbcTemplate.execute("SET REFERENTIAL_INTEGRITY FALSE");
        jdbcTemplate.execute("TRUNCATE TABLE reservations");
        jdbcTemplate.execute("ALTER TABLE reservations ALTER COLUMN reservation_id RESTART WITH 1");
        jdbcTemplate.execute("TRUNCATE TABLE plan_dates");
        jdbcTemplate.execute("ALTER TABLE plan_dates ALTER COLUMN plan_date_id RESTART WITH 1");
        jdbcTemplate.execute("TRUNCATE TABLE users");
        jdbcTemplate.execute("SET REFERENTIAL_INTEGRITY TRUE");
    }
}
