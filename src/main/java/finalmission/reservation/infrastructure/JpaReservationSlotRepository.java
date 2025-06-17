package finalmission.reservation.infrastructure;

import finalmission.reservation.domain.ReservationSlot;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaReservationSlotRepository extends JpaRepository<ReservationSlot, Long> {

}
