package shh.member.domain.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import shh.member.domain.Email;
import shh.member.domain.Member;
import shh.member.domain.Password;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByEmailAndPassword(Email email, Password password);
}
