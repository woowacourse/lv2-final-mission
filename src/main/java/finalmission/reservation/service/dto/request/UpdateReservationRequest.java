package finalmission.reservation.service.dto.request;

import jakarta.validation.constraints.NotNull;

public record UpdateReservationRequest(
        @NotNull
        Long updatedInformationId
) {
}
