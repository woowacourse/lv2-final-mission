package finalmission.service;

import finalmission.domain.Member;
import finalmission.domain.MemberRole;
import finalmission.dto.request.MemberCreateRequest;
import finalmission.dto.response.MemberCreateResponse;
import finalmission.dto.response.MemberResponse;
import finalmission.repository.MemberRepository;
import java.util.NoSuchElementException;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberService(final MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public MemberCreateResponse save(MemberCreateRequest memberCreateRequest) {
        Member member = new Member(memberCreateRequest.email(), memberCreateRequest.name(),
                memberCreateRequest.password(), MemberRole.MASTER);
        return MemberCreateResponse.from(memberRepository.save(member));
    }

    public MemberResponse findById(Long id) {
        Optional<Member> member = memberRepository.findById(id);

        if (member.isEmpty()) {
            throw new NoSuchElementException("[ERROR] 멤버 정보가 존재하지 않습니다.");
        }
        return MemberResponse.from(member.get());
    }

    public MemberResponse findByEmail(String email) {
        Optional<Member> member = memberRepository.findByEmail(email);

        if (member.isEmpty()) {
            throw new NoSuchElementException("[ERROR] 멤버가 존재하지 않습니다.");
        }
        return MemberResponse.from(member.get());
    }
}
