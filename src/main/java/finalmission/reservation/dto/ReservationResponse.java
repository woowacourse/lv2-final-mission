package finalmission.reservation.dto;

import finalmission.reservation.domain.Reservation;
import java.time.LocalDate;

public record ReservationResponse(
        Long id,
        LocalDate checkInDate,
        LocalDate checkOutDate,
        Long memberId,
        Long siteId
) {
    public static ReservationResponse from(Reservation reservation) {
        return new ReservationResponse(reservation.getId(), reservation.getCheckInDate(), reservation.getCheckOutDate(),
                reservation.getMember().getId(), reservation.getSite().getId());
    }
}
