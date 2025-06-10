package finalmission.music.service;

import finalmission.music.controller.dto.MemberRequest;
import finalmission.music.controller.dto.MemberResponse;
import finalmission.music.repository.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

@SpringBootTest(webEnvironment = WebEnvironment.NONE)
class MemberServiceTest {

    @Autowired
    private MemberService sut;

    @Autowired
    private MemberRepository memberRepository;

    @Test
    void 회원가입을_할_수_있다() {
        MemberRequest request = new MemberRequest("test");

        MemberResponse member = sut.save(request);

        Assertions.assertThat(member.name()).isEqualTo(request.name());
        Assertions.assertThat(memberRepository.existsById(member.name())).isTrue();
    }

    @Test
    void 중복된_이름으로_회원가입을_할_수_없다() {
        MemberRequest request1 = new MemberRequest("test");
        MemberRequest request2 = new MemberRequest("test");
        sut.save(request1);

        // when-then
        Assertions.assertThatThrownBy(() -> sut.save(request2))
            .isInstanceOf(IllegalArgumentException.class);
    }
}
