package finalmission.user.repository;

import finalmission.user.domain.Member;
import finalmission.user.domain.vo.Email;
import finalmission.user.domain.vo.Password;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    boolean existsByEmail(Email email);
    Optional<Member> findByEmailAndPassword(Email email, Password password);
}
