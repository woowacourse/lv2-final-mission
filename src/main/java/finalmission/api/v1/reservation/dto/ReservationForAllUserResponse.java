package finalmission.api.v1.reservation.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import finalmission.api.v1.reservation.domain.Reservation;
import java.time.LocalDate;
import java.time.LocalTime;

public record ReservationForAllUserResponse(
        Long id,
        @JsonFormat(pattern = "yyyy-MM-dd") LocalDate date,
        @JsonFormat(pattern = "HH:mm") LocalTime time,
        String restaurantName
) {
    public ReservationForAllUserResponse(final Reservation reservation) {
        this(reservation.getId(), reservation.getDate(), reservation.getReservationTime().getTime(), reservation.getRestaurant().getName());
    }
}
