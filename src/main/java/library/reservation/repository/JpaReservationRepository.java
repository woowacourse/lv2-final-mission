package library.reservation.repository;

import library.reservation.domain.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaReservationRepository extends ReservationRepository, JpaRepository<Reservation,Long> {
}
