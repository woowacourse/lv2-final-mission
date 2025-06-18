package finalmission.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class NicknameTest {

    @DisplayName("닉네임의 길이 제한을 벗어날 시 예외가 발생한다")
    @ValueSource(strings = {"레", "레레레레오"})
    @ParameterizedTest
    void aa(String nickname) {
        // when
        // then
        assertThatThrownBy(() -> new Nickname(nickname))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("크루 닉네임은 최소 2글자, 최대 4글자여야합니다.");
    }
}
