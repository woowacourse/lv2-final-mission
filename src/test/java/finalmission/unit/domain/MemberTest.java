package finalmission.unit.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;

import finalmission.domain.Member;
import finalmission.exception.LoginFailedException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MemberTest {

    private Member member;

    @BeforeEach
    void setUp() {
        member = new Member(1L, "이름1", "이메일1", "비밀번호1");
    }

    @Test
    void 같은_멤버인지_확인() {
        assertThat(member.isSameMember(1L)).isTrue();
    }

    @Test
    void 비밀번호_검증() {
        //given
        String validPassword = "비밀번호1";
        assertThatCode(() -> member.validatePassword(validPassword)).doesNotThrowAnyException();
    }

    @Test
    void 틀린_비밀번호_검증() {
        //given
        String wrongPassword = "비밀번호2";
        assertThatCode(() -> member.validatePassword(wrongPassword))
                .isInstanceOf(LoginFailedException.class)
                .hasMessage("로그인에 실패했습니다.");
    }
}
