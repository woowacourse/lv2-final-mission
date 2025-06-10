package finalmission.ballparkreservation.member;

import finalmission.ballparkreservation.member.dto.MemberCreateRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public void create(final MemberCreateRequest request) {
        Member member = new Member(request.email(), request.password(), request.name(), request.age());
        try {
            memberRepository.save(member);
        } catch (Exception e) {
            throw new IllegalArgumentException("회원가입에 실패했습니다.");
        }
    }

    public Member getByEmail(final String email) {
        return memberRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("등록되지 않은 회원입니다."));
    }

    public boolean existsByEmail(final String email) {
        return memberRepository.existsByEmail(email);
    }
}
