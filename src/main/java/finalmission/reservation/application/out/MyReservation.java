package finalmission.reservation.application.out;

import finalmission.reservation.domain.ReservationStatus;
import java.time.LocalDateTime;

public record MyReservation(
        Long reservationId,
        Long popupStoreId,
        String title,
        LocalDateTime reservedAt,
        ReservationStatus reservationStatus
) {
}
