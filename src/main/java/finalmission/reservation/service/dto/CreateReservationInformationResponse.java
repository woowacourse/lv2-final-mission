package finalmission.reservation.service.dto;

import finalmission.reservation.domain.ReservationInformation;
import finalmission.restaurant.service.RestaurantResponse;

import java.time.LocalDate;
import java.time.LocalTime;

public record CreateReservationInformationResponse(
        Long id,
        RestaurantResponse restaurantResponse,
        LocalDate date,
        LocalTime startAt,
        Integer maxCount
) {
    public static CreateReservationInformationResponse from(ReservationInformation information) {
        return new CreateReservationInformationResponse(
                information.getId(),
                RestaurantResponse.from(information.getRestaurant()),
                information.getDate(),
                information.getStartAt(),
                information.getMaxCount()
        );
    }
}
