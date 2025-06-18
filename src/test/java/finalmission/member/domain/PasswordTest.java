package finalmission.member.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class PasswordTest {

    private final PasswordEncoder passwordEncoder = new StubPasswordEncoder();

    @DisplayName("비밀번호와 인코더로 Password를 생성한다")
    @Test
    void createWithValidPassword() {
        // given
        String password = "password123";

        // when & then
        assertThatCode(() -> new Password(password, passwordEncoder))
                .doesNotThrowAnyException();
    }

    @DisplayName("Password의 값은 암호화되어 저장된다")
    @Test
    void getValue() {
        // given
        String rawPassword = "password123";
        Password password = new Password(rawPassword, passwordEncoder);

        // when
        String encodedValue = password.getValue();

        // then
        assertThat(encodedValue).isNotEqualTo(rawPassword);
    }

    @DisplayName("비밀번호가 빈 값이면 예외가 발생한다")
    @ParameterizedTest
    @ValueSource(strings = {"", " "})
    void createWithBlankPassword(String blankPassword) {
        // when & then
        assertThatThrownBy(() -> new Password(blankPassword, passwordEncoder))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("null 비밀번호로 생성하면 예외가 발생한다")
    @Test
    void createWithNullPassword() {
        // when & then
        assertThatThrownBy(() -> new Password(null, passwordEncoder))
                .isInstanceOf(IllegalArgumentException.class);
    }
}