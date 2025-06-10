package finalmission.reservation.service.dto;

import finalmission.reservation.domain.ReservationInformation;
import finalmission.restaurant.service.RestaurantResponse;

import java.time.LocalDate;
import java.time.LocalTime;

public record ReservationInformationResponse(
        Long id,
        RestaurantResponse restaurantResponse,
        LocalDate date,
        LocalTime startAt,
        Integer maxCount
) {
    public static ReservationInformationResponse from(ReservationInformation information) {
        return new ReservationInformationResponse(
                information.getId(),
                RestaurantResponse.from(information.getRestaurant()),
                information.getDate(),
                information.getStartAt(),
                information.getMaxCount()
        );
    }
}
