package finalmission.music.member.service;

import finalmission.music.common.BaseServiceTest;
import finalmission.music.member.controller.dto.MemberRequest;
import finalmission.music.member.controller.dto.MemberResponse;
import finalmission.music.member.repository.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

@SpringBootTest(webEnvironment = WebEnvironment.NONE)
class MemberServiceTest extends BaseServiceTest {

    @Autowired
    private MemberService sut;

    @Autowired
    private MemberRepository memberRepository;

    @Test
    @DisplayName("회원가입을 할 수 있다.")
    void can_sign_up() {
        MemberRequest request = new MemberRequest("test");

        MemberResponse member = sut.create(request);

        Assertions.assertThat(member.name()).isEqualTo(request.name());
        Assertions.assertThat(memberRepository.existsById(member.name())).isTrue();
    }

    @Test
    @DisplayName("중복된 이름으로 회원가입을 할 수 없다.")
    void dont_sign_up_duplicate_name() {
        MemberRequest request1 = new MemberRequest("test");
        MemberRequest request2 = new MemberRequest("test");
        sut.create(request1);

        // when-then
        Assertions.assertThatThrownBy(() -> sut.create(request2))
            .isInstanceOf(IllegalArgumentException.class);
    }
}
