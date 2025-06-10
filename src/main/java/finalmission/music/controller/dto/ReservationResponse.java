package finalmission.music.controller.dto;

import finalmission.music.domain.Reservation;

public record ReservationResponse(Long id, String memberName, Long albumId, String address) {

    public static ReservationResponse from(final Reservation reservation) {
        return new ReservationResponse(
            reservation.getId(),
            reservation.getMember().getName(),
            reservation.getAlbum().getId(),
            reservation.getAddress());
    }
}
