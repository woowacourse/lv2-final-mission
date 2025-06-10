package finalmission.music.controller.dto;

import finalmission.music.domain.Reservation;

public record MyReservationResponse(
    AlbumResponse albumResponse,
    String member,
    String address
) {
    public static MyReservationResponse from(Reservation reservation) {
        return new MyReservationResponse(
            new AlbumResponse(reservation.getAlbum().getName(), reservation.getAlbum().getArtistName(), reservation.getAlbum().getTotalTracks(), reservation.getAlbum().getReleaseDate()),
            reservation.getMember().getName(),
            reservation.getAddress()
        );
    }
}
