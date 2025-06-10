package finalmission.infrastructure;

import finalmission.domain.Member;
import java.util.Optional;
import org.springframework.data.repository.Repository;

public interface MemberRepository extends Repository<Member, Long> {

    Member save(Member member);

    Optional<Member> findByEmail(String email);

    Optional<Member> findById(Long memberId);
}
