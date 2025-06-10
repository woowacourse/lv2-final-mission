package finalmission.api.v1.reservation.repository;

import finalmission.api.v1.reservation.domain.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
}
