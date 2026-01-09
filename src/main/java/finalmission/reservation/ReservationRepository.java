package finalmission.reservation;

import finalmission.meetingroom.MeetingRoom;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    List<Reservation> findByMeetingRoomAndDate(MeetingRoom meetingRoomId, LocalDate date);

    boolean existsByMeetingRoomIdAndDateAndTimeId(Long meetingRoomId, LocalDate date, Long timeId);

    List<Reservation> findAllByMemberId(Long id);
}
