package finalmission.unit.service;

import static org.assertj.core.api.Assertions.assertThat;

import finalmission.dto.MemberRequest;
import finalmission.dto.MemberResponse;
import finalmission.service.MemberService;
import finalmission.unit.fake.FakeMemberRepository;
import org.junit.jupiter.api.Test;

public class MemberServiceTest {

    MemberService memberService = new MemberService(new FakeMemberRepository());

    @Test
    void 회원을_저장한다() {
        // given
        MemberRequest request = new MemberRequest("email1@domain.com", "이름1", "1234");
        // when
        MemberResponse response = memberService.createMember(request);
        // then
        assertThat(response.id()).isEqualTo(1L);
        assertThat(response.email()).isEqualTo("email1@domain.com");
        assertThat(response.name()).isEqualTo("이름1");
    }
}
