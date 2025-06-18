package finalmission.infrastructure;

import finalmission.domain.Reservation;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findByMemberId(Long memberId);

    Optional<Reservation> findById(Long id);

    void deleteById(Long id);
}
