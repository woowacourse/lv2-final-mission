package finalmission.presentation.dto;

import finalmission.domain.Reservation;
import java.util.List;

public record ReservationResponse(
        long id,
        MemberResponse memberResponse,
        YogaSessionResponse sessionServiceResponse
) {

    public static ReservationResponse from(Reservation reservation) {
        var member = reservation.getMember();
        var session = reservation.getSession();
        return new ReservationResponse(reservation.getId(), MemberResponse.from(member), YogaSessionResponse.from(session));
    }

    public static List<ReservationResponse> from(List<Reservation> reservations) {
        return reservations.stream().map(ReservationResponse::from).toList();
    }
}
