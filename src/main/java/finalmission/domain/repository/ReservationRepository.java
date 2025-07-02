package finalmission.domain.repository;

import finalmission.domain.Reservation;
import finalmission.domain.YogaSession;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    List<Reservation> findAllByMemberId(Long memberId);

    long countBySession(YogaSession session);
}
