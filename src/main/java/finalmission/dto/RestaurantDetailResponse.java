package finalmission.dto;

import finalmission.domain.restaurant.Restaurant;
import java.util.List;

public record RestaurantDetailResponse(
        long id,
        String name,
        String address,
        String description,
        List<ScheduleResponse> schedules
) {

    public RestaurantDetailResponse(final Restaurant restaurant, final List<ScheduleResponse> scheduleResponses) {
        this(
                restaurant.getId(),
                restaurant.getName().getValue(),
                restaurant.getAddress().getValue(),
                restaurant.getDescription().getValue(),
                scheduleResponses
        );
    }
}
