package finalmission.reservation.dto;

import finalmission.reservation.domain.Reservation;

import java.time.LocalDate;
import java.time.LocalTime;

public record ReservationResponse(
        Long reservationId,
        String userName,
        LocalDate reservationDate,
        LocalTime reservationTime,
        String meetingRoomName
) {

    public static ReservationResponse from(Reservation reservation) {
        return new ReservationResponse(
                reservation.getId(),
                reservation.getMember().getName(),
                reservation.getDate(),
                reservation.getTime().getStartAt(),
                reservation.getMeetingRoom().getRoomName()
        );
    }
}
