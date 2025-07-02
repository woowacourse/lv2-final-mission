package finalmission.domain.repository;

import finalmission.domain.Member;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {

    boolean existsByEmailAndPassword(String email, String password);

    Optional<Member> findByEmail(String email);
}
