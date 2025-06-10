package finalmission.repository;

import finalmission.entity.BlackList;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlackListRepository extends JpaRepository<BlackList, Long> {
    boolean existsByMemberId(Long memberId);
}
