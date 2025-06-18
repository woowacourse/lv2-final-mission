package finalmission.api.v1.reservation.repository;

import finalmission.api.v1.reservation.domain.ReservationTime;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationTimeRepository extends JpaRepository<ReservationTime, Long> {

    Optional<ReservationTime> findById(final Long id);
}
