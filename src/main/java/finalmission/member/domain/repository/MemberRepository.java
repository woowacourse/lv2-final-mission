package finalmission.member.domain.repository;

import finalmission.member.domain.Email;
import finalmission.member.domain.Member;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
    boolean existsByEmail(Email email);

    Optional<Member> findByEmailValue(String email);
}
