package finalmission.controller.reservation.controller.dto.response;

import finalmission.controller.member.controller.dto.response.MemberResponse;
import finalmission.controller.reservation.domain.Reservation;
import finalmission.reservationTime.controller.dto.response.ReservationTimeResponse;
import finalmission.controller.restaurant.controller.dto.response.RestaurantResponse;

import java.time.LocalDate;

public record ReservationResponse(
        long id,
        MemberResponse memberResponse,
        RestaurantResponse restaurantResponse,
        ReservationTimeResponse reservationTimeResponse,
        LocalDate date,
        int visitor,
        String status
) {
    public static ReservationResponse from(final Reservation reservation) {
        return new ReservationResponse(
                reservation.getId(),
                MemberResponse.from(reservation.getMember()),
                RestaurantResponse.from(reservation.getRestaurant()),
                ReservationTimeResponse.from(reservation.getReservationTime()),
                reservation.getDate(),
                reservation.getVisitor(),
                reservation.getStatus().toString()
        );
    }
}
