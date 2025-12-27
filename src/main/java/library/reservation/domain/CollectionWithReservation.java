package library.reservation.domain;

import java.time.LocalDate;

public record CollectionWithReservation(
        Long collectionId,
        String location,
        Long ReservationId,
        LocalDate dueDate
) {



}
