package finalmission.dto.response;

import finalmission.entity.MeetingRoom;
import finalmission.entity.Member;
import finalmission.entity.Reservation;
import finalmission.entity.ReservationTime;
import java.time.LocalDate;
import java.time.LocalTime;

public record CreateReservationResponse(Long id,
                                        String meetingRoomName,
                                        String describe,
                                        Integer availablePeopleCount,
                                        String memberName,
                                        LocalDate date,
                                        LocalTime startAt) {

    public static CreateReservationResponse from(Reservation reservation) {
        Member member = reservation.getMember();
        MeetingRoom meetingRoom = reservation.getMeetingRoom();
        ReservationTime reservationTime = reservation.getReservationTime();

        return new CreateReservationResponse(
                reservation.getId(),
                meetingRoom.getName(),
                meetingRoom.getDescribe(),
                meetingRoom.getAvailablePeopleCount(),
                member.getName(),
                reservation.getDate(),
                reservationTime.getStartAt()
        );
    }
}
