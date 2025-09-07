package lavatoryreservation.member.repository;

import java.util.Optional;
import lavatoryreservation.member.domain.Email;
import lavatoryreservation.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByEmail(Email email);
}
