package finalmission.member.application;

import finalmission.member.application.dto.MemberRequest;
import finalmission.member.application.dto.MemberResponse;
import finalmission.member.domain.Email;
import finalmission.member.domain.Member;
import finalmission.member.domain.Name;
import finalmission.member.domain.Password;
import finalmission.member.domain.PasswordEncoder;
import finalmission.member.domain.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final RandomNameGenerator randomNameGenerator;

    @Transactional
    public MemberResponse create(MemberRequest request) {
        Name name = new Name(randomNameGenerator.generateName());
        Password password = new Password(request.password(), passwordEncoder);
        Email email = new Email(request.email());
        validateEmailDuplicated(email);

        Member member = new Member(name, email, password);
        memberRepository.save(member);
        return MemberResponse.from(member);
    }

    private void validateEmailDuplicated(Email email) {
        if (memberRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("이미 존재하는 이메일입니다.");
        }
    }
}
