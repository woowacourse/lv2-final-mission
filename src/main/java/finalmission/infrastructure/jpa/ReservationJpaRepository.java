package finalmission.infrastructure.jpa;

import finalmission.domain.Reservation;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationJpaRepository extends JpaRepository<Reservation, Long> {

    List<Reservation> findAllByMemberId(long memberId);
}
