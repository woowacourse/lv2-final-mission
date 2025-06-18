package finalmission.reservation.dto;

import finalmission.reservation.domain.Reservation;
import java.time.LocalDate;

public record MyReservationResponse(
        Long id,
        LocalDate checkInDate,
        LocalDate checkOutDate,
        Long siteId
) {
    public static MyReservationResponse from(Reservation reservation) {
        return new MyReservationResponse(reservation.getId(), reservation.getCheckInDate(),
                reservation.getCheckOutDate(), reservation.getSite().getId());
    }
}
