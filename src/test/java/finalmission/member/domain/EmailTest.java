package finalmission.member.domain;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class EmailTest {

    @DisplayName("유효한 이메일로 Email을 생성한다")
    @Test
    void createWithValidEmail() {
        // given
        String validEmail = "test@example.com";

        // when & then
        assertThatCode(() -> new Email(validEmail))
                .doesNotThrowAnyException();
    }

    @DisplayName("이메일이 빈 값이면 예외가 발생한다")
    @ParameterizedTest
    @ValueSource(strings = {"", " "})
    void createWithBlankEmail(String blankEmail) {
        // when & then
        assertThatThrownBy(() -> new Email(blankEmail))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이메일은 빈 값일 수 없습니다.");
    }

    @DisplayName("null로 이메일을 생성하면 예외가 발생한다")
    @Test
    void createWithNullEmail() {
        // when & then
        assertThatThrownBy(() -> new Email(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이메일은 빈 값일 수 없습니다.");
    }

    @DisplayName("이메일 형식에 맞지 않으면 예외가 발생한다")
    @ParameterizedTest
    @ValueSource(strings = {"test", "test@", "test@example", "@example.com", "test@.com"})
    void createWithInvalidEmailFormat(String invalidEmail) {
        // when & then
        assertThatThrownBy(() -> new Email(invalidEmail))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이메일의 형식에 맞지 않습니다.");
    }
}
