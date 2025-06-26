package finalmission.reservation.database;

import finalmission.reservation.model.Time;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TimeRepository extends JpaRepository<Time, Long> {
}
