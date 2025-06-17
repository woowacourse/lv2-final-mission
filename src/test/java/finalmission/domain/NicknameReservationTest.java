package finalmission.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class NicknameReservationTest {

    @DisplayName("이미 확정된 예약을 확정할 경우 예외가 발생한다")
    @Test
    void aa() {
        // given
        NicknameReservation reservation = new NicknameReservation(new Member(), new Nickname("레오"));
        reservation.confirm();

        // when, then
        assertThatThrownBy(() -> reservation.confirm())
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이미 확정되었습니다.");
    }
}

