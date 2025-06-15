package finalmission.planning.ui.dto.request;

public record CreateReservationRequest(
        String userName,
        String password,
        Long reservationSlotId
) {
}
