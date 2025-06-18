package finalmission.repository;

import finalmission.domain.Member;
import finalmission.domain.vo.LolName;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByLolName(LolName lolName);

    boolean existsByLolName(LolName lolName);
}
