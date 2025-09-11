package finalmission.reservation.dto.response;

import finalmission.member.dto.response.MemberResponse;
import finalmission.reservation.domain.Reservation;
import finalmission.toilet.dto.response.ToiletResponse;
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
