package finalmission.service;

import finalmission.domain.Member;
import finalmission.dto.request.SignUpRequest;
import finalmission.infrastructure.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class MemberService {
    private final MemberRepository memberRepository;

    public MemberService(final MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public Member signUp(SignUpRequest request, String randomName) {
        Member member = request.toMember(randomName);
        return memberRepository.save(member);
    }
    @Transactional(readOnly = true)
    public Member findMemberById(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow();
    }
}
