package finalmission.unit.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import finalmission.domain.Member;
import finalmission.domain.WrongPasswordException;
import org.junit.jupiter.api.Test;

public class MemberTest {

    @Test
    void 비밀번호가_일치하지_않으면_예외가_발생한다() {
        // given
        Member member = new Member(1L, "email1@domain.com", "name", "1234");
        // when & then
        assertThatThrownBy(() -> member.validatePassword("WrongPassword"))
                .isInstanceOf(WrongPasswordException.class);
    }
}
