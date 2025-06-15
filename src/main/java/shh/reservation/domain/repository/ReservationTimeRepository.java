package shh.reservation.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import shh.reservation.domain.ReservationTime;

public interface ReservationTimeRepository extends JpaRepository<ReservationTime, Long> {
}
