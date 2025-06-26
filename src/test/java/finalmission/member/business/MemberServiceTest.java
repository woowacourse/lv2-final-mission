package finalmission.member.business;

import finalmission.general.auth.util.JwtProvider;
import finalmission.member.model.Member;
import finalmission.member.presentation.dto.request.MemberCreateWebRequest;
import finalmission.member.presentation.dto.request.MemberLoginWebRequest;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
@Transactional
class MemberServiceTest {

    @Autowired
    MemberService memberService;

    @MockitoBean
    JwtProvider jwtProvider;

    @Test
    void 멤버를_생성하여_저장할_수_있다() {
        // Given
        MemberCreateWebRequest memberCreateWebRequest = new MemberCreateWebRequest("username", "password", "프리");

        // When
        Member member = memberService.createUser(memberCreateWebRequest);

        // Then
        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(member).isNotNull();
            softAssertions.assertThat(member.getUsername()).isEqualTo("username");
            softAssertions.assertThat(member.getPassword()).isEqualTo("password");
            softAssertions.assertThat(member.getName()).isEqualTo("프리");
        });
    }

    @Test
    void 중복된_아이디로는_멤버를_생성할_수_없다() {
        // Given
        MemberCreateWebRequest memberCreateWebRequest1 = new MemberCreateWebRequest("username", "password", "프리");
        MemberCreateWebRequest memberCreateWebRequest2 = new MemberCreateWebRequest("username", "password", "프리2");
        Member member = memberService.createUser(memberCreateWebRequest1);

        // When & Then
        assertThatThrownBy(() -> memberService.createUser(memberCreateWebRequest2))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이미 존재하는 아이디입니다.");
    }

    @Test
    void 로그인_성공_시_JWT를_반환한다() {
        // Given
        String username = "username";
        MemberCreateWebRequest memberCreateWebRequest = new MemberCreateWebRequest(username, "password", "프리");
        Member member = memberService.createUser(memberCreateWebRequest);
        MemberLoginWebRequest memberLoginWebRequest = new MemberLoginWebRequest(member.getUsername(), member.getPassword());
        String expected = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiLtlITrpqwiLCJleHAiOjE3NTAxNTM3NDV9.bDb6JgQCnJ1t6gfNe1d8iWzwQZ-ukkIL88zrXdc_mvo";
        when(jwtProvider.generateToken(username)).thenReturn(expected);

        // When
        String token = memberService.login(memberLoginWebRequest);

        // Then
        assertThat(token).isEqualTo(expected);
    }

    @Test
    void 아이디가_틀렸을_경우에는_JWT를_발급해주지_않는다() {
        // Given
        String username = "username";
        MemberCreateWebRequest memberCreateWebRequest = new MemberCreateWebRequest(username, "password", "프리");
        Member member = memberService.createUser(memberCreateWebRequest);
        MemberLoginWebRequest memberLoginWebRequest = new MemberLoginWebRequest("invalidUsername", member.getPassword());
        String expected = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiLtlITrpqwiLCJleHAiOjE3NTAxNTM3NDV9.bDb6JgQCnJ1t6gfNe1d8iWzwQZ-ukkIL88zrXdc_mvo";
        when(jwtProvider.generateToken(username)).thenReturn(expected);

        // When & Then
        assertThatThrownBy(() -> memberService.login(memberLoginWebRequest))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("아이디 혹은 비밀번호를 잘못 입력하셨습니다.");
    }

    @Test
    void 비밀번호가_틀렸을_경우에는_JWT를_발급해주지_않는다() {
        // Given
        String username = "username";
        MemberCreateWebRequest memberCreateWebRequest = new MemberCreateWebRequest(username, "password", "프리");
        Member member = memberService.createUser(memberCreateWebRequest);
        MemberLoginWebRequest memberLoginWebRequest = new MemberLoginWebRequest(member.getUsername(), "invalidPassword");
        String expected = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiLtlITrpqwiLCJleHAiOjE3NTAxNTM3NDV9.bDb6JgQCnJ1t6gfNe1d8iWzwQZ-ukkIL88zrXdc_mvo";
        when(jwtProvider.generateToken(username)).thenReturn(expected);

        // When & Then
        assertThatThrownBy(() -> memberService.login(memberLoginWebRequest))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("아이디 혹은 비밀번호를 잘못 입력하셨습니다.");
    }
}