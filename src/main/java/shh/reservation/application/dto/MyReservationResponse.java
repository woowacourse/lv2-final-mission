package shh.reservation.application.dto;

import java.time.LocalDate;
import shh.member.application.dto.MemberResponse;
import shh.reservation.domain.Reservation;
import shh.stall.application.dto.StallResponse;

public record MyReservationResponse(
        Long id,
        LocalDate date,
        ReservationTimeResponse time,
        StallResponse stall,
        MemberResponse member,
        String alias
) {

    public static MyReservationResponse from(Reservation reservation) {
        return new MyReservationResponse(
                reservation.getId(),
                reservation.getDate(),
                ReservationTimeResponse.from(reservation.getTime()),
                StallResponse.from(reservation.getStall()),
                MemberResponse.from(reservation.getMember()),
                reservation.getAlias().getAlias()
        );
    }
}
