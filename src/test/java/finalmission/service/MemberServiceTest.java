package finalmission.service;

import finalmission.dto.MemberRegisterDto;
import finalmission.model.Member;
import finalmission.repository.MemberRepository;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.BeforeEach;
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

    @BeforeEach
    void setUp() {
        memberRepository.deleteAll();
    }

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

    @Test
    @DisplayName("랜덤으로 이름을 개수만큼 받는다")
    void test2() {
        // when
        List<String> randomNames = memberService.getRandomNames(10);

        // then
        assertThat(randomNames).hasSize(10);
    }

    @Test
    @DisplayName("랜덤으로 이름의 개수를 추출할 때 0을 입력하면 기본으로 10개가 반환된다")
    void test3() {
        // when
        List<String> randomNames = memberService.getRandomNames(0);

        // then
        assertThat(randomNames).hasSize(10);
    }
}
