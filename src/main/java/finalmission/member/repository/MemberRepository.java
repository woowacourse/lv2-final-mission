package finalmission.member.repository;

import finalmission.member.domain.Member;
import finalmission.member.domain.vo.Email;
import finalmission.member.domain.vo.Password;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    boolean existsByEmail(Email email);
    Optional<Member> findByEmailAndPassword(Email email, Password password);
}
