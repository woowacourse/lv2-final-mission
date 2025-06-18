package finalmission.reservation.domain;

import static org.assertj.core.api.Assertions.assertThatCode;

import java.time.LocalDate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ReservationDateTest {

    @DisplayName("LocalDate로 ReservationDate 객체를 생성한다")
    @Test
    void create() {
        // given
        LocalDate date = LocalDate.of(2025, 6, 16);

        // when & then
        assertThatCode(() -> new ReservationDate(date))
                .doesNotThrowAnyException();
    }
}
