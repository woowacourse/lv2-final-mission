package finalmission.application;

import finalmission.application.dto.request.CreateMemberRequest;
import finalmission.application.dto.response.CreateMemberResponse;
import finalmission.domain.Email;
import finalmission.domain.Member;
import finalmission.domain.MemberRepository;
import finalmission.domain.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional
    public CreateMemberResponse create(CreateMemberRequest createMemberRequest) {
        Member member = Member.create(
                new Email(createMemberRequest.email()),
                createMemberRequest.name(),
                createMemberRequest.password(),
                Role.USER
        );
        memberRepository.save(member);
        return new CreateMemberResponse(member.getId());
    }
}
