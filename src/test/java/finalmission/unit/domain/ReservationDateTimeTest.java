package finalmission.unit.domain;

import static org.assertj.core.api.Assertions.assertThatCode;

import finalmission.domain.ReservationDateTime;
import finalmission.exception.InvalidReservationTimeException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class ReservationDateTimeTest {

    static Stream<Arguments> validReservationTime() {
        return Stream.of(
                Arguments.arguments(LocalTime.of(8, 0)),
                Arguments.arguments(LocalTime.of(15, 0)),
                Arguments.arguments(LocalTime.of(23, 0))
        );
    }

    static Stream<Arguments> invalidReservationTime() {
        return Stream.of(
                Arguments.arguments(LocalTime.of(1, 0)),
                Arguments.arguments(LocalTime.of(7, 0)),
                Arguments.arguments(LocalTime.of(23, 30))
        );
    }

    @ParameterizedTest
    @MethodSource("validReservationTime")
    void 올바른_예약_시간(LocalTime time) {
        assertThatCode(() -> ReservationDateTime.createWithoutId(LocalDate.of(2025, 5, 5), time))
                .doesNotThrowAnyException();
    }

    @ParameterizedTest
    @MethodSource("invalidReservationTime")
    void 잘못된_예약_시간(LocalTime time) {
        assertThatCode(() -> ReservationDateTime.createWithoutId(LocalDate.of(2025, 5, 5), time))
                .isInstanceOf(InvalidReservationTimeException.class)
                .hasMessage("예약은 08시 이후, 24시 이전만 가능합니다.");
    }
}
