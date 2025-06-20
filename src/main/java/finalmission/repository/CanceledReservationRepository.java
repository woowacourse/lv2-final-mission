package finalmission.repository;

import finalmission.domain.CanceledReservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CanceledReservationRepository extends JpaRepository<CanceledReservation, Long> {
}
