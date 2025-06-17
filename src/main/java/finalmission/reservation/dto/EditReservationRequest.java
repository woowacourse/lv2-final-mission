package finalmission.reservation.dto;

public record EditReservationRequest(long id,
                                     String originName,
                                     String originPhoneNumber,
                                     String newName,
                                     String newPhoneNumber) {
}
