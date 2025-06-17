package finalmission.application;

import finalmission.domain.Member;
import finalmission.domain.MemberCredential;
import finalmission.domain.repository.MemberCredentialRepository;
import finalmission.domain.repository.MemberRepository;
import finalmission.dto.RequestSignup;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SignupService {

    private final MemberCredentialRepository memberCredentialRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public Long signup(RequestSignup requestSignup) {
        validateDuplicateEmail(requestSignup);
        Member member = createMember(requestSignup);
        Member savedMember = memberRepository.save(member);
        MemberCredential memberCredential = createMemberCredential(requestSignup, savedMember);
        memberCredentialRepository.save(memberCredential);
        return savedMember.getId();
    }

    private void validateDuplicateEmail(RequestSignup requestSignup) {
        boolean existsEmail = memberCredentialRepository.existsByEmail(requestSignup.email());
        if (existsEmail) {
            throw new IllegalArgumentException("이미 가입된 이메일입니다.");
        }
    }

    private Member createMember(RequestSignup requestSignup) {
        return Member.builder()
                .name(requestSignup.name())
                .age(requestSignup.age())
                .build();
    }

    private MemberCredential createMemberCredential(RequestSignup requestSignup, Member savedMember) {
        return MemberCredential.builder()
                .email(requestSignup.email())
                .password(requestSignup.password())
                .member(savedMember)
                .build();
    }
}
