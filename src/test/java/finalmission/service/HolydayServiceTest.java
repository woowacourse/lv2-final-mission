package finalmission.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class HolydayServiceTest {

    @Autowired
    HolidayService holidayService;

    @DisplayName("외부 api 테스트")
    @Test
    void apiTest() {
        // given

        assertThat(holidayService.isHolyDay(LocalDate.now())).isFalse();
        assertThat(holidayService.isHolyDay(LocalDate.of(2025,6,6))).isTrue();
        // when

        // then
    }

}
