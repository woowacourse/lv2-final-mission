package finalmission.controller.dto;

import finalmission.domain.Reservation;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public record ReservationResponse(
        Long id,
        String room,
        LocalDate date,
        String startTime,
        String endTime
) {

    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");

    public ReservationResponse(Reservation reservation) {
        this(
                reservation.getId(),
                reservation.getRoom().getName(),
                reservation.getDate(),
                reservation.getStartTime(TIME_FORMATTER),
                reservation.getEndTime(TIME_FORMATTER)
        );
    }
}
