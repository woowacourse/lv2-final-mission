package finalmission.member.application;

import static finalmission.auth.domain.AuthRole.ADMIN;
import static finalmission.auth.domain.AuthRole.MEMBER;

import finalmission.exception.auth.AuthorizationException;
import finalmission.member.domain.Member;
import finalmission.member.domain.MemberRepository;
import finalmission.member.ui.dto.MemberResponse;
import finalmission.member.ui.dto.SignUpRequest;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional
    public MemberResponse create(final SignUpRequest request) {
        final Member member = new Member(
                request.nickname(),
                request.email(),
                request.password(),
                MEMBER
        );

        final Member saved = memberRepository.save(member);
        return new MemberResponse(
                saved.getId(),
                saved.getNickname(),
                saved.getEmail()
        );
    }

    @Transactional
    public void delete(final Member member, final Long memberId) {
        final Member found = memberRepository.getById(memberId);

        if (!memberId.equals(member.getId())) {
            throw new AuthorizationException("본인 계정만 삭제할 수 있습니다");
        }

        if (found.getRole() == ADMIN) {
            throw new AuthorizationException("관리자 계정은 삭제할 수 없습니다.");
        }
        memberRepository.deleteById(memberId);
    }

    @Transactional(readOnly = true)
    public List<MemberResponse> findAllNames() {
        return memberRepository.findAll().stream()
                .map(MemberResponse::from)
                .toList();
    }
}
