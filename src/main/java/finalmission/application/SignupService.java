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
        Member member = Member.builder()
                .name(requestSignup.name())
                .age(requestSignup.age())
                .build();
        Member savedMember = memberRepository.save(member);
        MemberCredential memberCredential = MemberCredential.builder()
                .email(requestSignup.email())
                .password(requestSignup.password())
                .member(savedMember)
                .build();
        memberCredentialRepository.save(memberCredential);
        return savedMember.getId();
    }
}
