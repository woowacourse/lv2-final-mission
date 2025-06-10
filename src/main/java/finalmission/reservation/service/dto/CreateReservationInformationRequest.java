package finalmission.reservation.service.dto;

import finalmission.reservation.domain.ReservationInformation;
import finalmission.restaurant.domain.Restaurant;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.LocalDate;
import java.time.LocalTime;

public record CreateReservationInformationRequest(
        @NotNull
        Long restaurantId,
        @NotNull
        @FutureOrPresent
        LocalDate date,
        @NotNull
        LocalTime startAt,
        @NotNull
        @Positive
        Integer maxCount
) {
        public ReservationInformation toEntity(Restaurant restaurant) {
                return new ReservationInformation(restaurant, date, startAt, maxCount);
        }
}
