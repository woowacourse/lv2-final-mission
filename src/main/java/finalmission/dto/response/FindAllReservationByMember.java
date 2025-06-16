package finalmission.dto.response;

import finalmission.domain.Reservation;
import java.time.LocalDate;

public record FindAllReservationByMember(
        Long reservationId,
        Long seatId,
        LocalDate date
) {

    public FindAllReservationByMember(Reservation reservation) {
        this(reservation.getId(), reservation.getSeat().getId(), reservation.getDate());
    }
}
