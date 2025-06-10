package finalmission.member.infrastructure;

import finalmission.exception.resource.ResourceNotFoundException;
import finalmission.member.domain.Member;
import finalmission.member.domain.MemberRepository;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class MemberRepositoryImpl implements MemberRepository {

    private final JpaMemberRepository jpaMemberRepository;

    @Override
    public Member save(final Member member) {
        return jpaMemberRepository.save(member);
    }

    @Override
    public void deleteById(final Long id) {
        jpaMemberRepository.deleteById(id);
    }

    @Override
    public Optional<Member> findByEmail(final String email) {
        return jpaMemberRepository.findByEmail(email);
    }

    @Override
    public Member getById(final Long memberId) {
        return jpaMemberRepository.findById(memberId)
                .orElseThrow(() -> new ResourceNotFoundException("해당 회원을 찾을 수 없습니다. id = " + memberId));
    }

    @Override
    public List<Member> findAll() {
        return jpaMemberRepository.findAll();
    }
}
