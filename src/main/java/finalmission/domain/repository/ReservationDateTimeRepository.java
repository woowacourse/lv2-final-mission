package finalmission.domain.repository;

import finalmission.domain.Coach;
import finalmission.domain.ReservationDateTime;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservationDateTimeRepository extends JpaRepository<ReservationDateTime, Long> {
    List<ReservationDateTime> findByCoach(Coach coach);
    Optional<ReservationDateTime> findByDateTime(LocalDateTime localDateTime);

}
