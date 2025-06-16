package finalmission.repository;

import finalmission.domain.reservation.Reservation;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    List<Reservation> findAllByCrewId(Long crewId);

    List<Reservation> findAllByCoachId(Long coachId);
}
