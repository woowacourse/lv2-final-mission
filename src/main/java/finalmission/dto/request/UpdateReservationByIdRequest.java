package finalmission.dto.request;

public record UpdateReservationByIdRequest(
        Long reservationId,
        String reason
) {

}
