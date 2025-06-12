package finalmission.running.repository;

import finalmission.running.domain.RunningSession;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RunningReservationRepository extends JpaRepository<RunningSession, Long> {
}
