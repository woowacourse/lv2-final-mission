package finalmission.dto.layer;

import finalmission.dto.request.UpdateReservationByIdRequest;

public record ReservationUpdateContent(
        Long reservationId,
        String reason
) {

    public ReservationUpdateContent(UpdateReservationByIdRequest request) {
        this(request.reservationId(), request.reason());
    }
}
