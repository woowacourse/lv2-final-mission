package finalmission.application;

import finalmission.application.dto.request.CreateMemberRequest;
import finalmission.application.dto.response.CreateMemberResponse;
import finalmission.domain.Email;
import finalmission.domain.Member;
import finalmission.domain.MemberRepository;
import finalmission.domain.Role;
import finalmission.infrastructure.RandomNameClient;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;
    private final RandomNameClient randomNameClient;

    @Transactional
    public CreateMemberResponse create(CreateMemberRequest createMemberRequest) {
        Member member = Member.create(
                new Email(createMemberRequest.email()),
                getName(createMemberRequest),
                createMemberRequest.password(),
                Role.USER
        );
        memberRepository.save(member);
        return new CreateMemberResponse(member.getId());
    }

    private String getName(CreateMemberRequest createMemberRequest) {
        String name = createMemberRequest.name();
        if (name == null || name.isEmpty()) {
            List<String> fullname = randomNameClient.getRandomNames("fullname", 1);
            name = fullname.getFirst();
        }
        return name;
    }
}
