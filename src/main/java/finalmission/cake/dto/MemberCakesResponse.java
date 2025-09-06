package finalmission.cake.dto;

import finalmission.cake.model.Reservation;

public record MemberCakesResponse(
        Long reservationId,
        String cakeName,
        String flavorName,
        Integer size,
        String lettering
) {

    public static MemberCakesResponse from(Reservation reservation) {
        return new MemberCakesResponse(reservation.getId(), reservation.getCake().getName(),
                reservation.getFlavor().getName(), reservation.getSize().getDimension(),
                reservation.getLettering());
    }
}
