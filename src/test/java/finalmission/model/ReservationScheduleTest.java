package finalmission.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ReservationScheduleTest {
    @Test
    @DisplayName("예약 시작 시각이 예약 종료 시각보다 이후인 경우 예외를 던진다")
    void test1() {
        // given
        LocalDate reservationDate = LocalDate.now();
        LocalTime startAt = LocalTime.of(12, 30);
        LocalTime endAt = startAt.minusHours(12);

        // when & then
        assertThatThrownBy(() -> new ReservationSchedule(reservationDate, startAt, endAt))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("오늘 이후에 대한 예약을 요청한다면 예외를 던진다")
    void test2() {
        // given
        LocalDate reservationDate = LocalDate.now().minusDays(1);
        LocalTime startAt = LocalTime.of(12, 30);
        LocalTime endAt = startAt.plusHours(12);

        // when & then
        assertThatThrownBy(() -> new ReservationSchedule(reservationDate, startAt, endAt))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("예약 날짜가 오늘인데 현재 시각보다 이전에 대해 예약을 요청한다면 예외를 던진다")
    void test4() {
        // given
        LocalDateTime now = LocalDateTime.now();

        LocalDate reservationDate = now.toLocalDate();
        LocalTime startAt = now.toLocalTime().minusHours(2);
        LocalTime endAt = startAt.plusMinutes(30);

        // when & then
        assertThatThrownBy(() -> new ReservationSchedule(reservationDate, startAt, endAt))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("예약 날짜가 오늘이고 현재 시각보다 이후에 대해 예약을 요청한다면 예외를 던지지 않는다")
    void test3() {
        // given
        LocalDateTime now = LocalDateTime.now();

        LocalDate reservationDate = now.toLocalDate();
        LocalTime startAt = now.toLocalTime().plusHours(1);
        LocalTime endAt = startAt.plusMinutes(1);

        // when & then
        assertThatCode(() -> new ReservationSchedule(reservationDate, startAt, endAt))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("예약 날짜가 오늘 이후라면 예약 시각이 현재보다 이전이더라도 예외를 던지지 않는다")
    void test5() {
        // given
        LocalDateTime now = LocalDateTime.now();

        LocalDate reservationDate = now.toLocalDate().plusDays(1);
        LocalTime startAt = now.toLocalTime().minusHours(1);
        LocalTime endAt = startAt.plusMinutes(1);

        // when & then
        assertThatCode(() -> new ReservationSchedule(reservationDate, startAt, endAt))
                .doesNotThrowAnyException();
    }
}
