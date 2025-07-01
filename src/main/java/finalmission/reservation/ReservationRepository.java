package finalmission.reservation;

import java.time.LocalDate;
import java.util.List;
import finalmission.reservation.domain.Reservation;
import finalmission.subway.domain.Subway;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    List<Reservation> findByDateAndSubwayAndSeat(LocalDate date, Subway subway, Seat seat);
}
