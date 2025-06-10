package finalmission.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class ScheduleTest {

    @ParameterizedTest
    @MethodSource("provideTargetScheduleCases")
    void 이용가능한_스케줄인지_확인한다(
        Schedule targetSchedule,
        boolean expected
    ) {
        // given
        var schedule = createSchedule(LocalTime.of(18, 0));

        // when
        var result = schedule.isAvailableSchedule(targetSchedule);

        // then
        assertThat(result).isEqualTo(expected);

    }

    static Stream<Arguments> provideTargetScheduleCases() {
        return Stream.of(
            Arguments.of(createSchedule(LocalTime.of(17, 0)), true),
            Arguments.of(createSchedule(LocalTime.of(17, 30)), false),
            Arguments.of(createSchedule(LocalTime.of(18, 0)), false),
            Arguments.of(createSchedule(LocalTime.of(18, 30)), false),
            Arguments.of(createSchedule(LocalTime.of(19, 0)), true)
        );
    }

    private static Schedule createSchedule(LocalTime time) {
        return new Schedule(Room.백스윙, LocalDate.of(2025, 6, 10), time);
    }
}
