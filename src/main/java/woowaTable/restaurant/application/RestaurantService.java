package woowaTable.restaurant.application;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import woowaTable.reservation.application.ReservationCommandService;
import woowaTable.restaurant.application.dto.RestaurantResponse;
import woowaTable.user.application.UserQueryService;

@Service
@RequiredArgsConstructor
public class RestaurantService {

    private final RestaurantQueryService restaurantQueryService;
    private final ReservationCommandService reservationCommandService;
    private final UserQueryService userQueryService;

    public List<RestaurantResponse> findByOption(final Long region) {
        return restaurantQueryService.findByOption(region).stream()
                .map(RestaurantResponse::from)
                .toList();
    }


}
