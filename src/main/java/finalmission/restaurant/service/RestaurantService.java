package finalmission.restaurant.service;

import finalmission.restaurant.domain.Restaurant;
import finalmission.restaurant.domain.RestaurantRepository;
import finalmission.restaurant.dto.RestaurantRequest;
import finalmission.restaurant.dto.RestaurantResponse;
import org.springframework.stereotype.Service;

@Service
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;

    public RestaurantService(final RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    public RestaurantResponse save(RestaurantRequest restaurantRequest) {
        Restaurant savedRestaurant = restaurantRepository.save(restaurantRequest.toRestaurantEntity());
        return RestaurantResponse.from(savedRestaurant);
    }

    public RestaurantResponse findById(final Long id) {
        Restaurant restaurant = restaurantRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당하는 정보의 식당이 없습니다."));
        return RestaurantResponse.from(restaurant);
    }
}
