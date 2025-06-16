package finalmission.reservation.controller.dto.request;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record ReservationRequest(
        @NotNull long restaurantId,
        @NotNull long reservationTimeId,
        @NotNull LocalDate date,
        @NotNull int visitor
) {
}
