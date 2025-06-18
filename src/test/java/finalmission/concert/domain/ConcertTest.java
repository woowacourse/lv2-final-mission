package finalmission.concert.domain;

import finalmission.common.exception.InvalidInputException;
import finalmission.concert.domain.Concert;
import finalmission.venue.domain.Venue;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

class ConcertTest {

    @Test
    void 콘서트가_생성된다() {
        // Given
        final String title = "시소 콘서트";
        final String artist = "시소";
        final LocalDateTime concertDate = LocalDateTime.now();
        final Venue venue = new Venue("공연장 이름", "공연장 주소");
        final Long price = 100000L;
        final String description = "시소의 단독 공연";

        // When
        final Concert actual = new Concert(title, artist, concertDate, venue, price, description);

        // Then
        assertSoftly(softAssertions -> {
            softAssertions.assertThat(actual.getTitle()).isEqualTo(title);
            softAssertions.assertThat(actual.getArtist()).isEqualTo(artist);
            softAssertions.assertThat(actual.getConcertDate()).isEqualTo(concertDate);
            softAssertions.assertThat(actual.getVenue()).isEqualTo(venue);
            softAssertions.assertThat(actual.getPrice()).isEqualTo(price);
        });
    }

    @Test
    void 콘서트의_이름_공연자_일시_공연장_금액_설명이_null이_아니라면_예외가_발생하지_않는다() {
        // Given
        final String title = "시소 콘서트";
        final String artist = "시소";
        final LocalDateTime concertDate = LocalDateTime.now();
        final Venue venue = new Venue("공연장 이름", "공연장 주소");
        final Long price = 100000L;
        final String description = "시소의 단독 공연";

        // When & Then
        assertThatNoException().isThrownBy(() -> new Concert(title, artist, concertDate, venue, price, description));
    }

    @Test
    void 콘서트의_이름_또는_주소가_null이라면_예외가_발생한다() {
        // Given
        final String title = "시소 콘서트";
        final String artist = "시소";
        final LocalDateTime concertDate = LocalDateTime.now();
        final Venue venue = new Venue("공연장 이름", "공연장 주소");
        final Long price = 100000L;
        final String description = "시소의 단독 공연";

        final String emptyValue = "";
        final String nullValue = null;

        // When & Then
        assertSoftly(softAssertions -> {
            softAssertions.assertThatThrownBy(() -> new Concert(nullValue, artist, concertDate, venue, price, description))
                    .isInstanceOf(InvalidInputException.class)
                    .hasMessageContaining("제목은 null이거나 빈 값일 수 없습니다.");

            softAssertions.assertThatThrownBy(() -> new Concert(title, nullValue, concertDate, venue, price, description))
                    .isInstanceOf(InvalidInputException.class)
                    .hasMessageContaining("아티스트는 null이거나 빈 값일 수 없습니다.");

            softAssertions.assertThatThrownBy(() -> new Concert(title, artist, concertDate, null, price, description))
                    .isInstanceOf(InvalidInputException.class)
                    .hasMessageContaining("공연장은 null일 수 없습니다.");

            softAssertions.assertThatThrownBy(() -> new Concert(title, artist, concertDate, venue, 0L, description))
                    .isInstanceOf(InvalidInputException.class)
                    .hasMessageContaining("가격은 0보다 커야 합니다.");

            softAssertions.assertThatThrownBy(() -> new Concert(title, artist, concertDate, venue, price, emptyValue))
                    .isInstanceOf(InvalidInputException.class)
                    .hasMessageContaining("설명은 null이거나 빈 값일 수 없습니다.");
        });
    }
}
