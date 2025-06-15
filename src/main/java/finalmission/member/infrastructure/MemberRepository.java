package finalmission.member.infrastructure;

import finalmission.member.domain.Member;
import org.springframework.data.repository.Repository;

public interface MemberRepository extends Repository<Member, Long> {

    Member save(Member member);

    boolean existsByEmail(String email);
}
