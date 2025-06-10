package finalmission.reservationtime.infrastructure;

import finalmission.reservationtime.domain.ReservationTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservationTimeRepository extends JpaRepository<ReservationTime, Long> {
}
