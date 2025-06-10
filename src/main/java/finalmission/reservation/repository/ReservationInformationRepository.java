package finalmission.reservation.repository;

import finalmission.reservation.domain.ReservationInformation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationInformationRepository extends JpaRepository<ReservationInformation, Long> {
}
