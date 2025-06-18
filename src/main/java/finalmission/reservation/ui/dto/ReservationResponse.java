package finalmission.reservation.ui.dto;

import finalmission.reservation.domain.Reservation;
import java.time.LocalDate;
import java.time.LocalTime;
import lombok.Builder;

@Builder
public record ReservationResponse(
        Long id,
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
        return ReservationResponse.builder()
                .id(reservation.getId())
                .restaurantId(reservation.getReservationSlot().getRestaurant().getId())
                .date(reservation.getReservationSlot().getDate())
                .time(reservation.getReservationSlot().getTime().getStartAt())
                .restaurantName(reservation.getReservationSlot().getRestaurant().getName())
                .description(reservation.getReservationSlot().getRestaurant().getDescription())
                .place(reservation.getReservationSlot().getRestaurant().getPlace())
                .phoneNumber(reservation.getReservationSlot().getRestaurant().getPhoneNumber())
                .memberId(reservation.getMember().getId())
                .build();
    }
}
