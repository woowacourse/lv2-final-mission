package finalmission.service;

import finalmission.dto.MemberRegisterDto;
import finalmission.model.Member;
import finalmission.repository.MemberRepository;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

@SpringBootTest(webEnvironment = WebEnvironment.NONE)
class MemberServiceTest {

    @Autowired
    MemberService memberService;

    @Autowired
    MemberRepository memberRepository;

    @Test
    @DisplayName("회원가입 시 이름이 존재하지 않으면 랜덤 이름을 추출해 사용한다")
    void test1() {
        // given
        MemberRegisterDto memberRegisterDto = new MemberRegisterDto(null, "example@gmail.com", "password");

        // when
        memberService.signUp(memberRegisterDto);

        // then
        Member member = memberRepository.findAll().getFirst();
        assertThat(member.getName()).isNotNull();
    }
}
