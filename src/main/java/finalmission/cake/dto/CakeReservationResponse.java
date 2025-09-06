package finalmission.cake.dto;

import finalmission.cake.model.Reservation;

public record CakeReservationResponse(
        Long reservationId,
        String cakeName,
        String flavorName,
        Integer size,
        String lettering
) {

    public static CakeReservationResponse from(Reservation reservation) {
        return new CakeReservationResponse(reservation.getId(), reservation.getCake().getName(),
                reservation.getFlavor().getName(), reservation.getSize().getDimension(),
                reservation.getLettering());
    }
}
