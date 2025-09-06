package finalmission.cake.repository;

import finalmission.cake.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaReservationRepository extends ReservationRepository, JpaRepository<Reservation, Long> {
}
