package finalmission.controller.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import finalmission.domain.reservation.entity.Reservation;
import java.time.LocalDate;
import java.time.LocalTime;

public record ReservationResponse(
        Long id,
        String name,
        String phoneNumber,
        String lesson,
        LocalDate date,
        @JsonFormat(pattern = "HH:mm") LocalTime time
) {

    public static ReservationResponse from(final Reservation reservation) {
        return new ReservationResponse(
                reservation.getId(),
                reservation.getMember().getName(),
                reservation.getMember().getPhoneNumber(),
                reservation.getLesson(),
                reservation.getDate(),
                reservation.getTime()
        );
    }
}
