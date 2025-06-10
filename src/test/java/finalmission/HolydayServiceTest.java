package finalmission;

import static org.assertj.core.api.Assertions.assertThat;

import finalmission.service.HolidayService;
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

//        assertThat(holidayService.checkHolyDay(LocalDate.now())).isFalse();
        assertThat(holidayService.checkHolyDay(LocalDate.of(2025,12,25))).isTrue();
        // when

        // then
    }

}
