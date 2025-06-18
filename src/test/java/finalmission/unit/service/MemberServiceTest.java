package finalmission.unit.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import finalmission.domain.Member;
import finalmission.infrastructure.MemberRepository;
import finalmission.service.MemberService;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class MemberServiceTest {
    @Mock
    private MemberRepository memberRepository;

    @InjectMocks
    private MemberService memberService;

    @Test
    void id로_멤버_조회() {
        //given
        Member member = new Member(1L, "이름", "이메일", "123");
        given(memberRepository.findById(any())).willReturn(Optional.of(member));

        // when
        Member foundMember = memberService.findMemberById(1L);

        //then
        assertThat(foundMember.getId()).isEqualTo(member.getId());
    }
}
