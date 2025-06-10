package finalmission.member.service;

import finalmission.member.domain.Member;
import finalmission.member.domain.MemberRepository;
import finalmission.member.dto.LoginMemberRequest;
import finalmission.member.dto.MemberResponse;
import finalmission.member.dto.RegisterMemberRequest;
import org.springframework.stereotype.Service;

@Service
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberService(final MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public MemberResponse register(final RegisterMemberRequest registerMemberRequest) {
        Member member = registerMemberRequest.toMemberEntity();
        Member savedMember = memberRepository.save(member);
        return MemberResponse.toResponse(savedMember);
    }

    public MemberResponse login(final LoginMemberRequest loginMemberRequest) {
        Member member = memberRepository.findByEmailAndPassword(loginMemberRequest.email(), loginMemberRequest.password())
                .orElseThrow(() -> new IllegalArgumentException("해당하는 멤버 정보를 찾을 수 없습니다."));
        return MemberResponse.toResponse(member);
    }
}
