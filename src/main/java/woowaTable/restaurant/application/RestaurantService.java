package woowaTable.restaurant.application;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import woowaTable.restaurant.application.dto.RestaurantResponse;

@Service
@RequiredArgsConstructor
public class RestaurantService {

    private final RestaurantQueryService restaurantQueryService;

    public List<RestaurantResponse> findByOption(final Long region) {
        return restaurantQueryService.findByOption(region).stream()
                .map(RestaurantResponse::from)
                .toList();
    }
}
