package woowaTable.reservation.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import woowaTable.reservation.domain.Reservation;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
}
