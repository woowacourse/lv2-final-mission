package finalmission.service;

import static org.assertj.core.api.Assertions.assertThatCode;

import finalmission.controller.dto.MemberSignupRequest;
import finalmission.domain.Member;
import finalmission.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MemberServiceTest {

    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private MemberService memberService;

    @Test
    void 회원가입시_중복되는_이메일인_경우_예외가_발생한다() {
        // given
        memberRepository.save(new Member("test1", "duplicate@email.com", "password", "01012345676"));

        MemberSignupRequest request = new MemberSignupRequest("test2", "duplicate@email.com", "password",
                "01056781234");

        // when && then
        assertThatCode(() -> memberService.signup(request))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이미 가입된 이메일이 존재합니다. 다른 이메일을 이용해 주세요.");
    }

    @Test
    void 회원가입시_중복되는_전화번호인_경우_예외가_발생한다() {
        // given
        memberRepository.save(new Member("test1", "test1@email.com", "password", "01012345678"));

        MemberSignupRequest request = new MemberSignupRequest("test2", "test2@email.com", "password", "01012345678");

        // when && then
        assertThatCode(() -> memberService.signup(request))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이미 가입된 전화번호가 존재합니다. 다른 전화번호를 이용해 주세요.");
    }
}
