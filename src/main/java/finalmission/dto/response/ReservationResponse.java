package finalmission.dto.response;

import finalmission.domain.Reservation;
import java.time.LocalDate;
import java.time.LocalTime;

public record ReservationResponse(
        Long id,
        LocalDate date,
        LocalTime startAt,
        LocalTime endAt,
        MemberResponse member,
        ToiletResponse toilet
) {
    public static ReservationResponse from(Reservation reservation) {
        return new ReservationResponse(
                reservation.getId(),
                reservation.getDate(),
                reservation.getStartAt(),
                reservation.getEndAt(),
                MemberResponse.from(reservation.getMember()),
                ToiletResponse.from(reservation.getToilet())
        );
    }
}
