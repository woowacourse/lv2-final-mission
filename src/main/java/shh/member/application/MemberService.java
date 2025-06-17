package shh.member.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shh.common.exception.impl.NotFoundException;
import shh.member.domain.Email;
import shh.member.domain.Member;
import shh.member.domain.Password;
import shh.member.domain.repository.MemberRepository;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public Member findByEmailAndPassword(final String email, final String password) {
        return memberRepository.findByEmailAndPassword(new Email(email), new Password(password))
                .orElseThrow(() -> new NotFoundException("사용자가 존재하지 않습니다."));
    }

    public Member findById(final Long id) {
        return memberRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("사용자가 존재하지 않습니다."));
    }

    public Member saveMember(final Member member) {
        return memberRepository.save(member);
    }
}
