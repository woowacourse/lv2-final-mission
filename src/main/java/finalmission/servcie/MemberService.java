package finalmission.servcie;

import finalmission.domain.Member;
import finalmission.domain.Role;
import finalmission.dto.layer.MemberCreationContent;
import finalmission.exception.BadRequestException;
import finalmission.repository.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Transactional
    public Member addMember(MemberCreationContent creationContent) {
        validateDuplicatedEmail(creationContent.email());
        Member member = new Member(
                creationContent.email(), creationContent.password(), creationContent.name(), Role.GENERAL);
        return memberRepository.save(member);
    }

    private void validateDuplicatedEmail(String email) {
        boolean isDuplicated = memberRepository.existsByEmail(email);
        if (isDuplicated) {
            throw new BadRequestException("중복된 이메일입니다.");
        }
    }
}
