package finalmission.member;

import finalmission.member.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public void createMember(final String phoneNumber) {
        Member member = new Member(phoneNumber);
        memberRepository.save(member);
    }
}
