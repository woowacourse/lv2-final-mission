package finalmission.mungPlan.infra;

import finalmission.mungPlan.domain.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
}
