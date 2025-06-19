package finalmission.domain.service.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public record TrainerReservationResponse(LocalDate date, LocalTime time) {

    public static TrainerReservationResponse from(LocalDate date, LocalTime time) {
        return new TrainerReservationResponse(date, time);
    }
}
