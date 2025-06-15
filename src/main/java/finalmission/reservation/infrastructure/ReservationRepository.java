package finalmission.reservation.infrastructure;

import finalmission.reservation.domain.Reservation;
import org.springframework.data.repository.Repository;

public interface ReservationRepository extends Repository<Reservation, Long>, ReservationRepositoryCustom {
    Reservation save(Reservation reservation);
}
