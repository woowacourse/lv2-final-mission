package finalmission.member.service;

import finalmission.member.domain.Member;
import finalmission.member.domain.MemberRepository;
import finalmission.member.dto.MemberResult;
import finalmission.member.dto.RegisterMemberRequest;
import org.springframework.stereotype.Service;

@Service
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberService(final MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public MemberResult register(final RegisterMemberRequest registerMemberRequest) {
        Member member = registerMemberRequest.toMemberEntity();
        Member savedMember = memberRepository.save(member);
        return MemberResult.toResult(savedMember);
    }
}
