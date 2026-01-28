package finalmission.stallstatus.repository;

import finalmission.member.entity.Member;
import finalmission.stall.entity.Stall;
import finalmission.stallstatus.entity.StallStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StallStatusRepository extends JpaRepository<StallStatus, Long> {
    boolean existsByStall(Stall stall);

    boolean existsByMember(Member member);

    Optional<StallStatus> findTopByStallOrderByIdAsc(Stall stall);

    Optional<StallStatus> findByMember(Member member);
}
