package finalmission.reservation.repository;

import finalmission.reservation.domain.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;

public interface ReservationRepository extends JpaRepository<Reservation, Integer> {
    long findByReservationDate(LocalDate reservationDate);

    long countByReservationDate(LocalDate reservationDate);
}
