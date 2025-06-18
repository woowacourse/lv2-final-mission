package finalmission.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import finalmission.application.dto.request.CreateMemberRequest;
import finalmission.application.dto.response.CreateMemberResponse;
import finalmission.domain.Email;
import finalmission.domain.MemberRepository;
import finalmission.domain.Role;
import java.util.List;
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

    @Test
    void 사용자를_생성할떄_이름이_없다면_랜덤으로_이름을_생성해_사용자를_만든다다() {
        //given
        CreateMemberRequest createMemberRequest = new CreateMemberRequest("", "email@gmail.com", "password");
        when(randomNameClient.getRandomNames("fullname", 1)).thenReturn(List.of("randomName"));

        //when
        CreateMemberResponse createMemberResponse = memberService.create(createMemberRequest);

        //then
        assertThat(memberRepository.findById(createMemberResponse.memberId()))
                .isPresent()
                .get()
                .extracting("name", "email", "password", "role")
                .contains("randomName", new Email("email@gmail.com"), "password", Role.USER);
    }
}
