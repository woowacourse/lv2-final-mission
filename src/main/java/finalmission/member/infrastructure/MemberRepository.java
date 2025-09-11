package finalmission.member.infrastructure;

import finalmission.member.domain.Member;
import java.util.Optional;
import org.springframework.data.repository.Repository;

public interface MemberRepository extends Repository<Member, Long> {

    Member save(Member member);

    boolean existsByEmail(String email);

    Optional<Member> findByEmail(String email);

    Optional<Member> findById(Long memberId);
}
