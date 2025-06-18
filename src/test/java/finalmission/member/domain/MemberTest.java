package finalmission.member.domain;

import finalmission.common.exception.InvalidInputException;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

class MemberTest {

    @Test
    void 회원이_생성된다() {
        // Given
        final String name = "siso";
        final String email = "siso@gmail.com";
        final String password = "password123";

        // When
        final Member actual = new Member(name, email, password);

        // Then
        assertThat(actual.getName()).isEqualTo(name);
    }

    @Test
    void 회원의_이름_이메일_비밀번호가_null_또는_빈값이_아니라면_예외가_발생하지_않는다() {
        // Given
        final String name = "siso";
        final String email = "siso@gmail.com";
        final String password = "password123";

        // When & Then
        assertThatNoException().isThrownBy(() -> new Member(name, email, password));
    }

    @Test
    void 회원의_이름_이메일_비밀번호가_null_또는_빈값이라면_예외가_발생한다() {
        // Given
        final String name = "siso";
        final String email = "siso@gmail.com";
        final String password = "password123";
        final String emptyValue = "";
        final String nullValue = null;

        // When & Then
        assertSoftly(softAssertions -> {
            softAssertions.assertThatThrownBy(() -> new Member(emptyValue, email, password))
                    .isInstanceOf(InvalidInputException.class)
                    .hasMessageContaining("이름은 null이거나 빈 값일 수 없습니다.");
            softAssertions.assertThatThrownBy(() -> new Member(name, emptyValue, password))
                    .isInstanceOf(InvalidInputException.class)
                    .hasMessageContaining("이메일은 null이거나 빈 값일 수 없습니다.");
            softAssertions.assertThatThrownBy(() -> new Member(name, email, emptyValue))
                    .isInstanceOf(InvalidInputException.class)
                    .hasMessageContaining("비밀번호는 null이거나 빈 값일 수 없습니다.");
            softAssertions.assertThatThrownBy(() -> new Member(nullValue, email, password))
                    .isInstanceOf(InvalidInputException.class)
                    .hasMessageContaining("이름은 null이거나 빈 값일 수 없습니다.");
            softAssertions.assertThatThrownBy(() -> new Member(name, nullValue, password))
                    .isInstanceOf(InvalidInputException.class)
                    .hasMessageContaining("이메일은 null이거나 빈 값일 수 없습니다.");
            softAssertions.assertThatThrownBy(() -> new Member(name, email, nullValue))
                    .isInstanceOf(InvalidInputException.class)
                    .hasMessageContaining("비밀번호는 null이거나 빈 값일 수 없습니다.");
        });
    }

    @Test
    void 회원의_이메일_형식이_올바르지_않다면_예외가_발생한다() {
        // Given
        final String name = "siso";
        final String email = "siso.com";
        final String password = "password123";

        // When & Then
        assertThatThrownBy(() -> new Member(name, email, password))
                .isInstanceOf(InvalidInputException.class)
                .hasMessageContaining("이메일 형식이 올바르지 않습니다.");
    }
}
