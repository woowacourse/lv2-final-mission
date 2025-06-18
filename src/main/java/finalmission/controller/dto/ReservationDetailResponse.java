package finalmission.controller.dto;

import finalmission.domain.Reservation;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public record ReservationDetailResponse(
        Long id,
        String nickname,
        RoomResponse room,
        LocalDate date,
        String startTime,
        String endTime
) {

    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");

    public ReservationDetailResponse(Reservation reservation) {
        this(
                reservation.getId(),
                reservation.getMember().getNickname(),
                new RoomResponse(reservation.getRoom()),
                reservation.getDate(),
                reservation.getStartTime(TIME_FORMATTER),
                reservation.getEndTime(TIME_FORMATTER)
        );
    }
}
