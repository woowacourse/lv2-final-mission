package finalmission.reservation.infrastructure;

import finalmission.reservation.domain.ReservationTime;
import java.time.LocalTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaReservationTimeRepository extends JpaRepository<ReservationTime, Long> {

    List<ReservationTime> findAllByStartAt(LocalTime startAt);
}
