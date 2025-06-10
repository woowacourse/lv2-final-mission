package finalmission.domain.service.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public record ReservationDetailResponse(
        String trainerName,
        LocalDate date,
        LocalTime time
) {

    public static ReservationDetailResponse from(String trainerName, LocalDate date, LocalTime time) {
        return new ReservationDetailResponse(trainerName, date, time);
    }
}
