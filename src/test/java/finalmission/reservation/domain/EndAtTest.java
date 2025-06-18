package finalmission.reservation.domain;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class EndAtTest {

    @DisplayName("유효한 종료 시간으로 EndAt 객체를 생성한다")
    @Test
    void createWithValidEndTime() {
        // given
        LocalTime validEndTime = LocalTime.of(22, 30);

        // when & then
        assertThatCode(() -> new EndAt(validEndTime))
                .doesNotThrowAnyException();
    }

    @DisplayName("종료 시간이 오후 10시 30분 이후이면 예외가 발생한다")
    @Test
    void createWithEndTimeAfterCloseTime() {
        // given
        LocalTime invalidEndTime = LocalTime.of(22, 31);

        // when & then
        assertThatThrownBy(() -> new EndAt(invalidEndTime))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
