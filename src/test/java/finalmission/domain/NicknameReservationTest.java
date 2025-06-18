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

    @DisplayName("수정 이전과 이후 닉네임이 같을 시 예외가 발생한다")
    @Test
    void aaa() {
        // given
        Nickname nickname = new Nickname("레오");
        NicknameReservation reservation = new NicknameReservation(new Member(), nickname);

        // when, then
        assertThatThrownBy(() -> reservation.updateNickname(nickname))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("수정 전 닉네임과 동일합니다.");
    }

    @DisplayName("이미 확정된 예약을 수정할 경우 예외가 발생한다")
    @Test
    void aaaa() {
        // given
        NicknameReservation reservation = new NicknameReservation(new Member(), new Nickname("레오"));
        reservation.confirm();

        // when, then
        assertThatThrownBy(() -> reservation.updateNickname(new Nickname("레오레")))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이미 확정되었습니다.");
    }
}

