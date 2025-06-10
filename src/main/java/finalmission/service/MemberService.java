package finalmission.service;

import finalmission.controller.dto.MemberLoginResponse;
import finalmission.controller.dto.MemberSignUpRequest;
import finalmission.domain.Member;
import finalmission.domain.vo.LolName;
import finalmission.exception.NotFoundException;
import finalmission.exception.UnauthorizedException;
import finalmission.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MemberService {

    private final MemberRepository memberRepository;
    private final RiotRestClient riotRestClient;

    public void create(final MemberSignUpRequest request) {
        if (!riotRestClient.existsLolName(request.lolName())) {
            throw new NotFoundException("존재하지 않는 RIOT 이름 및 태그입니다.");
        }
        final Member member = request.toMember();

        memberRepository.save(member);
    }

    public MemberLoginResponse login(
            final LolName lolName,
            final String password
    ) {
        final Member member = memberRepository.findByLolName(lolName)
                .orElseThrow(() -> new NotFoundException("해당하는 이름의 회원이 존재하지 않습니다."));

        if (!member.getPassword().equals(password)) {
            throw new UnauthorizedException("비밀번호가 틀립니다.");
        }

        return MemberLoginResponse.from(member);
    }

    public Member getById(final Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new NotFoundException("해당하는 memberId의 멤버를 찾을 수 없습니다. memberId = " + memberId));
    }
}
