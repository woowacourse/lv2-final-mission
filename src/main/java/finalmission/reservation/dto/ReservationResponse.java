package finalmission.reservation.dto;

import finalmission.member.dto.MemberResponse;
import finalmission.reservation.domain.Reservation;
import finalmission.restaurant.dto.RestaurantResponse;

import java.time.LocalDateTime;
import java.util.List;

public record ReservationResponse(
        Long id,
        LocalDateTime reservationDateTime,
        MemberResponse memberResponse,
        RestaurantResponse restaurantResponse,
        int personnel
) {
    public static ReservationResponse from(Reservation reservation) {
        return new ReservationResponse(
                reservation.getId(),
                reservation.getReservationDateTime(),
                MemberResponse.from(reservation.getMember()),
                RestaurantResponse.from(reservation.getRestaurant()),
                reservation.getPersonnel()
        );
    }

    public static List<ReservationResponse> from(List<Reservation> reservations) {
        return reservations.stream()
                .map(ReservationResponse::from)
                .toList();
    }
}
