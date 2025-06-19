package finalmission;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.MethodMode;

@SpringBootTest
@DirtiesContext(methodMode = MethodMode.AFTER_METHOD)
class FinalMissionApplicationTests {

    @Test
    void contextLoads() {
    }

}
