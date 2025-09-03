package finalmission.dto.response;

import finalmission.domain.Reservation;
import java.time.LocalDate;

public record ReservationResponse(
        Long id,
        LocalDate date,
        MemberResponse member,
        SportResponse sport,
        ReservationTimeResponse time
) {
    public static ReservationResponse from(final Reservation reservation) {
        return new ReservationResponse(
                reservation.getId(),
                reservation.getDate(),
                MemberResponse.from(reservation.getMember()),
                SportResponse.from(reservation.getSport()),
                ReservationTimeResponse.from(reservation.getTime())
        );
    }
}
