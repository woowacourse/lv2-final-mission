package finalmission.unit.domain;

import static org.assertj.core.api.Assertions.assertThat;

import finalmission.domain.Guest;
import finalmission.domain.Member;
import finalmission.domain.Price;
import finalmission.domain.Reservation;
import finalmission.domain.ReservationDateTime;
import java.time.LocalDate;
import java.time.LocalTime;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class ReservationTest {

    private Member member;
    private ReservationDateTime reservationDateTime;
    private Reservation reservation;

    @BeforeEach
    void setUp() {
        member = new Member(1L, "이름", "이메일", "비번");
        reservationDateTime = ReservationDateTime.createWithoutId(LocalDate.of(2025, 5, 5), LocalTime.of(10, 0));
        reservation = Reservation.createWithoutId(reservationDateTime, member, new Guest(10), Price.WEEKDAY);
    }

    @ParameterizedTest
    @CsvSource({"1, true", "2, false"})
    void 예약_멤버_확인(Long id, boolean result) {
        assertThat(reservation.isReservedBy(id)).isEqualTo(result);
    }

    @Test
    void 예약_갱신() {
        ReservationDateTime newDateTime = ReservationDateTime.createWithoutId(
                LocalDate.of(2025, 5, 6),
                LocalTime.of(11, 0));
        reservation.updateReservation(newDateTime, new Guest(11));
        SoftAssertions soft = new SoftAssertions();
        soft.assertThat(reservation.getDateTime()).isEqualTo(newDateTime);
        soft.assertThat(reservation.getGuest().getSize()).isEqualTo(11);
        soft.assertAll();
    }
}
