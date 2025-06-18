package finalmission.member.service.detail;

import finalmission.common.exception.AlreadyExistException;
import finalmission.member.domain.Member;
import finalmission.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MemberCommandService {

    private final MemberRepository memberRepository;

    public Member create(final Member member) {
        if (memberRepository.existsByEmail(member.getEmail())) {
            throw new AlreadyExistException("이미 존재하는 이메일입니다.");
        }

        return memberRepository.save(member);
    }
}
