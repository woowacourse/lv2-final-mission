package finalmission.venue.domain;

import finalmission.common.exception.InvalidInputException;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

class VenueTest {

    @Test
    void 공연장이_생성된다() {
        // Given
        final String name = "공연장 이름";
        final String address = "공연장 주소";

        // When
        final Venue actual = new Venue(name, address);

        // Then
        assertSoftly(softAssertions -> {
            softAssertions.assertThat(actual.getName()).isEqualTo(name);
            softAssertions.assertThat(actual.getAddress()).isEqualTo(address);
        });
    }

    @Test
    void 공연장의_이름과_주소가_null_또는_빈값이_아니라면_예외가_발생하지_않는다() {
        // Given
        final String name = "공연장 이름";
        final String address = "공연장 주소";

        // When & Then
        assertThatNoException().isThrownBy(() -> new Venue(name, address));
    }

    @Test
    void 공연장의_이름_또는_주소가_null이라면_예외가_발생한다() {
        // Given
        final String name = "공연장 이름";
        final String address = "공연장 주소";
        final String emptyValue = "";
        final String nullValue = null;

        // When & Then
        assertSoftly(softAssertions -> {
            softAssertions.assertThatThrownBy(() -> new Venue(nullValue, address))
                    .isInstanceOf(InvalidInputException.class)
                    .hasMessageContaining("이름은 null이거나 빈 값일 수 없습니다.");

            softAssertions.assertThatThrownBy(() -> new Venue(name, nullValue))
                    .isInstanceOf(InvalidInputException.class)
                    .hasMessageContaining("주소는 null이거나 빈 값일 수 없습니다.");

            softAssertions.assertThatThrownBy(() -> new Venue(emptyValue, address))
                    .isInstanceOf(InvalidInputException.class)
                    .hasMessageContaining("이름은 null이거나 빈 값일 수 없습니다.");

            softAssertions.assertThatThrownBy(() -> new Venue(name, emptyValue))
                    .isInstanceOf(InvalidInputException.class)
                    .hasMessageContaining("주소는 null이거나 빈 값일 수 없습니다.");
        });
    }
}
