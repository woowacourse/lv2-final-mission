package finalmission.holiday.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class HolidayCheckServiceTest {

    @Autowired
    HolidayCheckService holidayCheckService;

    @Disabled
    @Test
    @DisplayName("공휴일 확인 API 연동 테스트")
    void 공휴일_확인_API_연동_테스트1() {
        // given
        LocalDate date = LocalDate.of(2025, 6, 6);

        // when
        boolean holiday = holidayCheckService.isHoliday(date);

        // then
        assertThat(holiday).isTrue();
    }

    @Disabled
    @Test
    @DisplayName("공휴일 확인 API 연동 테스트")
    void 공휴일_확인_API_연동_테스트2() {
        // given
        LocalDate date = LocalDate.of(2025, 6, 5);

        // when
        boolean holiday = holidayCheckService.isHoliday(date);

        // then
        assertThat(holiday).isFalse();
    }
}
