package finalmission.service;

import finalmission.domain.Member;
import finalmission.dto.response.MemberResponse;
import finalmission.repository.MemberRepository;
import java.util.NoSuchElementException;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberService(final MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public MemberResponse findById(Long id) {
        Optional<Member> member = memberRepository.findById(id);
        if (member.isEmpty()) {
            throw new NoSuchElementException("[ERROR] 멤버를 찾을 수 없습니다.");
        }
        return MemberResponse.from(member.get());
    }

    public MemberResponse findByEmail(String email) {
        Optional<Member> member = memberRepository.findByEmail(email);
        if (member.isEmpty()) {
            throw new NoSuchElementException("[ERROR] 멤버를 찾을 수 없습니다.");
        }
        return MemberResponse.from(member.get());
    }
}
