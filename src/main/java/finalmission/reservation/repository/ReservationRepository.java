package finalmission.reservation.repository;

import finalmission.reservation.domain.Reservation;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    boolean existsByTimeAndRoomId(LocalDateTime time, Long roomId);

    boolean existsByIdAndMemberId(Long reservationId, Long memberId);

    List<Reservation> findByMemberId(Long memberId);

    List<Reservation> findByTimeBetweenAndRoomId(LocalDateTime startTime, LocalDateTime endTime, Long roomId);
}
