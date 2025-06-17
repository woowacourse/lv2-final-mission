package shh.reservation.application.dto;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import shh.reservation.domain.ReservationTime;

public record ReservationUpdateRequest(
        @NotNull LocalDate date,
        @NotNull ReservationTime time
) {
}
