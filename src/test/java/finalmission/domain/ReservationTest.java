package finalmission.domain;

import static org.assertj.core.api.Assertions.assertThatCode;

import java.time.LocalDate;
import java.time.LocalTime;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class ReservationTest {

    @Test
    void 예약_시간이_10분_단위인_경우_예외가_발생하지_않는다() {
        // given
        Member member = new Member();
        Room room = new Room();
        LocalDate date = LocalDate.MAX;
        LocalTime startTime = LocalTime.of(12, 0);
        LocalTime endTime = LocalTime.of(12, 50);

        // when && then
        assertThatCode(() -> new Reservation(member, room, date, startTime, endTime))
                .doesNotThrowAnyException();
    }

    @ParameterizedTest
    @CsvSource({
            "12:15, 12:30",
            "12:00, 12:43"
    })
    void 예약_시간이_10분_단위가_아닌_경우_예외가_발생한다(LocalTime startTime, LocalTime endTime) {
        // given
        Member member = new Member();
        Room room = new Room();
        LocalDate date = LocalDate.MAX;

        // when && then
        assertThatCode(() -> new Reservation(member, room, date, startTime, endTime))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("예약 시간은 10분 단위만 가능합니다.");
    }

    @ParameterizedTest
    @CsvSource({
            "12:00, 12:50",
            "12:00, 13:00"
    })
    void 예약_시간_범위가_1시간_이하인_경우_예외가_발생하지_않는다(LocalTime startTime, LocalTime endTime) {
        // given
        Member member = new Member();
        Room room = new Room();
        LocalDate date = LocalDate.MAX;

        // when && then
        assertThatCode(() -> new Reservation(member, room, date, startTime, endTime))
                .doesNotThrowAnyException();
    }

    @ParameterizedTest
    @CsvSource({
            "12:00, 13:10",
            "12:00, 14:00"
    })
    void 예약_시간_범위가_1시간_초과인_경우_예외가_발생한다(LocalTime startTime, LocalTime endTime) {
        // given
        Member member = new Member();
        Room room = new Room();
        LocalDate date = LocalDate.MAX;

        // when && then
        assertThatCode(() -> new Reservation(member, room, date, startTime, endTime))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("예약 시간은 1시간을 초과할 수 없습니다.");
    }

    @ParameterizedTest
    @CsvSource({
            "13:00, 13:00",
            "13:10, 13:00",
            "14:00, 13:00"
    })
    void 예약_시작_시간이_종료_시간_같거나_이후인_경우_예외가_발생한다(LocalTime startTime, LocalTime endTime) {
        // given
        Member member = new Member();
        Room room = new Room();
        LocalDate date = LocalDate.MAX;

        // when && then
        assertThatCode(() -> new Reservation(member, room, date, startTime, endTime))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("예약 시작 시간은 종료 시간과 같거나 이후일 수 없습니다.");
    }
}
