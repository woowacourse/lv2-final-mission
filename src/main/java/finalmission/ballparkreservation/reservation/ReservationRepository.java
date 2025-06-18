package finalmission.ballparkreservation.reservation;

import finalmission.ballparkreservation.schedule.Schedule;
import finalmission.ballparkreservation.schedule.SeatRank;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    boolean existsBySchedule(Schedule schedule);

    List<Reservation> findAllByMember_Id(Long id);

    boolean existsBySchedule_DateAndSchedule_RankAndSchedule_Number(LocalDate date, SeatRank rank, int seatNumber);
}
