package finalmission.member.infrastructure;

import finalmission.member.domain.Member;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaMemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByEmail(String email);
}
