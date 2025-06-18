package finalmission.api.v1.reservation.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import finalmission.api.v1.reservation.domain.Reservation;
import java.time.LocalDate;
import java.time.LocalTime;

public record ReservationDetailResponse(
        Long id,
        @JsonFormat(pattern = "yyyy-MM-dd") LocalDate date,
        @JsonFormat(pattern = "HH:mm") LocalTime time,
        String restaurantName,
        String restaurantAddress,
        String nickname,
        String phoneNumber
) {
    public ReservationDetailResponse(final Reservation reservation, final LocalDate date, final LocalTime time) {
        this(reservation.getId(),
                date,
                time,
                reservation.getRestaurant().getName(),
                reservation.getRestaurant().getAddress(),
                reservation.getNickname(),
                reservation.getPhoneNumber());
    }
}

