package finalmission.service;

import finalmission.dto.MemberRegisterDto;
import finalmission.model.Member;
import finalmission.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public void signUp(MemberRegisterDto memberRegisterDto) {
        Member member = memberRegisterDto.toMember();
        memberRepository.save(member);
    }
}
