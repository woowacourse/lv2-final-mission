package finalmission.domain;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ReservationRepository extends JpaRepository<Reservation, Ticket> {

    @Query("select r.ticket from Reservation r")
    List<Ticket> findAllTickets();
}
