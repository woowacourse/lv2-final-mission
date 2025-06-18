package finalmission.reservationtime.repository;

import finalmission.reservationtime.domain.ReservationTime;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationTimeRepository extends JpaRepository<ReservationTime, Long> {
}
