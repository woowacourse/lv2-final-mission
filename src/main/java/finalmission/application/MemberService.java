package finalmission.application;

import finalmission.domain.Member;
import finalmission.domain.repository.MemberRepository;
import finalmission.exception.AuthenticationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MemberService {

    private final MemberRepository memberRepository;

    public long getIdBy(String email, String password) {
        if (isMember(email, password)) {
            return getMemberIdByEmail(email);
        }
        throw new AuthenticationException("이메일 또는 비밀번호가 옳지 않습니다.");
    }

    public boolean isMember(String email, String password) {
        return memberRepository.existsByEmailAndPassword(email, password);
    }

    public long getMemberIdByEmail(String email) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new AuthenticationException("가입된 이메일이 아닙니다."));
        return member.getId();
    }
}
