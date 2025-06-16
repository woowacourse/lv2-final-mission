package finalmission.reservation.infrastructure;

import finalmission.reservation.domain.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    boolean existsByExerciseCourseId(Long id);

    boolean existsByTimeId(Long id);
}
