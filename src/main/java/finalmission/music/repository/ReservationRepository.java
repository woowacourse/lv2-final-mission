package finalmission.music.repository;

import finalmission.music.domain.Reservation;
import java.util.List;
import org.springframework.data.repository.ListCrudRepository;

public interface ReservationRepository extends ListCrudRepository<Reservation, Long> {
    List<Reservation> findByMember_Name(String memberName);
}
