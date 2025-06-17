package finalmission.member;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import finalmission.global.error.exception.BadRequestException;
import finalmission.member.dto.request.MemberRequest;
import finalmission.member.entity.Member;
import finalmission.member.entity.RoleType;
import finalmission.member.repository.MemberRepository;
import finalmission.member.service.MemberService;
import finalmission.thirdparty.service.RandomNameService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class MemberServiceTest {

    @Autowired
    private MemberService memberService;

    @Autowired
    private RandomNameService randomNameService;

    @Autowired
    private MemberRepository memberRepository;

    @Test
    @DisplayName("중복된 이메일로 멤버를 생성할 수 없다.")
    void createMember_duplicatedEmail() {
        // given
        memberRepository.save(new Member("테스트", "테스트", "test@email.com", "test", RoleType.USER));

        var request = new MemberRequest("미소", "test@email.com", "miso");

        // when & then
        assertThatThrownBy(() -> memberService.createMember(request))
                .isInstanceOf(BadRequestException.class);
    }

    @Test
    @DisplayName("중복된 이메일로 어드민을 생성할 수 없다.")
    void createMemberAsAdmin_duplicatedEmail() {
        // given
        memberRepository.save(new Member("테스트", "테스트", "test@email.com", "test", RoleType.USER));

        var request = new MemberRequest("미소", "test@email.com", "miso");

        // when & then
        assertThatThrownBy(() -> memberService.createMemberAsAdmin(request))
                .isInstanceOf(BadRequestException.class);
    }
}
