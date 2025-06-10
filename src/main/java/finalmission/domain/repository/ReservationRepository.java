package finalmission.domain.repository;

import finalmission.domain.Reservation;
import finalmission.domain.ReservationDateTime;
import java.time.LocalDateTime;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    Optional<ReservationDateTime> findByReservationDateTime(LocalDateTime localDateTime);
}
