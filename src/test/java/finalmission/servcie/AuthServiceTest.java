package finalmission.servcie;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import finalmission.domain.Member;
import finalmission.domain.Role;
import finalmission.dto.layer.AccessTokenContent;
import finalmission.dto.layer.LoginContent;
import finalmission.exception.LoginException;
import finalmission.utility.JwtProvider;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;

@DataJpaTest
@Import(value = {JwtProvider.class, AuthService.class})
class AuthServiceTest {

    @Autowired
    private TestEntityManager entityManager;
    @Autowired
    private JwtProvider jwtProvider;
    @Autowired
    private AuthService authService;

    @Nested
    @DisplayName("로그인이 가능하다.")
    public class Login {

        @DisplayName("정상적으로 로그인이 가능하다.")
        @Test
        void login() {
            // given
            Member member = entityManager.persist(new Member("test@test.com", "qwer1234!", "kim", Role.GENERAL));
            LoginContent loginContent = new LoginContent("test@test.com", "qwer1234!");

            entityManager.flush();
            entityManager.clear();

            // when
            String accessToken = authService.login(loginContent);

            // then
            AccessTokenContent tokenContent = jwtProvider.parseAccessToken(accessToken);
            assertThat(tokenContent.memberId()).isEqualTo(member.getId());
        }

        @DisplayName("비밀번호가 맞지 않는 경우 로그인이 불가능하다.")
        @Test
        void testMethodNameHere() {
            // given
            Member member = entityManager.persist(new Member("test@test.com", "qwer1234!", "kim", Role.GENERAL));
            LoginContent loginContent = new LoginContent("test@test.com", "asdf1234!");

            entityManager.flush();
            entityManager.clear();

            // when & then
            assertThatThrownBy(() -> authService.login(loginContent))
                    .isInstanceOf(LoginException.class);
        }
    }
}
