package finalmission.member.domain;

import static org.assertj.core.api.Assertions.assertThatCode;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MemberTest {

    @DisplayName("이름, 이메일, 비밀번호로 Member 객체를 생성한다")
    @Test
    void create() {
        // given
        Name name = new Name("testName");
        Email email = new Email("test@example.com");
        Password password = new Password("password123", new StubPasswordEncoder());

        // when & then
        assertThatCode(() -> new Member(name, email, password))
                .doesNotThrowAnyException();
    }
}
