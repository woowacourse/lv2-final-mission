package finalmission.auth.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import finalmission.auth.application.dto.LoginRequest;
import finalmission.fixture.MemberFixture;
import finalmission.member.domain.Member;
import finalmission.member.domain.StubPasswordEncoder;
import finalmission.member.domain.repository.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

@DataJpaTest
@Import({
        AuthService.class,
        TokenProvider.class,
        StubPasswordEncoder.class
})
@ActiveProfiles("test")
class AuthServiceTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private AuthService authService;

    @BeforeEach
    void setUp() {
        // 테스트용 회원 생성
        Member member = MemberFixture.createMember("test", "test@example.com", "password");
        memberRepository.save(member);
    }

    @DisplayName("로그인 성공")
    @Test
    void login_success() {
        // given
        LoginRequest request = new LoginRequest("test@example.com", "password");

        // when
        String token = authService.login(request);

        // then
        assertThat(token).isNotNull();
    }

    @DisplayName("비밀번호가 불일치하면 로그인에 실패한다")
    @Test
    void login_invalid_password() {
        // given
        LoginRequest request = new LoginRequest("test@example.com", "wrongPassword");

        // when & then
        assertThatThrownBy(() -> authService.login(request))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("회원가입 된 이메일이 아니면 로그인에 실패한다")
    @Test
    void login_not_exist_email() {
        // given
        LoginRequest request = new LoginRequest("notfound@example.com", "password");

        // when, then
        assertThatThrownBy(() -> authService.login(request))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
