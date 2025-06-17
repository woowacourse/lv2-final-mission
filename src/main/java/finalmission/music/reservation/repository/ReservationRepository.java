package finalmission.music.reservation.repository;

import finalmission.music.reservation.domain.Reservation;
import java.util.List;
import java.util.Optional;
import org.springframework.data.repository.ListCrudRepository;

public interface ReservationRepository extends ListCrudRepository<Reservation, Long> {
    List<Reservation> findByMember_Name(String memberName);

    void deleteByIdAndMemberName(Long id, String memberName);

    Optional<Reservation> findByIdAndMemberName(Long id, String memberName);
}
