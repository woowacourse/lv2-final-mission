package finalmission.domain.repository;

import finalmission.domain.ReservationDateTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservationDateTimeRepository extends JpaRepository<ReservationDateTime, Long> {
}
