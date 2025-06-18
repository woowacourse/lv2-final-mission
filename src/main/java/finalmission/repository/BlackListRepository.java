package finalmission.repository;

import finalmission.entity.BlackList;
import finalmission.entity.Member;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface BlackListRepository extends JpaRepository<BlackList, Long> {
    boolean existsByMemberId(Long memberId);

    @Query(value = """
                select bl from BlackList bl join fetch bl.member
            """)
    List<BlackList> findAllFetch();

    Optional<BlackList> findByMember(Member member);
}
