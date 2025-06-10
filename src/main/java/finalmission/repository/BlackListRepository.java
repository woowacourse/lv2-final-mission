package finalmission.repository;

import finalmission.entity.BlackList;
import finalmission.entity.Member;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlackListRepository extends JpaRepository<BlackList, Long> {
    boolean existsByMemberId(Long memberId);

    Optional<BlackList> findByMember(Member member);
}
