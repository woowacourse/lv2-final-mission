package finalmission.member.service;

import finalmission.member.domian.Member;
import finalmission.member.domian.Role;
import finalmission.member.dto.MemberSignUpRequest;
import finalmission.member.dto.MemberSignUpResponse;
import finalmission.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberSignUpResponse createMember(MemberSignUpRequest request) {
        Member member = new Member(request.email(), request.name(), request.password(), Role.USER);
        Member createdMember = memberRepository.save(member);
        return new MemberSignUpResponse(createdMember.getEmail(), createdMember.getName());
    }
}
