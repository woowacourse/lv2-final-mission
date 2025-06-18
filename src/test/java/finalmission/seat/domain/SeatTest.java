package finalmission.seat.domain;

import finalmission.common.exception.InvalidInputException;
import finalmission.venue.domain.Venue;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

class SeatTest {

    @Test
    void 좌석이_생성된다() {
        // Given
        final int seatNumber = 1;
        final Venue venue = new Venue("공연장 이름", "공연장 주소");

        // When
        final Seat actual = new Seat(seatNumber, venue);

        // Then
        assertSoftly(softAssertions -> {
            softAssertions.assertThat(actual.getSeatNumber()).isEqualTo(seatNumber);
            softAssertions.assertThat(actual.getVenue()).isEqualTo(venue);
        });
    }

    @Test
    void 좌석의_번호와_공연장이_null이_아니라면_예외가_발생하지_않는다() {
        // Given
        final int seatNumber = 1;
        final Venue venue = new Venue("공연장 이름", "공연장 주소");

        // When & Then
        assertThatNoException().isThrownBy(() -> new Seat(seatNumber, venue));
    }

    @Test
    void 좌석의_이름_또는_주소가_null이라면_예외가_발생한다() {
        // Given
        final int seatNumber = 1;
        final Venue venue = new Venue("공연장 이름", "공연장 주소");
        final int minusValue = -1;
        final int zeroValue = 0;
        final Venue nullValue = null;

        // When & Then
        assertSoftly(softAssertions -> {
            softAssertions.assertThatThrownBy(() -> new Seat(minusValue, venue))
                    .isInstanceOf(InvalidInputException.class)
                    .hasMessageContaining("좌석 번호는 null이거나 0 이하일 수 없습니다.");

            softAssertions.assertThatThrownBy(() -> new Seat(zeroValue, venue))
                    .isInstanceOf(InvalidInputException.class)
                    .hasMessageContaining("좌석 번호는 null이거나 0 이하일 수 없습니다.");

            softAssertions.assertThatThrownBy(() -> new Seat(seatNumber, nullValue))
                    .isInstanceOf(InvalidInputException.class)
                    .hasMessageContaining("공연장은 null일 수 없습니다.");
        });
    }

}
