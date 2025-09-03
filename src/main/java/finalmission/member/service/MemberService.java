package finalmission.member.service;

import finalmission.global.error.exception.ConflictException;
import finalmission.member.dto.request.MemberCreateRequest;
import finalmission.member.dto.response.MemberResponse;
import finalmission.member.entity.Member;
import finalmission.member.entity.RoleType;
import finalmission.member.repository.MemberRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public MemberResponse createMember(MemberCreateRequest request) {
        validateEmailNotDuplicate(request.email());

        Member member = new Member(request.name(), request.email(), request.password(), RoleType.USER);
        memberRepository.save(member);
        return MemberResponse.from(member);
    }

    private void validateEmailNotDuplicate(String email) {
        if (memberRepository.existsByEmail(email)) {
            throw new ConflictException("중복된 이메일입니다.");
        }
    }

    public List<MemberResponse> getAllMembers() {
        return memberRepository.findAll().stream()
                .map(MemberResponse::from)
                .toList();
    }

    public void deleteMember(long id) {
        memberRepository.deleteById(id);
    }
}
