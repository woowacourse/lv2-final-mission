package finalmission.reservation.infrastructure;

import finalmission.reservation.domain.Reservation;
import java.time.LocalDate;
import java.util.List;

public interface ReservationRepositoryCustom {

    List<Reservation> findByToiletIdAndDate(Long toiletId, LocalDate date);
}
