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
                statement.execute("TRUNCATE TABLE yoga_session");
                statement.execute("ALTER TABLE yoga_session ALTER COLUMN id RESTART WITH 1");
                statement.execute("TRUNCATE TABLE yoga_course");
                statement.execute("ALTER TABLE yoga_course ALTER COLUMN id RESTART WITH 1");
                statement.execute("SET REFERENTIAL_INTEGRITY TRUE");
            }
            return null;
        });
    }
}
