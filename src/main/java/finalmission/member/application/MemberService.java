package finalmission.member.application;

import finalmission.member.application.in.dto.SignupMember;
import finalmission.member.application.out.MemberRepository;
import finalmission.member.application.out.client.RandomNicknameGenerator;
import finalmission.member.application.out.dto.SignedMember;
import finalmission.member.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class MemberService {

    private final MemberRepository memberRepository;
    private final RandomNicknameGenerator randomNicknameGenerator;

    @Transactional
    public SignedMember signUp(SignupMember command) {
        String nickname = randomNicknameGenerator.requestRandomNickname();

        Member member = Member.create(command.name(), nickname);
        memberRepository.save(member);

        return SignedMember.from(member);
    }
}
