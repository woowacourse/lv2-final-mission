package finalmission.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import finalmission.domain.AuthInfo;
import finalmission.domain.Member;
import finalmission.domain.MemberRepository;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class MemberServiceTest {

    private final MemberRepository memberRepository = Mockito.mock(MemberRepository.class);
    private final MemberService memberService = new MemberService(memberRepository);

    @Test
    @DisplayName("사용자를 등록한다.")
    void register() {
        memberService.register("popo", "password", "포포");
        Mockito.verify(memberRepository).save(Mockito.any());
    }

    @Test
    @DisplayName("로그인을 한다.")
    void login() {
        Mockito
            .doReturn(Optional.of(new Member("popo", "password", "포포")))
            .when(memberRepository).findById("popo");

        var authInfo = memberService.login("popo", "password");

        assertThat(authInfo.id()).isEqualTo("popo");
    }

    @Test
    @DisplayName("아이디가 존재하지 않으면 로그인에 실패한다.")
    void loginIdWrong() {
        Mockito
            .doReturn(Optional.empty())
            .when(memberRepository).findById("popo");

        assertThatThrownBy(() -> memberService.login("popo", "password"))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("비밀번호가 틀리면 로그인에 실패한다.")
    void loginPasswordWrong() {
        Mockito
            .doReturn(Optional.of(new Member("popo", "password", "포포")))
            .when(memberRepository).findById("popo");

        assertThatThrownBy(() -> memberService.login("popo", "xxxxxxxx"))
            .isInstanceOf(IllegalArgumentException.class);
    }
}
