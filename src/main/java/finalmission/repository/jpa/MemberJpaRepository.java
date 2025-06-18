package finalmission.repository.jpa;

import finalmission.domain.Member;
import finalmission.repository.MemberRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberJpaRepository extends JpaRepository<Member, Long>, MemberRepository {
}
