package finalmission.reservation.dto;

public record DeleteReservationRequest(long id,
                                       String name,
                                       String phoneNumber) {
}
