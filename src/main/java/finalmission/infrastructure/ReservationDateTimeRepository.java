package finalmission.infrastructure;

import finalmission.domain.ReservationDateTime;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservationDateTimeRepository extends JpaRepository<ReservationDateTime, Long> {
    Optional<ReservationDateTime> findById(Long id);
}
