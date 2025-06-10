package finalmission.reservation.dto;

import finalmission.member.dto.MemberResponse;
import finalmission.reservation.domain.Reservation;
import finalmission.umbrella.dto.UmbrellaResponse;

import java.time.LocalDate;

public record ReservationResponse(
        long id, LocalDate reservationDate, MemberResponse memberResponse, UmbrellaResponse umbrellaResponse) {
    public static ReservationResponse from(Reservation reservation) {
        MemberResponse memberDto = MemberResponse.from(reservation.getMember());
        UmbrellaResponse umbrellaDto = UmbrellaResponse.from(reservation.getUmbrella());

        return new ReservationResponse(reservation.getId(), reservation.getReservationDate(), memberDto, umbrellaDto);
    }
}
