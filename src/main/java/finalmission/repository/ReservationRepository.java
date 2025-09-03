package finalmission.repository;

import finalmission.domain.Reservation;
import java.time.LocalDate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    boolean existsByTimeId(Long id);

    boolean existsBySportId(Long id);

    boolean findByMemberId(Long id);

    boolean existsByMemberId(Long id);

    int countReservationsByDateAndTimeIdAndSportId(LocalDate date, Long timeId, Long sportId);
}
