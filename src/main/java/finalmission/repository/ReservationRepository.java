package finalmission.repository;

import finalmission.entity.Reservation;
import java.time.LocalDate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    boolean existsByMeetingRoomId(Long id);

    boolean existsByReservationTimeId(Long reservationTimeId);

    boolean existsByMeetingRoomIdAndDateAndReservationTimeId(Long meetingRoomId, LocalDate date,
                                                             Long reservationTimeId);

    boolean existsByIdAndMemberId(Long id, Long memberId);
}
