package finalmission.dto.layer;

import java.time.LocalDate;

public record ReservationBySeatContent(
        Long reservationId,
        Long seatId,
        LocalDate date
) {

}
