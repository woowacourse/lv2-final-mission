package shh.reservation.application.dto;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public record ReservationAddRequest(
        @NotNull LocalDate date,
        @NotNull Long timeId,
        @NotNull Long stallId
) {
}
