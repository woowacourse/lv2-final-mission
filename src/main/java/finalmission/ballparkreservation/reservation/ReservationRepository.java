package finalmission.ballparkreservation.reservation;

import finalmission.ballparkreservation.schedule.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    boolean existsBySchedule(Schedule schedule);

    List<Reservation> findAllByMember_Id(Long id);
}
