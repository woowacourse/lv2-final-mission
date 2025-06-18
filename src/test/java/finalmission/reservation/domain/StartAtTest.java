package finalmission.reservation.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class StartAtTest {

    @DisplayName("유효한 시작 시간으로 StartAt 객체를 생성한다")
    @Test
    void createWithValidStartTime() {
        // given
        LocalTime validStartTime = LocalTime.of(10, 0);

        // when & then
        assertThatCode(() -> new StartAt(validStartTime))
                .doesNotThrowAnyException();
    }

    @DisplayName("시작 시간이 오전 10시 이후인 경우 StartAt 객체를 생성한다")
    @ParameterizedTest
    @ValueSource(ints = {10, 12, 15, 18, 22})
    void createWithStartTimeAfterOpenTime(int hour) {
        // given
        LocalTime validStartTime = LocalTime.of(hour, 0);

        // when & then
        assertThatCode(() -> new StartAt(validStartTime))
                .doesNotThrowAnyException();
    }

    @DisplayName("시작 시간이 오전 10시 이전이면 예외가 발생한다")
    @ParameterizedTest
    @ValueSource(ints = {0, 5, 8, 9})
    void createWithStartTimeBeforeOpenTime(int hour) {
        // given
        LocalTime invalidStartTime = LocalTime.of(hour, 0);

        // when & then
        assertThatThrownBy(() -> new StartAt(invalidStartTime))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("예약 가능 시작 시간은 오전 10시입니다");
    }

    @DisplayName("getValue 메서드로 LocalTime 값을 반환한다")
    @Test
    void getValue() {
        // given
        LocalTime startTime = LocalTime.of(12, 0);
        StartAt startAt = new StartAt(startTime);

        // when
        LocalTime result = startAt.getValue();

        // then
        assertThat(result).isEqualTo(startTime);
    }
}