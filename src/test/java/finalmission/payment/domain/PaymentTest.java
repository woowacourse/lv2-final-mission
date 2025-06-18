package finalmission.payment.domain;

import finalmission.common.exception.InvalidInputException;
import finalmission.concert.domain.Concert;
import finalmission.member.domain.Member;
import finalmission.reservation.domain.Reservation;
import finalmission.seat.domain.Seat;
import finalmission.venue.domain.Venue;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

class PaymentTest {

    @Test
    void 결제내역이_생성된다() {
        // Given
        final String tid = "temp_tid_123";
        final Long amount = 10000L;

        final Member member = new Member("siso", "siso@gmail.com", "password");
        final Venue venue = new Venue("공연장", "서울쓰");
        final Concert concert = new Concert(
                "Rock Concert",
                "시소",
                LocalDateTime.now(),
                venue,
                10000L,
                "amazing"
        );
        final Seat seat = new Seat(1, venue);
        final Reservation reservation = new Reservation(member, concert, seat);

        // When
        final Payment actual = new Payment(tid, amount, reservation);

        // Then
        assertSoftly(softAssertions -> {
            softAssertions.assertThat(actual.getTid()).isEqualTo(tid);
            softAssertions.assertThat(actual.getAmount()).isEqualTo(amount);
            softAssertions.assertThat(actual.getReservation()).isEqualTo(reservation);
        });
    }

    @Test
    void 결제내역의_tid_amount_reservation이_null이_아니라면_예외가_발생하지_않는다() {
        // Given
        final String tid = "temp_tid_123";
        final Long amount = 10000L;

        final Member member = new Member("siso", "siso@gmail.com", "password");
        final Venue venue = new Venue("공연장", "서울쓰");
        final Concert concert = new Concert(
                "Rock Concert",
                "시소",
                LocalDateTime.now(),
                venue,
                10000L,
                "amazing"
        );
        final Seat seat = new Seat(1, venue);
        final Reservation reservation = new Reservation(member, concert, seat);

        // When & Then
        assertThatNoException().isThrownBy(() -> new Payment(tid, amount, reservation));
    }

    @Test
    void 결제내역의_tid_amount_reservation이_null이라면_예외가_발생한다() {
        // Given
        final String tid = "temp_tid_123";
        final Long amount = 10000L;

        final Member member = new Member("siso", "siso@gmail.com", "password");
        final Venue venue = new Venue("공연장", "서울쓰");
        final Concert concert = new Concert(
                "Rock Concert",
                "시소",
                LocalDateTime.now(),
                venue,
                10000L,
                "amazing"
        );
        final Seat seat = new Seat(1, venue);
        final Reservation reservation = new Reservation(member, concert, seat);

        // When & Then
        assertSoftly(softAssertions -> {
            softAssertions.assertThatThrownBy(() -> new Payment(null, amount, reservation))
                    .isInstanceOf(InvalidInputException.class)
                    .hasMessageContaining("tid는 null이거나 빈 값일 수 없습니다.");
            softAssertions.assertThatThrownBy(() -> new Payment(tid, 0L, reservation))
                    .isInstanceOf(InvalidInputException.class)
                    .hasMessageContaining("amount는 0보다 커야 합니다.");
            softAssertions.assertThatThrownBy(() -> new Payment(tid, amount, null))
                    .isInstanceOf(InvalidInputException.class)
                    .hasMessageContaining("reservation은 null일 수 없습니다.");
        });
    }
}
