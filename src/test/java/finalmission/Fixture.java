package finalmission;

import java.sql.Connection;
import java.sql.Statement;
import org.springframework.jdbc.core.JdbcTemplate;

public class Fixture {

    public static void resetH2TableIds(JdbcTemplate jdbcTemplate) {
        jdbcTemplate.execute((Connection connection) -> {
            try (Statement statement = connection.createStatement()) {
                statement.execute("SET REFERENTIAL_INTEGRITY FALSE");
                statement.execute("TRUNCATE TABLE reservation");
                statement.execute("ALTER TABLE reservation ALTER COLUMN id RESTART WITH 1");
                statement.execute("TRUNCATE TABLE member");
                statement.execute("ALTER TABLE member ALTER COLUMN id RESTART WITH 1");
                statement.execute("TRUNCATE TABLE yoga_class");
                statement.execute("ALTER TABLE yoga_class ALTER COLUMN id RESTART WITH 1");
                statement.execute("SET REFERENTIAL_INTEGRITY TRUE");
            }
            return null;
        });
    }
}
