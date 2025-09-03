package finalmission.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

import finalmission.domain.entity.Member;
import finalmission.domain.vo.MemberRole;
import finalmission.dto.LoginRequest;
import finalmission.repository.MemberRepository;
import finalmission.util.JwtTokenProvider;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    MemberRepository memberRepository;
    @Mock
    JwtTokenProvider jwtTokenProvider;
    @InjectMocks
    AuthService authService;

    @Test
    void loginTest() {
        // given
        LoginRequest request = new LoginRequest("member@email.com", "Password123!@#");
        when(memberRepository.findByEmail("member@email.com"))
                .thenReturn(Optional.of(new Member(1L, "member", "member@email.com", "Password123!@#", MemberRole.USER)));
        when(jwtTokenProvider.createToken(1L, "member", MemberRole.USER)).thenReturn("TokenCreatedSuccessful");

        // when
        String token = authService.login(request);

        // then
        assertThat(token).isNotNull();
        assertThat(token).isEqualTo("TokenCreatedSuccessful");
    }

    @DisplayName("이메일로 멤버를 찾지 못할 경우 예외를 발생한다.")
    @Test
    void loginFailTestWhenInvalidEmail() {
        // given
        LoginRequest request = new LoginRequest("member@email.com", "Password123!@#");
        when(memberRepository.findByEmail("member@email.com")).thenReturn(Optional.empty());

        // when // then
        assertThatThrownBy(() -> authService.login(request))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Invalid email or password");
    }

    @DisplayName("비밀번호가 일치하지 않을 경우 예외를 발생한다.")
    @Test
    void loginFailTestWhenInvalidPassword() {
        LoginRequest request = new LoginRequest("member@email.com", "InvalidPassword");
        when(memberRepository.findByEmail("member@email.com"))
                .thenReturn(
                        Optional.of(new Member(1L, "member", "member@email.com", "Password123!@#", MemberRole.USER)));

        // when // then
        assertThatThrownBy(() -> authService.login(request))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Invalid email or password");
    }
}
