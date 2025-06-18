package finalmission.dto.request;

import finalmission.entity.MeetingRoom;
import finalmission.entity.Member;
import finalmission.entity.Reservation;
import finalmission.entity.ReservationTime;
import java.time.LocalDate;
import java.time.LocalTime;

public record MyReservationResponse(
        Long id,
        String memberName,
        String meetingRoomName,
        String describe,
        LocalDate date,
        LocalTime startAt
) {
    public static MyReservationResponse from(final Reservation reservation) {
        Member member = reservation.getMember();
        MeetingRoom meetingRoom = reservation.getMeetingRoom();
        ReservationTime reservationTime = reservation.getReservationTime();

        return new MyReservationResponse(reservation.getId(), member.getName(),
                meetingRoom.getName(), meetingRoom.getDescribe(),
                reservation.getDate(), reservationTime.getStartAt());
    }
}
