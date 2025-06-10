package finalmission.member.application;

import finalmission.member.application.in.SignupMemberIn;
import finalmission.member.application.out.MemberRepository;
import finalmission.member.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional
    public void signUp(SignupMemberIn command) {
        memberRepository.save(
                Member.create(command.name())
        );
    }
}
