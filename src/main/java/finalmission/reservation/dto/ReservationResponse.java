package finalmission.reservation.dto;

import finalmission.reservation.domain.Reservation;

import java.time.LocalDate;
import java.time.LocalTime;

public record ReservationResponse(
        Long id,
        LocalDate date,
        LocalTime time,
        String meetingRoomName
) {

    public static ReservationResponse from(Reservation reservation) {
        return new ReservationResponse(
                reservation.getId(),
                reservation.getDate(),
                reservation.getTime().getStartAt(),
                reservation.getMeetingRoom().getRoomName()
        );
    }
}
