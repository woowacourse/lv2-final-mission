package finalmission.infra;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class HolidayCheckerClientTest {

    @Autowired
    private HolidayCheckerClient client;

    @Test
    void isHoliday() {
        var result = client.isHoliday(LocalDate.of(2025, 5, 5));
        System.out.println("result = " + result);
    }
}
