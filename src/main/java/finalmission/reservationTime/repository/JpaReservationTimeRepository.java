package finalmission.reservationTime.repository;

import finalmission.reservationTime.domain.ReservationTime;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaReservationTimeRepository extends JpaRepository<ReservationTime,Long> {
}
