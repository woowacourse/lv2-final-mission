package finalmission.repository;

import finalmission.domain.reservation.Reservation;
import finalmission.domain.schedule.Schedule;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    List<Reservation> findBySchedule(Schedule schedule);
}
