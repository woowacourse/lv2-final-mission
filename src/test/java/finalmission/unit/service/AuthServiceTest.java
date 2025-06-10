package finalmission.unit.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import finalmission.domain.Member;
import finalmission.dto.request.LoginRequest;
import finalmission.exception.EmailNotExistException;
import finalmission.exception.WrongPasswordException;
import finalmission.infrastructure.MemberRepository;
import finalmission.infrastructure.PasswordClient;
import finalmission.infrastructure.TokenProvider;
import finalmission.service.AuthService;
import finalmission.unit.fake.FakeMemberRepository;
import org.junit.jupiter.api.Test;

public class AuthServiceTest {

    AuthService authService;

    TokenProvider tokenProvider;

    PasswordClient passwordClient;

    MemberRepository memberRepository;

    public AuthServiceTest() {
        tokenProvider = mock(TokenProvider.class);
        passwordClient = mock(PasswordClient.class);
        memberRepository = new FakeMemberRepository();
        this.authService = new AuthService(memberRepository, tokenProvider, passwordClient);
    }

    @Test
    void 로그인_요청을_검증하고_토큰을_발행한다() {
        // given
        Member member = memberRepository.save(new Member("email1@domain.com", "이름1", "1234"));
        LoginRequest request = new LoginRequest(member.getEmail(), member.getPassword());
        when(tokenProvider.issue(member)).thenReturn("accessToken");
        // when
        String token = authService.login(request);
        // then
        assertThat(token).isEqualTo("accessToken");
    }

    @Test
    void 비밀번호가_일치하지_않으면_예외가_발생한다() {
        // given
        Member member = memberRepository.save(new Member("email1@domain.com", "이름1", "1234"));
        LoginRequest request = new LoginRequest(member.getEmail(), "5678");
        // when & then
        assertThatThrownBy(() -> authService.login(request))
                .isInstanceOf(WrongPasswordException.class);
    }

    @Test
    void 존재하지_않는_이메일이면_예외가_발생한다() {
        // given
        LoginRequest request = new LoginRequest("nobody@domain.com", "1234");
        // when & then
        assertThatThrownBy(() -> authService.login(request))
                .isInstanceOf(EmailNotExistException.class);
    }
}
