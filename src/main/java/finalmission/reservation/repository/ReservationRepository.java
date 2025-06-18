package finalmission.reservation.repository;

import finalmission.customer.domain.Customer;
import finalmission.reservation.domain.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Integer> {
    long countByReservationDate(LocalDate reservationDate);

    List<Reservation> findByCustomerAndReservationDate(Customer customer, LocalDate reservationDate);

    void deleteByCustomerAndReservationDate(Customer customer, LocalDate reservationDate);

    List<Reservation> findByReservationDate(LocalDate reservationDate);
}
