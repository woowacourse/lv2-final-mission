package finalmission.meetingroom.repository;

import java.time.LocalDate;
import java.time.LocalTime;

import org.springframework.data.jpa.repository.JpaRepository;

import finalmission.meetingroom.domain.MeetingRoom;
import finalmission.meetingroom.domain.Reservation;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    boolean existsByMeetingRoomAndReservationDateAndStartAtBetween(
            MeetingRoom meetingRoom,
            LocalDate reservationDate,
            LocalTime startTime,
            LocalTime endTime
    );

    boolean existsByMeetingRoomAndReservationDateAndEndAtBetween(
            MeetingRoom meetingRoom,
            LocalDate reservationDate,
            LocalTime startTime,
            LocalTime endTime
    );
}
