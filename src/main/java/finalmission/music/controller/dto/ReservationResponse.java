package finalmission.music.controller.dto;

import finalmission.music.domain.Reservation;

public record ReservationResponse(Long id, Long memberId, Long albumId, String address) {

    public static ReservationResponse from(final Reservation reservation) {
        return new ReservationResponse(
            reservation.getId(),
            reservation.getMember().getId(),
            reservation.getAlbum().getId(),
            reservation.getAddress());
    }
}
