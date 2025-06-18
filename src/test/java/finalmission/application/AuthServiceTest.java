package finalmission.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import finalmission.application.dto.request.LoginRequest;
import finalmission.application.dto.response.LoginResponse;
import finalmission.application.support.exception.AuthException;
import finalmission.application.support.exception.NotFoundEntityException;
import finalmission.domain.Email;
import finalmission.domain.Member;
import finalmission.domain.MemberRepository;
import finalmission.domain.Role;
import finalmission.infrastructure.JwtPayload;
import finalmission.infrastructure.JwtProvider;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class AuthServiceTest extends AbstractServiceIntegrationTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private AuthService authService;

    @Autowired
    private JwtProvider jwtProvider;

    @Test
    void 로그인을_할_수_있다() {
        //given
        Member member = createMember(new Email("fake@fake.com"), "1324");
        LoginRequest loginRequest = new LoginRequest("fake@fake.com", "1324");

        //when
        LoginResponse loginResponse = authService.login(loginRequest);

        //then
        assertThat(loginResponse).isEqualTo(new LoginResponse(
                jwtProvider.issue(new JwtPayload(member.getId(), member.getName(), member.getRole()))
        ));
    }

    @Test
    void 로그인시_이메일에_해당하는_사용자가_없는경우_예외가_발생한다() {
        //given
        LoginRequest loginRequest = new LoginRequest("fake@fake.com", "1324");

        //when & then
        assertThatThrownBy(() -> authService.login(loginRequest))
                .isInstanceOf(NotFoundEntityException.class)
                .hasMessageContaining("해당 이메일을 가진 사용자가 존재하지 않습니다.");
    }

    @Test
    void 로그인시_비밀번호가_틀리다면_예외가_발생한다() {
        //given
        createMember(new Email("fake@fake.com"), "1324");
        LoginRequest loginRequest = new LoginRequest("fake@fake.com", "1234");

        //when & then
        assertThatThrownBy(() -> authService.login(loginRequest))
                .isInstanceOf(AuthException.class)
                .hasMessageContaining("로그인에 실패하였습니다.");
    }

    private Member createMember(Email email, String password) {
        Member member = Member.create(email, "name", password, Role.USER);
        return memberRepository.save(member);
    }
}
