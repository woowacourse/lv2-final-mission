package finalmission.member.service;

import finalmission.global.error.exception.BadRequestException;
import finalmission.global.error.exception.NotFoundException;
import finalmission.member.dto.request.MemberRequest;
import finalmission.member.dto.response.MemberResponse;
import finalmission.member.entity.Member;
import finalmission.member.entity.RoleType;
import finalmission.member.repository.MemberRepository;
import finalmission.thirdparty.service.RandomNameService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final RandomNameService randomNameService;

    @Transactional
    public MemberResponse createMember(MemberRequest request) {
        validateDuplicatedEmail(request.email());

        String randomName = randomNameService.getRandomName();

        Member member = new Member(
                request.name(),
                randomName,
                request.email(),
                request.password(),
                RoleType.USER
        );
        Member saved = memberRepository.save(member);

        return MemberResponse.from(saved);
    }

    @Transactional
    public MemberResponse createMemberAsAdmin(MemberRequest request) {
        validateDuplicatedEmail(request.email());

        Member member = new Member(
                request.name(),
                request.name(),
                request.email(),
                request.password(),
                RoleType.ADMIN
        );
        Member saved = memberRepository.save(member);

        return MemberResponse.from(saved);
    }

    @Transactional(readOnly = true)
    public List<MemberResponse> findAllMembers() {
        return memberRepository.findAll().stream()
                .map(MemberResponse::from)
                .toList();
    }

    @Transactional(readOnly = true)
    public MemberResponse findMemberById(Long id) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("존재하지 않는 멤버입니다."));

        return MemberResponse.from(member);
    }

    @Transactional
    public void promoteToAdmin(Long id) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("존재하지 않는 멤버입니다."));

        member.promoteToAdmin();
    }

    @Transactional
    public void deleteMember(Long id) {
        memberRepository.deleteById(id);
    }

    private void validateDuplicatedEmail(String email) {
        if (memberRepository.existsByEmail(email)) {
            throw new BadRequestException("중복된 이메일입니다.");
        }
    }
}
