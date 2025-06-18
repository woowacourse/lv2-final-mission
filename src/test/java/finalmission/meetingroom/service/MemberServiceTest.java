package finalmission.meetingroom.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import finalmission.meetingroom.common.exception.AlreadyInUseException;
import finalmission.meetingroom.domain.Member;
import finalmission.meetingroom.repository.MemberRepository;
import finalmission.meetingroom.service.request.SignupRequest;
import finalmission.meetingroom.service.response.MemberResponse;

@ActiveProfiles("test")
@SpringBootTest
class MemberServiceTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private MemberService memberService;

    @AfterEach
    void tearDown() {
        memberRepository.deleteAllInBatch();
    }

    @DisplayName("사용자를 생성한다.")
    @Test
    void createMember() {
        SignupRequest request = new SignupRequest("포스티", "test@email.com", "1234");

        MemberResponse result = memberService.createMember(request);

        assertThat(result).isEqualTo(new MemberResponse(result.id(), "포스티", "test@email.com"));
    }

    @DisplayName("이미 존재하는 이메일로 사용자를 생성할 수 없다.")
    @Test
    void createMemberByDuplicatedEmail() {
        String duplicatedEmail = "test@email.com";
        memberRepository.save(new Member("구구", duplicatedEmail, "4321"));
        SignupRequest request = new SignupRequest("포스티", duplicatedEmail, "1234");

        assertThatThrownBy(() -> memberService.createMember(request))
                .isInstanceOf(AlreadyInUseException.class)
                .hasMessage("이미 존재하는 이메일 입니다.");
    }
}
