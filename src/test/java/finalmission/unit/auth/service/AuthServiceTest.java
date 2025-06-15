package finalmission.unit.auth.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import finalmission.auth.dto.request.LoginRequest;
import finalmission.auth.infrastructure.TokenProvider;
import finalmission.auth.service.AuthService;
import finalmission.exception.auth.LoginFailedException;
import finalmission.member.domain.Member;
import finalmission.member.infrastructure.MemberRepository;
import finalmission.unit.fake.FakeMemberRepository;
import org.junit.jupiter.api.Test;

class AuthServiceTest {

    private final AuthService authService;
    private final MemberRepository memberRepository = new FakeMemberRepository();
    private final TokenProvider tokenProvider = mock(TokenProvider.class);

    public AuthServiceTest() {
        this.authService = new AuthService(memberRepository, tokenProvider);
    }

    @Test
    void 로그인_정보를_검증하고_토큰을_발행한다() {
        // given
        Member member = memberRepository.save(new Member("email1@domain.com", "nickname1", "1234"));
        when(tokenProvider.issue(member)).thenReturn("token");
        LoginRequest request = new LoginRequest("email1@domain.com", "1234");
        // when
        String token = authService.login(request);
        // then
        assertThat(token).isEqualTo("token");
    }

    @Test
    void 존재하지_않는_이메일로_로그인을_시도하면_예외가_발생한다() {
        // given
        LoginRequest request = new LoginRequest("email1@domain.com", "1234");
        // when & then
        assertThatThrownBy(() -> authService.login(request))
                .isInstanceOf(LoginFailedException.class);
    }

    @Test
    void 잘못된_비밀번호로_로그인을_시도하면_예외가_발생한다() {
        // given
        memberRepository.save(new Member("email1@domain.com", "nickname1", "1234"));
        LoginRequest request = new LoginRequest("email1@domain.com", "invalidPassword");
        // when & then
        assertThatThrownBy(() -> authService.login(request))
                .isInstanceOf(LoginFailedException.class);
    }
}