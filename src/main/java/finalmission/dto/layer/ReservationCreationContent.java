package finalmission.dto.layer;

import finalmission.dto.request.AddReservationRequest;
import java.time.LocalDate;

public record ReservationCreationContent(
        Long memberId,
        Long seatId,
        String reason,
        LocalDate date
) {

    public ReservationCreationContent(long memberId, AddReservationRequest request) {
        this(memberId, request.seatId(), request.reason(), request.date());
    }
}
