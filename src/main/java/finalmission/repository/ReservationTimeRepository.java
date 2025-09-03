package finalmission.repository;

import finalmission.domain.ReservationTime;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationTimeRepository extends JpaRepository<ReservationTime, Long> {
}
