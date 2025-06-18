package finalmission.member.intrastructure;

import finalmission.common.exception.NotFoundException;
import finalmission.member.domain.Member;
import finalmission.member.domain.MemberRepository;
import finalmission.member.ui.dto.LoginRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class MemberRepositoryImpl implements MemberRepository {

    private final JpaMemberRepository memberRepository;

    @Override
    public Member getById(Long id) {
        return memberRepository.findById(id).orElseThrow(
                () -> new NotFoundException(Member.class.getSimpleName(), id)
        );
    }

    public Member signIn(LoginRequest request) {
        return memberRepository.findByEmail(request.email()).orElseThrow(
                () -> new NotFoundException(Member.class.getSimpleName(), request.email())

        );
    }
}
