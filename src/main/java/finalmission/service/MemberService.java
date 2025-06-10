package finalmission.service;

import finalmission.dto.MemberRegisterDto;
import finalmission.infrastructure.randommer.RandommerRestClient;
import finalmission.model.Member;
import finalmission.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    private final RandommerRestClient randommerRestClient;

    public void signUp(MemberRegisterDto memberRegisterDto) {
        if (memberRegisterDto.name() == null) {
            String randomName = randommerRestClient.generateSingleName();
            Member member = memberRegisterDto.toMember(randomName);
            memberRepository.save(member);
            return;
        }

        Member member = memberRegisterDto.toMember();
        memberRepository.save(member);
    }
}
