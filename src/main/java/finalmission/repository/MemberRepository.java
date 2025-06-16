package finalmission.repository;

import finalmission.domain.Member;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByEmail(@Param("email") String email);

    boolean existsByEmail(@Param("email") String email);
}
