package finalmission.application.dto;

import finalmission.domain.Reservation;
import finalmission.presentation.dto.MemberDto;
import java.util.List;

public record ReservationResponse(
        MemberDto memberDto,
        YogaSessionResponse sessionServiceResponse
) {

    public static ReservationResponse from(Reservation reservation) {
        var member = reservation.getMember();
        var session = reservation.getSession();
        return new ReservationResponse(MemberDto.from(member), YogaSessionResponse.from(session));
    }

    public static List<ReservationResponse> from(List<Reservation> reservations) {
        return reservations.stream().map(ReservationResponse::from).toList();
    }
}
