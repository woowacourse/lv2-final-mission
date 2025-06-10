package finalmission.service;

import finalmission.domain.Member;
import finalmission.dto.request.MemberRequest;
import finalmission.dto.response.MemberResponse;
import finalmission.infrastructure.MemberRepository;
import org.springframework.stereotype.Service;

@Service
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public MemberResponse createMember(MemberRequest request) {
        Member newMember = memberRepository.save(request.toMember());
        return MemberResponse.from(newMember);
    }
}
