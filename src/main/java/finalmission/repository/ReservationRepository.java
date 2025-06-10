package finalmission.repository;

import finalmission.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    boolean existsByMeetingRoomId(Long id);

    boolean existsByReservationTimeId(Long reservationTimeId);
}
