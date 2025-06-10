package finalmission.music.service;

import finalmission.music.controller.dto.MemberRequest;
import finalmission.music.controller.dto.MemberResponse;
import finalmission.music.domain.Member;
import finalmission.music.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberResponse save(MemberRequest request) {
        Member member = memberRepository.save(new Member(request.name()));

        return MemberResponse.from(member);
    }
}
