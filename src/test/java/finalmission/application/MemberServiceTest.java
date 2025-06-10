package finalmission.application;

import static org.assertj.core.api.Assertions.assertThat;

import finalmission.application.dto.request.CreateMemberRequest;
import finalmission.application.dto.response.CreateMemberResponse;
import finalmission.domain.Email;
import finalmission.domain.MemberRepository;
import finalmission.domain.Role;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class MemberServiceTest extends AbstractServiceIntegrationTest {

    @Autowired
    private MemberService memberService;

    @Autowired
    private MemberRepository memberRepository;

    @Test
    void 사용자를_생성할_수_있다() {
        //given
        CreateMemberRequest createMemberRequest = new CreateMemberRequest("name", "email@gmail.com", "password");

        //when
        CreateMemberResponse createMemberResponse = memberService.create(createMemberRequest);

        //then
        assertThat(memberRepository.findById(createMemberResponse.memberId()))
                .isPresent()
                .get()
                .extracting("name", "email", "password", "role")
                .contains("name", new Email("email@gmail.com"), "password", Role.USER);
    }
}
