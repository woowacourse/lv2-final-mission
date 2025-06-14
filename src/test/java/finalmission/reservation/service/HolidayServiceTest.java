package finalmission.reservation.service;

import finalmission.client.HolidayClient;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class HolidayServiceTest {

    @MockitoBean
    private HolidayClient holidayClient;

    @Autowired
    private HolidayService holidayService;

    @Test
    @DisplayName("공휴일이면 예외를 던짐")
    void validateHolidayTest1() {
        LocalDateTime reservationDateTime = LocalDateTime.of(2025, 6, 6, 10, 0, 0);

        when(holidayClient.getHoliday(reservationDateTime.getYear(), getMonthValue(reservationDateTime)))
                .thenReturn(List.of(LocalDate.of(2025, 6, 6)));

        assertThatThrownBy(() -> holidayService.validateHoliday(reservationDateTime))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("해당 날짜는 공휴일입니다.");
    }

    @Test
    @DisplayName("정상 흐름 진행 테스트")
    void validateHolidayTest2() {
        LocalDateTime reservationDateTime = LocalDateTime.of(2025, 6, 8, 10, 0, 0);

        when(holidayClient.getHoliday(reservationDateTime.getYear(), getMonthValue(reservationDateTime)))
                .thenReturn(List.of(LocalDate.of(2025, 6, 6)));

        assertThatCode(() -> holidayService.validateHoliday(reservationDateTime))
                .doesNotThrowAnyException();
    }

    private String getMonthValue(final LocalDateTime reservationDateTime) {
        String monthValue = String.valueOf(reservationDateTime.getMonth().getValue());
        if (monthValue.length() == 1) {
            monthValue = "0" + monthValue;
        }
        return monthValue;
    }
}