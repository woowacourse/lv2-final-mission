package finalmission.service;

import finalmission.controller.MemberSignUpRequest;
import finalmission.domain.Member;
import finalmission.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MemberService {

    private final MemberRepository memberRepository;

    public void create(final MemberSignUpRequest request) {
        final Member member = request.toMember();

        memberRepository.save(member);
    }
}
