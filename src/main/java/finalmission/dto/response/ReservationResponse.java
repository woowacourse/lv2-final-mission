package finalmission.dto.response;

import finalmission.entity.MeetingRoom;
import finalmission.entity.Member;
import finalmission.entity.Reservation;
import finalmission.entity.ReservationTime;
import java.time.LocalDate;
import java.time.LocalTime;

public record ReservationResponse(Long id,
                                  String memberName,
                                  String meetingRoomName,
                                  String describe,
                                  LocalDate date,
                                  LocalTime startAt) {

    public static ReservationResponse from(final Reservation reservation) {
        Member member = reservation.getMember();
        MeetingRoom meetingRoom = reservation.getMeetingRoom();
        ReservationTime reservationTime = reservation.getReservationTime();

        return new ReservationResponse(reservation.getId(), member.getName(),
                meetingRoom.getName(), meetingRoom.getDescribe(),
                reservation.getDate(), reservationTime.getStartAt());
    }
}
