package finalmission.member.service;

import finalmission.member.domain.Member;
import finalmission.member.dto.MemberResponse;
import finalmission.member.dto.RegisterRequest;
import finalmission.member.repository.MemberRepository;
import org.springframework.stereotype.Service;

@Service
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public MemberResponse register(RegisterRequest request) {
        Member savedMember = memberRepository.save(new Member(request.email(), request.password(), request.role()));
        return MemberResponse.of(savedMember);
    }
}
