package finalmission.reservation.domain;

import finalmission.common.exception.InvalidInputException;
import finalmission.concert.domain.Concert;
import finalmission.member.domain.Member;
import finalmission.seat.domain.Seat;
import finalmission.venue.domain.Venue;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

class ReservationTest {

    @Test
    void 예약이_생성된다() {
        // Given
        final Member member = new Member();
        final Concert concert = new Concert();
        final Seat seat = new Seat();

        // When
        final Reservation actual = new Reservation(member, concert, seat);

        // Then
        assertSoftly(softAssertions -> {
            softAssertions.assertThat(actual.getMember()).isEqualTo(member);
            softAssertions.assertThat(actual.getConcert()).isEqualTo(concert);
            softAssertions.assertThat(actual.getSeat()).isEqualTo(seat);
        });
    }

    @Test
    void 예약의_예약자_콘서트_좌석이_null이_아니라면_예외가_발생하지_않는다() {
        // Given
        final Member member = new Member("siso", "siso@gmail.com", "123");
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

        // When & Then
        assertThatNoException().isThrownBy(() -> new Reservation(member, concert, seat));
    }

    @Test
    void 예약의_이름_또는_주소가_null이라면_예외가_발생한다() {
        // Given
        final Member member = new Member("siso", "siso@gmail.com", "123");
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

        // When & Then
        assertSoftly(softAssertions -> {
            softAssertions.assertThatThrownBy(() -> new Reservation(null, concert, seat))
                    .isInstanceOf(InvalidInputException.class)
                    .hasMessageContaining("예약자는 null일 수 없습니다.");
            softAssertions.assertThatThrownBy(() -> new Reservation(member, null, seat))
                    .isInstanceOf(InvalidInputException.class)
                    .hasMessageContaining("공연은 null일 수 없습니다.");
            softAssertions.assertThatThrownBy(() -> new Reservation(member, concert, null))
                    .isInstanceOf(InvalidInputException.class)
                    .hasMessageContaining("좌석은 null일 수 없습니다.");
        });
    }

    @Test
    void 좌석을_변경할_수_있다() {
        // Given
        final Member member = new Member("siso", "siso@gmail.com", "123");
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
        final Seat newSeat = new Seat(2, venue);
        final Reservation reservation = new Reservation(member, concert, seat);

        // When
        reservation.changeSeat(newSeat);

        // Then
        assertThat(reservation.getSeat()).isEqualTo(newSeat);
    }
}
