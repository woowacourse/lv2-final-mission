package finalmission.reservation.ui.dto;

import finalmission.reservation.domain.Reservation;
import java.time.LocalDate;
import java.time.LocalTime;

public record ReservationResponse(
        Long restaurantId,
        LocalDate date,
        LocalTime time,
        String restaurantName,
        String description,
        String place,
        String phoneNumber,
        Long memberId
) {
    public static ReservationResponse from(final Reservation reservation) {
        return new ReservationResponse(
                reservation.getId(),
                reservation.getReservationSlot().getDate(),
                reservation.getReservationSlot().getTime().getStartAt(),
                reservation.getReservationSlot().getRestaurant().getName(),
                reservation.getReservationSlot().getRestaurant().getDescription(),
                reservation.getReservationSlot().getRestaurant().getPlace(),
                reservation.getReservationSlot().getRestaurant().getPhoneNumber(),
                reservation.getMember().getId()
        );
    }
}
