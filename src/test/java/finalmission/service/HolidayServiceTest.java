package finalmission.service;

import finalmission.domain.HolidayClient;
import finalmission.dto.response.HolidayElementResponse;
import finalmission.exception.custom.InvalidValueException;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class HolidayServiceTest {

    @Mock
    HolidayClient holidayClient;

    @InjectMocks
    HolidayService holidayService;

    @Test
    @DisplayName("대상 날짜가 주말이 아니면 통과한다.")
    void checkHolidayWhenNotHoliday() {
        //given
        LocalDate date = LocalDate.of(2025, 5, 8);
        when(holidayClient.getHolidayData(any(LocalDate.class)))
                .thenReturn(List.of(new HolidayElementResponse("어린이날", "Y", LocalDate.of(2025, 5, 5)),
                        new HolidayElementResponse("우테코 기념일", "N", LocalDate.of(2025, 5, 6))));

        //when //then
        assertDoesNotThrow(() -> holidayService.checkHoliday(date));
    }

    @ParameterizedTest
    @CsvSource(value = {"2025-05-03", "2025-05-04"})
    @DisplayName("대상 날짜가 주말이면 예외를 던진다.")
    void throwExceptionWhenWeekend(LocalDate date) {
        //given //when //then
        assertThatThrownBy(() -> holidayService.checkHoliday(date))
                .isInstanceOf(InvalidValueException.class)
                .hasMessageContaining("주말에는 예약할 수 없습니다.");
    }

    @Test
    @DisplayName("대상 날짜가 공휴일이면 예외를 던진다.")
    void throwExceptionWhenHoliday() {
        //given
        LocalDate date = LocalDate.of(2025, 5, 5);
        when(holidayClient.getHolidayData(any(LocalDate.class)))
                .thenReturn(List.of(new HolidayElementResponse("어린이날", "Y", LocalDate.of(2025, 5, 5)),
                        new HolidayElementResponse("우테코 기념일", "N", LocalDate.of(2025, 5, 6))));

        //when //then
        assertThatThrownBy(() -> holidayService.checkHoliday(date))
                .isInstanceOf(InvalidValueException.class)
                .hasMessageContaining("공휴일에는 예약할 수 없습니다.");
    }
}
