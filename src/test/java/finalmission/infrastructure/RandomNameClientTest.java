package finalmission.infrastructure;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@SpringBootTest
class RandomNameClientTest {

    @Autowired
    private RandomNameClient randomNameClient;

    @DisplayName("")
    @Test
    void test() {
        List<String> fullname = randomNameClient.getRandomNames("fullname", 1);
    }
}
