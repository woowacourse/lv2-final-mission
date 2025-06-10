package woowaTable.restaurant.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import woowaTable.restaurant.domain.Reservation;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
}
