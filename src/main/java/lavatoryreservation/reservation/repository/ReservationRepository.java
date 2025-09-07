package lavatoryreservation.reservation.repository;

import java.time.LocalDateTime;
import java.util.Optional;
import lavatoryreservation.reservation.domain.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    boolean existsByToiletTime_EndTimeBetween(LocalDateTime startDateTime, LocalDateTime endDateTime);

    Optional<Reservation> findByMember_id(Long memberId);
}
