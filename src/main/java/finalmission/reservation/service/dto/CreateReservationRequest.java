package finalmission.reservation.service.dto;

import jakarta.validation.constraints.NotNull;

public record CreateReservationRequest(
        @NotNull
        Long reservationInformationId
) {
}
