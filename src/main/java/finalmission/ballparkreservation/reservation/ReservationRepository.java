package finalmission.ballparkreservation.reservation;

import finalmission.ballparkreservation.schedule.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    boolean existsBySchedule(Schedule schedule);
}
