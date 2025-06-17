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

    public MemberResponse create(MemberRequest request) {
        if (memberRepository.existsById(request.name())) {
            throw new IllegalArgumentException("[ERROR] 중복된 이름입니다.");
        }
        Member member = memberRepository.save(new Member(request.name()));

        return MemberResponse.from(member);
    }
}
