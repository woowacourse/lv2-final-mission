package library.reservation.service;

import library.reservation.domain.Reservation;

public record ReservationResponse(
        Long id,
        String email,
        Long collectionId,
        String title,
        String author,
        Long isbn,
        String collectionStatus



) {

    public static ReservationResponse from(final Reservation reservation) {
        return new ReservationResponse(reservation.getId(), reservation.getMember().getEmail(),
                reservation.getCollection().getId(),reservation.getCollection().getBook().getTitle(),
                reservation.getCollection().getBook().getAuthor(),reservation.getCollection().getBook().getIsbn(),
                reservation.getCollection().getCollectionStatus().toString());
    }
}
