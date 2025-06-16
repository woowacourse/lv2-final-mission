package finalmission.dto.response;

import finalmission.domain.Reservation;
import java.time.LocalDate;

public record FindReservationById(
        Long reservationId,
        Long seatId,
        String seatName,
        String reason,
        LocalDate date
) {

    public FindReservationById(Reservation reservation) {
        this(reservation.getId(), reservation.getSeat().getId(), reservation.getSeat().getName(),
                reservation.getReason(), reservation.getDate());
    }
}
