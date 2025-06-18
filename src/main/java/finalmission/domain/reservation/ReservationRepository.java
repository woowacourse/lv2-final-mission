package finalmission.domain.reservation;

import finalmission.domain.customer.Customer;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    List<Reservation> findReservationByCustomer(Customer customer);
}
