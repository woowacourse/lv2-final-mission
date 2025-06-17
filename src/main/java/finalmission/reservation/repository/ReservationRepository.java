package finalmission.reservation.repository;

import finalmission.member.domain.User;
import finalmission.reservation.domain.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Integer> {
    long countByReservationDate(LocalDate reservationDate);

    List<Reservation> findByUserAndReservationDate(User user, LocalDate reservationDate);

    void deleteByUserAndReservationDate(User user, LocalDate reservationDate);

    List<Reservation> findByReservationDate(LocalDate reservationDate);
}
