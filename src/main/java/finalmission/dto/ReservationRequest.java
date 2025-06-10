package finalmission.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalTime;

public record ReservationRequest(
    @NotBlank String room,
    @NotNull LocalDate date,
    @NotNull LocalTime time,
    @NotBlank String crew,
    @NotBlank String description
) {

}
