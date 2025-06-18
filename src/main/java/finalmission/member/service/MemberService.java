package finalmission.member.service;

import finalmission.member.domain.Member;
import finalmission.member.domain.Role;
import finalmission.member.dto.SignupRequest;
import finalmission.member.dto.SignupResponse;
import finalmission.member.infrastructure.MemberRepository;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Transactional
    public SignupResponse createUser(SignupRequest request) {
        Member member = Member.createWithoutId(request.name(), request.email(), request.password(), Role.USER);
        Member savedMember = memberRepository.save(member);
        return SignupResponse.from(savedMember);
    }

    public List<SignupResponse> getMembers() {
        return memberRepository.findAll().stream()
                .map(SignupResponse::from)
                .toList();
    }
}
