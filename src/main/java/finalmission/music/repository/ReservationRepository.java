package finalmission.music.repository;

import finalmission.music.domain.Reservation;
import org.springframework.data.repository.ListCrudRepository;

public interface ReservationRepository extends ListCrudRepository<Reservation, Long> {
}
