package finalmission.holiday.presentation.dto.request;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class HolidayCreateWebRequestTest {

    @Test
    void 날짜는_null이_될_수_없다() {
        // Given
        // When
        // Then
        assertThatThrownBy(() -> new HolidayCreateWebRequest(null, "프리"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("날짜는 null이 될 수 없습니다.");
    }

    @Test
    void 이름은_빈_값이_될_수_없다() {
        // Given
        // When
        // Then
        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThatThrownBy(() -> new HolidayCreateWebRequest(LocalDate.now().plusDays(1), null))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("이름은 빈 값이 될 수 없습니다.");
            softAssertions.assertThatThrownBy(() -> new HolidayCreateWebRequest(LocalDate.now().plusDays(1), ""))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("이름은 빈 값이 될 수 없습니다.");
            softAssertions.assertThatThrownBy(() -> new HolidayCreateWebRequest(LocalDate.now().plusDays(1), "    "))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("이름은 빈 값이 될 수 없습니다.");
        });
    }
}