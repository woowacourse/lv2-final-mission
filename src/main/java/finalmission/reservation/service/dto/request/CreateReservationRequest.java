package finalmission.reservation.service.dto.request;

import jakarta.validation.constraints.NotNull;

public record CreateReservationRequest(
        @NotNull
        Long reservationInformationId
) {
}
