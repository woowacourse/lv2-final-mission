package finalmission.infrastructure;

import java.time.YearMonth;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@SpringBootTest
class OpenApiClientTest {

    @Autowired
    private OpenApiClient openApiClient;

    @DisplayName("")
    @Test
    void test() {
        //given
        openApiClient.getHolidays(YearMonth.of(2025, 5));
        //when

        //then

    }
}
