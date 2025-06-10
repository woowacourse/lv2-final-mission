package finalmission.member.application;

import finalmission.member.application.in.dto.SignupMember;
import finalmission.member.application.out.MemberRepository;
import finalmission.member.application.out.client.RandommerClient;
import finalmission.member.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class MemberService {

    private final MemberRepository memberRepository;
    private final RandommerClient randommerClient;

    @Transactional
    public void signUp(SignupMember command) {
        String nickname = randommerClient.requestRandomNickname();

        Member member = Member.create(command.name(), nickname);

        memberRepository.save(member);
    }
}
