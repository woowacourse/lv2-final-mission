package finalmission.reservationTime.controller.dto.request;

import jakarta.validation.constraints.NotBlank;

public record ReservationTimeRequest(@NotBlank String bookedAt) {
}
