package finalmission.util;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.MethodMode;

@SpringBootTest
@DirtiesContext(methodMode = MethodMode.AFTER_METHOD)
public abstract class AbstractServiceTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void resetAutoIncrement() {
        jdbcTemplate.update("ALTER TABLE member ALTER COLUMN id RESTART WITH 1;");
        jdbcTemplate.update("ALTER TABLE reservation ALTER COLUMN id RESTART WITH 1;");
        jdbcTemplate.update("ALTER TABLE trainer ALTER COLUMN id RESTART WITH 1;");
        jdbcTemplate.update("ALTER TABLE lesson_time ALTER COLUMN id RESTART WITH 1;");
    }
}