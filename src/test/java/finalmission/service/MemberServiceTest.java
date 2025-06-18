package finalmission.service;

import finalmission.domain.MemberRole;
import finalmission.dto.request.CreateTokenRequest;
import finalmission.dto.request.LoginRequest;
import finalmission.dto.request.SignUpRequest;
import finalmission.dto.response.SignUpResponse;
import finalmission.entity.Member;
import finalmission.exception.custom.DuplicatedValueException;
import finalmission.jwt.JwtTokenProvider;
import finalmission.repository.MemberRepository;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MemberServiceTest {

    @Mock
    MemberRepository memberRepository;

    @Mock
    JwtTokenProvider jwtTokenProvider;

    @InjectMocks
    MemberService memberService;

    Member member;

    @BeforeEach
    void setup() {
        member = new Member(1L, "test@test.com", "test", "test", MemberRole.MEMBER);
    }

    @Test
    @DisplayName("이메일과 패스워드를 이용하여 로그인한다.")
    void login() {
        //given
        when(memberRepository.findByEmailAndPassword(anyString(), anyString()))
                .thenReturn(Optional.of(member));
        when(jwtTokenProvider.createTokenByMember(any(CreateTokenRequest.class)))
                .thenReturn("token");
        LoginRequest request = new LoginRequest("test@test.com", "test");

        //when
        String actual = memberService.login(request);

        //then
        assertThat(actual).isEqualTo("token");
    }

    @Test
    @DisplayName("이메일, 패스워드, 이름을 기반으로 회원가입한다.")
    void signup() {
        //given
        when(memberRepository.existsByEmail(anyString()))
                .thenReturn(false);
        when(memberRepository.save(any(Member.class)))
                .thenReturn(member);

        SignUpRequest request = new SignUpRequest("test@test.com", "test", "test");

        //when
        SignUpResponse actual = memberService.signup(request);

        //then
        SignUpResponse expected = new SignUpResponse("test", "test@test.com", MemberRole.MEMBER);
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("이미 사용 중인 이메일은 회원가입할 수 없다.")
    void cannotSignupUsingExistedEmail() {
        //given
        when(memberRepository.existsByEmail(anyString()))
                .thenReturn(true);

        SignUpRequest request = new SignUpRequest("test@test.com", "test", "test");

        //when //then
        assertThatThrownBy(() -> memberService.signup(request))
                .isInstanceOf(DuplicatedValueException.class)
                .hasMessageContaining("이미 사용 중인 이메일입니다.");
    }
}
