package finalmission.reservation.dto.response;

import finalmission.member.domain.User;
import finalmission.member.dto.response.UserResponse;
import finalmission.reservation.domain.Reservation;
import finalmission.umbrella.domain.Umbrella;
import finalmission.umbrella.dto.response.UmbrellaResponse;

import java.time.LocalDate;

public record ReservationResponse(long reservationId, UserResponse userResponse, UmbrellaResponse umbrellaResponse, LocalDate reservationDate) {

    public static ReservationResponse from(Reservation reservation) {
        UserResponse userResponse = new UserResponse(reservation.getUser().getId(), reservation.getUser().getEmail());
        UmbrellaResponse umbrellaResponse = new UmbrellaResponse(reservation.getUmbrella().getId());
        return new ReservationResponse(reservation.getId(), userResponse, umbrellaResponse, reservation.getReservationDate());
    }
}
