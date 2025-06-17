package finalmission.reservation.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import finalmission.reservation.entity.Reservation;
import java.time.LocalDate;
import java.time.LocalTime;

public record ReservationResponse(
        Long id,
        Long memberId,
        Long roomId,
        LocalDate date,
        @JsonFormat(pattern = "HH:mm") LocalTime startTime,
        @JsonFormat(pattern = "HH:mm") LocalTime endTime,
        String purpose
) {
    public static ReservationResponse from(Reservation reservation) {
        return new ReservationResponse(
                reservation.getId(),
                reservation.getMember().getId(),
                reservation.getRoom().getId(),
                reservation.getDate(),
                reservation.getStartTime(),
                reservation.getEndTime(),
                reservation.getPurpose()
        );
    }
}
