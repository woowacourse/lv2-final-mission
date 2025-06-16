package finalmission.dto.response;

import finalmission.domain.Reservation;
import java.time.LocalDate;

public record FindAllReservationBySeat(
        Long reservationId,
        LocalDate date
) {

    public FindAllReservationBySeat(Reservation reservation) {
        this(reservation.getId(), reservation.getDate());
    }
}
